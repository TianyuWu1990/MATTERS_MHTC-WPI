toggleMultiselect = function(){
	$("#multiSelecter").toggle("slide",{direction:"right"},200);$("#sidebar").toggle("slide",{direction:"left"},200);
	$("#map").usmap('toggleMultiselect');
}
selectState = function(state){
	$('#map').usmap('select',state, false);
	loadState(state);
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
		getData("data/stats/query?states=MA&metrics=all", function(data) { currData = data[0]; }); 
}
var dataIndex = 0;
showGraph = function(ind){
	dataIndex = ind;
	document.getElementById("graphTitle").innerHTML = currData.params[ind].name;
	document.getElementById("graphStates").innerHTML = currData.name;
	setTimeout(function(){
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
				 var data = currData.params[dataIndex].data.map(function(d){
				 	return {
				 		year: d["year"],
				 		value: d["value"]
				 	};
				 });
				chart.yAxis
				.tickFormat(d3.format(',.1%'));
				 console.log("Data Index: " + dataIndex);
				 console.log("CurrData: " + currData);
				 console.log(data);
				d3.select('#mbody svg')
				.datum(data)
				.transition().
				duration(500)
				.call(chart);
				 
				//TODO: Figure out a good way to do this automatically
				nv.utils.windowResize(chart.update);
				 
				return chart;
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
		 console.log(data);
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

function getData(url, callback){
	http = new XMLHttpRequest();
	http.open("GET", url, true);
	http.onreadystatechange = function() { 
		if(http.readyState == 4 && http.status == 200) { 
			callback(JSON.parse(http.responseText)); 
	} }
	http.send(null);
}

function loadState(stateAbbr){
	getData("data/stats/query?states="+stateAbbr+"&metrics=all", loadData);
}

var currData = "";
function loadData(stateData){
        currData = stateData[0];
        var toLoad = document.getElementById("national_table_body");
        toLoad.innerHTML = '';
        var metrics = stateData[0].params;
        for(var i = 0; i < metrics.length; i++){
                var tr = document.createElement('tr');
                var name = document.createElement('td');
                name.appendChild(document.createTextNode(metrics[i].name));
                var trend = document.createElement('td');
                var trendSp = document.createElement('span');
                trendSp.setAttribute("class","trend_icon trend_"+metrics[i].trend);
                trend.appendChild(trendSp);
                var avg = document.createElement('td');
                avg.appendChild(document.createTextNode(metrics[i].dataAverage));
                var src = document.createElement('td');
                var srcLink = document.createElement('a');
                srcLink.href = metrics[i].urlFrom;
                srcLink.appendChild(document.createTextNode(metrics[i].sourceName));
                var dropDown = document.createElement('tr');
                dropDown.innerHTML = "<div class=\"btn-group btn-group-sm\"><button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"glyphicon glyphicon-chevron-down\"></span></button><ul class=\"dropdown-menu\" role=\"menu\"><li><a href=\"#\">Compare to Peer States</a></li><li><a href=\"#\" onClick=\"toggleMultiselect()\">Compare to Select States</a></li><li><a href=\"#\">Compare to Top Ten</a></li><li><a href=\"#\">Compare to Bottom Ten</a></li><li><a data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showGraph("+i+")\">View Graph</a></li><li class=\"divider\"></li><li><a href=\"#\">Open Source</a></li></ul></div>";
                src.appendChild(srcLink);
                tr.appendChild(name);
                tr.appendChild(trend);
                tr.appendChild(avg);
                tr.appendChild(src);
                tr.appendChild(dropDown);
                toLoad.appendChild(tr);
        }
}