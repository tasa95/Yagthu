/**
 * Created by thierryallardsaintalbin on 22/03/15.
 */
var mongoose = require('mongoose');

module.exports = function(server)
{
    //ObjectId = mongoose.Schema.ObjectId;
    var ClassSchema = server.mongoose.Schema({
        name : {
            type : String,
            required : true,
            unique : true
        }
    });

    ClassSchema.plugin(require('mongoose-timestamp'));

    ClassSchema.methods.toJSON = function() {
        return this.toObject();
    };

    ClassSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    ClassSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };

    ClassSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(ClassSchema.tree[key] != null)
            {
                this[key] = dictionnary[key];
                number++;
            }
        }
        return number;
    };

    ClassSchema.methods.Destruct = function (Creation) {
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



    ClassSchema.methods.length = function () {
        var number = 0;



        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.Class, ClassSchema);
};