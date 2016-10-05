/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */

module.exports = function(server) {
    server.delete('/attendance/:_id',server.middleware.isLoggedIn,server.middleware.isAnAdmin, function (req, res) {

        server.models.Attendance.findByIdAndRemove({_id: req.params.id},onAttendanceDelete);

        function onAttendanceDelete(err,data)
        {
            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());
                res.send(500,{error:"Oops Something wrong with the server"});
                return;
            }
            else {
                res.send(202, {data : data });
                return;
            }
        }
    });
}