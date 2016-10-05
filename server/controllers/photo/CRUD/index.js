/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */

module.exports = function(server){
    require('./Create.js')(server);
    require('./Delete.js')(server);
    require('./List.js')(server);
    require('./Update.js')(server);
};