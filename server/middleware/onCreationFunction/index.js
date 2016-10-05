/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */
module.exports = function(server) {
    require("./MissingParameterCreationAttendance.js")(server);
    require('./MissingParameterCreationClass.js')(server);
    require('./MissingParameterCreationClassRoom.js')(server);
    require('./MissingParameterCreationCourse.js')(server);
    require('./MissingParameterCreationDiscipline.js')(server);
    require('./MissingParameterCreationDocument.js')(server);
    require('./MissingParameterCreationPhoto.js')(server);
    require('./MissingParameterCreationUser.js')(server);
    require('./MissingParameterCreationGrades.js')(server);
};