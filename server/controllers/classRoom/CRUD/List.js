/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */

module.exports = function(server) {
    server.get('/classRoom', server.middleware.isLoggedIn, server.middleware.isAnAdmin, function (req, res) {
        server.models.ClassRoom.find({}, handleQueryResponse);

        function handleQueryResponse(err, data) {

            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());

                res.status(500).send({error: "Oops Something is wrong with the server"});

                return;
            }
            else {

                res.status(200).send({data: data});
                return;
            }
        }
    });

}
