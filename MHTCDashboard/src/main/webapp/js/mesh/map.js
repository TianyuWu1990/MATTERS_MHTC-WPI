$(document).on("ready", function() {
	
	var peerStateStyles = {
			'WA': { 'fill' : "#BD1C2E" },
			'CA': { 'fill' : "#BD1C2E" },
			'UT': { 'fill' : "#BD1C2E" },
			'CO': { 'fill' : "#BD1C2E" },
			'TX': { 'fill' : "#BD1C2E" },
			'MN': { 'fill' : "#BD1C2E" },
			'IL': { 'fill' : "#BD1C2E" },
			'OH': { 'fill' : "#BD1C2E" },
			'VA': { 'fill' : "#BD1C2E" },
			'PA': { 'fill' : "#BD1C2E" },
			'MD': { 'fill' : "#BD1C2E" },
			'NJ': { 'fill' : "#BD1C2E" },
			'NY': { 'fill' : "#BD1C2E" },
			'CT': { 'fill' : "#BD1C2E" },
			'MA': { 'fill' : "#BD1C2E" }, 
	}
	
	$("#matters_map_canvas").usmap({
		stateHoverAnimation: 100,
		showLabels: true,
		stateHoverStyles: {'stroke-width': 3, 'stroke': '#fff'},
		
		stateStyles: { 'fill': "#5F0410", 'stroke-width': 1, 'stroke': '#000'},
		stateSpecificStyles: peerStateStyles,
		
		click: function(event, data) {				
			var stateProfile = StateProfileData[data.name];
			
			$("#matters_map_tooltip_name").html(stateProfile["Name"]);
			
			$("#metric_milken").html(stateProfile["Milken Tech and Science Index"]);
			$("#metric_tax_found").html(stateProfile["Tax Foundation Business Tax Index"]);
			$("#metric_cnbc").html(stateProfile["CNBC Top States for Business"]);
			$("#metric_demand").html(stateProfile["Hiring difficulty for tech rank"]);
			$("#metric_tech").html(stateProfile["Tech employment rank"]);
			$("#metric_bach").html(stateProfile["Bachelors degree holders rank"]);
			$("#metric_unemp").html(stateProfile["UI Premium rank"]);
			$("#metric_tax_burden").html(stateProfile["Tax burden per capita rank"]);
			
			
			
			
			$("#matters_map_tooltip_learnMore").attr("href", "profile?name=" + stateProfile["Name"]);
			$("#matters_map_tooltip").show();
			
			var tooltipX = event.originalEvent.clientX;
			var tooltipY = $("body").scrollTop();
								
			if (tooltipX < ($("body").width() / 2))
				tooltipX = $("#matters_map_canvas").width() + $("#matters_map_canvas").get(0).getBoundingClientRect().left - 350;
			else
				tooltipX = $("#matters_map_canvas").get(0).getBoundingClientRect().left - 250;
			
			if (tooltipX < 10)
				tooltipX = 10;
			
			$("#matters_map_tooltip").attr("style", "left: " + tooltipX + "px; top: " + tooltipY +"px;");
		},
	});
	
	$(window).on("resize", function(){
		var containerWidth = $("#matters_map_canvas").width();
		$("#matters_map_canvas > svg").width(containerWidth);	
		
		$("#matters_map_tooltip").hide();
	});
	
	$("#matters_map_tooltip_hide").on("click", function() {
		$("#matters_map_tooltip").hide();
	});
	
});