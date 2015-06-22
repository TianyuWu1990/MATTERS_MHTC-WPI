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
		
		this.heatMapColorMap = {};
		this.heatMapValuesMap = {};
		
		this.heatMapColorScheme = ["#ffffcc","#ffeda0","#fed976","#feb24c","#fd8d3c","#fc4e2a","#e31a1c","#bd0026","#800026"];
		// Setup type processing for datatables to work with ranks.
		jQuery.fn.dataTableExt.aTypes.push(
			    function ( sData )
			    {
			    	if ( sData.length < 3) // can't possibly be a rank.
			    	{
			    		return null;
			    	}
			    	
			    	var isRank = false;
			    	
			    	// To be a rank, must end with a rank suffix.
			    	isRank = isRank || sData.indexOf("th", sData.length - 2) !== -1;
			    	isRank = isRank || sData.indexOf("st", sData.length - 2) !== -1;
			    	isRank = isRank || sData.indexOf("nd", sData.length - 2) !== -1;
			    	isRank = isRank || sData.indexOf("rd", sData.length - 2) !== -1;
			    	
			    	
			    	// And before the suffix, must have some digit.
			    	var digitChars = "0123456789";
			    	var charBeforeSuffix = sData.charAt(sData.length - 3);
			    	
			    	isRank = isRank && digitChars.indexOf(charBeforeSuffix) !== -1;
			    	
			    	if (isRank)
			    	{
						return 'rank';
			    	}
			    	else
			    	{
			    		return null;
			    	}		
			    }
			);
		
		jQuery.fn.dataTableExt.oSort['rank-asc']  = function(x,y) {
		    
			// Isolate actual digits
			var xIsolate = parseInt(x.substring(0, x.length - 2));
			var yIsolate = parseInt(y.substring(0, y.length - 2));
			
			return ((xIsolate < yIsolate) ? -1 : ((xIsolate > yIsolate) ?  1 : 0));
		};
		 
		jQuery.fn.dataTableExt.oSort['rank-desc'] = function(x,y) {
			var xIsolate = parseInt(x.substring(0, x.length - 2));
			var yIsolate = parseInt(y.substring(0, y.length - 2));
			
		    return ((xIsolate < yIsolate) ?  1 : ((xIsolate > yIsolate) ? -1 : 0));
		};
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
			case this.visualizationTypes.HEATMAP:
				this.refreshHeatMap();
				break;
		}
	};
	
	/**
	 * Handles resizing charts when window size changes.
	 */
	Chart.prototype.refreshSizing = function() {
		this.refreshHeatMapSizing();
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
	 * Refreshes the heat map based on the currently selected metric in the App State.
	 */
	Chart.prototype.refreshHeatMap = function() {
		this.refreshHeatMapSizing(); // Make sure sizing is right
		
		// Get data from server on the currently selected metric
		var allStates = States.getAllstates();
		
		var allStatesForQuery = allStates.map(function(s) {
			return s.abbr;
		});

		var query = DQ.create().addState(allStatesForQuery)
			.addMetric(Metrics.getMetricByID(as.currentind).getName());
		
		var isReversed = Metrics.getMetricByID(as.currentind).trendType == "reversed";
		var isRank = Metrics.getMetricByID(as.currentind).type == "rank";
		
		query.execute(function(multiData) {
			var yearsForMetric = cm.getYearsMetricState(allStates, multiData); // Get the years that the metric exists for from the data
		
			// If no data, say so.
			if (yearsForMetric.length == 0)
			{
				$("#heatmap-content-wrapper").hide();
				$("#heatmap-error").show();
				return;
			}
			else
			{
				$("#heatmap-content-wrapper").show();
				$("#heatmap-error").hide();
			}
				
			yearsForMetric.sort(function(a,b) {return b - a;} ); 
			
			if (cm.yearSelected == -1)
				cm.selectYear(yearsForMetric[0]);
				
			// Show the years table		
			var timelineTableHTML = cm.buildTimeline(yearsForMetric);
				
			$("#heatmap-timeline").empty();
			$("#heatmap-timeline").append(timelineTableHTML);
			
			
			// Create coloring map for all states.
					
			// First, separate out the info we care about (ABBR, VALUE, TYPE)
			var stateValueInOrder = multiData.map(function(e) { 
				var dataPointForYear = e[0].dataPoints.filter(function(f){
					return f.year == cm.yearSelected;
				});
				
				dataPointForYear = dataPointForYear.length == 0 ? null : dataPointForYear[0].value;
				
				return [e[0].state.abbr, dataPointForYear, e[0].metric.type];
			});
			
			// Remove any without real data, but keep track of them for later.
			// Also remove US average
			var missingData = [];
			stateValueInOrder = stateValueInOrder.filter(function(e) {
				
				if (e[1] == null)
				{
					missingData.push(e);
					return false;
				}
				
				if (e[0] == "US")
				{
					return false;
				}
				
				return true;				
			});
			
			// Then we sort so that the highest value is at the 0th position
			stateValueInOrder.sort(function (a,b) {
				return b[1] - a[1];
			});
					
			
			// Create the stored data structure for each state
			cm.heatMapValuesMap = {};
			cm.heatMapColorMap = {};
			
			var maxValue = stateValueInOrder[0][1];
			var minValue = stateValueInOrder[stateValueInOrder.length - 1][1];
			
			// Add records for every state we have data for
			for (var i = 0; i < stateValueInOrder.length; i++)
			{
				var value = stateValueInOrder[i][1];
				var stateAbbr = stateValueInOrder[i][0];
				var metricType = stateValueInOrder[i][2];
				
				var ranking = i + 1;
				
				if (isRank || isReversed)
				{
					ranking = stateValueInOrder.length - i;
				}
				
				var stateColor = cm.getHeatmapColor(cm.heatMapColorScheme, value, minValue, maxValue);
				
				var actualState = States.getStateByAbbreviation(stateAbbr);
				
				cm.heatMapValuesMap[stateAbbr] = { 
						ranking	:	cm.getFormattedMetricValue("rank", ranking),
						value			:	cm.getFormattedMetricValue(metricType, value),
						state			:	actualState,
						metricType		:	metricType,
						color			:	stateColor,
				};
				
				cm.heatMapColorMap[stateAbbr] = { 
						'fill' : stateColor, 
						'stroke' : '#000',
						'stroke-width' : 1
						};
			}
			
			// Add record for the states where we are missing data.
			for (var i = 0; i < missingData.length; i++)
			{
				var stateAbbr = missingData[i][0];
				var metricType = missingData[i][2];
				
				var ranking = missingData.length + 1 + i;
				
				var stateColor = "#fff";
				
				var actualState = States.getStateByAbbreviation(stateAbbr);
				
				cm.heatMapValuesMap[stateAbbr] = { 
						ranking	:	cm.getFormattedMetricValue("rank", ranking),
						value			:	null,
						state			:	actualState,
						metricType		:	metricType,
						color			:	stateColor,
				};
				
				cm.heatMapColorMap[stateAbbr] = { 
						'fill' : stateColor, 
						'stroke' : '#000',
						'stroke-width' : 1
						};
			}
			
			// What first rank is depends on the type of metric.
			if (isRank || isReversed)
			{
				$("#heatmap-generalinfo-first").html(stateValueInOrder[stateValueInOrder.length - 1][0]);
				$("#heatmap-generalinfo-last").html(stateValueInOrder[0][0]);
			}
			else
			{
				$("#heatmap-generalinfo-first").html(stateValueInOrder[0][0]);
				$("#heatmap-generalinfo-last").html(stateValueInOrder[stateValueInOrder.length - 1][0]);
			}
			
			if (isRank)
			{
				$("#heatmap-value-block").hide();
			}
			else
			{
				$("#heatmap-value-block").show();
			}
			
			$("#heatmap-generalinfo-ma").html(cm.heatMapValuesMap["MA"].ranking);
			
			
			cm.buildHeatMapLegend(minValue, maxValue, stateValueInOrder[0][2]);
			
			
			$("#heatmap-actual").empty();
			$("#heatmap-actual").removeData("pluginUsmap");
			$("#heatmap-actual").usmap({
				stateHoverAnimation: 100,
				showLabels: true,
				stateHoverStyles: {'stroke-width': 3},
				
				mouseover: function(event, data) {					
					var infoForState = cm.heatMapValuesMap[data.name];
					
					$("#heatmap-specificDetails-name").html(infoForState.state.getName() + " (" + infoForState.state.getAbbr() + ")" );
					
					if (infoForState.value == null)
					{
						$("#heatmap-specificDetails-rank").html("No Data");
						$("#heatmap-specificDetails-value").html("No Data");
					}
					else
					{
						$("#heatmap-specificDetails-rank").html(infoForState.ranking);
						$("#heatmap-specificDetails-value").html(infoForState.value);
					}
				
					if (infoForState.state.isPeerState())
					{
						$("#heatmap-specificDetails-peer").show();
					}
					else
					{
						$("#heatmap-specificDetails-peer").hide();
					}
					
					var tooltipX = event.originalEvent.clientX - $("#heatmap-actual").offset().left + 220;
					var tooltipY = event.originalEvent.clientY + $("body").scrollTop() - $("#heatmap-actual").offset().top;
										
					if (tooltipX > $("#heatmap-actual").width())
						tooltipX = tooltipX - 220;
					
					$("#heatmap-tooltip").attr("style", "left: " + tooltipX + "px; top: " + tooltipY +"px;");
					
					$("#heatmap-tooltip").show();
				},
				
				mouseout: function(event, data) {
					$("#heatmap-tooltip").hide();
				},
				
				stateSpecificStyles: cm.heatMapColorMap,
			});
		});
	};
	
	Chart.prototype.getHeatmapColor = function(colorMap, value, highest, lowest)
	{	
		
		var endVal = highest - lowest;
		var actValue = value - lowest;
		
		var indexInMap = Math.floor((1 - (actValue / endVal)) * (colorMap.length - 1));
			
		return colorMap[indexInMap];	
	}
	
	Chart.prototype.buildHeatMapLegend = function(lowerBound, upperBound, metricType)
	{
		// Empty existing legend, if any.
		$("#heatmap-legend-legend").empty();
		
		var numberBuckets = this.heatMapColorScheme.length;
		var range = upperBound - lowerBound;
		var increment = range / numberBuckets;
		
		
		var currLowerBound = this.getFormattedMetricValue(metricType, lowerBound);
		var currUpperBound = null;
		
		//Add a bucket for each.
		for (var i = 0; i < numberBuckets; i++)
		{
			var bucketColor = this.heatMapColorScheme[i];
		
			currUpperBound = lowerBound + increment * (i + 1);
			currUpperBound = this.getFormattedMetricValue(metricType, currUpperBound);
			
			var bucketRange = currLowerBound + " - " + currUpperBound;
	
			if (bucketRange.length > 22)
			{
				bucketRange = bucketRange.substr(0, 19) + "...";
			}
			
			var bucketHTML = '<div class="heatmap-legend-bucket">'
				+ '<svg class="heatmap-legend-bucket-swatch" xmlns="http://www.w3.org/2000/svg"><rect x="0" y="0" width="20px" height="20px"' 
				+ 'fill="' + bucketColor + '"></rect></svg>'
				+ '<div class="heatmap-legend-bucket-num">' + bucketRange + '</div></div>';
			
			$("#heatmap-legend-legend").append(bucketHTML);
			
			currLowerBound = currUpperBound;
		}
	}
	
	Chart.prototype.refreshHeatMapSizing = function() {
		var containerHeight = $(".tab-content").height() - 150; // 150 is room for year and metric controls at top				
				
		if (containerHeight < 500)
			containerHeight = 500;
		
		var width = containerHeight * 1.518;
		
		$("#heatmap-actual").height(containerHeight);
		
		$("#heatmap-actual").width(width);
		$("#heatmap-inner-wrapper").width(width + 200);
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
			if(selectedStates.length == 1) { // If we only have 1 state to load data for
				
				$("#timelinetable").hide();
				
				var fullState = States.getStateByAbbreviation(selectedStates[0]);
				$("#optionalTableTitle").html(fullState.name + " (" + fullState.abbr + ")");
				$("#optionalTableTitle").show();
				
				// We treat the query as if it is a multi-metric query as there can be any number of metrics selected.
				var processedMetrics = selectedMetrics.map(function(e) { return Metrics.getMetricByID(e).getName(); });
				
				query = DQ.create().addState(selectedStates).addMultipleMetrics(processedMetrics);
				
				query.execute(function(multiData) {
					
					if(multiData.length == 0)
						return; // Do nothing if we got no data back.
					
					var yearsForMetrics = cm.getYearsWhereDataExistsForMultipleMetrics(multiData);
					yearsForMetrics.sort(function(a,b) {return a - b;} ); 
					
					
					if(yearsForMetrics.length==0) { // If theres no data for the metric...
		        		 table.append("<tr><td>No data available for your current selection.</td></tr>");
		        	} else {
		        		//Table builder
		        		// Build header
		        		
		        		var row ="<th><div style=\"width: 150px;\">Metric</div></th>";
		        		
		        		for (var r = 0; r < yearsForMetrics.length; r++)
		        		{
		        			var yearName = yearsForMetrics[r];
		        			
		        			row = row + "<th><div style=\"width: 100px;\">" + yearName + "</div></th>";
		        		}
		        		
		        		row = "<thead>" + row + "</thead>";
		        		table.append(row);
		        		
		        		// Populate data for each metric.		        		
		        		for (var r = 0; r < multiData[0].length; r++)
		        		{
		        			var metricData = multiData[0][r];
		        			var metric = metricData.metric;
		        			var data = metricData.dataPoints;

		        			row = "<th>"+ '<span id="info" title="' + metric.desc 
							+ '"><span>' + " " + metric.name + "</th>";
		        			
		        			var yearIndex = 0;
		        			for (var k = 0; k < data.length; k++)
		        			{
		        				var year = data[k].year;
		        				
		        				while (year !== yearsForMetrics[yearIndex])
		        				{
		        					row = row + "<td> N/A </td>";
		        					yearIndex++;
		        				}
		        				
		        				row = row + "<td>" + cm.getFormattedMetricValue(metric.type, data[k].value) + "</td>";
		        				yearIndex++;
		        			}
		        			
		        			while (yearIndex < yearsForMetrics.length)
		        			{
		        				row = row + "<td> N/A </td>";
		        				yearIndex++;
		        			}
		        			
		        			row = "<tr>" + row + "</tr>";
		        			
		        			table.append(row);
		        		}
		        			
		        	}
					cm.setDataTable(false);
				
				});
				
				
			} 
			else if(selectedMetrics.length == 1) { // If we only have one metric to load
				
				$("#timelinetable").hide();
				
				var fullMetric = Metrics.getMetricByID(selectedMetrics[0]);
				
				// Query for appropriate data
				var query = DQ.create().addState(selectedStates).addMetric(fullMetric.getName());
				
				query.execute(function(multiData) {
					
						// TODO: THIS CODE IS BUGGY WHEN YOU SELECTED MULTIPLE STATES AND 
						// ONE METRIC THAT HAS NO DATA IN IT
						// multidata[0][0] will be undefined.
						var metricFromQuery = multiData[0][0].metric;
						
						$("#optionalTableTitle").html('<span id="info" title="' + metricFromQuery.desc 
								+ '"><span>' + " " + metricFromQuery.name);
						
						$("#optionalTableTitle").show();
					
					
			        	var yearsForMetric = cm.getYearsMetricState(selectedStates, multiData); // Get the years that the metric exists for from the data
			        	yearsForMetric.sort(function(a,b) {return a - b;} ); 
			        	
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

			                    var indexModifier = 0;
			                    
			                    for (var j = 0; j < yearsForMetric.length; j++)
			                    {
			                    	var actualIndex = j - indexModifier;
			                    	
			                    	if (multiData[i][0].dataPoints.length <= actualIndex)
			                    	{
			                    		row = row + "<td> N/A </td>";
			                    	}
			                    	else
			                    	{
			                    		var currDataYear = multiData[i][0].dataPoints[actualIndex].year;

			                    		if (currDataYear !== yearsForMetric[j])
			                    		{
			                    			indexModifier++;
			                    			row = row + "<td> N/A </td>";
			                    		}
			                    		else
			                    		{
			                    			// Format the data based on type.
				                    		var formattedData = cm.getFormattedMetricValue(multiData[i][0].metric.type, 
				                    				multiData[i][0].dataPoints[actualIndex].value);
				                    		row = row + "<td>" + formattedData + "</td>";
			                    		}
			                    	}
			                    }
			                    row = "<tr>" +row +"</tr>";
				                table.append(row);
			                }
			        	}
			        	cm.setDataTable(true);
				});
			}
			else	// Multiple metrics
			{					
					$("#optionalTableTitle").hide();
				
					var processedMetrics = selectedMetrics.map(function(e) { return Metrics.getMetricByID(e).getName(); });
					
					query = DQ.create().addState(selectedStates).addMultipleMetrics(processedMetrics);
					
					query.execute(function(multiData) {
						
						if(multiData.length == 0)
							return; // Do nothing if we got no data back.
						
						var yearsForMetrics = cm.getYearsWhereDataExistsForMultipleMetrics(multiData);
						yearsForMetrics.sort(function(a,b) {return b - a;} ); 
						
					    // Set the selected year if its not already set.
					    if(cm.yearSelected == -1)
							cm.selectYear(yearsForMetrics[0]);
						
						// Show the years table		
						var timelineTableHTML = cm.buildTimeline(yearsForMetrics);
						
						$("#timelinetable").empty();
						$("#timelinetable").append(timelineTableHTML);
						$("#timelinetable").show();
						
						// Build Header
						var row = "<th>State</th>";
						
						for(var r = 0; r < multiData[0].length; r++)
						{
							var metricName = multiData[0][r].metric.name;
							
							row = row + "<th>"+ '<span id="info" title="' + multiData[0][r].metric.desc 
							+ '"><span>' + " " + metricName + "</th>";
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
						cm.setDataTable(true);
					
					});
				}		
			}	
	};
		
	/**
	 * Performs additional setup functions for the data table.
	 * Should not be called on its own - is used as a utility function by refreshTable
	 */
	Chart.prototype.setDataTable = function(enableSort) {		
		if ( $("#myTable").html().indexOf("<thead>") != -1 ){
			
			$('#myTable tbody tr td').each( function() {
				var sTitle= $(this).text();
				if ( sTitle == "N/A" )
					this.setAttribute( 'title', "No value for this year." );	
				
			} );
			
			
			//scrollCollapse when vertical y scrolling is enabled, DataTables will force the height of the table's viewport to the given height at all times.
			//Alength allows us to readily specify the entries in the length drop down menu that dataTables shows when pagination is enabled
			
			if( !$.fn.DataTable.isDataTable( '#myTable' ) ){
				var oTable = $('#myTable').dataTable(
						{
							"scrollY":        "300px",
							"scrollX":        "100%",
					        "scrollCollapse": true,
					        "paging":         true,
							"iDisplayLength": 15,
							"aLengthMenu": [[15, 25, 50, -1], [15, 25, 50, "All"]],
							"bSort": enableSort,
							"columnDefs": [{ "type": "num-fmt", "targets": "_all"}]
						
						});
				new $.fn.dataTable.FixedColumns( oTable );
				dt = oTable;
			}
			else
			{
				$('#myTable').dataTable().fnDestroy();
				var oTable = $('#myTable').dataTable(
						{
							"scrollY":        "300px",
							"scrollX":        "100%",
					        "scrollCollapse": true,
					        "paging":         true,
							"iDisplayLength": 15,
							"aLengthMenu": [[15, 25, 50, -1], [15, 25, 50, "All"]],
							"bSort": enableSort,
							"columnDefs": [{ "type": "num-fmt", "targets": "_all"}]
						
						});
				new $.fn.dataTable.FixedColumns( oTable );
				dt = oTable;
			}

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
	    		
	    		$("#mbody").append( "<svg style=\"background-color:#fff\"></svg>" );

			    d3.selectAll("#mbody svg > *").remove();
	    	}
    	}else{
    		$("#mbodyBar > *").remove();
    		if($("#mbodyBar svg").length==0){
	    		$("#mbodyBar").append( "<svg style=\"background-color:#fff\"></svg>" );

			    d3.selectAll("#mbodyBar svg > *").remove();
			        
	    	}
    	}
	    
    	// Query for data
	    var query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(as.currentind).getName());
	    query.execute(function(multiData) {
            nv.addGraph(function() {
              
                var data = [];
                
                var years = [];
                
                // Get collection of all data
                for (var i = 0; i < multiData.length; i++)
                {
                	if (multiData[i].length == 0)
                		continue;
                	
                	data[i] = {
                			key : multiData[i][0].state.abbr,
                			color : cm.array_colors[i % cm.array_colors.length]
                	};
                	
                	data[i]["values"] = multiData[i][0].dataPoints.map(function(d) {
                		var yearForPoint = d["year"];
                		
                		if (years.indexOf(yearForPoint) == -1)
                			years.push(yearForPoint);
                		
                		return { "x" : yearForPoint, "y" : (d["value"].toFixed(2)) * 1 };
                	});
                }
                
                years.sort(function(a,b) { return a - b; });
                            	
                // Build the chart for the data.
            	var chart;
    
                if (cm.currentVisualization == cm.visualizationTypes.LINE) 
                {
                    chart = nv.models.lineChart()
                    	.transitionDuration(350)
                    	.useInteractiveGuideline(true)
                    	.margin({ left : 150, right : 50 });     
                } 
                else if (cm.currentVisualization == cm.visualizationTypes.BAR) 
                {
                    chart = nv.models.multiBarChart()
                    	.transitionDuration(350)
                    	.showControls(false)
                    	.margin({ left : 150, right : 50 });
                    
                    // Calculate new min y for the bar charts
                    var minY = d3.min(data, function(d) { 
                    	return d3.min(d["values"], function(e) {
                    		return e["y"];
                    	});
                    });
                    	
                    var maxY = d3.max(data, function(d) { 
                    	return d3.max(d["values"], function(e) {
                    		return e["y"];
                    	});
                    });
                    	
                    var range = maxY - minY;
                    
                    var newRange = range + (range / 10);
                    var newMin = maxY - newRange;
                    newMin = (newMin.toFixed(2)) * 1;
                    
                    if (newMin < 0)
                    	newMin = 0;
                    
                    chart.forceY(newMin);
                }
                                
                chart.xAxis.axisLabel("Year").tickValues(years).tickFormat(d3.format('.0f'));

                var type_var = Metrics.getMetricByID(as.currentind).getType();
                if (type_var == "integer") 
                {
			        chart.yAxis.axisLabel("Count").tickFormat(d3.format(',.0f'));
	            } 
                else if (type_var == "rank") 
                {
	                chart.yAxis.axisLabel("Ranking out of 50 States").tickFormat(d3.format('.0f'));
	            } 
                else if (type_var == "percentage") 
                {
	                chart.yAxis.axisLabel("%").tickFormat(d3.format(',.2%'));
	            } 
                else if (type_var == "numeric") 
                {
	                chart.yAxis.axisLabel("Value").tickFormat(d3.format(',.2f'));
	            } 
                else if (type_var == "currency") 
                {
	            	chart.yAxis.axisLabel("$").tickFormat(d3.format('$,.2'));
	            }
                
                
                if (cm.currentVisualization == cm.visualizationTypes.LINE) 
                {
                	d3.select('#mbody svg').datum(data).transition().duration(500).call(chart);
                }
                else if (cm.currentVisualization == cm.visualizationTypes.BAR) 
                {
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
		}else if(metricType == "rank") {
			
			value = Math.floor(value); // Make sure we get a whole number
			
			var firstDigit = value % 10;
		
			var suffix = "th";
			
			if (value <= 10 || value >= 14) // 11th, 12th, 13th...
			{
				switch(firstDigit)
				{
					case 1:
						suffix = "st";
						break;
					case 2:
						suffix = "nd";
						break;
					case 3:
						suffix = "rd";
						break;
				}
			}
			
			formattedValue = value.toFixed(0);
		}else if(metricType=="integer"){
			formattedValue = value.toFixed(0);
		}else{
			formattedValue = value;
		}	
		
		return formattedValue;
	};
	
	/**
	 * Builds the timeline for year selection
	 * @param yearsList The years to show in the timeline
	 */
	Chart.prototype.buildTimeline = function(yearsList) {				
		var timeLineHTML = '<table align="center"><tr><td></td><td><ul class="timelineListStyle">';
		for(var k = yearsList.length - 1; k >= 0; k--){
			if(cm.yearSelected != yearsList[k]){
				timeLineHTML += '<li ><button class="" id="tableTimeLineButton" onClick="return tableButtonClicked(this,'+yearsList[k]+')" >'+yearsList[k]+'</button></li>';
			}else{
				timeLineHTML += '<li id="clicktable'+cm.yearSelected+'"><button class="active"  id="tableTimeLineButton" >'+cm.yearSelected+'</button></li>';
			}
		}
		
		timeLineHTML += '</ul ></td></tr></table>';
		
		return timeLineHTML;
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


