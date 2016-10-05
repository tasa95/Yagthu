/**
 * Created by thierryallardsaintalbin on 29/04/15.
 */

module.exports = function(server) {
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationGrades = function (req, res, next) {

        var grades = server.models.grades();
        console.log(".........\n");
        grades.populateForUpdate(req.body);
        console.log(grades);
        console.log(grades.length());
        console.log(grades.length() > 0);

        if (grades.length() > 0 && !grades.iDDiscipline && !grades.name && !grades.iDUser && !grades.grades) {
            next();
            return;
        }
        else {
            res.status(400).send({error: "Missing parameters"});
            return;
        }
    };
};