var loopback = require('loopback');
var boot = require('loopback-boot');
var path = require("path");
var app = module.exports = loopback();

app.start = function() {
  // start the web server
  return app.listen(function() {
    app.emit('started');
    console.log('Web server listening at: %s', app.get('url'));
  });
};



app.use(loopback.bodyParser.json({limit:524288000}))
app.use(loopback.bodyParser.urlencoded({
  extended: true
}));


/*
app.post("/api/user/signup" , function(req, res, next) {

  console.log(req.body);
  console.log(req.body.password);
  if(req.body.email && req.body.password) {

      var User = new app.models.user();
    User.email = req.body.email;
    User.password = req.body.password;
    User.Name = req.body.Name;
    User.FirstName = req.body.FirstName;
    User.Admin = req.body.Admin;
    User.Professor = req.body.Professor;
    User.save(function(err, user) {
      if(err)
      {
          res.status(502).send({err : err.toString()})
      }
      else
      {
        console.log(user);
        res.status(200).send({Object : user.toJSON()});
      }


    });

  }
  else
    res.status(403).send({error:"interdit"});

});

*/

/*
app.get("/api/user/:id/myNonAttendance" , function(req, res, next) {

  var trueResponse ;
  var response = [] ;
  var classId ;
  var classCourse;
  var modelCourse = loopback.getModel("Course");
  var modelAttendance = loopback.getModel("attendance");

  var id = req.params.id;

  app.models.user.find({id : id} ,function(err,data)
  {
    if(err)
    {

      res.status(500).send("error "+err);
    }
    else
    {
      if(data)
      {
        classId = data.classId

        modelCourse.find({classId : classId ,endDate : {$lt :new Date()} }, function (err,data)
        {
          if (err) {
            res.status(500).send("error " + err);
          }
          else
          {
            if(data)
            {
              classCourse = data;



              modelAttendance.find({userId : id}, function(err,data) {

                if (err) {

                  res.status(500).send("error " + err);
                }
                else {
                  if (data) {
                    console.log("TEST 8");
                    console.log(data);
                    if(data.length == 0)
                    {
                      res.status(200).send(classCourse);
                    }
                    else {
                      var size = data.length;
                      for (var i = 0; i < data.length; i++) {
                        modelCourse.findById(data[i].courseId, function (err, data) {
                          if (err) {
                            res.status(500).send("error " + err);
                          }
                          else {
                            if (data) {
                              classCourse= classCourse.filter(function(eleme)
                              {
                                if(eleme.id.toString() !== data.id.toString()) {
                                  return true;
                                }
                                else {
                                  return false;
                                }
                              });

                              var futuri = i +1;
                              console.log(futuri);
                              console.log(size);
                              if( futuri >= size)
                              {
                                res.status(200).send(classCourse);
                              }

                            }
                            else {
                              console.log("test 2");
                              console.log(classCourse);
                              response = classCourse;
                              res.status(200).send(classCourse);
                              //res.status(200).send(classCourse);
                            }
                          }
                        });
                      }


                    }
                  }
                  else {
                    console.log("test 6");
                    res.status(200).send(data);
                  }
                }
              });
            }
            else
            {
              console.log("test 5");
              res.status(200).send(data)
            }
          }


        });
      }
      else
      {
        console.log("test 4");
        res.status(500).send(data);
      }
    }



  });

});
*/

var options =

{
  accepts: [
    {arg: 'ids', type: 'string'}],
  returns: {arg: 'getAttendance', type: 'array'},
  http: {path: '/user/:id/my-non-attendance', verb: 'GET', status : 200}
};

var user =loopback.getModel("User");
user.remoteMethod("getAttendance", options);

var options =
{
  accepts: [{arg: 'res', type: 'object', 'http': {source: 'res'}},
    {arg: 'req', type: 'object', 'http': {source: 'req'}}],
  returns: {arg: 'document', type: 'array'},
  http: {path: '/:id/course/:idcourse/document', verb: 'GET', status : 200}
};

user.remoteMethod("getDocument", options);


var options =
{
  accepts: [{arg: 'res', type: 'object', 'http': {source: 'res'}},
    {arg: 'req', type: 'object', 'http': {source: 'req'}}],
  returns: {arg: 'document', type: 'array'},
  http: {path: '/:id/course/documents', verb: 'GET', status : 200}
};

user.remoteMethod("getAllDocument", options);


var options =
{
  accepts: [{arg: 'res', type: 'object', 'http': {source: 'res'}},
    {arg: 'req', type: 'object', 'http': {source: 'req'}}],
  returns: {arg: 'document', type: 'array'},
  http: {path: '/:id/course/documents', verb: 'GET', status : 200}
};

user.remoteMethod("getCourseAtDate", options);




// Bootstrap the application, configure models, datasources and middleware.
// Sub-apps like REST API are mounted via boot scripts.
boot(app, __dirname, function(err) {
  if (err) throw err;

  // start the server if `$ node server.js`
  if (require.main === module)
    app.start();
});

app.models.user.settings.acls = [
  {
    "principalType": "ROLE",
    "principalId": "$everyone",
    "permission": "Allow"
  },
  {
    "accessType": "*",
    "principalType": "ROLE",
    "principalId": "$authenticated",
    "permission": "ALLOW"
  }
];
