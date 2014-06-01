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
 	for( var i=0 ; i< this.metrics.length; i++ ){
 		if (this.metrics[i].id == parseInt(""+id,10)) {
 			return this.metrics[i];
 		}
 	}
 	return null;
 };


 metric.prototype.getMetricByName = function(name){
 	for( var i=0 ; i< this.metrics.length; i++ ){
 		if (this.metrics[i].name===name) {
 			return this.metrics[i];
 		}
 	}
 	return null;
 };


 metric.prototype.getMetricFromString = function(metric){
 	for( var i=0 ; i< this.metrics.length; i++ ){
 		if (this.metrics[i].id == parseInt(""+metric,10) || 
 			this.metrics[i].name===metric ) {
 			
 			return this.metrics[i];
 		}
 	}
 	return null;
 };

 metric.prototype.getAllMetrics = function() {
 	return this.metrics;
 };

 metric.prototype.getAllInBin = function(id){
 	var metricsInBin = [];
 	for( var i=0 ; i< this.metrics.length; i++ ){
 		if (this.metrics[i].binId == parseInt(""+id,10)) {
 			metricsInBin.push(this.metrics[i]);
 		}
 	}
 	return metricsInBin;
 };

 metric.prototype.toString = function(){
 	return JSON.stringify(this.metrics);
 };
