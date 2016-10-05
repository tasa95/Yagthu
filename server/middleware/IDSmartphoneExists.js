/**
 * Created by thierryallardsaintalbin on 26/04/15.
 */
 var sha1 =require("sha1");
module.exports = function(server)
{

    server.middleware = server.middleware || {};

    server.middleware.iDSmartphoneExists = function (req, res, next) {
        /**
         * get User from the body
         * @param err
         * @param data
         */
        function getUser(err, data) {
            if (err) {
                res.status(500).send({error: "Something went wrong"});
                return;
            }
            else {
                if (!data) {
                    server.models.User.findOne({
                        login: req.body.login,
                        password: sha1(req.body.password)
                    }, compareWithOtherIdSmartphone);

                    return;
                }
                else {
                    next();
                }
            }
        }

        /**
         * Verify if current user is an admin if he is an admin he can log without using iDSmartphone
         * @param err
         * @param data
         */
        function verificationAdmin(err, data) {
            if (err) {
                res.status(500).send({error: "Something went wrong"});
                return;
            }
            else {
                if (!data) {
                    res.status(404).send({error: "try again"});
                    return;
                }
                else {
                    if (data.admin == 0) {
                        res.status(401).send({error: "Your are not authorized"});
                        return;
                    }
                    else {
                        next();
                    }
                }
            }
        }

        /**
         * Compare if the req.body.iDSmartphone == currentUser.iDSmartphone
         * if the iDSmartphone is not the same the function delete the the req.body.iDSmartphone from the database, in case of a Smartphone loan between students
         * and update the currentUser.iDSmartphone with the req.body.iDSmartphone
         * @param err
         * @param data
         */
        function compareWithOtherIdSmartphone(err, data) {

            if (err) {
                res.status(500).send({error: "Something went wrong"});
                return;
            }
            else {
                if (!data) {
                    res.status(401).send({error: "not in the base"});
                    return;
                }
                else {
                    if (data.iDSmartphone != req.body.iDSmartphone) {
                        server.models.User.update({iDSmartphone: req.body.iDSmartphone}, {iDSmartphone: ""}, DeleteNewIdSmartphone);
                        server.models.User.update({_id: data._id}, {iDSmartphone: req.body.iDSmartphone}, UpdateNewIdSmartphone);
                        next();
                        return;
                    }
                    else {
                        next();
                        return;
                    }
                }
            }
        }

        function DeleteNewIdSmartphone(err, data) {
            if (err) {
                res.status(500).send({error: "Something went wrong"});
                return;
            }
            else {
                if (!data) {
                    return;
                }
                else {
                    var userModified = server.models.User(data);
                    console.log(userModified.toJSON());
                    console.log("\n");
                    console.log("has been modified");
                    return;
                }
            }
        }


        function UpdateNewIdSmartphone(err, data) {
            if (err) {
                res.status(500).send({error: "Something went wrong"});
                return;
            }
            else {
                if (!data) {
                    return;
                }
                else {
                    var userModified = server.models.User(data);
                    console.log(userModified.toJSON());
                    console.log("\n");
                    console.log("has been modified");
                    return;
                }
            }
        }

        /*####################################################################################################*/
        if (!req.body.iDSmartphone) {

            var user = server.models.User();
            user.login = req.body.login;
            user.password = sha1(req.body.password);
            server.models.User.findOne({login: user.login, password: user.password}, verificationAdmin);

        }
        else {
            server.models.User.findOne({
                iDSmartphone: req.body.iDSmartphone,
                login: req.body.login,
                password: sha1(req.body.password)
            }, getUser);

        }
    }

}

