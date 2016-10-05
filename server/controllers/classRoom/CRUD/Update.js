/**
 * Created by thierryallardsaintalbin on 20/04/15.
 */

module.exports = function (server) {
    server.put('/classRoom/:id', server.middleware.isLoggedIn, server.middleware.isAnAdmin, server.middleware.MissingParameterModificationClassRoom ,function (req, res) {

        var MyClassRoom = server.models.ClassRoom();

        MyClassRoom.populateForUpdate(req.body);
        server.models.ClassRoom.findOneAndUpdate({_id: req.params.id}, {
            $set: MyClassRoom,
            $currentDate: {updatedAt: true}
        }, onClassRoomModified);

        function onClassRoomModified(err,data)
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
}
