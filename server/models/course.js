var mongoose = require('mongoose');

module.exports = function(server)
{
    ObjectId = mongoose.Schema.ObjectId;
    var CourseSchema = server.mongoose.Schema({


        name : {
            type : String ,
            required : true,
            unique : false
        },
        startDate : {
            type : Date,
            required : true,
            unique : false
        },
        endDate : {
            type: Date,
            required: true,
            unique: false

        },
        iDDiscipline : {
            type : ObjectId,
            required : false,
            ref: server.referenceModel.Discipline
        },
        occured : {
            type : Boolean,
            required : false,
            unique : false ,
            default : false
        },
        iDClassRoom : {
            type : ObjectId,
            required : false,
            ref: server.referenceModel.ClassRoom
        },
        isAConference : {
            type : Boolean,
            required : false,
            unique : false ,
            default : false
        },

        iDClass : {
            type : ObjectId,
            required : false,
            unique : false

        },

        iDProfessor : {
            type : ObjectId,
            required : false, unique : false

        }

    });

    CourseSchema.plugin(require('mongoose-timestamp'));

    CourseSchema.methods.toJSON = function() {
        return this.toObject();
    };



    CourseSchema.methods.populateForUpdate = function (dictionnary) {
        var number = 0;
        this.Destruct(false);
        number = this.Construct(dictionnary);
        return number
    };


    CourseSchema.methods.populateForCreation = function (dictionnary) {
        var number = 0;
        this.Destruct(true);
        number = this.Construct(dictionnary);
        return number
    };

    CourseSchema.methods.Construct = function (dictionnary) {
        var number = 0;
        for( key in dictionnary)
        {
            if(CourseSchema.tree[key] != null) {
                if (key != "startDate" && key != "endDate")
                    this[key] = dictionnary[key];
                else
                    this[key] = new Date(dictionnary[key]);
                number++;
            }
        }
        return number;
    };

    CourseSchema.methods.Destruct = function (Creation) {
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


    CourseSchema.methods.length = function () {
        var number = 0;


        for( key in this)
        {
            number++;
        }
        return number++;
    };

    return server.mongoose.model(server.referenceModel.Course, CourseSchema);

};