/**
 * @author salah
 */

//TODO fully document

/**
 * A module that loads a list of metrics from the server when the page starts
 * and maintains it. Provides functionality for obtaining metrics from the list
 * by name or id.
 */
var Metrics = (function() {

	Metric = function() { };
	
	var metrics = $.parseJSON($.ajax({
		url : 'data/metrics/all',
		dataType : 'text',
		async : false,
		success : function(data) {
			return data;
		}
	}).responseText);
	
	metrics = metrics.map(function(metric) {
		return metric; // TODO convert this to the metric prototype
	});

	function returnFirstOrNull(array) {
		if (array.length == 1)
			return filtered[0];
		else
			return null;
	}

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

	publicInterface.getAllMetrics = function() {
		return this.metrics;
	};

	publicInterface.getAllInBin = function(id) {
		return this.metrics.filter(function(each) {
			return each.binId == id;
		});
	};

	publicInterface.toString = function() {
		return JSON.stringify(this.metrics);
	};

	return publicInterface;

}());

