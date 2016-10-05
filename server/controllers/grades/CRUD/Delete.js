/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */
var uploadManager = require('../../../uploadManager.js');
module.exports = function(server) {

    server.delete('/grades/:id',server.middleware.isLoggedIn,server.middleware.isAProfessor,function(req,res,next) {

        var grades = {};

        grades._id = req.params.id;


        var grades = server.models.grades(grades);

        grades.findOneAndRemove(({_id:grades._id}),onPhotoRemove);
        function onPhotoRemove(err,data)
        {
            if (err) {
                res.status(500).send({errorMessage:err.toString()});
                console.log("onGrades : " + err);
                return;
            }
            else
            {
                console.log(data.json())
                next();
            }

        }

        return;
    });

}