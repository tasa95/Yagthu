var options = {
    tmpDir:  __dirname + './public/uploaded/iDPhoto/tmp',
    uploadDir: __dirname + './public/uploaded/iDPhoto/files',
    uploadUrl:  '/uploaded/files/',
    maxPostSize: 11000000, // 11 GB
    minFileSize:  1,
    maxFileSize:  10000000, // 10 GB
    acceptFileTypes:  /.+/i,
    // Files not matched by this regular expression force a download dialog,
    // to prevent executing any scripts in the context of the service domain:
    inlineFileTypes:  /\.(gif|jpe?g|png)/i,
    imageTypes:  /\.(gif|jpe?g|png)/i,
    copyImgAsThumb : true, // required
    imageVersions :{
        maxWidth : 500,
        maxHeight : 500
    },
    accessControl: {
        allowOrigin: '*',
        allowMethods: 'OPTIONS, HEAD, GET, POST, PUT, DELETE',
        allowHeaders: 'Content-Type, Content-Range, Content-Disposition'
    },
    storage : {
        type : 'local'
    }
};

var uploader = require('blueimp-file-upload-expressjs')(options);
