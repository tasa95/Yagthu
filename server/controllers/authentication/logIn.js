var sha1 = require('sha1');
var bodyParser = require('body-parser').json();

/**
 * Created by thierryallardsaintalbin on 22/12/14.
 */

module.exports = function (server) {


    /**
     * get
     * Gets signin page
     */

    server.get('/login', function (req, res)
    {
        //console.log(req.session);
        res.status('200').send(req.session);
    });

    /**
     * post
     * signs in application
     */

    server.post('/login' ,function (req,res){
        //console.log("General : " + req.body);
        if(req.body.token && !req.body.login && !req.body.password)
        {
            console.log("token : " + req.body.token);
              server.models.User.findOne({
                token: req.body.token
            }, handleQueryResponse);
        }
        else
        {
            if(req.body.login && req.body.password && req.body.login.length !=0 && req.body.password.length != 0 ) {

                server.models.User.findOne({
                    login: req.body.login,
                    password: sha1(req.body.password)
                }, handleQueryResponse);
            }
            else {
                res.status(400).send({error : "Missing parameters"});
            }


        }


        //##############################################################################################################
        function handleQueryResponse(err,user){
            if(err){
                res.send(500,{errorMsg:"Something went wrong"},err);
                return ;
            }
            if(!user)
            {
                // On recupere ici une 401 si l'utilisateur a mal saisi son login ou son mot de passe
                res.send(401,{errorMsg:"you are not registered in the database"});
                return ;
            }
            else
            {

                console.log(user);
                req.session.token = user.token;
                req.session._id = user._id;

                res.status(202).send({data : {token : user.token }, Message : "You are login"});
                return ;
            }
        }
    });

}
