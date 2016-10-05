/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */

module.exports = function(server) {
    server.delete('/document/:id',server.middleware.isLoggedIn,server.middleware.isAProfessor, function (req, res,next) {

        server.models.Document.findByIdAndRemove({_id: req.params.id},onDocumentDelete);
        function onDocumentDelete(err,data)
        {
            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());
                res.send(500,{error:"Oops Something wrong with the server"});
                return;
            }
            else {
                next()
                return;
            }
        }
    });
}