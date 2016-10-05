/**
 * Created by thierryallardsaintalbin on 26/06/15.
 */


module.exports = function(server) {
// Add headers
    server.middleware = server.middleware || {};
    server.middleware.ensureAuthorized = function ensureAuthorized(req, res, next) {
        var bearerToken;
        var bearerHeader = req.headers["authorization"];
        if (typeof bearerHeader !== 'undefined') {
            var bearer = bearerHeader.split(" ");
            bearerToken = bearer[1];
            req.token = bearerToken;
            next();
        } else {
            res.send(403);
        }
    }
};