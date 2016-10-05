/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */


module.exports = function (server) {
    server.put('/grades/:id', server.middleware.isLoggedIn,server.middleware.isAProfessor, server.middleware.MissingParameterModificationGrades ,function (req, res) {


        var grades = server.models.grades();

        grades.populateForUpdate(req.body);

        server.models.grades.findOneAndUpdate({_id: req.params.id},
            {
                $set : grades,
                $currentDate:{updatedAt:true}
            }, onGradesModified);
        return;

        function onGradesModified(err,data){

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
    });
}

