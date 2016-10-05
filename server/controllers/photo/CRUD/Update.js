/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */


module.exports = function (server) {
    server.put('/Photo/file/:id', server.middleware.isLoggedIn, server.middleware.MissingParameterModificationPhoto ,function (req, res) {


        var Photo = server.models.Photo();

        Photo.populate(req.body);

        server.models.Photo.findOneAndUpdate({_id: req.params.id},
            {
                $set : Photo,
                $currentDate:{updatedAt:true}
            }, onUserModified);
        return;

        function onUserModified(err,data){

            if(err)
            {
                res.status(500).status({error : "Error on our server\nPlease wait"});
                return;
            }
            else
            {
                if(data == null)
                {
                    res.status(400).send({error : "No user corresponding"});

                }
                else {
                        res.status(200).send({data :data});
                }
                return;
            }

        }


        if(req.params.path == "userToken")
        {
            if (req.params.Credential == req.session.token)
                server.models.User.findOne({token: req.session.token}, onUserModified);
            else
                res.status(401).send({error: "Unauthorized action"});
            return;
        }
        else
        {
            if(req.params.path == "userId")
            {
                server.models.User.findOne({token: req.session.token}, onVerificationAdministrator);

            }
            else
            {
                res.status(401).send({error:"have you lost your way ?"});
            }
        }



//#############################################################################################################################
        function onUserModified(err,data){

            if(err)
            {
                res.status(500).status({error : "Error on our server\nPlease wait"});
                return;
            }
            else
            {
                if(data == null)
                {
                    res.status(400).send({error : "No user corresponding"});

                }
                else {
                    res.status(200).send({data: data});
                }
                return;
            }

        }



        function onVerificationAdministrator(err,data)
        {

            if(err)
            {
                res.status(500).send({error: "Error on our server\nPlease wait"});
            }
            else
            {
                if(data.admin == 0)
                {
                    res.status(401).send({error : "Your are not authorized"})
                    return;
                }
                else
                {

                    var newUser = server.models.User();

                    newUser.populate(req.body);


                    server.models.User.findOneAndUpdate({_id: req.params.Credential},
                        {
                            $set : newUser,
                            $currentDate:{updatedAt:true}
                        }, onUserModified);
                    return;
                }
            }
        }



    });
}

