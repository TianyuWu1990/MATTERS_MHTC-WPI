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
		
		mouseover: function(event, data) {					
		},
		
		mouseout: function(event, data) {
		}		
	});
	
	$(window).on("resize", function(){
		var containerWidth = $("#matters_map_canvas").width();
		$("#matters_map_canvas > svg").width(containerWidth);		
	});
	
});