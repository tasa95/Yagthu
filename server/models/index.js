var mongoose = require('mongoose');

module.exports = function(server) {
    server.models   = {};
    server.mongoose = mongoose.connect('mongodb://localhost:27017/yagthu');

    server.models.User    = require('./user')(server);
    server.models.Document = require('./document')(server);
    server.models.Course = require('./course')(server);
    server.models.Attendance = require('./attendance')(server);
    server.models.Class = require('./class')(server);
    server.models.ClassRoom = require('./classRoom')(server);
    server.models.Discipline = require('./discipline')(server);
    server.models.Photo = require('./Photo')(server);
};