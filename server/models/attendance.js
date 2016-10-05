/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */
var mongoose = require('mongoose');

module.exports = function(server)
{
    ObjectId = mongoose.Schema.ObjectId;
    var AttendanceSchema = server.mongoose.Schema({
        iDUser : {
            type : ObjectId,
            required : true,
            unique : false,
            ref : server.referenceModel.User
        },

        iDCourse : {
            type : ObjectId,
            required : true,
            unique : false,
            ref : server.referenceModel.Course
        }
    });

    AttendanceSchema.plugin(require('mongoose-timestamp'));

    AttendanceSchema.methods.toJSON = function() {
        return this.toObject();
    };

    AttendanceSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    AttendanceSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };

    AttendanceSchema.methods.length = function () {
        var number = 0;
        for( key in this)
        {
            number++;
        }
        return number++;
    };

    AttendanceSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(AttendanceSchema.tree[key] != null)
            {
                this[key] = dictionnary[key];
                number++;
            }
        }
        return number;
    };

    AttendanceSchema.methods.Destruct = function (Creation) {
            for( key in this._doc)
            {
                if( key != "_id") {
                    console.log(delete this[key]);
                    console.log(delete this["_doc"][key]);
                }
                else
                {
                    if(Creation == true)
                    {

                    }
                    else
                    {
                        console.log(delete this[key]);
                        console.log(delete this["_doc"][key]);
                    }

                }
            }
    };

    return server.mongoose.model(server.referenceModel.Attendance, AttendanceSchema);
};