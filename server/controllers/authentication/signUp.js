/**
 * Created by thierryallardsaintalbin on 22/12/14.
 */
var mongoose = require('mongoose');

var bodyParser = require('body-parser').json();
var sha1 = require('sha1');



module.exports = function(server) {
    server.post('/signup',bodyParser,server.middleware.MissingParameterCreationUser,NotInBase(server), createUser(server));


}

function createUser(server)
{
    return function saveUser(req,res,next)
    {
        if (req.body.name && req.body.password) {


            console.log("creating a user..." + req.body.name);
            var newUser = {};
            newUser.name = req.body.name;
            newUser.firstName = req.body.firstName;
            if(req.body.iDPhoto)
                newUser.iDPhoto = req.body.iDPhoto;
            newUser.password = sha1(req.body.password);
            newUser.Class = req.body.class;
            newUser.login = req.body.login;
            newUser.admin = req.body.admin;
            newUser.Professor = req.body.Professor;



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
                    res.status(500).send({errorMessage:err.toString()});
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
        }
        else {
            res.send(401, {errorMessage:"need password, name and mail"});
        }
    };
}




function NotInBase(server) {

    return function (req,res,next)
    {

        var bool = server.models.User.findOne({
            login: req.body.login,
            password:sha1(req.body.password)
        }, handleQueryResponse);

        function handleQueryResponse(err,user){
            if(err){
                res.status(500).send({error:"Something went wrong"});

                return ;
            }
            if(!user)
            {
                next();
                return;
            }
            else
            {
                res.status(403).send({error:"there is a user registered in the database with these information"});
                return;
            }
        }
    }
}



