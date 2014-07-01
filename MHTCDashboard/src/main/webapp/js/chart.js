
// list of potential inputs, customizations, variabilities
// line vs bar vs etc etc


var CM = (function($) {
	
	function Chart() {

		this.current_graph = 'line'; 
		this.current_graph_function = null; 
		this.graph_title_prefix = ''; 
		
	   $("#graph_toggle").click(function(e) {
	       if (cm.current_graph == 'line') {
	           cm.current_graph = 'bar';
	           $("#graph_toggle").html("Switch to Line");
	       } else /*if (cm.current_graph == 'bar')*/ {
	           cm.current_graph = 'line';
	           $("#graph_toggle").html("Switch to Bar");
	       }
	       
	    	   
	       
	       cm.showMultiGraph(as.selected);
	   });
	   
	};
	
	

	Chart.prototype.showMultiGraph = function(states) {
		    if (this.current_graph == 'table') {
	    	$("#mbody > *").remove();
	    	document.getElementById("graphTitle").innerHTML = this.graph_title_prefix + Metrics.getMetricByID(as.currentind).getName();
		    document.getElementById("graphStates").innerHTML = states.join(", ");
		    
	    	$("#mbody").append("<table class='table table-condensed' style='font-size: 13px;'></table>");
	    	var table=$("#mbody table");
		    var query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(as.currentind).getName());
		    query.execute(function(
		            multiData) {
		        setTimeout(function() {
		                var chart;
		                var row="<th>&nbsp;</th>"

		                row = row + multiData[0][0].dataPoints.map(function(d) {
		                    return "<th>"+d["year"] + "</th>";
		                }).join("");
		                row = "<tr>" +row +"</tr>"
		                table.append(row);

		                var data = new Array();
		                for (var i = 0; i < multiData.length; i++) {
		                        row = "<th>" + multiData[i][0].state.name + "</th>" ;
		                    row = row + multiData[i][0].dataPoints.map(function(d) {
		                        return "<td>" +  d["value"] + "</td>";
		                    }).join("");
		                    row = "<tr>" +row +"</tr>"
			                table.append(row);
		                }
		                
		                
		        }, 500);
		    });


	    	
	    }
	    else
	    {
	    	$("#mbody > *").remove();
	    	
	    	if($("#mbody svg").length==0){
	    	

	    		$("#mbody").append( "<svg style=\"height: 50%;\"></svg>" );
	    		
	    	}
		    	
		    d3.selectAll("#mbody svg > *").remove();
		    
		    document.getElementById("graphTitle").innerHTML = this.graph_title_prefix + Metrics.getMetricByID(as.currentind).getName();
		    document.getElementById("graphStates").innerHTML = states.join(", ");
		    
		    var query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(as.currentind).getName());
		    query.execute(function(
		            multiData) {
		        setTimeout(function() {
		            nv.addGraph(function() {
		                var chart;
		                if (cm.current_graph == 'line') {
		                    chart = nv.models.lineChart()
		                    .useInteractiveGuideline(true);
		                } else if (cm.current_graph == 'bar') {
		                    chart = nv.models.multiBarChart();
		                }
		                chart.margin({
		                    left : 100
		                }).x(function(d) {
		                    return d[0]
		                }).y(function(d) {
		                    return d[1]
		                }) // adjusting, 100% is 1.00, not 100 as it is in the data
		                .color(d3.scale.category10().range())
	

		                var xtickvalues = multiData[0][0].dataPoints.map(function(d) {
		                    return d["year"];
		                });
		                
		                chart.xAxis.axisLabel("Year").tickValues(xtickvalues).tickFormat(d3.format('.0f'));
	
		                var type_var=Metrics.getMetricByID(as.currentind).getType();
		                if (type_var == "integer") {
					           chart.yAxis.axisLabel("Count").tickFormat(d3.format(',.0f'));
			                } else if (type_var == "rank") {
			                    chart.yAxis.axisLabel("Ranking out of 50 States").tickFormat(d3.format('.0f'));
			                } else if (type_var == "percentage") {
			                    chart.yAxis.axisLabel("%").tickFormat(d3.format(',.2%'));
			                } else if (type_var == "numeric") {
			                    chart.yAxis.axisLabel("Value").tickFormat(d3.format(',.2f'));
			                } else if (type_var == "currency") {
			                    chart.yAxis.axisLabel("$").tickFormat(d3.format('$,.2'));
			                }
		                var data = new Array();
		                for (var i = 0; i < multiData.length; i++) {
		                    data[i] = {
		                        key : multiData[i][0].state.abbr
		                    };
		                    data[i]["values"] = multiData[i][0].dataPoints.map(function(d) {
		                        return [ d["year"], d["value"] ];
		                    });
		                }
		                d3.select('#mbody svg').datum(data).transition().duration(500).call(chart);
	
		                // TODO: Figure out a good way to do this automatically
		                nv.utils.windowResize(chart.update);
	
		                return chart;
		            });
		        }, 500);
		    });
		  }
	   
	}
	
	var publicInterface = {};
	
	publicInterface.create = function() {
		return new Chart();
	};
	
	return publicInterface;
	
}($));


