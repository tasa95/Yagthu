/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */


module.exports = function(server) {
    server.get('/attendance', server.middleware.isLoggedIn, server.middleware.isAnAdmin, function (req, res) {
        server.models.Attendance.find({}, handleQueryResponse);

        function handleQueryResponse(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {

                res.status(200).send({data: data});
                return;
            }
        }
    });


    server.get('/attendance/:path/:id', server.middleware.isLoggedIn, server.middleware.isAnAdmin, function (req, res) {


        if (req.params.path == "course") {
            server.models.Attendance.find({iDCourse: req.params.id}, handleQueryResponse);
        }
        else {
            if (req.params.path == "user") {
                server.models.Attendance.find({iDUser: req.params.id}, handleQueryResponse);
            }
            else {
                res.status(401).send({error: "have you lost your way ?"});
            }
        }
        server.models.Attendance.find({}, handleQueryResponse);

        function handleQueryResponse(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {

                res.status(200).send({data: data});
                return;
            }
        }
    });




    server.get('/attendance/myAttendance/', server.middleware.isLoggedIn, function (req, res) {
        server.models.User.findOne({token:req.session.token},onFindingUser)

        function onFindingUser(err,data)
        {
            if (err){
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {

                if(!data)
                {
                    res.status(404).send({error:"You are not in our database"});
                }
                else
                {
                    var User = data;
                    server.models.Attendance.find({iDUser: data._id}, handleQueryResponse);

                    function handleQueryResponse(err, data) {

                        if (err) {
                            // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                            //res.send(500, err.toString());

                            res.status(500).send({error: "Oops Something is wrong with the server"});

                            return;
                        }
                        else {

                            res.status(200).send({data: data});
                            return;
                        }
                    }
                }

                return;
            }
        }
    });

}