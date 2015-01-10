/*******************************************************************************************************************************************/
/** IMPOSSIBLE TO INCLUDE THESE JQUERY CALLS IN loadFunction in load.js  because loadFunction gets called once and when clicked on another state
 * then the references to them get lost and they never get dynamically executed **************************************************************  
 * ADDED BY Alexis Espinoza
 * 
 * **/
 	$("#selectedmultiplemetrics").addClass("hidden");//make the selection box hidden.But it is used in the multiple metric selection

          window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
          window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
      	
      	 $("#multiplemetrics").change(function(){
      		
			as.getMetricMultipleSelect(this.options);
			
	
	 });
	
 
$(function() {
	
    $('#ms').change(function() {
        console.log($(this).val());
    }).multipleSelect({
        width: '280%'
    });
});
