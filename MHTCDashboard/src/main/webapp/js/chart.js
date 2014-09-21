
/**
 * This module handles the display functionality of different type of charts.  
 */


var CM = (function($) {
	
	function Chart() {
		this.array_colors=["#1f77b4","#ff7f0e","#2ca02c","#d62728","#9467bd","#8c564b","#e377c2",
		                  "#7f7f7f","#bcbd22","#17becf","#F2F5A9","#FE2EF7","#6E6E6E","#F6CED8","#3B0B0B","#181907"];
		this.current_graph = 'line'; 
		this.current_graph_function = null; 
		this.graph_title_prefix = ''; 
		this.multiDataMultipleQuery=[];/**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
		this.kcounter;/**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
		this.kcounterexecute;/**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
		this.year_selected; /**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
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
	/*********************************************************************************************/
	/**************GET ONLY THE POSSIBLE YEARS ACCORDING TO THE METRICS SELECTED ****************/
	/*********************************************************************************************/					
	Chart.prototype.getMultipleYearsMetricState = function(states,multiDataMultipleQuery) {
		var array_years=[];
		var k=0;
		for(var j=0; j<states.length; j++){
			for(var i=0;i<multiDataMultipleQuery.length;i++){
				for(var w=0;w<multiDataMultipleQuery[i][j][0].dataPoints.length; w++){
					if(array_years.indexOf(multiDataMultipleQuery[i][j][0].dataPoints[w].year) < 0){
						array_years[k]=multiDataMultipleQuery[i][j][0].dataPoints[w].year;
						k++;
					}
				}
			}
		}
		return array_years;
	};

	Chart.prototype.showMultipleMetricsStatesYears = function(states,selected_multiple_metrics,year_in) {
		var query;
		this.year_selected=year_in;
		$("#mbodyMultipleQuery > *").remove();
		//document.getElementById("graphStatesMultipleQuery").innerHTML = states.join(", ");
		
		$("#mbodyMultipleQuery").append("<table id='myTable' class='table table-condensed' style='font-size: 13px; background-color:#fff'></table>");
		var table=$("#mbodyMultipleQuery table");
		if(selected_multiple_metrics.length>0){
			
			if(selected_multiple_metrics.length==1){ /*Similar to the one we had**/
				$("#yearsMultipleQuery").addClass("hidden");
				query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(selected_multiple_metrics[0]).getName());
				document.getElementById("graphTitleMultipleQuery").innerHTML = this.graph_title_prefix + Metrics.getMetricByID(selected_multiple_metrics[0]).getName();
				
				query.execute(function(multiData) {
			        setTimeout(function() {
			        	
		                var row="<th>&nbsp;</th>";
		                row = row + multiData[0][0].dataPoints.map(function(d) {
		                    return "<th>"+d["year"] + "</th>";
		                }).join("");
		                row = "<tr>" +row +"</tr>";
		                table.append(row);
		                var data = new Array();
		                for (var i = 0; i < multiData.length; i++) {
		                        row = "<th>" + multiData[i][0].state.name + "</th>" ;
		                    row = row + multiData[i][0].dataPoints.map(function(d) {
		                        return "<td>" +  d["value"] + "</td>";
		                    }).join("");
		                    row = "<tr>" +row +"</tr>";
			                table.append(row);
		                }
			        }, 500);
				});
			}else{
				/*var d = new Date();
			    var current_year = d.getFullYear();
			    var first_year =current_year-10;*/
				
				document.getElementById("graphTitleMultipleQuery").innerHTML = this.graph_title_prefix + "Multiple metrics selected";
					this.kcounterexecute=0;
					this.multiDataMultipleQuery=[];
					for(this.kcounter=0; this.kcounter<selected_multiple_metrics.length;this.kcounter++){
						query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(selected_multiple_metrics[this.kcounter]).getName());
						
						query.execute(function(multiData) {
							cm.multiDataMultipleQuery[cm.kcounterexecute]=multiData;
							cm.kcounterexecute++;
						});
					}
					
					setTimeout(function() {	
						
						
						var array_years=cm.getMultipleYearsMetricState(states,cm.multiDataMultipleQuery);
					    $("#yearsMultipleQuery").removeClass("hidden");
					    var sel = $("#yearsMultipleQuery");
		    			sel.empty();
		    			array_years.sort(function(a,b){return b - a;}); 
		    			if(cm.year_selected==-1)
		    				cm.year_selected=array_years[0];
		    			for(var k=0; k<array_years.length; k++){
		    				
		    				if(array_years[k]==cm.year_selected)
		    					sel.append('<option value="' + array_years[k] + '" selected>' + array_years[k] + '</option>');
		    				else
		       					sel.append('<option value="' + array_years[k] + '">' + array_years[k] + '</option>');
		    			}
						
						var row="<th>&nbsp;</th>";
						for(cm.kcounter=0;cm.kcounter<cm.multiDataMultipleQuery.length;cm.kcounter++){
							row = row + "<th>"+cm.multiDataMultipleQuery[cm.kcounter][0][0].metric.name+"</th>";
						}
						row = "<tr>" +row +"</tr>"; 
						table.append(row);
						
						var data = new Array();
						var current_state="-1";
						var band;
						for(var j=0; j<states.length; j++){
							if(current_state!=states[j]){
								current_state=states[j];
								band=false;
							}
							
							for(cm.kcounterexecute=0;cm.kcounterexecute<cm.multiDataMultipleQuery.length;cm.kcounterexecute++){
								
								for(var w=0;w<cm.multiDataMultipleQuery[cm.kcounterexecute][j][0].dataPoints.length; w++){
									
									if(cm.multiDataMultipleQuery[cm.kcounterexecute][j][0].dataPoints[w].year==cm.year_selected){
										if(!band){
											row = "<th>" + cm.multiDataMultipleQuery[cm.kcounterexecute][j][0].state.name + "</th>";
											band=true;
										}
											
										row = row +"<td>"+cm.multiDataMultipleQuery[cm.kcounterexecute][j][0].dataPoints[w].value+"</td>";
										
									}
									
								}
								
									
							}
							if(band){ 
								row = "<tr>" +row +"</tr>";
								table.append(row);
							}
						}
						
						
					}, 500);
				
				
				
			}	
				
				
					
		}else{
			var row="<tr><td valign='top' align='center'>Select at least one metric<td></tr>";
			table.append(row);
		}
			
	};
	/**
	 * This function display the different types of chart (line,bar or table) for selected state/s and metric.
	 */
    
	Chart.prototype.showMultiGraph = function(states) {
		
		   if (this.current_graph == 'table') {
		    	$("#mbody > *").remove();
	    	document.getElementById("graphTitle").innerHTML = this.graph_title_prefix + Metrics.getMetricByID(as.currentind).getName();
		    document.getElementById("graphStates").innerHTML = states.join(", ");
		    
	    	$("#mbody").append("<table id='myTable' class='table table-condensed' style='font-size: 13px; background-color:#fff'></table>");
	    	var table=$("#mbody table");
		    var query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(as.currentind).getName());
		    // Adding a Table view in chart 
		    query.execute(function(multiData) {
		        setTimeout(function() {
		                var chart;
		                var row="<th>&nbsp;</th>";
		                row = row + multiData[0][0].dataPoints.map(function(d) {
		                    return "<th>"+d["year"] + "</th>";
		                }).join("");
		                row = "<tr>" +row +"</tr>";
		                table.append(row);
		                var data = new Array();
		                for (var i = 0; i < multiData.length; i++) {
		                        row = "<th>" + multiData[i][0].state.name + "</th>" ;
		                    row = row + multiData[i][0].dataPoints.map(function(d) {
		                        return "<td>" +  d["value"] + "</td>";
		                    }).join("");
		                    row = "<tr>" +row +"</tr>";
			                table.append(row);
		                }    
		        }, 500);
		    });

		    
	    	
	    }else{
	    	
	    	$("#mbody > *").remove();
	    	if($("#mbody svg").length==0){
	    		$("#mbody").append( "<svg style=\"height: 50%; background-color:#fff\"></svg>" );
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
		                        key : multiData[i][0].state.abbr,
		                        color: cm.array_colors[i%cm.array_colors.length]
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
	/**
	 * Initializes the default Chart parameters.  
	 */
	publicInterface.create = function() {
		return new Chart();
	};
	
	return publicInterface;
	
}($));


