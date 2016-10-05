
var sha1 = require('sha1');
var mongoose = require('mongoose');

module.exports = function(server) {
    ObjectId = mongoose.Schema.ObjectId;
    var UserSchema = server.mongoose.Schema({


        iDSmartphone: {
            type: String,
            required: false,
            default : null
        },

        name: {
            type: String,
            required: true,
            unique: false

        },
        firstName : {
            type : String,
            required : true ,
            unique : false
        },
        iDPhoto : {
            type : ObjectId,
            required : false,
            unique : false,
            ref : server.referenceModel.Photo
        },
        password: {
            type: String,
            required: false,
            unique : false
        },
        token : {
            type : String,
            required : false,
            unique : true
        },

        iDClass : {
            type : ObjectId,
            required : false,
            ref : server.referenceModel.Class
        },

        login : {
            type : String ,
            required : true,
            unique : true
        },

        admin : {
            type : Boolean,
            required : true,
            unique : false,
            default : false
        },
        Professor : {
            type : Boolean,
            required : true,
            unique : false

        },

        numberStudent : {
            type : String,
            required : false,
            unique : false
        },

        address : {
            type : String ,
            required : false,
            unique : false

        },

        birthCity : {
            type : String ,
            required : false,
            unique : false

        },


        state : {
            type : String ,
            required : false,
            unique : false
        },

        phoneNumber : {
                type : String,
                required : false,
                unique : false
        },

        INE : {
            type : Number,
            required : false,
            unique : false
        },

        title : {
            type : Number,
            required : false,
            unique : false
        },

        nationality : {
            type : String,
            required : false,
            unique : false
        },

        birthState : {
            type : String,
            required : false,
            unique : false
        },

        postalCode : {
            type : Number,
            required : false,
            unique : false

        },

        mobilePhoneNumber : {
            type : Number,
            required : false,
            unique : false
        },

        email : {
            type : String,
            required : false,
            unique : false
        }


    });

    UserSchema.plugin(require('mongoose-timestamp'));

    UserSchema.methods.toJSON = function() {
        return this.toObject();
    };

    UserSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number;
    };


    UserSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };


    UserSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {

            if(UserSchema.tree[key] != null)
            {
                if(key != "password") {
                    this[key] = dictionnary[key];
                    number++;
                }
                else
                {
                    this[key] = sha1(dictionnary[key]);
                }
            }
        }
        return number;
    };

    UserSchema.methods.Destruct = function (Creation) {
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

    UserSchema.methods.length = function () {
        var number = 0;
        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.User, UserSchema);
};