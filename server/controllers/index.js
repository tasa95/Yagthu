module.exports = function(server) {
    require('./attendance')(server);
    require('./authentication')(server);
    require('./class')(server);
    require('./classRoom')(server);
    require('./course')(server);

    require('./discipline')(server);
    require('./document')(server);

    require('./photo')(server);
    require('./user')(server);
    require('./grades')(server);



};
