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
	 Metric = function(id, binId, name, displayName, urlFrom, sourceName, 
							type, trendType, parentId) {
	    this.id=id;
	    this.binId=binId;
	    this.name=name;
	    this.displayName=displayName;
		
	    this.urlFrom=urlFrom;
	    this.sourceName=sourceName;
		
	    this.type=type;
	    this.trendType=trendType;
	    
		this.parentId=parentId;
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
	
	Metric.prototype.getId=function() {
	    return this.id;
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
	
	Metric.prototype.getDisplayName=function() {
		return this.displayName;
	}
	
	Metric.prototype.getParentId=function() {
		return this.parentId;
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
		return new Metric(metric.id, metric.binId, metric.name, 
				metric.displayName, metric.urlFrom, metric.sourceName, 
				metric.type, metric.trendType, metric.parentId); 
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
