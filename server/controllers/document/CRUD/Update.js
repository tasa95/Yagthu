/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */
module.exports = function (server) {
    server.put('/document/:id', server.middleware.isLoggedIn,server.middleware.isAProfessor, server.middleware.MissingParameterModificationDocument,function (req, res) {

        var Document = server.models.Document();
        Document.populateForUpdate(req.body);
        server.models.Document.findOneAndUpdate({_id: req.params.id}, {
            $set: Document,
            $currentDate: {updatedAt: true}
        }, onDocumentModified);

        function onDocumentModified(err,data)
        {
            if(err)
            {
                res.status(500).status({error : "Error on our server\nPlease wait"});
                return;
            }
            else
            {
                if(data == null)
                {
                    res.status(400).send({error : "No Attendance corresponding"});

                }
                else {
                    res.status(200).send({data: data});
                }
                return;
            }
        }
    });
}