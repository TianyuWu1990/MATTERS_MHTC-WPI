toggleMultiselect = function(){
	$("#multiSelecter").toggle("slide",{direction:"right"},200);$("#sidebar").toggle("slide",{direction:"left"},200);
	$("#map").usmap('toggleMultiselect');
}
selectState = function(state){
	$('#map').usmap('select',state, false);
}
loadFunction = function(){
		$('#sidebar li:eq(0) a').tab('show');
		$(function () {
			        $("[rel='tooltip']").tooltip();
    			    });
		$( "#toggle" ).toggle( "slide");
		$('#map').usmap({
			'stateSpecificStyles': {
				'WA': {fill: 'MidnightBlue'},
				'CA': {fill: 'MidnightBlue'},
				'UT': {fill: 'MidnightBlue'},
				'CO': {fill: 'MidnightBlue'},
				'TX': {fill: 'MidnightBlue'},
				'MN': {fill: 'MidnightBlue'},
				'GA': {fill: 'MidnightBlue'},
				'NC': {fill: 'MidnightBlue'},
				'VA': {fill: 'MidnightBlue'},
				'MD': {fill: 'MidnightBlue'},
				'PA': {fill: 'MidnightBlue'},
				'NY': {fill: 'MidnightBlue'},
				'CT': {fill: 'MidnightBlue'},
				'NH': {fill: 'MidnightBlue'},
				'MA': {fill: 'green'}
			}
		});
}
addGraph = function(){
	setTimeout(function(){
		d3.json('data/cumulativeLineData.json', function(data) {
		nv.addGraph(function() {
		var chart = nv.models.cumulativeLineChart()
		.x(function(d) { return d[0] })
		.y(function(d) { return d[1]/100 }) //adjusting, 100% is 1.00, not 100 as it is in the data
		.color(d3.scale.category10().range())
		.useInteractiveGuideline(true)
		;
		 
		chart.xAxis
		.tickValues([1078030800000,1122782400000,1167541200000,1251691200000])
		.tickFormat(function(d) {
		return d3.time.format('%x')(new Date(d))
		});
		 
		chart.yAxis
		.tickFormat(d3.format(',.1%'));
		 
		d3.select('#mbody svg')
		.datum(data)
		.transition().
		duration(500)
		.call(chart);
		 
		//TODO: Figure out a good way to do this automatically
		nv.utils.windowResize(chart.update);
		 
		return chart;
		});
		});
	}, 500);
}

addMultiGraph = function(){
	setTimeout(function(){
		d3.json('data/mcumulativeLineData.json', function(data) {
		nv.addGraph(function() {
		var chart = nv.models.cumulativeLineChart()
		.x(function(d) { return d[0] })
		.y(function(d) { return d[1]/100 }) //adjusting, 100% is 1.00, not 100 as it is in the data
		.color(d3.scale.category10().range())
		.useInteractiveGuideline(true)
		;
		 
		chart.xAxis
		.tickValues([1078030800000,1122782400000,1167541200000,1251691200000])
		.tickFormat(function(d) {
		return d3.time.format('%x')(new Date(d))
		});
		 
		chart.yAxis
		.tickFormat(d3.format(',.1%'));
		 
		d3.select('#mmbody svg')
		.datum(data)
		.transition().
		duration(500)
		.call(chart);
		 
		//TODO: Figure out a good way to do this automatically
		nv.utils.windowResize(chart.update);
		 
		return chart;
		});
		});
	}, 500);
}