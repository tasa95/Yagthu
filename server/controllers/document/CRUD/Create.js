/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */



module.exports = function(server) {
    server.post('/document',server.middleware.isLoggedIn,server.middleware.isAProfessor,server.middleware.MissingParameterCreationDocument ,function(req,res,next) {


        var Document = server.models.Document();
        Document.populateForCreation(req.body);

        Document.save(onSavingDocument);
        function onSavingDocument(err,data)
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
                next();
            }
        }
    },server.middleware.DocumentUploadFile);

    server.post('/document/upload',server.middleware.isLoggedIn,server.middleware.isAProfessor,server.middleware.DocumentUploadFile);
}
