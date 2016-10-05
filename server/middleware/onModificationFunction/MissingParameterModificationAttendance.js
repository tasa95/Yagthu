/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */


module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationAttendance = function(req,res,next)
    {




        var Attendance = server.models.Attendance();
        console.log(".........\n");
        Attendance.populateForUpdate(req.body);
        console.log(Attendance);
        console.log(Attendance.length());
        console.log(Attendance.length() > 0);


        if(Attendance.length() > 0 && !Attendance._id )
        {
            next();
            return;
        }
        else
        {
            res.status(400).send({error : "Missing parameters"});
            return;
        }
    };
};
