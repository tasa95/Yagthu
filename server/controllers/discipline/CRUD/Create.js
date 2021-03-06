/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */



module.exports = function(server) {
    server.post('/discipline',server.middleware.isLoggedIn,server.middleware.isAnAdmin,server.middleware.MissingParameterCreationDiscipline ,function(req,res,next) {


        var Discipline = server.models.Discipline();
        Discipline.populateForCreation(req.body);

        Discipline.save(onSavingDiscipline);

        function onSavingDiscipline(err,data)
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