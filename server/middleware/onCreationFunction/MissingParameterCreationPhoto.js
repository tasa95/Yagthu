/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */
module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterCreationPhoto = function(req,res,next)
    {

        var Photo = server.models.Photo();
        console.log(".........\n");
        Photo.populateForCreation(req.body);
        console.log(Photo);
        console.log(Photo.length());
        console.log(Photo.length() > 0);
        console.log((Photo));

        if(Photo.length() > 0 && Photo.length() == 2)
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