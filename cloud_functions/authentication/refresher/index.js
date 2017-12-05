var app = require('express')();
var SpotifyWebApi = require('spotify-web-api-node');
var bodyParser = require('body-parser');
var admin = require("firebase-admin");

admin.initializeApp({
  credential: admin.credential.cert({
    projectId: "YOUR_PROJECT_ID",
    clientEmail: "YOUR_CLIENT_EMAIL",
    private_key: "-----BEGIN PRIVATE KEY-----\nYOUR_PRIVATE_KEY\n-----END PRIVATE KEY-----\n"
  }),
  databaseURL: "YOUR_DATABASE_URL"
});

exports.refresher = function auth (req,res){

  var credentials = {
    clientId : 'YOUR_CLIENT_ID',
    clientSecret : 'YOUR_CLIENT_SECRET',
    redirectUri : 'YOUR_REDIRECT_URI'
  };

  var spotifyApi = new SpotifyWebApi(credentials);

  spotifyApi.setRefreshToken(req.body.message);
  spotifyApi.refreshAccessToken()
     .then(function(refdata) {

      spotifyApi.setAccessToken(refdata.body['access_token']);

      spotifyApi.getMe().then(function(data) {

        admin.auth().createCustomToken(data.body.id)
  		.then(function(customToken) {
          	var j = {}
			var key = 'spotify';
            var key2 = 'firebase';
			j[key] = [];
            j[key2] = [];
            j[key].push(refdata.body['access_token']);
            j[key2].push(customToken);

          	res.contentType('application/json');
      		res.send(JSON.stringify(j));
  	  	})
  	  	.catch(function(error) {
    		console.log("Error creating custom token:", error);
  	  	});
      });

      }, function(err) {
        console.log('Could not refresh the access token!', err.message);
      });

};
