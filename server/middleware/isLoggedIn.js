/**
 * Created by thierryallardsaintalbin on 22/12/14.
 */

module.exports = function(server)
{

    server.middleware = server.middleware || {};

    server.middleware.isLoggedIn = function (req, res, next)
    {

        if( !req.session.token){
            res.status(401).send({error:"You must be authenticated"});
            return;
        }
        else
        {

            server.models.User.findOne({token : req.session.token},handleQueryResponse);

            function handleQueryResponse(err,data)
            {
                if(err)
                {
                    //res.send(500,{error:"Something went wrong"},err);
                    res.status(500).send({error:"Something went wrong"});
                    return ;
                }
                else {
                    if (!data)
                    {
                        res.status(404).send({error: "try again"});
                        return;
                    }
                    else
                    {
                        next();
                    }
                }
            }

        }
    }
};


