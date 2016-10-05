var Express    = require('express');
var bodyParser = require('body-parser');
var session = require('express-session');

var MongoStore = require('connect-mongo')(session);
var mongoose = require('mongoose');

var server = Express();
var sha1 = require('sha1');

server.use(bodyParser.json());
server.use(session({
        secret: 'S3CR3T',
        resave: true,
        saveUninitialized: true,
        key: 'session',
        store: new MongoStore({
            db : 'yagthu'
        })
    })
);





server.referenceModel = {};
server.referenceModel.User = "users";
server.referenceModel.Course = "course";
server.referenceModel.Document = "document";
server.referenceModel.Smartphone = "smartPhone";
server.referenceModel.Discipline = "discipline";
server.referenceModel.Attendance = "attendance";
server.referenceModel.ClassRoom = "classRoom";
server.referenceModel.Class = "class";
server.referenceModel.Photo = "Photo";
server.referenceModel.grades = "grades";


server.totalUser = {};



require('./models')(server);
require('./middleware')(server);
require('./controllers')(server);


server.listen(3000);

console.log('Magic happens on port 3000');
