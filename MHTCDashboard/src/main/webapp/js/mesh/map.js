$(document).on("ready", function() {
	
	var peerStateStyles = {
			'WA': { 'fill' : "#ff4444" },
			'CA': { 'fill' : "#ff4444" },
			'UT': { 'fill' : "#ff4444" },
			'CO': { 'fill' : "#ff4444" },
			'TX': { 'fill' : "#ff4444" },
			'MN': { 'fill' : "#ff4444" },
			'IL': { 'fill' : "#ff4444" },
			'OH': { 'fill' : "#ff4444" },
			'VA': { 'fill' : "#ff4444" },
			'PA': { 'fill' : "#ff4444" },
			'MD': { 'fill' : "#ff4444" },
			'NJ': { 'fill' : "#ff4444" },
			'NY': { 'fill' : "#ff4444" },
			'CT': { 'fill' : "#ff4444" },
			'MA': { 'fill' : "#ff4444" },
	}
	
	$("#matters_map_canvas").usmap({
		stateHoverAnimation: 100,
		showLabels: true,
		stateHoverStyles: {'stroke-width': 3, 'stroke': '#fff'},
		
		stateStyles: { 'fill': "#680017", 'stroke-width': 1, 'stroke': '#000'},
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
			
			var tooltipX = event.originalEvent.clientX - 250;
			var tooltipY = event.originalEvent.clientY - 220 + $("body").scrollTop();
								
			console.log(tooltipX);
			console.log($("#matters_map_canvas").width());
			if (tooltipX > $("#matters_map_canvas").width() - 150)
				tooltipX = tooltipX - 400;
			
			$("#matters_map_tooltip").attr("style", "left: " + tooltipX + "px; top: " + tooltipY +"px;");
		},
	});
	
	$(window).on("resize", function(){
		var containerWidth = $("#matters_map_canvas").width();
		$("#matters_map_canvas > svg").width(containerWidth);		
	});
	
	$("#matters_map_tooltip_hide").on("click", function() {
		$("#matters_map_tooltip").hide();
	});
	
});