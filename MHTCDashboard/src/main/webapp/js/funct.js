/*******************************************************************************************************************************************/
/** IMPOSSIBLE TO INCLUDE THESE JQUERY CALLS IN loadFunction in load.js  because loadFunction gets called once and when clicked on another state
 * then the references to them get lost and they never get dynamically executed **************************************************************  
 * ADDED BY Alexis Espinoza
 * 
 * **/
 
$( document ).ready(function() {
	 $("#multiplemetrics").change(function(){
			as.getMetricMultipleSelect(this.options);
	
	 });
	 $("#yearmultiplemetric").change(function(){
		///alert("YEAR: "+this.value);
	});
  });
