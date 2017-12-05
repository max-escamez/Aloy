'use strict';
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendAchievementNotification = functions.database.ref('/users/{userId}/achievements/{achievement}').onWrite(event => {
  const userId = event.params.userId;
  const achievement = event.params.achievement;
  const getDeviceTokensPromise = admin.database().ref(`/users/${userId}/notificationTokens`).once('value');
  const getAchievementValue = admin.database().ref(`/users/${userId}/achievements/${achievement}`).once('value');

  return Promise.all([getDeviceTokensPromise, getAchievementValue]).then(results => {
    const tokensSnapshot = results[0];
    const achievement_value = results[1];
    var a = JSON.stringify(achievement_value);
    a = a.substring(1, a.length-1);
    var h=achievement_value.key;

    if (!tokensSnapshot.hasChildren()) {
      return console.log('There are no notification tokens to send to.');
    }
    console.log("achievement",h);
    console.log("value",a);

    var helper="";
    if(a=="1"){
      helper="1";
    }else if(a=="5"){
      helper="5";
    }else if(a=="15"){
      helper="15";
    }else if(a=="30"){
      helper="30";
    }else{
      return "not yet";
    }

    var msg="";
    if(h=="questions"){
      if(helper=="1"){
        msg="You asked "+helper+" question !";
      }else{
       msg="You asked "+helper+" questions !";
      }
    }else if(h=="answers"){
      if(helper=="1"){
        msg="You answered "+helper+" question !";
      }else{
        msg="You answered "+helper+" questions !";
      }
    }else if(h=="requests"){
      msg="You requested "+helper+" people !";
    }else if(h=="answersVIP"){
      if(helper=="1"){
        msg="You got "+helper+" answer on your questions !";
      }else{
        msg="You got "+helper+" answers on your questions !";
      }
    }else if(h=="upvotesVIP"){
      if(helper=="1"){
        msg="You got "+helper+" upvote on your answers !";
      }else{
        msg="You got "+helper+" upvotes on your answers !";
      }
    }else if(h=="requestsVIP"){
      if(helper=="1"){
        msg="You got requested "+helper+" time !";
      }else{
        msg="You got requested "+helper+" times !";
      }
    }else if(h=="followersVIP"){
      msg="Your questions are followed by "+helper+" people !";
    }else if(h=="followersTOP"){
      msg="One of your question is followed by "+helper+" people !";
    }else if(h=="answersTOP"){
      if(helper=="1"){
        msg="One of your question has reached "+helper+" answer !";
      }else{
        msg="One of your question has reached "+helper+" answers !";
      }
    }else if(h=="upvotesTOP"){
      if(helper=="1"){
        msg="One of your answer has reached "+helper+" upvote !";
      }else{
        msg="One of your answer has reached "+helper+" upvotes !";
      }
    }

    const payload = {
      notification: {
        title: 'You gain an achievement !',
        body: msg
      }
    };
    const tokens = Object.keys(tokensSnapshot.val());
    console.log('payload',payload);
    return admin.messaging().sendToDevice(tokens, payload).then(response => {
      const tokensToRemove = [];
      response.results.forEach((result, index) => {
        const error = result.error;
        if (error) {
          console.error('Failure sending notification to', tokens[index], error);
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
