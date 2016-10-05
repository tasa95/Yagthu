/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */



var ObjectId = require('mongoose').Types.ObjectId;
var count = 0;
var i = 0 ;
module.exports = function(server) {




    server.get('/user', server.middleware.isLoggedIn, server.middleware.isAnAdmin, function (req, res) {
        var response = {};
        server.models.User.find({}, handleQueryResponse);

        function handleQueryResponse(err, data) {

            if (err) {

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                response = data;
                if( Object.prototype.toString.call( data ) === '[object Array]' ) {
                    var j = 0;
                    for(j = 0 ; j < data.length ; j++)
                    {
                        i = j;
                        server.models.Class.find({_id : data[i].iDClass },addToResponse);
                    }

                }
                else
                {
                    server.models.Class.find({_id : data.iDClass },addToResponse);
                }
                res.status(200).send({data: response});
                return;
            }
        }

        function addToResponse(err,data){

            if (err) {

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                response[i].class = data;
                return;
            }
        }
    });


    server.get('/user/attendance', server.middleware.isLoggedIn, function (req, res) {
        var response = {};
        server.models.User.find({token: req.session.token}, handleQueryResponse);

        function handleQueryResponse(err, data) {

            if (err) {
                console.log(err);
                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {

                response.user = data;
                var query = server.models.Attendance.find({});

                /*console.log(data);
                server.models.Attendance.find({},function (err,data)
                {
                    console.log(data);
                });
                */
                server.models.Attendance.find({"iDUser":new ObjectId(data[0]._id)}, getEveryAttendance);
                return;
            }
        }

        function getEveryAttendance(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {


                console.log(data);
                response.Attendance= {};
                if (!data) {
                    response.Attendance= "";
                }
                else
                {
                    if(data.constructor != Array)
                    {
                        response.Attendance[0] = data;
                        var attendance = server.models.Attendance(data);
                        server.models.Course.find({_id: new ObjectId(attendance.iDCourse)}, getEveryCourse);
                    }
                    else
                    {

                        count = data.length;
                        for(index = 0 ; index < data.length ; index++)
                        {
                            i = index;
                            response.Attendance[index] = data[index];
                            var attendance = server.models.Attendance(data[index]);
                            server.models.Course.find({_id: new ObjectId(attendance.iDCourse)}, getEveryCourse);
                        }
                    }
                }

            }

        }

        function getEveryCourse(err, data) {
            {
                if (err) {

                    res.status(500).send({error: "Oops Something is wrong with the server"});

                    return;
                }
                else {
                    if (!data) {
                        response.Course= "";
                        //res.status(200).send({data: response})
                    }
                    else {
                        response.Course = data;
                    }
                    if((i + 1 ) == count )
                    {
                        res.status(200).send({data: response});
                    }
                }
            }
        }
    });


    server.get('/user/course', server.middleware.isLoggedIn, function (req, res) {

        var response = {};

        server.models.User.find({token: req.session.token}, handleQueryResponse);

        function handleQueryResponse(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {

                response.User = data;
                var user = server.models.User(data);
                server.models.Class.find({_id: user.iDClass}, getClass)
                return;
            }
        }

        function getClass(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                if (!data) {
                    response.Class = "";
                    res.status(200).send({data: response})

                }
                else {

                    response.Class = data;
                    var myClass = server.models.Class(data);
                    console.log(myClass._id);
                    console.log(new ObjectId(myClass._id));
                    server.models.Course.find({_idClass: myClass._id}, getEveryCourse);
                }
            }

        }

        function getEveryCourse(err, data) {
            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                if (!data) {
                    response.Course = "";
                    res.status(200).send({data: response})

                }
                else {
                    response.Course = {};

                    if(data.constructor === Array)
                    {
                        var j = 0;
                        for (j = 0 ; j < data.length ; j++)
                        {
                            server.models.Discipline.find({_id: data[j].iDDiscipline}, getEveryDiscipline);
                        }
                    }
                    else
                    {
                        response.Course[i] = data;
                        server.models.Discipline.find({_id : data.iDDiscipline},getEveryDiscipline)
                    }

                    i = 0;
                    for( i = 0 ; i < response.length ; i++)
                    {
                        server.models.ClassRoom.find({_id: element.iDClassRoom}, getEveryClassRoom);
                    }


                    res.status(200).send({data: response});
                }
            }
        }

        function getEveryDiscipline(err, data) {

            if (err) {
                res.status(500).send({error: "Oops Something is wrong with the server"});
                return;
            }
            else {
                if (!data) {
                    response.Course[i].Discipline = "";
                }
                else {
                    response.Course[i].Discipline = data;
                }
            }
        }

        function getEveryClassRoom(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                if (!data) {
                    response.Course[i].ClassRoom = "";


                }
                else {
                    response.Course[i].ClassRoom = data;
                }
            }

        }


    });


    server.get('/user/document', server.middleware.isLoggedIn, function (req, res) {

        var response = {};
        server.models.User.find({token: req.session.token}, handleQueryResponse);

        function handleQueryResponse(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {

                response.User = data;
                var user = server.models.User(data);
                server.models.Class.find({_id: user.iDClass}, getClass)
                return;
            }
        }

        function getClass(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                if (!data) {
                    response.Class = "";
                    res.status(200).send({data: response})

                }
                else {
                    response.Class = data;
                    var myClass = server.models.Class(data);
                    console.log(myClass._id);
                    console.log(new ObjectId(myClass._id));
                    server.models.Course.find({_idClass: myClass._id}, getEveryCourse);
                }
            }

        }

        function getEveryCourse(err, data) {
            if (err) {
                res.status(500).send({error: "Oops Something is wrong with the server"});
                return;
            }
            else {
                if (!data) {
                    response.Course = "";
                    res.status(200).send({data: response})

                }
                else {
                    response.Course = {};

                    data.each(function addDisciplineName(element, index, array) {
                            i = index;
                            response.Course[i] = element;
                            server.models.Discipline.find({_id: element.iDDiscipline}, getEveryDiscipline);
                        }
                    );
                    i = 0;
                    response.Course.each(function addClassRoom(element, index, array) {
                        i : index;
                        server.models.ClassRoom.find({_id: element.iDClassRoom}, getEveryClassRoom);
                    });

                    i = 0;

                    res.status(200).send({data: response});
                }
            }
        }

        function getEveryDiscipline(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                if (!data) {
                    response.Course[i].Discipline = "";
                }
                else {
                    response.Course[i].Discipline = data;
                }
            }
        }

        function getEveryClassRoom(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {
                if (!data) {
                    response.Course[i].ClassRoom = "";
                }
                else {
                    response.Course[i].ClassRoom = "";
                }
            }

        }


    });
}

