/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */

module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterCreationCourse= function(req,res,next)
    {
        var Course = server.models.Course();
        console.log(".........\n");
        Course.populateForCreation(req.body);
        console.log(Course);
        console.log(Course.length());
        console.log(Course.length() > 0);
        console.log((Course));

        if(Course.length() > 0 && Course.name && Course.startDate && Course.endDate && Course.iDDiscipline && Course.occured != null && Course.iDClassRoom && Course.isAConference != null)
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