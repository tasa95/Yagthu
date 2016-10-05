/**
 * Created by thierryallardsaintalbin on 25/04/15.
 */
module.exports = function(server) {
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationDocument = function (req, res, next) {


        var Document = server.models.Discipline();
        console.log(".........\n");
        Document.populateForUpdate(req.body);
        console.log(Document);
        console.log(Document.length());
        console.log(Document.length() > 0);


        if (Document.length() > 0 && !Document._id) {
            next();
            return;
        }
        else {
            res.status(400).send({error: "Missing parameters"});
            return;
        }
    };
}