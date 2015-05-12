$(document).on("ready", function() {
	
	var peerStateStyles = {
			'WA': { 'fill' : "#AD3852" },
			'CA': { 'fill' : "#AD3852" },
			'UT': { 'fill' : "#AD3852" },
			'CO': { 'fill' : "#AD3852" },
			'TX': { 'fill' : "#AD3852" },
			'MN': { 'fill' : "#AD3852" },
			'IL': { 'fill' : "#AD3852" },
			'OH': { 'fill' : "#AD3852" },
			'VA': { 'fill' : "#AD3852" },
			'PA': { 'fill' : "#AD3852" },
			'MD': { 'fill' : "#AD3852" },
			'NJ': { 'fill' : "#AD3852" },
			'NY': { 'fill' : "#AD3852" },
			'CT': { 'fill' : "#AD3852" },
			'MA': { 'fill' : "#AD3852" },
	}
	
	$("#matters_map_canvas").usmap({
		stateHoverAnimation: 100,
		showLabels: true,
		stateHoverStyles: {'stroke-width': 3, 'stroke': '#fff'},
		
		stateStyles: { 'fill': "#631021", 'stroke-width': 1, 'stroke': '#000'},
		stateSpecificStyles: peerStateStyles,
		
		click: function(event, data) {				
			var stateProfile = StateProfileData[data.name];
			
			$("#matters_map_tooltip_name").html(stateProfile["Name"]);
			
			$("#metric_cnbc").html(stateProfile["CNBC Top States for Business"]);
			$("#metric_milken").html(stateProfile["Milken Science and Tech Index"]);
			$("#metric_tax_found").html(stateProfile["Tax Foundation Business Tax Index"]);
			$("#metric_tax_burden").html(stateProfile["Tax burden per capita rank"]);
			$("#metric_tech").html(stateProfile["Tech employment rank"]);
			$("#metric_unemp").html(stateProfile["Unempl insurance rank"]);
			$("#metric_bach").html(stateProfile["Bachelors degree holders rank"]);
			$("#metric_demand").html(stateProfile["Key tech demand hiring rank"]);
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