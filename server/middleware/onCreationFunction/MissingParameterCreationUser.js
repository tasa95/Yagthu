module.exports = function(server)
{
    server.middleware = server.middleware || {};
    server.middleware.MissingParameterCreationUser = function(req,res,next)
    {
        if( req.body.name && req.body.firstName && req.body.password && req.body.login  && req.body.admin != null && req.body.Professor != null)
        {
            next();
        }
        else
        {
            res.status(400).send({error : "Missing parameters"});
            return;
        }
    };
};