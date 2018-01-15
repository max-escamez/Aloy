'use strict';

// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');
// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


/* Triggers when a user gets a new request and sends a notification.
 * Requesters add a flag to `/users/{userId}/requests/{questionId}/requesters/{requesterId}`.
 * Users save their device notification tokens to `/users/{userId}/notificationTokens/{notificationToken}`.
 */
exports.sendRequestNotification = functions.database.ref('/users/{userId}/requests/{questionId}/requesters/{requesterId}').onWrite(event => {
  const userId = event.params.userId;
  const questionId = event.params.questionId;
  const requesterId = event.params.requesterId;

  // Get the list of device notification tokens.
  const getDeviceTokensPromise = admin.database().ref(`/users/${userId}/notificationTokens`).once('value');
  const getQuestionBody = admin.database().ref(`/questions/${questionId}/body`).once('value');

  // Get the requester profile.
  //Maybe problem here just get the key..
  const getRequesterId = admin.database().ref(`/users/${requesterId}/id`).once('value');
  const getRequesterName = admin.database().ref(`/users/${requesterId}/name`).once('value');


  return Promise.all([getDeviceTokensPromise, getRequesterId, getQuestionBody,getRequesterName]).then(results => {
    const tokensSnapshot = results[0];
    const requesterId = results[1];
    const questionBody = results[2];
    const requesterName = results[3];

    if(!requesterName){
      requesterId=requesterName;
    }
    // Check if there are any device tokens.
    if (!tokensSnapshot.hasChildren()) {
      return console.log('There are no notification tokens to send to.');
    }
    var n = JSON.stringify(requesterId);
    n = n.substring(1, n.length-1);
    // Notification details.
    const payload = {
      notification: {
        title: 'You\'ve been requested !',
        body: n+" requested your answer on "+JSON.stringify(questionBody)
      }
    };

    // Listing all tokens.
    const tokens = Object.keys(tokensSnapshot.val());
    console.log('payload',payload);
    // Send notifications to all tokens.
    return admin.messaging().sendToDevice(tokens, payload).then(response => {
      // For each message check if there was an error.
      const tokensToRemove = [];
      response.results.forEach((result, index) => {
        const error = result.error;
        if (error) {
          console.error('Failure sending notification to', tokens[index], error);
          // Cleanup the tokens who are not registered anymore.
          if (error.code === 'messaging/invalid-registration-token' ||
              error.code === 'messaging/registration-token-not-registered') {
                console.log(error.code);
            tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
          }
        }
      });
      return Promise.all(tokensToRemove);
    });
  });
});
