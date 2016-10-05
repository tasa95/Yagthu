/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */
var mongoose = require('mongoose');

module.exports = function(server)
{
    ObjectId = mongoose.Schema.ObjectId;
    var DisciplineSchema = server.mongoose.Schema({


        name : {
            type : String ,
            required : true,
            Unique : true
        }
    });

    DisciplineSchema.plugin(require('mongoose-timestamp'));

    DisciplineSchema.methods.toJSON = function() {
        return this.toObject();
    };


    DisciplineSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    DisciplineSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };


    DisciplineSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(DisciplineSchema.tree[key] != null)
            {
                this[key] = dictionnary[key];
                number++;
            }
        }
        return number;
    };

    DisciplineSchema.methods.Destruct = function (Creation) {
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


    DisciplineSchema.methods.length = function () {
        var number = 0;


        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.Discipline, DisciplineSchema);

};;