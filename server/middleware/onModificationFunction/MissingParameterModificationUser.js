/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */
module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterModificationUser = function(req,res,next)
    {




        var newUser = server.models.User();
        console.log(".........\n");
        newUser.populateForUpdate(req.body);
        console.log(newUser);
        console.log(newUser.length());
        console.log(newUser.length() > 0);
        console.log((!newUser.token));

        if(newUser.length() > 0 && (!newUser.token))
        {
            next();
            return;
        }
        else
        {
            res.status(400).send({error : "Missing parameters"});
            return;
        }
    };
};