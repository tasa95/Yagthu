/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */
var uploadManager = require('../../../uploadManager.js');
module.exports = function(server) {

    server.delete('/Photo/file/:id',server.middleware.isLoggedIn,server.middleware.isAnAdmin,server.middleware.MissingParameterCreationPhoto,function(req,res,next) {

        var Photo = {};

        Photo._id = req.params.id;


        var newPhoto = server.models.iDPhoto(Photo);

        newPhoto.findOneAndRemove(({_id:Photo._id}),onPhotoRemove);
        function onPhotoRemove(err,data)
        {
            if (err) {
                res.status(500).send({errorMessage:err.toString()});
                console.log("onPhotoCreate : " + err);
                return;
            }
            else
            {
                console.log(data.json())
                next();
            }

        }

        return;
    });

    server.delete('/Photo/file/delete/:id',server.middleware.isLoggedIn,server.middleware.isAnAdmin,server.middleware.DeleteFile)



}