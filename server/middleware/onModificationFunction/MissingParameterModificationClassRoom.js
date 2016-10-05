/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */


module.exports = function(server) {
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationClassRoom = function (req, res, next) {


        var ClassRoom = server.models.ClassRoom();
        console.log(".........\n");
        ClassRoom.populateForUpdate(req.body);
        console.log(ClassRoom);
        console.log(ClassRoom.length());
        console.log(ClassRoom.length() > 0);


        if (ClassRoom.length() > 0 && !ClassRoom._id) {
            next();
            return;
        }
        else {
            res.status(400).send({error: "Missing parameters"});
            return;
        }
    };;
}