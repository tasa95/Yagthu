/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */

module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterCreationDiscipline = function(req,res,next)
    {
        var Discipline = server.models.Discipline();
        console.log(".........\n");
        Discipline.populateForCreation(req.body);
        console.log(Discipline);
        console.log(Discipline.length());
        console.log(Discipline.length() > 0);
        console.log((Discipline));

        if(Discipline.length() > 0 && Discipline.name)
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