/**
 * Created by thierryallardsaintalbin on 15/04/15.
 */

// config the uploader
var options = {
    tmpDir:  __dirname + 'public/uploaded/tmp',
    publicDir: __dirname + 'public/uploaded',
    uploadDir: __dirname + 'public/uploaded/files',
    uploadUrl:  '/uploaded/files/',
    maxPostSize: 11000000000, // 11 GB
    minFileSize:  1,
    maxFileSize:  10000000000, // 10 GB
    acceptFileTypes:  /.+/i,
    // Files not matched by this regular expression force a download dialog,
    // to prevent executing any scripts in the context of the service domain:
    inlineFileTypes:  /\.(gif|jpe?g|png)$/i,
    imageTypes:  /\.(gif|jpe?g|png)$/i,
    imageVersions: {
        maxWidth:  80,
        maxHeight: 80
    },
    accessControl: {
        allowOrigin: '*',
        allowMethods: 'OPTIONS, HEAD, GET, POST, PUT, DELETE',
        allowHeaders: 'Content-Type, Content-Range, Content-Disposition'
    },
    nodeStatic: {
        cache:  3600 // seconds to cache served files
    }
};

var uploader = require('blueimp-file-upload-expressjs')(options);



//var uploadManager = require('../uploadManager.js');
module.exports = function(server) {
    server.middleware = server.middleware || {};
    server.middleware.UploadFile = function(req,res)
    {
        uploader.post(req, res, function (obj) {
            res.send({data:JSON.stringify(obj)});
        });
    };

};
