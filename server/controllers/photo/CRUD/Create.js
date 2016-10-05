/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */

var uploadManager = require('../../../uploadManager.js');
module.exports = function(server) {

        server.post('/Photo',server.middleware.isLoggedIn,server.middleware.MissingParameterCreationPhoto,function(req,res,next) {



            var relativePath = uploadManager.uploadDir.replace("./","/");
            var Photo = {};
            Photo.path = window.location.pathname + relativePath
            Photo.name = req.body.name;
            Photo.statut = req.body.statut;
            var newPhoto = server.models.iDPhoto(Photo);

            newPhoto.save(onPhotoCreate);
            function onPhotoCreate(err,data)
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


    server.post('/Photo/upload',server.middleware.isLoggedIn,server.middleware.UploadFile);





}