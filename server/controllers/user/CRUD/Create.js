/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */


var mongoose = require('mongoose');

var bodyParser = require('body-parser').json();
var sha1 = require('sha1');



module.exports = function(server) {
    server.post('/user',bodyParser,server.middleware.isLoggedIn,server.middleware.isAnAdmin,server.middleware.MissingParameterCreationUser ,function(req,res,next) {

        console.log("creating a user..." + req.body.name);
        var newUser = {};
        var newUser = server.models.User();
        newUser.populateForCreation(req.body);


        function TokenUpdated(err,data)
        {
            if(err)
            {
                console.log("TokenUpdated : " +err);
                res.status(500).send({error:"A problem with the server happened"});
                return;
            }
            else
            {
                console.log("TokenUpdated : " + data);

                return;
            }
        }

        //console.log(req.body);
        var newUser = server.models.User(newUser);

        newUser.save(onUserCreate);
        function onUserCreate(err, user) {
            if (err) {
                res.status(500).send({error:err.toString()});
                console.log("onUserCreate : " + err);
                return;
            }
            else {
                user.token = sha1(user._id.toString());
                req.session.token = user.token;

                console.log(user.token);
                server.models.User.findOneAndUpdate({   _id : user._id},
                    {   token : user.token},
                    TokenUpdated);

                res.status(201).send({data:user})
                //req.session.token = data.token;
            }
        }
        return;
    });
}