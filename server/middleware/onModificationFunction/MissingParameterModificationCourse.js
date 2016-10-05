/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */
module.exports = function(server) {
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationCourse = function (req, res, next) {


        var Course = server.models.Course();
        console.log(".........\n");
        Course.populateForUpdate(req.body);
        console.log(Course);
        console.log(Course.length());
        console.log(Course.length() > 0);


        if (Course.length() > 0 && !Course._id) {
            next();
            return;
        }
        else {
            res.status(400).send({error: "Missing parameters"});
            return;
        }
    };
};