/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */
module.exports = function(server) {

    require('./MissingParameterModificationAttendance.js')(server);
    require('./MissingParameterModificationClass.js')(server);
    require('./MissingParameterModificationClassRoom.js')(server);
    require('./MissingParameterModificationCourse.js')(server);
    require('./MissingParameterModificationDiscipline.js')(server);
    require('./MissingParameterModificationDocument.js')(server);
    require('./MissingParameterModificationUser.js')(server);
    require('./MissingParameterModificationPhoto.js')(server);
    require('./MissingParameterModificationGrades.js')(server);
};