$(document).on("ready", function() {
	
	var peerStateStyles = {
			'WA': { 'fill' : "#b41432" },
			'CA': { 'fill' : "#b41432" },
			'UT': { 'fill' : "#b41432" },
			'CO': { 'fill' : "#b41432" },
			'TX': { 'fill' : "#b41432" },
			'MN': { 'fill' : "#b41432" },
			'IL': { 'fill' : "#b41432" },
			'OH': { 'fill' : "#b41432" },
			'VA': { 'fill' : "#b41432" },
			'PA': { 'fill' : "#b41432" },
			'MD': { 'fill' : "#b41432" },
			'NJ': { 'fill' : "#b41432" },
			'NY': { 'fill' : "#b41432" },
			'CT': { 'fill' : "#b41432" },
			'MA': { 'fill' : "#b41432" },
	}
	
	$("#matters_map_canvas").usmap({
		stateHoverAnimation: 100,
		showLabels: true,
		stateHoverStyles: {'stroke-width': 3, 'stroke': '#fff'},
		
		stateStyles: { 'fill': "#680017", 'stroke-width': 1, 'stroke': '#000'},
		stateSpecificStyles: peerStateStyles,
		
		mouseover: function(event, data) {					
		},
		
		mouseout: function(event, data) {
		},		
	});
	
	$(window).on("resize", function(){
		var containerWidth = $("#matters_map_canvas").width();
		
		$("#matters_map_canvas > svg").width(containerWidth);		
	});
	
});