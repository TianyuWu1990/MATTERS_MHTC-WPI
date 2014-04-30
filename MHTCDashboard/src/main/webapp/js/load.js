toggleMultiSelect = function(ind){
	$("#multiSelecter").toggle("slide",{direction:"right"},200);$("#sidebar").toggle("slide",{direction:"left"},200);
	$("#map").usmap('toggleMultiselect');
	dataIndex = ind;
}
selectState = function(state){
	$('#map').usmap('select',state, false);
	if(!multiMode)
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
				.y(function(d) { return d[1] }) //adjusting, 100% is 1.00, not 100 as it is in the data
				.color(d3.scale.category10().range())
				.useInteractiveGuideline(true)
				;
				 
				 var data = {key:currData.abbr};
				 data["values"] = currData.params[dataIndex].data.map(function(d){
				 	return [d["year"], d["value"]];
				 });
				chart.yAxis
				.tickFormat(d3.format(',.1%'));
				d3.select('#mbody svg')
				.datum([data])
				.transition().
				duration(500)
				.call(chart);
				 
				//TODO: Figure out a good way to do this automatically
				nv.utils.windowResize(chart.update);
				 
				return chart;
			});
	}, 500);
}

showMultiGraph = function(){
	document.getElementById("graphTitle").innerHTML = currData.params[dataIndex].name;
	document.getElementById("graphStates").innerHTML = selected.join(", ");
	getData("data/stats/query?states=" + selected.join(",") + "&metrics=" + currData.params[dataIndex].name, function(multiData){
		setTimeout(function(){
			nv.addGraph(function() {
				var chart = nv.models.cumulativeLineChart()
				.x(function(d) { return d[0] })
				.y(function(d) { return d[1] }) //adjusting, 100% is 1.00, not 100 as it is in the data
				.color(d3.scale.category10().range())
				.useInteractiveGuideline(true)
				;
				 
				 var data = new Array();
				 for(var i = 0; i < multiData.length; i++)
				 {
				 	data[i] = {key:multiData[i].abbr};
					data[i]["values"] = multiData[i].params[0].data.map(function(d){
				 	return [d["year"], d["value"]];
				 	});
				 }
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
		}, 500);
	});
}

showPeerGraph = function(ind){
	dataIndex = ind;
	document.getElementById("graphTitle").innerHTML = currData.params[dataIndex].name;
	document.getElementById("graphStates").innerHTML = "All Peer States";
	getData("data/stats/query?states=CA,CO,CT,GA,MD,MA,MN,NH,NJ,NY,NC,PA,TX,UT,VA,WA&metrics=" + currData.params[dataIndex].name, function(multiData){
		setTimeout(function(){
			nv.addGraph(function() {
				var chart = nv.models.cumulativeLineChart()
				.x(function(d) { return d[0] })
				.y(function(d) { return d[1]}) //adjusting, 100% is 1.00, not 100 as it is in the data
				.color(d3.scale.category10().range())
				.useInteractiveGuideline(true)
				;
				 
				 var data = new Array();
				 for(var i = 0; i < multiData.length; i++)
				 {
				 	data[i] = {key:multiData[i].abbr};
					data[i]["values"] = multiData[i].params[0].data.map(function(d){
				 	return [d["year"], d["value"]];
				 	});
				 }
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
		}, 500);
	});
}

showTopGraph = function(ind){
	dataIndex = ind;
	document.getElementById("graphTitle").innerHTML = currData.params[dataIndex].name;
	document.getElementById("graphStates").innerHTML = "Top Ten States";
	getData("data/stats/query?states=CA,CO,CT,GA,MD,MA,MN,NH,NJ,NY,NC,PA,TX,UT,VA,WA&metrics=" + currData.params[dataIndex].name, function(multiData){
		setTimeout(function(){
			nv.addGraph(function() {
				var chart = nv.models.cumulativeLineChart()
				.x(function(d) { return d[0] })
				.y(function(d) { return d[1]}) //adjusting, 100% is 1.00, not 100 as it is in the data
				.color(d3.scale.category10().range())
				.useInteractiveGuideline(true)
				;
				 

				 var data = new Array();
				 for(var i = 0; i < multiData.length; i++)
				 {
				 	data[i] = {key:multiData[i].abbr};
					data[i]["values"] = multiData[i].params[0].data.map(function(d){
				 	return [d["year"], d["value"]];
				 	});
				    data[i]["values"].sort(compareYear);
				 }
				 data.sort(compareValue);
				 data.splice(10,16);
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
		}, 500);
	});	
}

function compareYear(a,b) {
  if (a.year < b.year)
     return -1;
  if (a.year > b.year)
    return 1;
  return 0;
}

function compareValue(a,b) {
  if (a.values[0].value < b.values[0].value)
     return -1;
  if (a.values[0].value > b.values[0].value)
    return 1;
  return 0;
}

showBottomGraph = function(ind){
	dataIndex = ind;
	document.getElementById("graphTitle").innerHTML = currData.params[dataIndex].name;
	document.getElementById("graphStates").innerHTML = "Bottom Ten States";
	getData("data/stats/query?states=CA,CO,CT,GA,MD,MA,MN,NH,NJ,NY,NC,PA,TX,UT,VA,WA&metrics=" + currData.params[dataIndex].name, function(multiData){
		setTimeout(function(){
			nv.addGraph(function() {
				var chart = nv.models.cumulativeLineChart()
				.x(function(d) { return d[0] })
				.y(function(d) { return d[1]}) //adjusting, 100% is 1.00, not 100 as it is in the data
				.color(d3.scale.category10().range())
				.useInteractiveGuideline(true)
				;
				 

				 var data = new Array();
				 for(var i = 0; i < multiData.length; i++)
				 {
				 	data[i] = {key:multiData[i].abbr};
					data[i]["values"] = multiData[i].params[0].data.map(function(d){
				 	return [d["year"], d["value"]];
				 	});
				 	data[i]["values"].sort(compareYear);
				 }
				 data.sort(compareValue);
				 data.reverse();
				 data.splice(10,16);

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
		}, 500);
	});	
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
	var metrics = stateData[0].params;
        document.getElementById('National-tbody').innerHTML = '';
        document.getElementById('Talent-tbody').innerHTML = '';
        document.getElementById('Economy-tbody').innerHTML = '';
        document.getElementById('Cost-tbody').innerHTML = '';
        for(var i = 0; i < metrics.length; i++){
		
                var tr = document.createElement('tr');
                var name = document.createElement('td');
                name.appendChild(document.createTextNode(metrics[i].name));
                var trend = document.createElement('td');
                var trendSp = document.createElement('span');
                trendSp.setAttribute("class","trend_icon trend_"+metrics[i].trend);
                trend.appendChild(trendSp);
                var avg = document.createElement('td');
		if(metrics[i].binName == "National"){
			var innerhtml = "<table class=\"table table-condensed\"	style=\"font-size: 13px;\"><tr><td>Rank</td>";
			for(var j = 0; j < metrics[i].data.length; j++){
				innerhtml += "<td>" + metrics[i].data[j].value + "</td>";
			}
			innerhtml += "</tr><tr><td>Year</td>";
			for(var j = 0; j < metrics[i].data.length; j++){
				innerhtml += "<td>" + metrics[i].data[j].year + "</td>";
			}
			innerhtml += "</tr></table>";
			avg.innerHTML = innerhtml;
			//{#data}{/data}</tr><tr><td>Year</td>{#data}d>'{year}</td>{/data}</tr>	</table>"
		}else{
	                avg.appendChild(document.createTextNode(metrics[i].dataAverage));
		}
                var src = document.createElement('td');
                var srcLink = document.createElement('a');
                srcLink.href = metrics[i].urlFrom;
                srcLink.appendChild(document.createTextNode(metrics[i].sourceName));
                var dropDown = document.createElement('tr');
                dropDown.innerHTML = "<div class=\"btn-group btn-group-sm\"><button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"glyphicon glyphicon-chevron-down\"></span></button><ul class=\"dropdown-menu\" role=\"menu\"><li><a  data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showPeerGraph(" + i + ")\">Compare to Peer States</a></li><li><a href=\"#\" onClick=\"toggleMultiSelect(" + i + ")\">Compare to Select States</a></li><li><a data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showTopGraph(" + i + ")\">Compare to Top Ten</a></li><li><a data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showBottomGraph(" + i + ")\">Compare to Bottom Ten</a></li><li><a data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showGraph("+i+")\">View Graph</a></li></ul></div>";
                src.appendChild(srcLink);
                tr.appendChild(name);
                if(metrics[i].binName != "National")
                	tr.appendChild(trend);
                tr.appendChild(avg);
                tr.appendChild(src);
                tr.appendChild(dropDown);
                document.getElementById(metrics[i].binName + "-tbody").appendChild(tr);
        }
}
