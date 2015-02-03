 /*
  * Copyright (C) 2013 Worcester Polytechnic Institute
  * All Rights Reserved. 
  *  
 */  
/**
 * This module handles the display functionality of different type of charts.  
 */


var CM = (function($) {
	
	function Chart() {
		this.array_colors=["#F90101","#F2B50F","#00933B","#0266C8","#603CBA","#1E7145","#9F00A7",
		                  "#7f7f7f","#bcbd22","#17becf","#F2F5A9","#FE2EF7","#6E6E6E","#F6CED8","#3B0B0B","#181907"];
		
		this.current_graph = 'line'; 
		this.current_graph_function = null; 
		this.graph_title_prefix = ''; 
		this.multiDataMultipleQuery=[];/**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
		this.kcounter;/**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
		this.kcounterexecute;/**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
		this.year_selected; /**MULTIPLE METRICS+MULTIPLE STATE+MULTIPLE YEARS**/
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
	/*
	 * This is a 2d table. WHen you only have one metric to show
	 */
	Chart.prototype.getYearsMetricState = function(states,multiData) {
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
	
	/*changed by manik*/
	Chart.prototype.showMultipleMetricsStatesYears = function(states,selected_multiple_metrics,year_in) {
		var query;
		this.year_selected=year_in;
		$("#mbodyMultipleQuery > *").remove();
		
		$("#mbodyMultipleQuery").append("<table id='myTable' class='table '  style='font-size: 13px; background-color:#fff'></table>");
		var table = $("#mbodyMultipleQuery table");

		if(selected_multiple_metrics.length>0){
			
			if(selected_multiple_metrics.length==1){
				$("#yearsMultipleQuery").addClass("hidden");
				$("#timelinetable").addClass("hidden");
				query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(selected_multiple_metrics[0]).getName());
				
				query.execute(function(multiData) {
			        setTimeout(function() {
			        	var row;
			        	var array_years=cm.getYearsMetricState(states,multiData);
			        	if(array_years.length==0){
			        		 row="<tr><td> Please select atleast one state </td></tr>";
			        		 table.append(row);
			        	}else{
			        		 row="<thead><th>States</th>";
			        		 var i=0;
			        		while(i<array_years.length){			        			
			        			 row = row+"<th>"+array_years[i]+ "</th>";
			        			 i++;
			        		}
			        		row = row +"</thead>";
				            table.append(row);
				             
			                var data = new Array();
			                var percentage;
			                var j;
			                var type_var;
			                for (var i = 0; i < multiData.length; i++)
			                {
			                    row = "<th>" + multiData[i][0].state.attr + "</th>" ;
			                    j=0;
			                    while(j<array_years.length)
			                    {
			                    	if(multiData[i][0].dataPoints.length==0)
			                    	{
			                    		row = row +"<td> N/A </td>"; 
			                    	}
			                    	else
			                    	{
			                    		type_var=Metrics.getMetricByID(multiData[i][0].metric.id).getType();
			                    		if(type_var=="percentage"){
			                    			percentage=(multiData[i][0].dataPoints[j].value*100).toFixed(2)+"%";
			                    			row=row+"<td>" +  percentage+ "</td>";
			                    		}else if(type_var =="currency"){
			                    			
			                    			row=row+"<td>$" +  multiData[i][0].dataPoints[j].value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+"</td>";
			                    		}else if(type_var=="numeric"){
			                    			row=row+"<td>" +  multiData[i][0].dataPoints[j].value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+"</td>";
			                    		}else{
			                    			row=row+"<td>" +  multiData[i][0].dataPoints[j].value+"</td>";
			                    		}	
			                    	}
			                    	j++;
			                    }
			                    row = "<tr>" +row +"</tr>";
				                table.append(row);
			                }
			        	}
			        	cm.setDataTable();
			        }, 500);
				});
			}
			else
			{
					this.kcounterexecute=0;
					this.multiDataMultipleQuery=[];
					
					for(this.kcounter=0; this.kcounter<selected_multiple_metrics.length;this.kcounter++){

						this.multiDataMultipleQuery=[];
						query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(selected_multiple_metrics[this.kcounter]).getName());

						query.execute(function(multiData) {
							cm.multiDataMultipleQuery[cm.kcounterexecute]=multiData;

							cm.kcounterexecute++;
						});
						
					}
					setTimeout(function() {	
						
						var array_years=cm.getMultipleYearsMetricState(states,cm.multiDataMultipleQuery);
						
					    $("#yearsMultipleQuery").removeClass("hidden");
					    $("#timelinetable").removeClass("hidden");
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

		    			//modified by manik
		    			var seltimeline=$("#timelinetable");
		    			seltimeline.empty();
		    			var liststring =""
		    			array_years.sort(function(a,b){return b - a;});
						if(cm.year_selected==-1)
		    				cm.year_selected=array_years[0];
						liststring += '<table align="center"><tr><td></td><td><ul class="timelineListStyle">';
						for(var k=array_years.length-1; k>=0; k--){
							if(cm.year_selected!=array_years[k]){
								liststring += '<li ><button class="" id="tableTimeLineButton" onClick="return tableButtonClicked(this,'+array_years[k]+')" >'+array_years[k]+'</button></li>';
							}else{
								liststring += '<li id="clicktable'+cm.year_selected+'"><button class="active"  id="tableTimeLineButton" >'+cm.year_selected+'</button></li>';
							}
						}
						liststring += '</ul ></td></tr></table>';
						seltimeline.append(liststring);
						
						var row="<th>&nbsp;</th>";
						var checkduplicity;//hack to fix the fact the titles and rows were strnagly duplicating
						var array_duplicates=new Array();
						var counter_control_duplicates=0;// Hack: the rows were duplicating like crazy but now always
						//.. needed a way to work around it but currently it is not the elegant  way
						
						for(var r=0;r<cm.multiDataMultipleQuery.length;r++){
							checkduplicity = $.inArray(cm.multiDataMultipleQuery[r][0][0].metric.name, array_duplicates) > -1;
							if(checkduplicity==false){
								row = row + "<th>"+cm.multiDataMultipleQuery[r][0][0].metric.name+"</th>";
								array_duplicates[counter_control_duplicates]=cm.multiDataMultipleQuery[r][0][0].metric.name;
								counter_control_duplicates++;
							}
	
						}
						row = "<thead>" +row +"</thead>"; 
						table.append(row);
						
						var data = new Array();
						var current_state="-1";
						var band;
						var foundvalue;
						var sentinel;
						var w;
						var percentage;
						 var type_var;
						for(var j=0; j<states.length; j++){
							if(current_state!=states[j]){
								current_state=states[j];
								band=false;
							}
							var rows_written=0;// Hack: the rows were duplicating like crazy but now always
							//.. needed a way to work around it but currently it is not the elegant  way
							for(var r=0;r<cm.multiDataMultipleQuery.length;r++){
								foundvalue=false;
								sentinel=false;
								w=0; 
								
								while((w< cm.multiDataMultipleQuery[r][j][0].dataPoints.length)&&(!sentinel)){

									if(cm.multiDataMultipleQuery[r][j][0].dataPoints[w].year==cm.year_selected){
										if(!band){
											
											row = "<th>" + cm.multiDataMultipleQuery[r][j][0].state.abbr + "</th>";
											band=true;
										}
										
										if((cm.multiDataMultipleQuery[r][j][0].dataPoints[w].value!=null)&&(rows_written<counter_control_duplicates)){
											rows_written++;
											type_var=Metrics.getMetricByID(cm.multiDataMultipleQuery[r][j][0].metric.id).getType();
											
											if(type_var=="percentage"){
												percentage=(cm.multiDataMultipleQuery[r][j][0].dataPoints[w].value*100).toFixed(2)+"%";
				                    			row=row+"<td>" +  percentage+ "</td>";
				                    		}else if(type_var=="currency"){
				                    			
				                    			row=row+"<td>$" +  cm.multiDataMultipleQuery[r][j][0].dataPoints[w].value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+"</td>";
				                    		}else if(type_var=="numeric"){
				                    			row = row +"<td>"+cm.multiDataMultipleQuery[r][j][0].dataPoints[w].value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+"</td>";
				                    			
				                    		}else{
				                    			row=row+"<td>" +  cm.multiDataMultipleQuery[r][j][0].dataPoints[w].value+"</td>";
				                    		}	
											foundvalue=true;
											
										}
									sentinel=true;		
									}
								w++;
								}
								if(!band){
									row = "<th>" + cm.multiDataMultipleQuery[r][j][0].state.abbr + "</th>";
									band=true;
								}
								if((!foundvalue)&&(rows_written<counter_control_duplicates)){
									rows_written++;
									row = row +"<td>N/A</td>";
								}
							}
							if(band){ 
								row = "<tr>" +row +"</tr>";
								table.append(row);
							}
						}
						cm.setDataTable();
					}, 500);
			}		
					
		}	
		
	};
	/**added by manik*/
	Chart.prototype.setDataTable = function() {

		stateList = States.getAllstates();

		
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
			}
			else
			{
				console.log("table already present");
				$('#myTable').dataTable().fnDestroy();
				var oTable = $('#myTable').dataTable({"iDisplayLength": 20});
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
	}
	
	
	/**
	 * This function display the different types of chart (line,bar or table) for selected state/s and metric.
	 */
    
	Chart.prototype.showMultiGraph = function(states) {
		
		   if (this.current_graph == 'table') {
		    	$("#mbody > *").remove();
		    
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
		                        row = "<th>" + multiData[i][0].state.abbr + "</th>" ;
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
		    
		    // Query for data
		    var query = DQ.create().addState(states).addMetric(Metrics.getMetricByID(as.currentind).getName());
		   
		    query.execute(function(
		            multiData) {
		    	// On query callback, populate graph/table/chart with data
		        setTimeout(function() {
		            nv.addGraph(function() {
		                var chart;
		    
		                if (cm.current_graph == 'line') {
		                    chart = nv.models.lineChart()
		                    		.useInteractiveGuideline(true)
		                    		.transitionDuration(350);
		                } else if (cm.current_graph == 'bar') {
		                    chart = nv.models.multiBarChart();
		                }
		                
		                chart.margin({left : 100})
		                	.x(function(d) { return d[0] })
		                	.y(function(d) { return d[1] }) // adjusting, 100% is 1.00, not 100 as it is in the data
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
	Chart.prototype.showMultiGraphReloded = function(states) {
		if(as.currentind == null || as.currentind == undefined)
			return;
		   
	    	if(this.current_graph=='line'){
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
		    query.execute(function(
		            multiData) {
		        setTimeout(function() { // On query callback, populate graph/table with data from server
		            nv.addGraph(function() {
		                var chart;
		    
		                if (cm.current_graph == 'line') {
		                    chart = nv.models.lineChart()
		                    	.useInteractiveGuideline(true)
		                    	.transitionDuration(350);     
		                } else if (cm.current_graph == 'bar') {
		                    chart = nv.models.multiBarChart();
		                }
		                
		                chart.margin({left : 100})
		                	.x(function(d) {return d[0]})
		                	.y(function(d) {return d[1]}) // adjusting, 100% is 1.00, not 100 as it is in the data
		                	.color(d3.scale.category10().range())
	
		                var k=0;var sentinel=0;
		                while((k<multiData.length)&&(sentinel==0)){
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
		                
		                
		                if (cm.current_graph == 'line') {
		                	d3.select('#mbody svg').datum(data).transition().duration(500).call(chart);
		                }else if (cm.current_graph == 'bar') {
		                	d3.select('#mbodyBar svg').datum(data).transition().duration(500).call(chart);
		                }
		                // TODO: Figure out a good way to do this automatically
		                
		                nv.utils.windowResize(chart.update);
		                
		                return chart;
		            });
		        }, 500);
		    });
	};
	
	Chart.prototype.showMultipleMetricsStatesYearsReloaded = function(states,selected_multiple_metrics,year_in) {
		var query;
		this.year_selected=year_in;
		$("#mbodyMultipleQuery > *").remove();
		
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
		                        row = "<th>" + multiData[i][0].state.abbr + "</th>" ;
		                    row = row + multiData[i][0].dataPoints.map(function(d) {
		                        return "<td>" +  d["value"] + "</td>";
		                    }).join("");
		                    row = "<tr>" +row +"</tr>";
			                table.append(row);
		                }
			        }, 500);
				});
			}else{
				
				document.getElementById("graphTitleMultipleQuery").innerHTML = this.graph_title_prefix + "Multiple metrics selected";
					this.kcounterexecute=0;
					this.multiDataMultipleQuery=[];
					
					for(this.kcounter=0; this.kcounter<selected_multiple_metrics.length;this.kcounter++){

						this.multiDataMultipleQuery=[];
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

						for(var r=0;r<cm.multiDataMultipleQuery.length;r++){
							row = row + "<th>"+cm.multiDataMultipleQuery[r][0][0].metric.name+"</th>";
						}
						row = "<tr>" +row +"</tr>"; 
						table.append(row);
						
						var data = new Array();
						var current_state="-1";
						var band;
						var foundvalue;
						var sentinel;
						var w;
						for(var j=0; j<states.length; j++){
							if(current_state!=states[j]){
								current_state=states[j];
								band=false;
							}
							
							for(var r=0;r<cm.multiDataMultipleQuery.length;r++){
								foundvalue=false;
								sentinel=false;
								w=0;
								while((w<cm.multiDataMultipleQuery[r][j][0].dataPoints.length)&&(!sentinel)){

									if(cm.multiDataMultipleQuery[r][j][0].dataPoints[w].year==cm.year_selected){
										if(!band){
											row = "<th>" + cm.multiDataMultipleQuery[r][j][0].state.abbr + "</th>";
											band=true;
										}
										
										if(cm.multiDataMultipleQuery[r][j][0].dataPoints[w].value!=null){
											row = row +"<td>"+cm.multiDataMultipleQuery[r][j][0].dataPoints[w].value+"</td>";
											foundvalue=true;
										}
									sentinel=true;		
									}
								w++;
								}
								if(!band){
									row = "<th>" + cm.multiDataMultipleQuery[r][j][0].state.abbr + "</th>";
									band=true;
								}
								if(!foundvalue)	
									row = row +"<td>N/A</td>";
									
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
	var publicInterface = {};
	/**
	 * Initializes the default Chart parameters.  
	 */
	publicInterface.create = function() {
		return new Chart();
	};
	
	return publicInterface;
	
}($));


