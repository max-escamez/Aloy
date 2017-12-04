'use strict';
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendAnswersNotification = functions.database.ref('/questions/{questionId}/answers/{answerId}').onCreate(event => {
  const answerId = event.params.answerId;
  const questionId = event.params.questionId;
  const getAuthorId = admin.database().ref(`/questions/${questionId}/username`).once('value');
  return Promise.all([getAuthorId]).then(results => {

    const authorId = results[0];
    const followers = results[1];
    const tokens = results[2];
    var a = JSON.stringify(authorId);
    a = a.substring(1, a.length-1);

    const getTokenFollower = admin.database().ref(`/questions/${questionId}/following/notificationTokens`).once('value');

    const getAnswererName = admin.database().ref(`/questions/${questionId}/answers/${answerId}/name`).once('value');
    const getAnswererId = admin.database().ref(`/questions/${questionId}/answers/${answerId}/username`).once('value');

    const getDeviceTokensPromise = admin.database().ref("/users/"+a+"/notificationTokens").once('value');
    const getQuestionBody = admin.database().ref(`/questions/${questionId}/body`).once('value');

    return Promise.all([getDeviceTokensPromise, getQuestionBody, getAnswererName, getAnswererId, getTokenFollower]).then(results => {

      const tokensSnapshot = results[0];
      const question_body = results[1];
      const answererName = results[2];
      const answererId = results[3];
      const tokens_follow = results[4];
      var t=JSON.stringify(tokensSnapshot);
      t = t.substring(1, t.length-1);
      var array=[];
      var array2=[];
      var j;
      var i;
      tokensSnapshot.forEach(function(child){
        j=child.key;
      });
      i=0;
      tokens_follow.forEach(function(child) {
        if(!(j==child.key)){
          array[i]=child.key;
          console.log("Token "+i,array[i]);
          i++;
        }
      });

      if(!answererName){
        answererId=answererName;
      }
      // Check if there are any device tokens.
      if (!tokensSnapshot.hasChildren()) {
        return console.log('There are no notification tokens to send to.');
      }

      var n = JSON.stringify(answererId);
      n = n.substring(1, n.length-1);

      // Notification details.
      const payload = {
        notification: {
          title: 'Someone answered you !',
          body: n+" answered to your question "+JSON.stringify(question_body)
        }
      };

      const payload_alt = {
        notification: {
          title: 'A question you follow has been answered !',
          body: n+" answered to the question "+JSON.stringify(question_body)
        }
      };

      // Listing all tokens.
      const tokens = Object.keys(tokensSnapshot.val());
      console.log('payload',payload);
      var token_alt;
      for (i = 0; i < array.length; i++) {
        token_alt=array[i];
        admin.messaging().sendToDevice(token_alt, payload_alt)
        console.log("payload_alt",payload_alt);
        console.log("token_alt",token_alt)
      }
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
});
