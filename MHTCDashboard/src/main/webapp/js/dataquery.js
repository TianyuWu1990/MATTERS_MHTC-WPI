
var DQ = (function($){
    
    function buildQuery(dataQuery) {
        var query = "data/stats/query?states=" + dataQuery.states.join(",") + "&metrics=" + dataQuery.metrics.join(",");
        
        return query;
    }
    
    /**
     * Creates an object that stores information about a query to the server and can be executed.
     * @constructor
     */
    function Query() { 
    	this.states = [];
    	this.metrics = [];
    };
    
    /**
     * Adds state(s) to the query.
     * @param state Can either be a string of a state or 
     * @returns {Query}
     */
    Query.prototype.addState = function(state) {
        if (typeof state == "string" || state instanceof String) {
            if ($.inArray(state, this.states) == -1) {
                this.states.push(state);
            }
        } else if (state instanceof Array) {
            var self = this;
            state.forEach(function(innerState) {
               self.addState(innerState); 
            });
        }
        return this;
    };
    
    /**
     * @param metric Can either be a string of a state or 
     * @returns {Query}
     */
    Query.prototype.addMetric = function(metric) {
        if ($.inArray(metric, this.metric) === -1) {
            this.metrics.push(metric);
        }
        return this;
    };
    
    /**
     * 
     * @param callback
     */
    Query.prototype.execute = function(callback) {
        var query = buildQuery(this);
        
        $.get( query, function(data) {
            callback(data);
        });
    }

    var DataQuery = {};
    
    DataQuery.create = function() {
        return new Query();
    };
    
    DataQuery.QueryPrototype = Query;
    
    return DataQuery;
    
})(jQuery);
