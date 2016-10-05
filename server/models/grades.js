/**
 * Created by thierryallardsaintalbin on 29/04/15.
 */

var mongoose = require('mongoose');

module.exports = function(server)
{
    ObjectId = mongoose.Schema.ObjectId;
    var GradesSchema = server.mongoose.Schema({


        name : {
            type : String ,
            required : true,
            Unique : true
        },
        iDDiscipline : {
            type: ObjectId,
            required: true,
            ref: server.referenceModel.Discipline
        },
        iDUser : {
            type : ObjectId,
            required : true,
            ref: server.referenceModel.User
        },
        grades : {
            type : Number,
            required : true,
            Unique : true
        }
    });

    GradesSchema.plugin(require('mongoose-timestamp'));

    GradesSchema.methods.toJSON = function() {
        return this.toObject();
    };

    GradesSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    GradesSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };


    GradesSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(GradesSchema.tree[key] != null)
            {
                this[key] = dictionnary[key];
                number++;
            }
        }
        return number;
    };

    GradesSchema.methods.Destruct = function (Creation) {
        for( key in this._doc)
        {
            if(key != "_id") {
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

    GradesSchema.methods.length = function () {
        var number = 0;

        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.grades, GradesSchema);

};