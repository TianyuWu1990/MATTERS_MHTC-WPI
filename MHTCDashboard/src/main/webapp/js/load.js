 var global_timer = null;
 var year_global=-1;

 var globalcounter=0;

 // Hack that gets called 
 //when clicking on a button or when scrolling the div, flips the dropdown if it will make it display better
adjustDropDown = function(e) {
    setTimeout(function() {
        var divWithScroll = $("#sidebar div.active");
        var openMenuContainer = $("#sidebar div.open");
        $("#sidebar div.open ul.dropdown-menu").each(function() {
            
            var height = divWithScroll.height();
            var top = openMenuContainer.position().top + $(this).position().top;
            var bottom = openMenuContainer.position().top + $(this).position().top + $(this).height();
            
            if (top < 0) {
                $(this).css("bottom", "auto");
                $(this).css("top", "" + openMenuContainer.height() + "px");
            }
            
            if (bottom > height) {

                $(this).css("top", "auto");
                $(this).css("bottom", "" + (openMenuContainer.height() +2) + "px");
            }
            
        });
        
        //var scrollTop = divWithScroll.scrollTop();
        
    }, 20);
};
/**
 * 
 * Returns the value of the style selector. The color returned is used to paint the background of the modal windows and peerStates depending
 * on the tab tabbed
 */
 /**getStyleRuleValue=function(style, selector, sheet) {
    var sheets = typeof sheet !== 'undefined' ? [sheet] : document.styleSheets;
    //for (var i = 0, l = sheets.length; i < l; i++) {
        var sheet = sheets[3];
       // if( !sheet.cssRules ) { continue; }
        for (var j = 0, k = sheet.cssRules.length; j < k; j++) {
            var rule = sheet.cssRules[j];
            if (rule.selectorText && rule.selectorText.split(',').indexOf(selector) !== -1) {
            	//alert("This is style: "+rule.style[style]);
                return rule.style[style];
            }
        }
    //}
    return null;
}**/
 
getStyleRuleValue=function(styleName, className) {

	    //for (var i=0;i<document.styleSheets.length;i++) {
	        var s = document.styleSheets[1];

	        var classes = s.rules || s.cssRules
	        for(var x=0;x<classes.length;x++) {
	            if(classes[x].selectorText==className) {
	                return classes[x].style[styleName] ? classes[x].style[styleName] : classes[x].style.getPropertyValue(styleName);
	            }
	       // }
	    }
	}
 /**
  * Converts a hexadimal color into a RGB
  */
  hexToRgb=function(hex) {
	  var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
	    hex = hex.replace(shorthandRegex, function(m, r, g, b) {
	        return r + r + g + g + b + b;
	    });

	    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
	    return result ? {
	        r: parseInt(result[1], 16),
	        g: parseInt(result[2], 16),
	        b: parseInt(result[3], 16)
	    } : null;
	};
  /**
   * Converts an RGB color To HEXA
   */
  colorToHex= function (color) {
	  	
	  	if(color == undefined || color == null) return "#F17171"
	  	
	    if (color.substr(0, 1) === '#') {
	        return color;
	    }
	    var digits = /(.*?)rgb\((\d+), (\d+), (\d+)\)/.exec(color);

	    var red = parseInt(digits[2]);
	    var green = parseInt(digits[3]);
	    var blue = parseInt(digits[4]);

	    var rgb = blue | (green << 8) | (red << 16);
	    return digits[1] + '#' + rgb.toString(16);
	}
  /**
   * Gets a new letter to get a different mix of a hexadecimal color
   */
  nextLetter=function (s){
	    return s.replace(/([a-fA-F])[^a-fA-F]*$/, function(a){
	        var c= a.charCodeAt(0);
	        switch(c){
	            case 90: return 'A';
	            case 122: return 'a';
	            default: return String.fromCharCode(++c);
	        }
	    });
	} 
/**
 * 
 * Returns a new mix of color. Currently not in use
 */  
getNewMix=function(letterNum){
	
	if(!isNaN(letterNum)){
		if(letterNum<9){
			letterNum=parseInt(letterNum);
			letterNum++;
			
		}else{
			letterNum="A";
		}
	}else{
		if((letterNum!='F')&&(letterNum!='f')){
			letterNum=nextLetter(letterNum);
		}
		
	}
	return letterNum;
}  
/**
 * 
 * @param hexcolor hexadecimal color to be degraded
 * @param value: current element of the array   
 * @param highest : last element of array
 * @returns {String} : this functions returns a new color degraded 
 */
getNewColor=function(hexcolor,value,highest){
	var red=hexcolor.substring(1, 3);
	var green=hexcolor.substring(3, 5);
	var blue=hexcolor.substring(5, 7);
	
	red=parseInt(red, 16);
	green=parseInt(green, 16);
	blue=parseInt(blue, 16);
	
	
	red=Math.floor(red*value/highest);
	green=Math.floor(green*value/highest);
	blue=Math.floor(blue*value/highest);
    red=red>255?255:red;
    green=green>255?255:green;
    blue=blue>255?255:blue;
     
	
	  
	red=red.toString(16)
	green=green.toString(16)
	blue=blue.toString(16)
	
	var thecolor="#" + (red.length < 2 ? "0"+red : red) + (green.length < 2 ? "0"+green : green) + (blue.length < 2 ? "0"+blue : blue);
	
	
	
	/*var newColor="#";
	var changed=false;
	var currentLetterNum=null;
	var i=1;
	while(i<8){
		currentLetterNum=hexcolor.substring(i, i+1);
		if(!changed){
			if(currentLetterNum!=getNewMix(currentLetterNum)){
				currentLetterNum=getNewMix(currentLetterNum);
				changed=true;
			}
		}
		newColor=newColor+currentLetterNum;
		i++;
	}*/
	
	return thecolor;		
};  

animateHeatMap=function(){
	var limit=array_years_global.length;
	if(year_global==-1)
		
	
		year_global=array_years_global[globalcounter];
		//as.showHeatMapGraphReloaded(as.currentind,as.current_tab_style,'#mbodyHeatMap', year_global);
		as.showHeatMapGraphReloaded(as.currentind,'#mbodyHeatMap', year_global);
		globalcounter++;
		if(globalcounter==limit){
			year_global=-1;
			globalcounter=0;
		}else{
			year_global=array_years_global[globalcounter];
		}
		
		

}; 
stopHeatMapAnimation=function() {
	if (!global_timer) return false;
	  	clearInterval(global_timer);
	global_timer = null;
};
// new loadfunction to be called from body-onload


loadFunction = function() {
	//$('#map').usmap({});
	
	
	/**********************************************************************/
	/***************CHECKING LEFT MENU ************************************/	
	/**********************************************************************/

	
	/*$("div.tab-pane button.dropdown-toggle").click(adjustDropDown);
	$("div.tab-pane").scroll(adjustDropDown);
	$("#selectallmultiplemetric").addClass("hidden");
	$("#unselectallmultiplemetric").addClass("hidden");
	$("#selectpeerstates").addClass("hidden");*/
	cm=CM.create();
	as=AS.create();
	//cm.loadFunction();
	as.loadFunction();
	 $(function() {
	        $('#ms').change(function() {
	        	as.setStatesSelected($(this).val());
	        }).multipleSelect({
	            width: '30%'
	        });
	        
	    });

		
	$("#chartType" ).change(function() {
		
		  cm.current_graph = this.value;
		  //alert("current_graph11"+cm.current_graph);
		  //cm.showMultiGraph(cm.selected);
		  cm.showMultiGraph(as.selected);
		});
	$("#yearHeatMap").change(function(){
		as.showHeatMapGraph(as.currentind,as.current_tab_style,'#mbodyHeatMap', this.value);
	});
	
	
	$( "#playbuttonanimation" ).click(function() {
			
		if (!global_timer)
			global_timer = setInterval(animateHeatMap,3500);
		$("#stopbuttonanimation").prop('disabled', false); 
		$("#playbuttonanimation").prop('disabled', true);

		
		//as.startPlayer(as.currentind,as.current_tab_style, '#mbodyHeatMap',  true);
	});
	$( "#stopbuttonanimation" ).click(function() {
		//$("#yearHeatMap").prop('disabled', false);
		
		stopHeatMapAnimation();
		$("#stopbuttonanimation").prop('disabled', true); 
		$("#playbuttonanimation").prop('disabled', false);
		global_timer = null;
		year_global=-1;
		globalcounter=0;
	});
	$("#yearsMultipleQuery").change(function(){
		console.log("current value: ",this.value);
		as.showMultipleMetricsStatesYears(this.value);
	});
	
/**********************************************************************/
/**********************NEW DESIGN**************************************/
/**********************************************************************/
/**********************************************************************/
	 
/**********************************************************************/
/***************CHECKING LEFT MENU *************************************/	
/**********************************************************************/
	/***COMMNETED BY MANIK */
	/**TODO 101 is the number of metrics.***/
	/*var isChecked;	
	for(var t=1;t<101;t++){ 
		
		isChecked = $('#check'+t).attr('checked')?true:false;
		if(isChecked==true){
			t=t.toString();
			as.SelectUnselectMultipleMetric(t,1);
		}
			
			
		
			
		var checkboxchecked="check"+t;	
		$('input[id="'+checkboxchecked+'"]').click(function(){
			var  str= $(this).attr('id');
			var checkboxcheckedid = str.substr(5, str.length);

			if($(this).is(":checked")){
				as.SelectUnselectMultipleMetric(checkboxcheckedid,1);
				
			}else if($(this).is(":not(:checked)")){
				as.SelectUnselectMultipleMetric(checkboxcheckedid,2);
			}
		});
	}
/**********************************************************************/
/***************CHECKING LEFT MENU *************************************/	
/**********************************************************************/
	  
	
/**********************************************************************/
/**********************NEW DESIGN**************************************/
/**********************************************************************/
/**********************************************************************/
	 
	/*$("#selectedmultiplemetrics").addClass("hidden");//make the selection box hidden.But it is used in the multiple metric selection

    window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
    window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
	
	 $("#multiplemetrics").change(function(){
		
		as.getMetricMultipleSelect(this.options);
		

});*/
	/***********************added by manik*********************************************/
	
	$(".submenu").click(function(){
		//console.log("submenu")
		var inputTag = this.getElementsByTagName("input")[0];
		var isChecked = inputTag.checked;
		var checkedMetricId = inputTag.id;
		var currentParentId = this.parentNode.parentNode.id;
		console.log(currentParentId);
		if(!isChecked)
		{		
			//$("#"+checkedMetricId).css({"display":"inline"});
			$("#"+checkedMetricId).removeAttr("disabled");
			inputTag.checked = true;
			this.style.color = "#000";
			this.style.fontWeight = "bold"
			//as.SelectUnselectMultipleMetric([1,3,4],1);	
			as.SelectUnselectMultipleMetric(checkedMetricId.substr(5, checkedMetricId.length),1);
			duplicateMetricParentId = as.getDuplicateMetricCategory(currentParentId,checkedMetricId);
			if(duplicateMetricParentId !== "")
			{
				var duplicatecategory = document.getElementById(duplicateMetricParentId).getElementsByTagName('li');
				//console.log("duplicatecategory: ",duplicatecategory);
				for(d = 1;d<duplicatecategory.length;d++)
				{
					if(duplicatecategory[d].getElementsByTagName("input")[0].id == checkedMetricId)
					{
						duplicatecategory[d].getElementsByTagName("a")[0].style.fontWeight="bold";
						duplicatecategory[d].getElementsByTagName("a")[0].style.color = "#000";
						duplicatecategory[d].getElementsByTagName("input")[0].checked = true;
						duplicatecategory[d].getElementsByTagName("input")[0].disabled = "";
						break;
					}
				}
				
			}
		}
		else if ( isChecked)
		{	
			$("#"+checkedMetricId).attr("disabled","disabled");
			inputTag.checked = false;
			this.style.color = "#666";
			this.style.fontWeight = "normal"
			duplicateMetricParentId = as.getDuplicateMetricCategory(currentParentId,checkedMetricId);
			if(duplicateMetricParentId !== "")
			{
				var duplicatecategory = document.getElementById(duplicateMetricParentId).getElementsByTagName('li');
				//console.log("duplicatecategory: ",duplicatecategory);
				for(d = 1;d<duplicatecategory.length;d++)
				{
					if(duplicatecategory[d].getElementsByTagName("input")[0].id == checkedMetricId)
					{
						duplicatecategory[d].getElementsByTagName("a")[0].style.fontWeight="normal";
						duplicatecategory[d].getElementsByTagName("a")[0].style.color = "#666";
						duplicatecategory[d].getElementsByTagName("input")[0].checked = false;
						break;
					}
				}
				
			}
			as.SelectUnselectMultipleMetric(checkedMetricId.substr(5, checkedMetricId.length),2);
		}
	});
	/*******************************************************************************************
	/*table creation and default selection*/
		var unorderedList   = document.getElementById('stateMetric');
	    var ListItems       = unorderedList.getElementsByTagName('li');
	  
	    defaultlist =[]
		for(i = 1;i < ListItems.length ;i++ )
		{
			var inputTag = ListItems[i].getElementsByTagName("input")[0];
			var aTag = ListItems[i].getElementsByTagName("a")[0];
			checkedMetricId = inputTag.id;
			 var currentParentId = 'stateMetric'
			if(inputTag.checked){
				
				$("#"+aTag.id).css("color","#000");
				$("#"+aTag.id).css("font-weight","bold");
				//as.SelectUnselectMultipleMetric(checkedMetricId.substr(5, checkedMetricId.length),1);
				defaultlist[i-1] = checkedMetricId.substr(5, checkedMetricId.length);
				duplicateMetricParentId = as.getDuplicateMetricCategory(currentParentId,checkedMetricId);
				if(duplicateMetricParentId !== "")
				{
					var duplicatecategory = document.getElementById(duplicateMetricParentId).getElementsByTagName('li');
					//console.log("duplicatecategory: ",duplicatecategory);
					for(d = 1;d<duplicatecategory.length;d++)
					{
						if(duplicatecategory[d].getElementsByTagName("input")[0].id == checkedMetricId)
						{
							duplicatecategory[d].getElementsByTagName("a")[0].style.fontWeight="bold";
							duplicatecategory[d].getElementsByTagName("a")[0].style.color = "#000";
							duplicatecategory[d].getElementsByTagName("input")[0].checked = true;
							duplicatecategory[d].getElementsByTagName("input")[0].disabled = "";
							break;
						}
					}
					
				}
			}
		}
	    //console.log("defaultlist len: [",defaultlist);
	    as.SelectUnselectMultipleMetric(defaultlist,1);
	
		
		/*******************************************************************************************/
		/*select and clear all the metric under category*/
	$(".selectUnselectAll" ).click(function(){ 
		var selectionTagInput = this.getElementsByTagName("input")[0];
		var isChecked = selectionTagInput.checked;
		var allListMetrics = this.parentNode.getElementsByClassName("submenu");
		var currentParentId = this.parentNode.id;
		var duplicateMetricParentId;
		var metricList = []
		if(isChecked)
		{
			selectionTagInput.checked = false;
			this.getElementsByTagName("a")[0].innerHTML = "Select All"
				var index = 0;
			var i = 0;
				while(i < allListMetrics.length)
				{
				var inputTag = allListMetrics[i].getElementsByTagName("input")[0];
				var checkedMetricId = inputTag.id;
				
				if(inputTag.checked){
					inputTag.checked = false;
					inputTag.disabled = "disabled";
					allListMetrics[i].style.color = "#666";
					allListMetrics[i].style.fontWeight = "normal"

					metricList[index] = checkedMetricId.substr(5, checkedMetricId.length);
					index++;
					duplicateMetricParentId = as.getDuplicateMetricCategory(currentParentId,checkedMetricId);
					if(duplicateMetricParentId !== "")
					{
						var duplicatecategory = document.getElementById(duplicateMetricParentId).getElementsByTagName('li');
						for(d = 1;d<duplicatecategory.length;d++)
						{
							if(duplicatecategory[d].getElementsByTagName("input")[0].id == checkedMetricId)
							{
								duplicatecategory[d].getElementsByTagName("a")[0].style.fontWeight="normal";
								duplicatecategory[d].getElementsByTagName("a")[0].style.color = "#666";
								duplicatecategory[d].getElementsByTagName("input")[0].checked = false;
								break;
							}
						}
						
					}
				}
				i++;
			}
			console.log("metric list: ",metricList);
			as.SelectUnselectMultipleMetric(metricList,2);
		}
		else if (!isChecked)
		{
			selectionTagInput.checked = true;
			this.getElementsByTagName("a")[0].innerHTML = "Deselect All"
			var index = 0;
			var i = 0;
			//for(i = 0; i < allListMetrics.length; i++ )
			while(i < allListMetrics.length)
			{
				var inputTag = allListMetrics[i].getElementsByTagName("input")[0];
				var checkedMetricId = inputTag.id;

				if(!inputTag.checked){
					inputTag.checked = true;
					inputTag.disabled = "";
					allListMetrics[i].style.color = "#000";
					allListMetrics[i].style.fontWeight = "bold"
					metricList[index] = checkedMetricId.substr(5, checkedMetricId.length);
					index++;
					duplicateMetricParentId = as.getDuplicateMetricCategory(currentParentId,checkedMetricId);
					if(duplicateMetricParentId !== "")
					{
						var duplicatecategory = document.getElementById(duplicateMetricParentId).getElementsByTagName('li');
						for(d = 1;d<duplicatecategory.length;d++)
						{
							if(duplicatecategory[d].getElementsByTagName("input")[0].id == checkedMetricId)
							{
								duplicatecategory[d].getElementsByTagName("a")[0].style.fontWeight="bold";
								duplicatecategory[d].getElementsByTagName("a")[0].style.color = "#000";
								duplicatecategory[d].getElementsByTagName("input")[0].checked = true;
								duplicatecategory[d].getElementsByTagName("input")[0].disabled = "";
								break;
							}
						}
						
					}
				}
				i++;
			}
			console.log("metric list: ",metricList);
			as.SelectUnselectMultipleMetric(metricList,1);
		}
	});

	$(".backButton" ).click(function(){ 
		//console.log("back button pressed");
		var currentIdString = $(this).attr('id');
		var currentId = currentIdString.substr(19, 2);	 
		as.SelectUnselectMultipleMetric(currentId,3);
	});
	$(".nextButton" ).click(function(){ 
		//console.log("next button pressed");
		var currentIdString = $(this).attr('id');
		var currentId = currentIdString.substr(19, 2);	 
		as.SelectUnselectMultipleMetric(currentId,3);
	});
	 
/********************************************************************/
	/**COMMNETED BY MANIK**/
	/*$( "#MultipleMetricTitle").hover( function(){
		
		var sentinel=0;
		var h=0;
		while((h<300)&&(sentinel==0)){
			$( "#clickMultipleMetric"+h ).click(function(){
				sentinel=1; 
				 var currentIdString = $(this).attr('id');
				 var currentId = currentIdString.substr(19, 2);
				 console.log("What is this: "+currentId);
				 as.SelectUnselectMultipleMetric(currentId,3);
			});
			h++;
		}
		 
	});*/
	
		
	
	
	 /*$( "#timeline").hover( function(){
		 for(t=1950;t<2099;t++){ // The idea is to pick whatever possible year in the time line. Since it is dynamic I could not find a better way
			 $( "#click"+t ).click(function(){
				 
				 var year_in;
				 var currentId = $(this).attr('id');
				 year_in=currentId.substr(5, 4);
				// alert("as.currentind: "+as.currentind);
				 as.showHeatMapGraphReloaded(as.currentind,'#mbodyHeatMap',year_in);
				 //as.showHeatMapGraphReloaded(as.currentind,as.current_tab_style,'#mbodyHeatMap', year_in);
				
			});
		 }
		 
	});
	 $( "#timelinetable").hover( function(){
		 var isclicked = false;
		 for(t=1950;t<2099;t++){ // The idea is to pick whatever possible year in the time line. Since it is dynamic I could not find a better way
			 console.log("before table button cliecked....");
			 $( "#clicktable"+t ).click(function(){
				 console.log(" after table button cliecked....");
				 var year_in;
				 var currentId = $(this).attr('id');
				 year_in=currentId.substr(10, 4);
				
				// alert("as.currentind: "+as.currentind);
				 as.showMultipleMetricsStatesYears(year_in);
				 //as.showHeatMapGraphReloaded(as.currentind,as.current_tab_style,'#mbodyHeatMap', year_in);
				 isclicked = true;
			});
			 if(isclicked)
				 break;
		 }
		 
	});*/
	//var table=$("#mbodyMultipleQuery table");
	//console.log("table: ",table.html());
	fancyTable = function(obj)
	 {
		 console.log(obj.id," id ");
		 obj.dataTable( {
		        "aaSorting": [[ 4, "desc" ]]
		    } );
	 }
	$('.dropdown-toggle').dropdown();
	$('.dropdown-menu form input, .dropdown-menu label').click(function(e) {
        e.stopPropagation();
    });
	tableButtonClicked = function(obj, year_in)
	 { 	
		 as.showMultipleMetricsStatesYears(year_in);
		 return true;
		 
	 }
	 heatmapButtonClicked = function(year_in)
	 { 	
		 as.showHeatMapGraphReloaded(as.currentind,'#mbodyHeatMap',year_in);
	 }
	/**needed if selection states does not want in graph mode*/
	/*$(".graph-tab").click(function(){
		 
		 var id = this.id;
		 if(id =="heatmap-tab")
		 {
			//console.log("prev display:",document.getElementById("stateSelection").style.display);
			 $('#stateSelection').css("display","none");
		 }
		 else
		 {
			 //document.getElementById("stateSelection").style.display="inline";
			 $('#stateSelection').css("display","block");
			 //$('#stateSelection').css("margin-top","7px");
		 }
	});*/
	
	 //$("#myTable").tablesorter(); 
	// console.log("table", $('#myTable').HTML(),"jfghjh");
	 /*$('#myTable').dataTable( {
	        "aaSorting": [[ 4, "desc" ]]
	    } );*/
	 
};
