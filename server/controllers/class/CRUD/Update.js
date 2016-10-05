/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */



module.exports = function (server) {
    server.put('/class/:id', server.middleware.isLoggedIn, server.middleware.isAnAdmin,server.middleware.MissingParameterModificationClass, function (req, res) {

        var MyClass = server.models.Class();

        MyClass.populateForUpdate(req.body);
        server.models.Class.findOneAndUpdate({_id: req.params.id}, {
            $set: MyClass,
            $currentDate: {updatedAt: true}
        }, onClassModified);

        function onClassModified(err,data)
        {
            if(err)
            {
                res.status(500).status({error : "Error on our server\nPlease wait"});
                return;
            }
            else
            {
                if(data == null)
                {
                    res.status(400).send({error : "No Attendance corresponding"});

                }
                else {
                    res.status(200).send({data: data});
                }
                return;
            }
        }
    });
};

