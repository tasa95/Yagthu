/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */
module.exports = function (server) {
    server.put('/discipline/:id', server.middleware.isLoggedIn, server.middleware.isAnAdmin,server.middleware.MissingParameterModificationDiscipline, function (req, res) {

        var Discipline = server.models.ClassRoom();
        Discipline.populateForUpdate(req.body);
        server.models.Discipline.findOneAndUpdate({_id: req.params.id}, {
            $set: Discipline,
            $currentDate: {updatedAt: true}
        }, onDisciplineModified);

        function onDisciplineModified(err,data)
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