/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */

module.exports = function(server) {
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationClass = function (req, res, next) {


        var Class = server.models.Class();
        console.log(".........\n");
        Class.populateForUpdate(req.body);
        console.log(Class);
        console.log(Class.length());
        console.log(Class.length() > 0);


        if (Class.length() > 0 && !Class._id) {
            next();
            return;
        }
        else {
            res.status(400).send({error: "Missing parameters"});
            return;
        }
    };
}