/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */
/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */


var mongoose = require('mongoose');

var bodyParser = require('body-parser').json();
var sha1 = require('sha1');



module.exports = function(server) {
    server.post('/attendance',bodyParser,server.middleware.isLoggedIn,server.middleware.isAnAdmin,server.middleware.MissingParameterCreationAttendance ,function(req,res,next) {


        var attendance = server.models.Attendance();
        attendance.populateForCreation(req.body);
        attendance.save(onSavingAttendance)


        function onSavingAttendance(err,data)
        {
            if(err)
            {
                console.log("error : " +err);
                res.status(500).send({error:"A problem with the server happened"});
                return;
            }
            else
            {
                console.log("data : " + data);
                res.status(201).send({data:data})
                return;
            }
        }
    });
}