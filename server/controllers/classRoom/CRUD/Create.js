/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */


module.exports = function(server) {
    server.post('/classRoom',
        server.middleware.isLoggedIn,
        server.middleware.isAnAdmin,
        server.middleware.MissingParameterCreationClassRoom ,
        function(req,res,next) {


        var newClassRoom = server.models.Class();
        newClassRoom.populateForCreation(req.body);

        newClassRoom.save(onSavingClassRoom)
        function onSavingClassRoom(err,data)
        {
            if(err)
            {
                console.log("error : " +err);
                res.status(500).send({error:"A problem with the server happened"});
                return;
            }
            else
            {
                console.log("data : " + data);
                res.status(201).send({data:data})
                return;
            }
        }
    });
}