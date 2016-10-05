/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */

module.exports = function(server) {
    server.post('/course',server.middleware.isLoggedIn,server.middleware.isAnAdmin ,server.middleware.MissingParameterCreationCourse,function(req,res,next) {


        var course = server.models.Course();
        course.populateForCreation(req.body);

        course.save(onSavingCourse);

        function onSavingCourse(err,data)
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