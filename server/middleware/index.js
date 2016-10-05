/**
 * Created by thierryallardsaintalbin on 22/12/14.
 */
module.exports = function(server) {
    require('./isLoggedIn.js')(server);
    require('./isAnEmail.js')(server);
    require('./onCreationFunction')(server);
    require('./onModificationFunction')(server);
    require('./IDSmartphoneExists.js')(server);
    require('./isAnAdmin.js')(server);
    require('./isAProfessor.js')(server);
    require('./DeleteFile.js')(server);
    require('./UploadFile.js')(server);
    require('./documentUploadFile.js')(server);
    require('./documentDeleteFile.js')(server);
};
