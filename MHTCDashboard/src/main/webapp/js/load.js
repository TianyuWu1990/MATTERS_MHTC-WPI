/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */

var global_timer = null;
var year_global=-1;

var globalcounter=0;
 
getStyleRuleValue=function(styleName, className) {

	    //for (var i=0;i<document.styleSheets.length;i++) {
	        var s = document.styleSheets[3];

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
	cm = CM.create();
	as = AS.create();
	
	as.loadFunction();
	
    // Initializes button that toggles the sidebar
		
    $("#toggle-sidebar").click(function() {
    	
    	var icon = $(this).find("i")[0];
    	
    	if ($("#sidebar-left").hasClass("open"))
    	{    				
    		$("#sidebar-left").animate({ marginLeft: -$("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#sidebar-left").removeClass("open");
    		
    		$(icon).removeClass("fa-caret-left");
    		$(icon).addClass("fa-caret-right");
    	}
    	else
    	{	
    		$("#sidebar-left").animate({ marginLeft: 0}, 300, function() { $(window).trigger('resize'); });
    		$("#sidebar-left").addClass("open");
    		
    		$(icon).addClass("fa-caret-left");
    		$(icon).removeClass("fa-caret-right");
    	}
    	
    });
    
    /**************************************************
     * Start Sidebar Metric Menus logic
     **************************************************/
    
    // Utility function to calculate the appropriate height of the metric list
    getSizeOfMetricList = function() {
    	var numMetricHeaders = $('.metricHeader').length;
    	var metricHeaderHeight = $('.metricHeader').first().outerHeight();
    	
    	var sidebarHeightTotal = $('#metricListWrapper').height();
    	
    	return sidebarHeightTotal - (numMetricHeaders * metricHeaderHeight);
    };
    
    // Utility function to open the given metric list.
    // This will close all other open metric lists
    openMetricList = function(metricList) {
    	var heightOfList = getSizeOfMetricList();
    	
    	$(".metricList.open").each(function(e) {
    		closeMetricList(this);
    	});
    	
    	$(metricList).animate({ height: heightOfList}, 300);
    	$(metricList).addClass("open");
    	
    };
    
    // Utility function to close the given metric list
    closeMetricList = function(metricList) {
    	$(metricList).animate({ height: 0}, 300);
    	$(metricList).removeClass("open");
    };
    
    // Make sure to resize the list when the window resizes
    $(window).on('resize', function() {
    	var listHeight = getSizeOfMetricList();
    	
    	$(".metricList.open").height(listHeight);
    });
    
    // Event listener to open/close metric lists on metric header click
    $('.metricHeader').click(function(e){
    	
    	var list = $(this).parent().find('.metricList').first();
    	
    	if (list.hasClass("open"))
    	{
    		closeMetricList(list);
    	}
    	else
    	{
    		openMetricList(list);
    	}    	
	});
    
    // Open national rankings by default...
    openMetricList($("#nationalProfileList"));
    
    /**************************************************
     * End Sidebar Metric Menus logic
     **************************************************/
    
    /******************************************
     * Start state selection logic
     ******************************************/
    
    // When a state selection option is clicked, designate it as selected
    $(".stateSelectionOption").click(function(e){
    	
    	// Check the clicked input if it has not already been checked.
    	var isCheckedAlready = $(this).hasClass("selected");
    	
    	if (!isCheckedAlready)
    		$(this).addClass("selected");
    	else
    		$(this).removeClass("selected");
    	    	
    	$(".selectPeerStates").removeClass("selected");
    	
    	updateStateSelection(); // Reflect changes in data
    });
    
    // When unselect/select all button is clicked, do the appropriate action
    $(".selectUnselectAllStates").click(function(e){
    	
    	var checked = $(this).attr("id") == "select";

    	// Go through and deselect/select all of the states
    	var stateOptions = $(".stateSelectionOption");
    	
    	for(i = 0; i < stateOptions.length; i++)
    	{
    		var curNode = stateOptions[i]
    		
    		if (checked)
    		{
    			$(curNode).addClass("selected");
    		}
    		else
    		{
    			$(curNode).removeClass("selected");
    		}
    	}
    	
    	$(".selectPeerStates").removeClass("selected");
    	
    	updateStateSelection(); // Reflect changes in data
    });
    
    // When select peers is clicked, select only the peer states.
    $(".selectPeerStates").click(function(e){
    	
    	if ($(".selectPeerStates").hasClass("selected"))
    	{
    		$(".selectUnselectAllStates").click(); // Force deselect
    		return;
    	}
    	
    	// Check peer states checkbox
    	$(".selectPeerStates").addClass("selected");
    	
    	// Deselect all states to start
    	var allStates = $(".stateSelectionOption");
    	
    	for(i = 0; i < allStates.length; i++)
    	{
    		$(allStates[i]).removeClass("selected");
    	}
    	
    	// Then go through and select only the peer states
    	var peerStates = States.getPeers();
    	
    	for(i = 0; i < peerStates.length; i++)
    	{
    		var curNode = "#" + peerStates[i].id + ".stateSelectionOption";
    		
    		$(curNode).addClass("selected");
    	}
    	
    	// Reflect updated state selection within data.
    	updateStateSelection();
    });
	 
    // When the user filters the states...
    $(".stateFilter > input").on("input", function(e){
    	// First, show all options
    	var allOptions = $(".stateSelectionOptionWrapper");
    	for(i = 0; i < allOptions.length; i++)
    	{
    		$(allOptions[i]).removeClass("hidden");
    	}
    	
    	var inputVal = $(this).val().trim();
    	
    	var nonMatchingStates = States.filterStates(inputVal);
    	
    	for(i = 0; i < nonMatchingStates.length; i++)
    	{
    		var matchingText = "#" + nonMatchingStates[i].id + ".stateSelectionOptionWrapper";

    		$(matchingText).addClass("hidden");
    	}
    	
    });
    
    /******************************************
     * End state selection logic
     ******************************************/
	
	/******************************************
     * Start metric selection logic
     ******************************************/
	
	$(".metricOption").click(function(){
		var isChecked = $(this).hasClass("selected");
		var checkedMetricId = $(this).attr("id");
		
		if(!isChecked)
		{		
			selectMetric(checkedMetricId);
			
			as.SelectUnselectMultipleMetric(checkedMetricId, 1);
		}
		else if (isChecked)
		{	
			unselectMetric(checkedMetricId);
			
			as.SelectUnselectMultipleMetric(checkedMetricId, 2);
		}
	});
	
	$(".selectUnselectAll" ).click(function() { 
    	var checked = $(this).attr("id") == "select";

    	var targetFunction = selectMetric;
    	var actionID = 1;
    	
    	if (!checked)
    	{
    		targetFunction = unselectMetric;
    		actionID = 2;
    	}
    	
    	// Go through and deselect/select all of the metrics
    	var metricOptions = $(this).parent().find(".metricOption");
    	var metricList = [];
    	
    	for(i = 0; i < metricOptions.length; i++)
    	{
    		var curNode = metricOptions[i]
    		var curNodeID = $(curNode).attr("id");
    		
    		metricList[i] = curNodeID;
    		
    		targetFunction(curNodeID);
    	}    	
    	
		as.SelectUnselectMultipleMetric(metricList, actionID);
	});
	
	$(".backButton" ).click(function(){ 
		var currentIdString = $(this).attr('id');
		//strip current Id from end of string
		prefix = "clickMultipleMetric";
		var currentId = currentIdString.substring(prefix.length);	 
		as.SelectUnselectMultipleMetric(currentId,3);
	});
	$(".nextButton" ).click(function(){ 
		var currentIdString = $(this).attr('id');
		prefix = "clickMultipleMetric";
		var currentId = currentIdString.substring(prefix.length);;	 
		as.SelectUnselectMultipleMetric(currentId,3);
	});
	
	/******************************************
     * End metric selection logic
     ******************************************/


	$('.dropdown-toggle').dropdown();
	$('.dropdown-menu form input, .dropdown-menu label').click(function(e) {
        e.stopPropagation();
    });
	
	$('#loginform').click(function () {
		console.log("login button clicked");
        if ($('#signin-dropdown').is(":visible")) {
            $('#signin-dropdown').hide()
			
        } else {
            $('#signin-dropdown').show()
        }
		return false;
    });
	$('#signin-dropdown').click(function(e) {
        e.stopPropagation();
    });
    $(document).click(function() {
        $('#signin-dropdown').hide();
    });
};

/**
 * Updates the list of selected states
 */
function updateStateSelection() 
{
	var stateOptions = $(".stateSelectionOption");
	
	// Get the list of all states that are checked
	var selectedItems = [];
	for (i = 0; i < stateOptions.length; i++)
	{
		if ($(stateOptions[i]).hasClass("selected"))
		{
			selectedItems.push($(stateOptions[i]).attr('id'));
		}
	}
	
	// Update the text of the select/unselect button based on how many states are selected
	if (selectedItems.length > 0)
	{
		$(".selectUnselectAllStates > a").html("Deselect All");
		$(".selectUnselectAllStates").attr("id", "deselect");
	}
	else
	{
		$(".selectUnselectAllStates > a").html("Select All");
		$(".selectUnselectAllStates").attr("id", "select");
	}
	
	// Update the data visualization
	as.setStatesSelected(selectedItems, -1); // -1 means set to list of selected states.
}

/**
 * Selects the given metric ID
 * @param metricID the ID of the metric to select
 */
function selectMetric(metricID)
{
	return selectUnselectMetricHelper(metricID, true);
}

/**
 * Unselects the given metric ID
 * @param metricID the ID of the metric to unselect
 */
function unselectMetric(metricID)
{
	return selectUnselectMetricHelper(metricID, false);
}

/**
 * Helper for selecting and unselecting metrics
 * @param metricID the ID of the metric to select/unselect
 * @param select whether or not the metric should be selected
 */
function selectUnselectMetricHelper(metricID, select)
{
	// Get the identifier for the metric in question
	var metricOption = $("#" + metricID + ".metricOption");
	
	// Either select or deselect, as appropriate
	if (select)
	{
		$(metricOption).addClass("selected");
	}
	else
	{
		$(metricOption).removeClass("selected");
	}

	
	// Metrics can be in 2 + categories. Make sure that we reflect the change to the metric selection in
	// all categories, if applicable.
	for (var i = 0; i < metricOption.length; i++)
	{
		var currMetricOption = metricOption[i];
		var parentUL = $(currMetricOption).parent().parent();
		
		// Update the select all text for the parent category
		var numberSelectedInCat = $(parentUL).find("a.selected").length;
		var selectAllButtonForCat = $(parentUL).find(".selectUnselectAll > a")[0];
		var selectAllWrapperForCat = $(parentUL).find(".selectUnselectAll")[0];
		
		
		if (numberSelectedInCat > 0)
		{
			$(selectAllButtonForCat).html("Deselect All");
			$(selectAllWrapperForCat).attr("id", "deselect");
		}
		else
		{
			$(selectAllButtonForCat).html("Select All");
			$(selectAllWrapperForCat).attr("id", "select");
		}	
	}
}

function tableButtonClicked(obj, year_in)
{ 	
	cm.selectYear(year_in);
}