/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */




module.exports = function(server){
    require('./Create.js')(server);
    require('./Delete.js')(server);
    require('./List.js')(server);
    require('./Update.js')(server);
};
