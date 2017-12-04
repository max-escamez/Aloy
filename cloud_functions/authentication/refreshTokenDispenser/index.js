var app = require('express')();
var SpotifyWebApi = require('spotify-web-api-node');
var bodyParser = require('body-parser');

exports.refreshTokenDispenser = function auth(req, res) {

  var credentials = {
    clientId : 'YOUR_CLIENT_ID',
    clientSecret : 'YOUR_CLIENT_SECRET',
    redirectUri : 'YOUR_REDIRECT_URI'
  };

  var spotifyApi = new SpotifyWebApi(credentials);

  spotifyApi.authorizationCodeGrant(req.body.message)
 	     .then(function(data) {
      
   	res.contentType('application/json');
   	res.send(JSON.stringify(data.body['refresh_token']));

   }, function(err) {
        	console.log('Authorization Code Grant Flow ERROR: ', err);
  	});

};
