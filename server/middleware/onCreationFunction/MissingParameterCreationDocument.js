/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */

module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterCreationDocument= function(req,res,next)
    {
        var Document = server.models.Document();
        console.log(".........\n");
        Document.populateForCreation(req.body);
        console.log(Document);
        console.log(Document.length());
        console.log(Document.length() > 0);
        console.log((Document));
        if(Document.length() > 0 && Document.name && Document.iDCourse)
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