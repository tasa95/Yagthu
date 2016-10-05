/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */

module.exports = function (server) {
    server.put('/course/:id', server.middleware.isLoggedIn, server.middleware.isAnAdmin, server.middleware.MissingParameterModificationCourse, function (req, res) {

        var myCourse = server.models.Course();

        myCourse.populateForUpdate(req.body);
        server.models.Course.findOneAndUpdate({_id: req.params.id}, {
            $set: myCourse,
            $currentDate: {updatedAt: true}
        }, onCourseModified);
        function onCourseModified(err,data)
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
};