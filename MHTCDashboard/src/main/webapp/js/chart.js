 /*
  * Copyright (C) 2013 Worcester Polytechnic Institute
  * All Rights Reserved. 
  *  
 */  
/**
 * This module handles the display functionality of different type of charts.  
 */

var dt;
var CM = (function($) {
	
	/**
	 * Constructor
	 */
	function Chart() {
		this.array_colors=["#F90101","#F2B50F","#00933B","#0266C8","#603CBA","#1E7145","#9F00A7",
		                  "#7f7f7f","#bcbd22","#17becf","#F2F5A9","#FE2EF7","#6E6E6E","#F6CED8","#3B0B0B","#181907"];
		
		/**************************
		 * Visualization Types
		 **************************/
		this.visualizationTypes = {
				TABLE : 0,
				LINE : 1,
				BAR : 2,
				HEATMAP : 3
		}
		
		this.currentVisualization = this.visualizationTypes.TABLE;
		
		this.yearSelected = -1;
	};
	
	/**
	 * Sets the table as the currently displayed visualization and displays it.
	 */
	Chart.prototype.displayTable = function() {
		this.currentVisualization = this.visualizationTypes.TABLE;
		
		this.refresh();
	};
	
	/**
	 * Sets the line graph as the currently displayed visualization and displays it.
	 */
	Chart.prototype.displayLineGraph = function() {
		this.currentVisualization = this.visualizationTypes.LINE;
		
		this.refresh();
	};
	
	/**
	 * Sets the bar graph as the currently displayed visualization and displays it.
	 */
	Chart.prototype.displayBarGraph = function() {
		this.currentVisualization = this.visualizationTypes.BAR;
		
		this.refresh();
	};
	
	/**
	 * Sets the heat map as the currently displayed visualization and displays it.
	 */
	Chart.prototype.displayHeatMap = function() {
		this.currentVisualization = this.visualizationTypes.HEATMAP;
		
		this.refresh();
	};
	
	/**
	 * Refreshes the currently displayed visualization/chart.
	 */
	Chart.prototype.refresh = function() {
		switch (this.currentVisualization) 
		{
			case this.visualizationTypes.TABLE:
				this.refreshTable();
				break;
			case this.visualizationTypes.LINE:
				this.refreshGraphs();
				break;
			case this.visualizationTypes.BAR:
				this.refreshGraphs();
				break;
		}
	};
	
	/**
	 * Sets the currently selected year for the chart. 
	 * Note: Does not refresh the charts to display the year. This must be done
	 * manually with refresh()
	 * @param year The year to select
	 */
	Chart.prototype.selectYear = function(year) {
		this.yearSelected = year;
	};
	
	/**
	 * Resets the currently selected year for the chart to -1.
	 * This means that the year will default to the most recent year upon refresh.
	 * Note: Does not refresh the charts to display this year. This must be done manually
	 * with refresh()
	 */
	Chart.prototype.resetYear = function() {
		this.yearSelected = -1;
	};
	
	/**
	 * Refreshes the table view based on the currently selected states and metrics in the App State.
	 */
	Chart.prototype.refreshTable = function() {
		
		var selectedStates = as.getSelectedStates();
		var selectedMetrics = as.getSelectedMetrics();
				
		// Clear table
		$("#mbodyMultipleQuery > *").remove();
		
		// Add new table
		$("#mbodyMultipleQuery").append("<table id='myTable' class='table '  style='font-size: 13px; background-color:#fff'></table>");
		
		// Select the new table for future manipulation
		var table = $("#mbodyMultipleQuery table");

		if(selectedMetrics.length > 0) {	
			if(selectedMetrics.length == 1) { // If we only have one metric to load
				
				// Hide timeline..with 1 metric we display all years for that metric.
				$("#yearsMultipleQuery").addClass("hidden");
				$("#timelinetable").addClass("hidden");
				
				// Query for appropriate data
				var query = DQ.create().addState(selectedStates).addMetric(Metrics.getMetricByID(selectedMetrics[0]).getName());
				
				query.execute(function(multiData) {
			        	var yearsForMetric = cm.getYearsMetricState(selectedStates, multiData); // Get the years that the metric exists for from the data
			        	
			        	if(yearsForMetric.length==0) { // If theres no data for the metric...
			        		 table.append("<tr><td>No data available for your current selection.</td></tr>");
			        	} else {
			        		
			        		// Create single metric header
			        		var row = "<thead><th>State</th>";
			        		
			        		for(var i = 0; i < yearsForMetric.length; i++) {			        			
			        			 row = row + "<th>" + yearsForMetric[i] + "</th>";
			        		}
			        		
			        		row = row + "</thead>";
				            table.append(row); // Append header
				             
			                for (var i = 0; i < multiData.length; i++)
			                {
			                    row = "<th>" + multiData[i][0].state.abbr + "</th>" ;

			                    for (var j = 0; j < yearsForMetric.length; j++)
			                    {
			                    	if(multiData[i][0].dataPoints.length==0)
			                    	{
			                    		row = row +"<td> N/A </td>"; 
			                    	}
			                    	else
			                    	{
			                    		// Format the data based on type.
			                    		var formattedData = cm.getFormattedMetricValue(multiData[i][0].metric.type, 
			                    				multiData[i][0].dataPoints[j].value);
			                    		row = row + "<td>" + formattedData + "</td>";
			                    	}
			                    }
			                    row = "<tr>" +row +"</tr>";
				                table.append(row);
			                }
			        	}
			        	cm.setDataTable();
				});
			}
			else	// Multiple metrics
			{					
					var processedMetrics = selectedMetrics.map(function(e) { return Metrics.getMetricByID(e).getName(); });
					
					query = DQ.create().addState(selectedStates).addMultipleMetrics(processedMetrics);
					
					query.execute(function(multiData) {
						
						if(multiData.length == 0)
							return; // Do nothing if we got no data back.
						
						var yearsForMetrics = cm.getYearsWhereDataExistsForMultipleMetrics(multiData);
						
						// Build the timeline
						// Show the years table
					    $("#timelinetable").removeClass("hidden");
					    
					    yearsForMetrics.sort(function(a,b) {return b - a;} );    
					    
					    // Set the selected year
					    if(cm.yearSelected == -1)
		    				cm.yearSelected = yearsForMetrics[0];
			
		    			var timeLine = $("#timelinetable");
		    			timeLine.empty();
		    			
						var timeLineHTML = '<table align="center"><tr><td></td><td><ul class="timelineListStyle">';
						for(var k = yearsForMetrics.length - 1; k >= 0; k--){
							if(cm.yearSelected != yearsForMetrics[k]){
								timeLineHTML += '<li ><button class="" id="tableTimeLineButton" onClick="return tableButtonClicked(this,'+yearsForMetrics[k]+')" >'+yearsForMetrics[k]+'</button></li>';
							}else{
								timeLineHTML += '<li id="clicktable'+cm.yearSelected+'"><button class="active"  id="tableTimeLineButton" >'+cm.yearSelected+'</button></li>';
							}
						}
						
						timeLineHTML += '</ul ></td></tr></table>';
						timeLine.append(timeLineHTML);
						// Finish building the timeline
						
						// Build Header
						var row = "<th>State</th>";
						
						for(var r = 0; r < multiData[0].length; r++)
						{
							var metricName = multiData[0][r].metric.name;
							
							row = row + "<th>"+ '<span id="info" title="' + multiData[0][r].metric.desc 
							+ '"><i class="fa fa-info-circle"></i><span>' + " " + metricName + "</th>";
						}
						
						row = "<thead>" + row +"</thead>"; 
						table.append(row);
						// Finish building State header
						
						// Populate data from each state
						// Note: we assume here that there was atleast 1 metric data returned for this state.
						for(var j = 0; j < multiData.length; j++)
						{
							row = "";
							
							for(var k = 0; k < multiData[j].length; k++)
							{
								var metricData = multiData[j][k];
								
								if (k == 0) // First one...have to add the state header.
								{
									row = "<th>" + metricData.state.abbr + "</th>";
								}
								
								var dataPointForCurrentYear = metricData.dataPoints.filter(function(point) { return point.year == cm.yearSelected; });
								
								dataPointForCurrentYear = dataPointForCurrentYear.length == 0 ? null : dataPointForCurrentYear[0].value;
								
								var formattedValue = cm.getFormattedMetricValue(metricData.metric.type, dataPointForCurrentYear);
								
								row = row + "<td>" + formattedValue + "</td>";
							}
							
							row = "<tr>" + row + "</tr>";
							table.append(row);
						}
						cm.setDataTable();
					
					});
				}		
			}	
	};
		
	/**
	 * Performs additional setup functions for the data table.
	 * Should not be called on its own - is used as a utility function by refreshTable
	 */
	Chart.prototype.setDataTable = function() {		
		if ( $("#myTable").html().indexOf("<thead>") != -1 ){
			
			$('#myTable tbody tr td').each( function() {
				var sTitle= $(this).text();
				if ( sTitle == "N/A" )
					this.setAttribute( 'title', "No Value for the year selected" );	
				
			} );
			
			$('#myTable tbody tr th').each( function() {
				var sTitle= $(this).text();
				this.setAttribute( 'title', States.getStateFromString(sTitle).name );	

			} );
			
			if( !$.fn.DataTable.isDataTable( '#myTable' ) ){
				var oTable = $('#myTable').dataTable({"iDisplayLength": 20}, {});
				dt = oTable;
			}
			else
			{
				console.log("table already present");
				$('#myTable').dataTable().fnDestroy();
				var oTable = $('#myTable').dataTable({"iDisplayLength": 20});
				dt = oTable;
			}
			
		 /* Apply the tooltips */
			oTable.$('tr').tooltip( {
				"delay": 0,
				"track": true,
				"fade": 0
			} );
			
			$('#myTable tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }
		        else {
		        	oTable.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
		    } );
		}
	};
	
	/**
	 * Refreshes the bar and line graphs
	 */
	Chart.prototype.refreshGraphs = function() {
		if(as.currentind == null || as.currentind == undefined)
			return;
		   
		var states = as.getSelectedStates();
		
    	if(this.currentVisualization == this.visualizationTypes.LINE){
    		$("#mbody > *").remove();
	    	
	    	if($("#mbody svg").length==0){
	    		
	    		$("#mbody").append( "<svg style=\"height: 70%; background-color:#fff\"></svg>" );

			    d3.selectAll("#mbody svg > *").remove();
	    	}
    	}else{
    		$("#mbodyBar > *").remove();
    		if($("#mbodyBar svg").length==0){
	    		$("#mbodyBar").append( "<svg style=\"height: 70%; background-color:#fff\"></svg>" );

			    d3.selectAll("#mbodyBar svg > *").remove();
			        
	    	}
    	}
	    
    	// Query for data
	    var query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(as.currentind).getName());
	    query.execute(function(multiData) {
            nv.addGraph(function() {
              
            	var chart;
    
                if (cm.currentVisualization == cm.visualizationTypes.LINE) {
                    chart = nv.models.lineChart()
                    	.useInteractiveGuideline(true)
                    	.transitionDuration(350);     
                } else if (cm.currentVisualization == cm.visualizationTypes.BAR) {
                    chart = nv.models.multiBarChart();
                }
                
                chart.margin({left : 100})
                	.x(function(d) {return d[0]})
                	.y(function(d) {return d[1]}) // adjusting, 100% is 1.00, not 100 as it is in the data
                	.color(d3.scale.category10().range())

                var k = 0; var sentinel = 0;
                while((k<multiData.length) && (sentinel==0)){
                	var xtickvalues = multiData[k][0].dataPoints.map(function(d) {
	                    return d["year"];
	                });
                	if(xtickvalues.length>0)
                		sentinel=1;
                	k++;
                }
                
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
                if(multiData[0][0].metric.binName!="National"){ //Attemnpting to fix the inverted line and bar graphs
                												//For national ranking. Unsuccesful so far
                	for (var i = 0; i < multiData.length; i++) {
                		
                			data[i] = {
	                				key : multiData[i][0].state.abbr,
	                				color: cm.array_colors[i%cm.array_colors.length]
	                		};
	                		data[i]["values"] = multiData[i][0].dataPoints.map(function(d) {
	                			return [ d["year"], d["value"] ];
	                		});
                	}
                }else{
                	var limit_array=multiData.length-1;
                	for (var i = limit_array; i>=0  ; i--) {
                			data[i] = {
                				key : multiData[i][0].state.abbr,
                				color: cm.array_colors[i%cm.array_colors.length]
                			};
                			data[i]["values"] = multiData[i][0].dataPoints.map(function(d) {
                			return [ d["year"], d["value"] ];
                			});
                	}
                }
                if(data[0].values.length==0){
                	var counter=0;
                	var sentinel=0;
                	var temp;
                	while((counter<data.length)&&(sentinel==0)){
                		if(data[counter].values.length>0){
                			temp=data[0];
                			data[0]=data[counter];
                			data[counter]=temp;
                			sentinel=1;
                		}
                		counter++;
                	}
	                
                }
                
                if (cm.currentVisualization == cm.visualizationTypes.LINE) {
                	d3.select('#mbody svg').datum(data).transition().duration(500).call(chart);
                }else if (cm.currentVisualization == cm.visualizationTypes.BAR) {
                	d3.select('#mbodyBar svg').datum(data).transition().duration(500).call(chart);
                }
                
                nv.utils.windowResize(chart.update);
                
                return chart;
            });
	    });
	};
	
	/**
	 * Returns a formatted string of the given value based on the given metric type.
	 * @param metricType The type of metric.
	 * @param value The value of the metric.
	 * @returns {String} A formatted string of the value based on the metric type.
	 */
	Chart.prototype.getFormattedMetricValue = function(metricType, value)
	{
		var formattedValue = "";
		
		if (value == null)
			return "N/A";
		
		if(metricType == "percentage") {
			formattedValue = (value * 100).toFixed(2)+"%";
		} else if(metricType =="currency") {
			formattedValue = "$" + value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
		}else if(metricType == "numeric"){
			formattedValue = value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
		}else{
			formattedValue = value;
		}	
		
		return formattedValue;
	};
	
	/**
	 * Returns the years where any of the metrics within the query have data.
	 * @param multiDataMultipleQuery The query data to search over
	 * @returns {Array} An array of the years where there is data
	 */
	Chart.prototype.getYearsWhereDataExistsForMultipleMetrics = function(multiDataMultipleQuery) {
		var array_years = [];
		var k=0;
		
		for(var i = 0; i < multiDataMultipleQuery.length;i++) { // For every state...
			for(var w = 0; w < multiDataMultipleQuery[i].length; w++) { // Check every returned metric
				for(var j = 0; j < multiDataMultipleQuery[i][w].dataPoints.length; j++) // For every data point for those metrics..
				{
					if(array_years.indexOf(multiDataMultipleQuery[i][w].dataPoints[j].year) < 0)
					{
						array_years[k] = multiDataMultipleQuery[i][w].dataPoints[j].year;
						k++;
					}
				}
			}
		}
		
		return array_years;
	};
	
	/**
	 * Returns the years where there is data within the given query data
	 * @param states The states to look for data for
	 * @param multiData The query data to search over
	 * @returns {Array} An array of the years where there is data
	 */
	Chart.prototype.getYearsMetricState = function(states, multiData) {
		var array_years=[];
		var k=0;
		for(var j=0; j<states.length; j++){
			for(var i=0;i<multiData.length;i++){
				for(w=0;w<multiData[j][0].dataPoints.length;w++){
					if(array_years.indexOf(multiData[j][0].dataPoints[w].year) < 0){
					  array_years[k]=multiData[j][0].dataPoints[w].year;
					  k++;
					}
				}	
			}
		}
		return array_years;
	};
	
	
	var publicInterface = {};
	/**
	 * Initializes the default Chart parameters.  
	 */
	publicInterface.create = function() {
		return new Chart();
	};
	
	return publicInterface;
	
}($));


