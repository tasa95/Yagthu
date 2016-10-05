/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */


module.exports = function(server) {
    server.post('/class',server.middleware.isLoggedIn,server.middleware.isAnAdmin,server.middleware.MissingParameterCreationClass ,function(req,res,next) {


        var newClass = server.models.Class();
        newClass.populateForCreation(req.body);
        newClass.save(onSavingClass);
        function onSavingClass(err,data)
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