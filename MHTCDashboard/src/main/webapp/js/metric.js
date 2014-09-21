/**
 * @author salah
 */


/**
 * A module that loads a list of metrics from the server when the page starts
 * and maintains it. Provides functionality for obtaining metrics from the list
 * by name or id.
 */
 var Metrics = (function() {

	/**
	* Prototype definition for a single matric
	*/
	 Metric = function(id, binId, binName, name, urlFrom, sourceName, 
							type, trendType, tabbed) {
	    this.id=id;
	    this.binId=binId;
	    this.binName=binName;
	    this.name=name;
		
	    this.urlFrom=urlFrom;
	    this.sourceName=sourceName;
		
	    this.type=type;
	    this.trendType=trendType;
	    this.tabbed=tabbed;
			
	};

	Metric.prototype.getName=function() {
		return this.name;
	}

	Metric.prototype.getUrlFrom=function() {
		return this.urlFrom;
	}

	Metric.prototype.getSourceName=function() {
		return this.sourceName;
	}

	Metric.prototype.getBinName=function() {
		return this.binName;
	}
	
	Metric.prototype.getId=function() {
	    return this.id;
	}
	
	Metric.prototype.getTabbed=function() {
	    return this.tabbed;
	}
	
	Metric.prototype.getTabString=function() {
	    if (this.tabbed)
	        return "tabbed_metric";
	    else
	        return "";
	}
	
	Metric.prototype.getType=function() {
        return this.type;
    }

	Metric.prototype.getBinId=function() {
        return this.binId;
    }

	Metric.prototype.getTrendType=function() {
        return this.trendType;
    }
	
	Metric.prototype.toString=function() {
        return JSON.stringify(this);
    }
	
	
	/**
	 * variable to cache all metrics
	 * 
	 */
	var metrics = $.parseJSON($.ajax({
		url : 'data/metrics/all',
		dataType : 'text',
		async : false,
		success : function(data) {
			return data;
		}
	}).responseText);
	
	/**
	* each matric is converted to a Metric object
	*/
	metrics = metrics.map(function(metric) {
		return new Metric(metric.id, metric.binId, metric.binName, 
				metric.name, metric.urlFrom, metric.sourceName, 
				metric.type, metric.trendType, metric.tabbed); 
	});
	
	

	/**
	* helper method to return a single metric object, instead of an array
	*/
	
	function returnFirstOrNull(array) {
		if (array.length == 1)
			return array[0];
		else
			return null;
	}

	/**
	* interface definition for publicly accessible methods
	*/
	var publicInterface = {};

	/**
	 * Gets a metric with the given id from the list of cached metrics.
	 */
	publicInterface.getMetricByID = function(id) {
		return returnFirstOrNull(metrics.filter(function(each) {
			return each.id == id;
		}));
	};
	
	publicInterface.arrayGetMetricByID = function(id) {
		var array_return;
		var i=0;
		return metrics.filter(function(each) {
			if(each.id == id){
				array_return[i]=id;
				i++;
			}
			return array_return;	
		});
	};
	/**
	 * Gets a metric with the given name from the list of cached metrics.
	 */
	publicInterface.getMetricByName = function(name) {
		return returnFirstOrNull(metrics.filter(function(each) {
			return each.name === name;
		}));
	};
   
	/**
	 * Given a string that could be a name or id, returns a metric that matches
	 * either.
	 */
	publicInterface.getMetricFromString = function(metric) {
		return returnFirstOrNull(metrics.filter(function(each) {
			return each.id == metric || each.name === metric;
		}));
	};

	/**
	 * Retuens all metrics
	 * 
	 * 
	 */
	publicInterface.getAllMetrics = function() {
		return metrics;
	};

	/**
	 * Given id if a bin/top-level-category, returns all metrics in that bin
	 * 
	 */
	publicInterface.getAllInBin = function(id) {
		return metrics.filter(function(each) {
			return each.binId == id;
		});
	};

	/**
	 * Returns a string representation of the metrics array
	 */
	publicInterface.toString = function() {
		return JSON.stringify(metrics);
	};

	return publicInterface;

 }());
