var mongoose = require('mongoose');

module.exports = function(server)
{
    ObjectId = mongoose.Schema.ObjectId;
    var DocumentSchema = server.mongoose.Schema({


        name : {
            type : String ,
            required : true,
            Unique : true
        },
        iDCourse : {
            type: ObjectId,
            required: true,
            Unique: true,
            ref: server.referenceModel.Course
        }
    });

    DocumentSchema.plugin(require('mongoose-timestamp'));

    DocumentSchema.methods.toJSON = function() {
        return this.toObject();
    };

    DocumentSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    DocumentSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };


    DocumentSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(DocumentSchema.tree[key] != null)
            {
                this[key] = dictionnary[key];
                number++;
            }
        }
        return number;
    };

    DocumentSchema.methods.Destruct = function (Creation) {
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

    DocumentSchema.methods.length = function () {
        var number = 0;

        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.Document, DocumentSchema);

};