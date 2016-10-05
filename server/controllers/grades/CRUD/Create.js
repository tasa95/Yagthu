/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */

var uploadManager = require('../../../uploadManager.js');
module.exports = function(server) {

        server.post('/grades',server.middleware.isLoggedIn,server.middleware.MissingParameterCreationGrades,function(req,res,next) {


            var grades = server.models.grades();
            grades.populateForCreation(req.body);

            grades.save(onSavingGrades);

            function onSavingGrades(err,data)
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