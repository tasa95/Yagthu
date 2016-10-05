/**
 * Created by thierryallardsaintalbin on 26/04/15.
 */


var options = {
    tmpDir:  __dirname + './public/uploaded/document/tmp',
    uploadDir: __dirname + './public/uploaded/document/files',
    uploadUrl:  '/uploaded/files/',
    maxPostSize: 11000000, // 11 GB
    minFileSize:  1,
    maxFileSize:  10000000, // 10 GB
    acceptFileTypes:  /.+/i,
    // Files not matched by this regular expression force a download dialog,
    // to prevent executing any scripts in the context of the service domain:
    inlineFileTypes:  /\.(gif|jpe?g|png|doc|pdf|ppt)/i,
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