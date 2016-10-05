var loopback = require('loopback');

module.exports = function(User) {



  User.getAttendance = function(res,req,cb ) {

    var trueResponse ;
    var response = [] ;
    var classId ;
    var classCourse;
    var modelCourse = loopback.getModel("Course");
    var modelAttendance = loopback.getModel("attendance");
    var modeluser = loopback.getModel("user");
    var id = req.params.id;


    modeluser.find({id : id} ,function(err,data)
    {
      if(err)
      {
        cb(null, err);
      }
      else
      {
        if(data)
        {
          console.log("TEST 1");
          classId = data.classId;
          var date = new Date(new Date().toISOString());
          console.log(date);
          modelCourse.find({classId : classId ,endDate : {$lt :date} }, function (err,data)
          {
            if (err) {
              cb(null, err);
            }
            else
            {
              if(data)
              {
                console.log("TEST 2");
                data.forEach(function(elem)
                {
                  data.endDate = new Date(data.endDate);
                });

                data=data.filter(function(element)
                {
                  if(element.endDate < date)
                    return true;
                  else
                    return false;
                });



                classCourse = data;



                modelAttendance.find({userId : id}, function(err,data) {

                  if (err) {

                    cb(null, err);
                  }
                  else {
                    console.log("TEST 3");
                    if (data) {
                      console.log("TEST 8");
                      console.log(data);
                      if(data.length == 0)
                      {
                        res.status(200).send(classCourse);
                      }
                      else {
                        console.log("TEST 4");
                        var size = data.length;
                        for (var i = 0; i < data.length; i++) {
                          modelCourse.findById(data[i].courseId, function (err, data) {
                            if (err) {
                              var futuri = i +1;
                              console.log(futuri);
                              console.log(size);
                              if( futuri >= size) {
                                cb(null, err);
                              }
                            }
                            else {
                              console.log("TEST 6");
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
                                console.log("TEST 7");
                                var futuri = i +1;
                                console.log(futuri);
                                console.log(size);
                                if( futuri >= parseInt( size))
                                {
                                  console.log("send");


                                }

                              }
                              else {
                                var futuri = i +1;
                                console.log(futuri);
                                console.log(size);
                                if( futuri >= size) {
                                  console.log("test 2");
                                  console.log(classCourse);
                                  response = classCourse;
                                  cb(null, classCourse);
                                }

                                //res.status(200).send(classCourse);
                              }
                            }
                          });
                        }

                        cb(null, classCourse);


                      }
                    }
                    else {
                      console.log("test 9");
                      cb(null, data);
                    }
                  }
                });
              }
              else
              {
                console.log("test 10");
                cb(null, data);
              }
            }


          });
        }
        else
        {
          console.log("test 11");
          cb(null, data);
        }
      }
    });

  }

  User.remoteMethod(
    'getAttendance',
    {
      accepts: [
        {arg: 'res', type: 'object', 'http': {source: 'res'}},
        {arg: 'req', type: 'object', 'http': {source: 'req'}}],
      returns: {arg: 'response', type: 'string'},
      http: {path: '/:id/my-non-attendance', verb: 'GET', status : 200}
    }
  );




  User.getDocument = function(res,req,cb ) {
    var modelCourse = loopback.getModel("Course");
    var modelAttendance = loopback.getModel("attendance");
    var modeluser = loopback.getModel("user");
    var modelDocument= loopback.getModel("Document");
    var id = req.params.id;
    var idCourse = req.params.idcourse;


    modeluser.find({id : id} ,function(err,data)
    {
      if(err)
      {
        cb(null,err)
      }
      else
      {
        if(data)
        {
          modelAttendance.find({userId : id , courseId : idCourse}, function(err,data) {

            if (err)
            {

              cb(null, err);
            }
            else
            {
              if (data) {
                console.log(data);
                modelCourse.findById(idCourse, function(err,data)
                {

                  if(err)
                  {
                    cb(null,err);
                  }
                  else
                  {
                    if(data)
                    {
                      console.log(data);

                      modelDocument.find({where: {courseId: data.id}}, function(err,data)
                      {
                        if(err)
                        {
                          cb(null,err);
                        }
                        else
                        {
                          if(data)
                          {

                            cb(null,data);
                          }
                          else
                          {
                            cb(null,data);
                          }
                        }

                      });
                    }
                    else
                    {
                     cb(null,data);
                    }
                  }


                });

              }
              else
              {
                cb(null,data);
              }
            }
          });
        }
        else
        {
          cb(null,err);
        }
      }
    });
  }


  User.getAllDocument = function(res,req,cb ) {
    var modelCourse = loopback.getModel("Course");
    var modelAttendance = loopback.getModel("attendance");
    var modeluser = loopback.getModel("user");
    var modelDocument= loopback.getModel("Document");
    var id = req.params.id;
    var response = [];



          modelAttendance.find({ where: { userId : id }}, function(err,data) {

            if (err)
            {

              cb(null, err);
            }
            else
            {
              if (data) {

                var size = data.length;



                for(var i = 0 ; i < data.length ; i++)
                {

                  var id = data[i].courseId;
                  console.log(i);
                  console.log(data);
                  modelCourse.find( {where: {id : id}}, function (err, data) {
                    if (err) {
                      cb(null, err);
                    }
                    else {
                      if (data) {
                       console.log(data);
                        console.log(i);


                        modelDocument.find({where: { courseId: data[0].id }}, function (err, data) {
                          if (err) {
                            cb(null, err);
                          }
                          else {
                            if (data) {
                              console.log(i);
                              response[i] = data;
                              var futuri = i +1;

                              response = response.filter(function(element){
                                { return element !== null;}
                              });
                              if( futuri >= size)
                              {
                                cb(null, response);
                              }

                            }
                            else {
                              cb(null, data);
                            }
                          }

                        });
                      }
                      else {
                        cb(null, data);
                      }
                    }


                  });
                }
              }
              else
              {
                cb(null,data);
              }
            }
          });



  }


  User.remoteMethod(
    'getDocument',
    {
      accepts: [
        {arg: 'res', type: 'object', 'http': {source: 'res'}},
        {arg: 'req', type: 'object', 'http': {source: 'req'}}],
      returns: {arg: 'response', type: 'string'},
      http: {path: '/:id/course/:idcourse/document', verb: 'GET', status : 200}
    }
  );



  User.remoteMethod(
    'getAllDocument',
    {
      accepts: [
        {arg: 'res', type: 'object', 'http': {source: 'res'}},
        {arg: 'req', type: 'object', 'http': {source: 'req'}}],
      returns: {arg: 'response', type: 'string'},
      http: {path: '/:id/course/documents', verb: 'GET', status : 200}
    }
  );



  User.remoteMethod(
    'getCourseAtDate',
    {
      accepts: [
        {arg: 'today', type: 'date'},
        {arg: 'res', type: 'object', 'http': {source: 'res'}},
        {arg: 'req', type: 'object', 'http': {source: 'req'}}],
      returns: {arg: 'response', type: 'string'},
      http: {path: '/:id/course/date', verb: 'GET', status : 200}
    }
  );

};
