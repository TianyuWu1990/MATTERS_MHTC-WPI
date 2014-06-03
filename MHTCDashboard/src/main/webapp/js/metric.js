/**
 * @author salah
 */

 function metric()
 {
     this.metrics = $.parseJSON($.ajax({
     	url: 'data/stats/available',
         dataType: 'text',
         async:false,
         success: function(data) {
            return data;
         }
     }).responseText);
 }

 metric.prototype.getMetricByID = function(id){
		return this.metrics.filter(function(each) {
		return each.id == parseInt(""+id,10);
		});
 };
 

 metric.prototype.getMetricByName = function(name){
		return this.metrics.filter(function(each) {
		return each.name === name;
		});
 };


 metric.prototype.getMetricFromString = function(metric){
		return this.metrics.filter(function(each) {
		return each.id == parseInt(""+metric,10) || each.name===metric;
		});
 };

 metric.prototype.getAllMetrics = function() {
 	return this.metrics;
 };

 metric.prototype.getAllInBin = function(id){
	return this.metrics.filter(function(each) {
		return each.binId == parseInt(""+id,10);
	});
 };

 metric.prototype.toString = function(){
 	return JSON.stringify(this.metrics);
 };
