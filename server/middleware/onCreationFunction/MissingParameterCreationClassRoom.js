/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */

module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterCreationClassRoom = function(req,res,next)
    {
        var ClassRoom = server.models.ClassRoom();
        console.log(".........\n");
        ClassRoom.populateForCreation(req.body);
        console.log(ClassRoom);
        console.log(ClassRoom.length());
        console.log(ClassRoom.length() > 0);
        console.log((ClassRoom));

        if(ClassRoom.length() > 0 && ClassRoom.name)
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
