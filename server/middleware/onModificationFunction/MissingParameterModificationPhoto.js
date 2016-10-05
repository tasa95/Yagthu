/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */
module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationPhoto = function(req,res,next)
    {

        var Photo = server.models.Photo();
        console.log(".........\n");
        Photo.populateForUpdate(req.body);
        console.log(Photo);
        console.log(Photo.length());
        console.log(Photo.length() > 0);

        if(Photo.length() > 0 && !Photo.path && !Photo.name )
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