/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */

var global_timer = null;
var year_global=-1;

var globalcounter=0;

setTableWidth = function(window){}

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

$(document).ready(function() {
	
	$("#noJSError").hide();
	
	// Init menu
	$(".menu-close").prependTo(".menu > ul");
	   
	$(".menu-toggle").click(function(){
		$(".menu").slideDown();
	});

	$(".menu-close").click(function(){
		$(".menu").slideUp();
	});
	loadFunction();
});


var mobileMode = false;

loadFunction = function() {	

	cm = CM.create();
	as = AS.create();
	
	as.loadFunction();
	
	
	$(window).on('resize', function() {
//		if ($("#sidebar-left").hasClass("open")){
//			$(".DTFC_ScrollWrapper").attr("style", "width:"+ tableWidth +"px");
//		}
			// Perform resize functions on resize.
//		var tableLengthSidebarOpen = $(window).width() *0.7;
//		var tableLengthSidebarClose = $(window).width() *0.9;
		setTableWidth(window);
/*
		cm.refreshSizing(this);
		if ($(window).width() > 830)
		{
			mobileMode = false;
			var sidebarOpen = $("#sidebar-left").hasClass("open");
					
			var icon = $("#toggle-sidebar").find("i")[0];
			
			$("#metrics-trigger").removeClass("open");
			$("#states-trigger").removeClass("open");
			
			$("#metricSelectionCol").attr("style", "width: 250px;");
			$("#stateSelectionCol").attr("style", "width: 200px;");
				
			if (sidebarOpen)
			{
				$(icon).removeClass("fa-caret-right");
				$(icon).addClass("fa-caret-left");
				$("#scrollTest").attr("style", "position:relative; clear:both; height: 343px; top: 0px; left: 0px; overflow:hidden;width:" + tableLengthSidebarOpen.toString() + "px");	
			}
			else
			{
				$(icon).removeClass("fa-caret-left");
				$(icon).addClass("fa-caret-right");
				$("#scrollTest").attr("style", "position:relative; clear:both; height: 343px; top: 0px; left: 0px; overflow: hidden; width:" + tableLengthSidebarClose.toString() + "px");
			}
		}else {
			if (sidebarOpen)
			{
				$(icon).removeClass("fa-caret-right");
				$(icon).addClass("fa-caret-left");
				$("#scrollTest").attr("style", "position:relative; clear:both; height: 343px; top: 0px; left: 0px; overflow:hidden;width:" + tableLengthSidebarOpen.toString() + "px");
			}
			else
			{
				$(icon).removeClass("fa-caret-left");
				$(icon).addClass("fa-caret-right");
				$("#scrollTest").attr("style", "position:relative; clear:both; height: 343px; top: 0px; left: 0px; overflow: hidden; width:" + tableLengthSidebarClose.toString() + "px");
			}
			
			if (mobileMode == false){
				
				$("#sidebar-left").removeClass("open");
				$("#stateSelectionCol").removeClass("open");
				$("#metricSelectionCol").removeClass("open");
				$("#sidebar-left").attr("style", "margin-left:-450px;");
				$("#viewWrapper").attr("style", "left: 0px");
				mobileMode = true;
			}
		}*/
		
	});	
	
	
	setTableWidth = function(window){
		cm.refreshSizing(this);
		var tableLengthSidebarOpen = $(window).width() *0.7;
		var tableLengthSidebarClose = $(window).width() *0.9;
		
		if ($(window).width() > 830)
		{
			mobileMode = false;
			var sidebarOpen = $("#sidebar-left").hasClass("open");
					
			var icon = $("#toggle-sidebar").find("i")[0];
			
			$("#metrics-trigger").removeClass("open");
			$("#states-trigger").removeClass("open");
			
			$("#metricSelectionCol").attr("style", "width: 250px;");
			$("#stateSelectionCol").attr("style", "width: 200px;");
				
			if (sidebarOpen)
			{
				$(icon).removeClass("fa-caret-right");
				$(icon).addClass("fa-caret-left");
				$(".DTFC_ScrollWrapper").attr("style", "position:relative; clear:both; top: 0px; left: 0px; overflow:hidden;width:" + tableLengthSidebarOpen.toString() + "px");	
			}
			else
			{
				$(icon).removeClass("fa-caret-left");
				$(icon).addClass("fa-caret-right");
				$(".DTFC_ScrollWrapper").attr("style", "position:relative; clear:both; top: 0px; left: 0px; overflow: hidden; width:" + tableLengthSidebarClose.toString() + "px");
			}
		}else {
			if (sidebarOpen)
			{
				$(icon).removeClass("fa-caret-right");
				$(icon).addClass("fa-caret-left");
				$(".DTFC_ScrollWrapper").attr("style", "position:relative; clear:both; top: 0px; left: 0px; overflow:hidden;width:" + tableLengthSidebarOpen.toString() + "px");
			}
			else
			{
				$(icon).removeClass("fa-caret-left");
				$(icon).addClass("fa-caret-right");
				$(".DTFC_ScrollWrapper").attr("style", "position:relative; clear:both; top: 0px; left: 0px; overflow: hidden; width:" + tableLengthSidebarClose.toString() + "px");
			}
			
			if (mobileMode == false){
				
				$("#sidebar-left").removeClass("open");
				$("#stateSelectionCol").removeClass("open");
				$("#metricSelectionCol").removeClass("open");
				$("#sidebar-left").attr("style", "margin-left:-450px;");
				$("#viewWrapper").attr("style", "left: 0px");
				mobileMode = true;
			}
		}
	}
	
	if ($(window).width() < 830)
	{
		$("#sidebar-left").removeClass("open");
		$("#stateSelectionCol").removeClass("open");
		$("#metricSelectionCol").removeClass("open");
		$("#sidebar-left").attr("style", "margin-left:-450px;");
		$("#viewWrapper").attr("style", "left: 0px");
		mobileMode = true;
	}
	
    // Initializes button that toggles the sidebar
		
    $("#toggle-sidebar").click(function() {
    	
    	var icon = $(this).find("i")[0];
    	
    	if ($("#sidebar-left").hasClass("open"))
    	{    		
    		$("#viewWrapper").attr("style","left: 0px;");
    		
    		$("#sidebar-left").animate({ marginLeft: -$("#sidebar-left").width()}, 300);
    		
    		$("#viewWrapper").animate({ left: 0 }, 300, function() { $(window).trigger('resize'); });
    		
    		$("#sidebar-left").removeClass("open");
    		
    		$(icon).removeClass("fa-caret-left");
    		$(icon).addClass("fa-caret-right");
    	}
    	else
    	{	
    		$("#viewWrapper").attr("style","left:450px;");
    		$("#sidebar-left").animate({ marginLeft: 0}, 300);
    		
    		$("#viewWrapper").animate({ left: $("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#sidebar-left").addClass("open");
    		
    		$(icon).addClass("fa-caret-left");
    		$(icon).removeClass("fa-caret-right");
    	}
    	
    });
    
	$("#metrics-trigger").click(function(){
    	
		$("#metricSelectionCol").attr("style", "width: 100%");
		$("#stateSelectionCol").attr("style", "width: 0%");
		
    	if ($("#sidebar-left").hasClass("open") && !$("#metricSelectionCol").hasClass("open"))
    	{    
    		
    		$("#sidebar-left").animate({ marginLeft: 0}, 300);
    		
    		$("#viewWrapper").animate({ left: $("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#metrics-trigger").addClass("open");
    		$("#metricSelectionCol").addClass("open");
    		$("#sidebar-left").addClass("open");
    	}
    	else if ($("#sidebar-left").hasClass("open")&& $("#metricSelectionCol").hasClass("open"))
    	{	
    		
			$("#sidebar-left").animate({ marginLeft: -830}, 300);
    		
    		$("#viewWrapper").animate({ left: 0}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#metrics-trigger").removeClass("open");
    		$("#metricSelectionCol").removeClass("open");
    		$("#sidebar-left").removeClass("open");
    
    	}else if(!$("#sidebar-left").hasClass("open")&& !$("#metricSelectionCol").hasClass("open")){
    		$("#sidebar-left").animate({ marginLeft: 0}, 300);
    		
    		$("#viewWrapper").animate({ left: $("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#metrics-trigger").addClass("open");
    		$("#metricSelectionCol").addClass("open");
    		$("#sidebar-left").addClass("open");
    	}
    	else if(!$("#sidebar-left").hasClass("open")&& $("#metricSelectionCol").hasClass("open")){
    		$("#sidebar-left").animate({ marginLeft: 0}, 300);
    		
    		$("#viewWrapper").animate({ left: $("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#metrics-trigger").addClass("open");
    		$("#metricSelectionCol").addClass("open");
    		$("#sidebar-left").addClass("open");
    	}
    	
    	$("#states-trigger").removeClass("open");
    	$("#stateSelectionCol").removeClass("open");
	});
	
	$("#states-trigger").click(function(){
		$("#metricSelectionCol").attr("style", "width: 0%");
		$("#stateSelectionCol").attr("style", "width: 100%");
		
		if ($("#sidebar-left").hasClass("open") && !$("#stateSelectionCol").hasClass("open"))
    	{    
    		
    		$("#sidebar-left").animate({ marginLeft: 0}, 300);
    		
    		$("#viewWrapper").animate({ left: $("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#states-trigger").addClass("open");
    		$("#stateSelectionCol").addClass("open");
    		$("#sidebar-left").addClass("open");
    	}
    	else if ($("#sidebar-left").hasClass("open")&& $("#stateSelectionCol").hasClass("open"))
    	{	
    		
			$("#sidebar-left").animate({ marginLeft: -830}, 300);
    		
    		$("#viewWrapper").animate({ left: 0}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#states-trigger").removeClass("open");
    		$("#stateSelectionCol").removeClass("open");
    		$("#sidebar-left").removeClass("open");
    
    	}else if(!$("#sidebar-left").hasClass("open")&& !$("#stateSelectionCol").hasClass("open")){
    		$("#sidebar-left").animate({ marginLeft: 0}, 300);
    		
    		$("#viewWrapper").animate({ left: $("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#states-trigger").addClass("open");
    		$("#stateSelectionCol").addClass("open");
    		$("#sidebar-left").addClass("open");
    	}
    	else if(!$("#sidebar-left").hasClass("open")&& $("#stateSelectionCol").hasClass("open")){
    		$("#sidebar-left").animate({ marginLeft: 0}, 300);
    		
    		$("#viewWrapper").animate({ left: $("#sidebar-left").width()}, 300, function() { $(window).trigger('resize'); });
    		
    		$("#states-trigger").addClass("open");
    		$("#stateSelectionCol").addClass("open");
    		$("#sidebar-left").addClass("open");
    	}
		
		$("#metrics-trigger").removeClass("open");
    	$("#metricSelectionCol").removeClass("open");
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
	 
    var filterStates = function() {
    	// First, show all options
    	var allOptions = $(".stateSelectionOptionWrapper");
    	for(i = 0; i < allOptions.length; i++)
    	{
    		$(allOptions[i]).removeClass("hidden");
    	}
    	
    	var inputVal = $(".stateFilter > input").val().trim();
    	
    	var nonMatchingStates = States.filterStates(inputVal);
    	
    	for(i = 0; i < nonMatchingStates.length; i++)
    	{
    		var matchingText = "#" + nonMatchingStates[i].id + ".stateSelectionOptionWrapper";

    		$(matchingText).addClass("hidden");
    	}
    }
    
    // When the user filters the states...
    $(".stateFilter > input").on("input", function(e){
    	filterStates();
    });
    
    // Keyup event used as workaround for IE9 bug where backspace does not register as input
    // Note that there are still bugs with Cut and drag in IE9.
    $(".stateFilter > input").on("keyup", function(e){
    	filterStates();
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
	cm.refresh();
}