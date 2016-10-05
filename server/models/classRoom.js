/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */
var mongoose = require('mongoose');

module.exports = function(server)
{

    var ClassRoomSchema = server.mongoose.Schema({

        name : {
            type : String ,
            required : true,
            index: {unique: true}
        }
    });

    ClassRoomSchema.plugin(require('mongoose-timestamp'));

    ClassRoomSchema.methods.toJSON = function() {
        return this.toObject();
    };

    ClassRoomSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    ClassRoomSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };


    ClassRoomSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(ClassRoomSchema.tree[key] != null)
            {
                this[key] = dictionnary[key];
                number++;
            }
        }
        return number;
    };

    ClassRoomSchema.methods.Destruct = function (Creation) {
        for( key in this._doc)
        {
            if(Creation == true && key != "_id") {
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

    ClassRoomSchema.methods.length = function () {
        var number = 0;



        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.ClassRoom, ClassRoomSchema);
};