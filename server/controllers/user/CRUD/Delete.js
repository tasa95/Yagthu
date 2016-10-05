/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */

module.exports = function(server) {
    server.delete('/user/:id',server.middleware.isLoggedIn,server.middleware.isAnAdmin, function (req, res) {

        function onUserRemove(err, data) {
            if (err) {
                // mauvaise pratique d'envoyer l'erreur comme cela il faut le remplacer par un message générique
                //res.send(500, err.toString());
                res.send(500,{error:"Oops Something wrong with the server"});
                return;
            }
            else {
                server.totalUser = server.totalUser.filter(function (user) {
                    return(user != req.body.id);
                });
                res.send(202, {data : server.totalUser });
                return;
            }
        }

//######################################################################################################################

            if(req.params.id)
            {
                User._id = req.params.id
                User = new server.Models.User(User);
                server.models.User.findOneAndRemove({_id :User._id},onUserRemove)
            }
            else
            {
                res.status(500).send({error : "Missing ressources"});
            }
    });
}
