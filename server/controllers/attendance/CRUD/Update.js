/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */

module.exports = function (server) {
    server.put('/attendance/:id', server.middleware.isLoggedIn, server.middleware.isAnAdmin, server.middleware.MissingParameterModificationAttendance, function (req, res) {

        var attendance = server.models.Attendance();

        attendance.populateForUpdate(req.body);
        server.models.Attendance.findOneAndUpdate({_id: req.params.id}, {
            $set: attendance,
            $currentDate: {updatedAt: true}
        }, onAttendanceModified);

        function onAttendanceModified(err,data)
        {
            if(err)
            {
                res.status(500).status({error : "Error on our server\nPlease wait"});
                return;
            }
            else
            {
                if(data == null)
                {
                    res.status(400).send({error : "No Attendance corresponding"});

                }
                else {
                    res.status(200).send({data: data});
                }
                return;
            }
        }
    });
}

