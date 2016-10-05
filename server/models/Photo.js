var mongoose = require('mongoose');

module.exports = function(server) {

    var PhotoSchema = server.mongoose.Schema({

        statut : {
            type : Number,
            required : true,
            unique : false
        },
        name : {
            type : String,
            required : true,
            unique : true
        },

        path : {
            type : String,
            required : true
        }
    });

    PhotoSchema.plugin(require('mongoose-timestamp'));

    PhotoSchema.methods.toJSON = function() {
        return this.toObject();
    };


    PhotoSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    PhotoSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };


    PhotoSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(PhotoSchema.tree[key] != null)
            {
                this[key] = dictionnary[key];
                number++;
            }
        }
        return number;
    };

    PhotoSchema.methods.Destruct = function (Creation) {
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
    PhotoSchema.methods.length = function () {
        var number = 0;
        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.Photo, PhotoSchema);
};