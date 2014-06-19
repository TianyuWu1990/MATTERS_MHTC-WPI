
// list of potential inputs, customizations, variabilities
// line vs bar vs etc etc


var CM = (function($) {
	
	function Chart() {

		this.dataIndex = 0; // load.js
		this.currentind = 0; // load.js
		this.currData = ''; // load.js
		this.stateAbbr = "MA"; // load.js
		this.current_graph = 'line'; // laod.js
		this.current_graph_function = null; // load.js
		this.current_tab = 'national'; // load.js
		this.graph_title_prefix = ''; // load.js
		this.selected = ['MA']; //load.js, jquery.usmap.js
		this.multiMode = false; //load.js, jquery.usmap.js

	   $("#graph_toggle").click(function(e) {
	       if (current_graph == 'line') {
	           current_graph = 'bar';
	           $("#graph_toggle").html("Switch to Line");
	       } else if (current_graph == 'bar') {
	           current_graph = 'line';
	           $("#graph_toggle").html("Switch to Bar");
	       }
	       
	       current_graph_function(currentind);
	   });
	   
	   
	   current_graph_function = null;
	   current_graph = 'line';
	   current_tab = 'national';
	   graph_title_prefix = '';
	   
	};
/*
	// Hack that gets called when clicking on a button or when scrolling the div, flips the dropdown if it will make it display better
	Chart.prototype.adjustDropDown = function(e) {
	    setTimeout(function() {
	        var divWithScroll = $("#sidebar div.active");
	        var openMenuContainer = $("#sidebar div.open");
	        $("#sidebar div.open ul.dropdown-menu").each(function() {
	            
	            var height = divWithScroll.height();
	            var top = openMenuContainer.position().top + $(this).position().top;
	            var bottom = openMenuContainer.position().top + $(this).position().top + $(this).height();
	            
	            if (top < 0) {
	                $(this).css("bottom", "auto");
	                $(this).css("top", "" + openMenuContainer.height() + "px");
	            }
	            
	            if (bottom > height) {

	                $(this).css("top", "auto");
	                $(this).css("bottom", "" + (openMenuContainer.height() +2) + "px");
	            }
	            
	        });
	        
	        //var scrollTop = divWithScroll.scrollTop();
	        
	    }, 20);
	}
*/

	// Gets called when doing compare to select states or when going back, does a bunch of hacks to make that crap work
	Chart.prototype.toggleMultiSelect = function(ind) {
		 
		
	    $("#multiSelecter").toggle("slide", {
	        direction : "right"
	    }, 200);
	    $("#sidebar").toggle("slide", {
	        direction : "left"
	    }, 200);

	    $("#map").usmap('toggleMultiselect');
	  
	    dataIndex = this.getParamsOfId(ind);
        
	    this.selected = [ this.stateAbbr ];
	    
	    if ($("#normallegend").hasClass("hidden")) {
	        $("#normallegend").removeClass("hidden");
	        $("#multilegend").addClass("hidden");
	    } else {
	        $("#normallegend").addClass("hidden");
	        $("#multilegend").removeClass("hidden");
	    }

	    $(".stateButton").each(function(i) {
	    	
	        var state;
	        $(this).find("input").each(function(checkbox) {
	            var id = $(this).attr("id");
	            state = id.substr(3, 2);
	            
	        });
	        
	        if ($(this).hasClass("active") && state != stateAbbr) {
	            $(this).removeClass("active");
	            
	        }

	        if (state == stateAbbr && !$(this).hasClass("active")) {
	            $(this).addClass("active");
	           
	        }
	    });
	}
	

	// Gets called when clicking on the map (i have no idea how), deals with switching a state or doing multiselect
	Chart.prototype.selectState = function(state) {
		
	    if (state == stateAbbr) {
             
	        return;
	    }
	   
	    $('#map').usmap('select', state, false);
	    if (!this.multiMode)
	    	
	        this.loadState(state);
	}

	// gets called on page load, sets up the map does other hacks
	Chart.prototype.loadFunction = function() {
		
	    $('#sidebar li:eq(0) a').tab('show');
	    $(function() {
	        $("[rel='tooltip']").tooltip();
	    });
	    $("#toggle").toggle("slide");
	    $('#map').usmap({
	        'stateSpecificStyles' : {
	            'WA' : {
	                fill : 'MidnightBlue'
	            },
	            'CA' : {
	                fill : 'MidnightBlue'
	            },
	            'UT' : {
	                fill : 'MidnightBlue'
	            },
	            'CO' : {
	                fill : 'MidnightBlue'
	            },
	            'TX' : {
	                fill : 'MidnightBlue'
	            },
	            'MN' : {
	                fill : 'MidnightBlue'
	            },
	            'GA' : {
	                fill : 'MidnightBlue'
	            },
	            'NC' : {
	                fill : 'MidnightBlue'
	            },
	            'VA' : {
	                fill : 'MidnightBlue'
	            },
	            'MD' : {
	                fill : 'MidnightBlue'
	            },
	            'PA' : {
	                fill : 'MidnightBlue'
	            },
	            'NY' : {
	                fill : 'MidnightBlue'
	            },
	            'CT' : {
	                fill : 'MidnightBlue'
	            },
	            'NH' : {
	                fill : 'MidnightBlue'
	            },
	            'NJ' : {
	                fill : 'MidnightBlue'
	            },
	            'MA' : {
	                fill : 'green'
	            }
	        }
	    });
	    this.getData("data/stats/query?states=MA&metrics=all", function(data) {
	        currData = data[0];
	        stateAbbr = currData[0].state.abbr;
	    });
	    
	}

	// gets called when doing display selected state data, shows a graph for a single state single metric 
	Chart.prototype.showGraph = function(ind) {
	    d3.selectAll("#mbody svg > *").remove();
	    current_graph_function = this.showGraph;
	    currentind = ind;
	    dataIndex = this.getParamsOfId(ind);
	    document.getElementById("graphTitle").innerHTML = currData[dataIndex].metric.name;
	    document.getElementById("graphStates").innerHTML = currData[0].state.name;
	    setTimeout(function() {
	        nv.addGraph(function() {
	            var chart;
	            if (current_graph == 'line') {
	                chart = nv.models.lineChart()
	                .useInteractiveGuideline(true);
	            } else if (current_graph == 'bar') {
	                chart = nv.models.discreteBarChart();
	            }
	            chart.margin({
	                left : 100
	            }).x(function(d) {
	                return d[0]
	            }).y(function(d) {
	                return d[1]
	            }) // adjusting, 100% is 1.00, not 100 as it is in the data
	            .color(d3.scale.category10().range())

	            var xtickvalues = currData[dataIndex].dataPoints.map(function(d) {
	                return d["year"];
	            });
	            
	            chart.xAxis.axisLabel("Year").tickValues(xtickvalues).tickFormat(d3.format('.0f'));

	            if (currData[dataIndex].metric.type == "integer") {
	                chart.yAxis.axisLabel("Count").tickFormat(d3.format(',.0f'));
	            } else if (currData[dataIndex].metric.type == "rank") {
	                chart.yAxis.axisLabel("Ranking out of 50 States").tickFormat(d3.format('.0f'));
	            } else if (currData[dataIndex].metric.type == "percentage") {
	                chart.yAxis.axisLabel("%").tickFormat(d3.format(',.2%'));
	            } else if (currData[dataIndex].metric.type == "numeric") {
	                chart.yAxis.axisLabel("Value").tickFormat(d3.format(',.2f'));
	            } else if (currData[dataIndex].metric.type == "currency") {
	                chart.yAxis.axisLabel("$").tickFormat(d3.format('$,.2'));
	                
	            }
	            var data = {
	                key : stateAbbr
	            };
	            data["values"] = currData[dataIndex].dataPoints.map(function(d) {
	                return [ d["year"], d["value"] ];
	            });
	            // chart.yAxis.tickFormat(d3.format(',.1%'));
	            d3.select('#mbody svg').datum([ data ]).transition().duration(500).call(chart);

	            // TODO: Figure out a good way to do this automatically
	            nv.utils.windowResize(chart.update);

	            return chart;
	        });
	    }, 500);
	}

	// gets called when doing compared to selected states
	Chart.prototype.showMultiGraphOnSelected = function() {
		 graph_title_prefix = "Compare to Selected: ";
	    current_graph_function = this.showMultiGraphOnSelected;
	    //currentind = ind;
	    this.showMultiGraph(this.selected);
	}

	//gets called when compare to top ten, shows a multigraph for the top ten
	Chart.prototype.showMultiGraphOnTopTen = function(ind) {
	    graph_title_prefix = "Compare Top TenStates: ";
	    current_graph_function = this.showMultiGraphOnTopTen;
	    currentind = ind;
	    dataIndex = this.getParamsOfId(ind);

	    /*
	    this.getData("data/peers/top?metric=" + currData[dataIndex].metric.name + "&year="
	            + currData[dataIndex].recent.year, function(states) {
	        selected = states.map(function(s) {
	            return s.abbr;
	        });
	    }, 500);
	    */
	    //alert(cm.selected);
	    selected = $.parseJSON($.ajax({
				url : "data/peers/top?metric=" + currData[dataIndex].metric.name + "&year="
	            + currData[dataIndex].recent.year,
				dataType : 'text',
				async : false,
				success : function(data) {
					return data;
				}
			}).responseText).map(function(s) {
	            return s.abbr;
	        });
			
        this.showMultiGraph(selected);

	}

	// gets called when compare to bottom ten, shows a multigraph for the bottom ten
	Chart.prototype.showMultiGraphOnBottomTen = function(ind) {
	    graph_title_prefix = "Compare Bottom Ten States: ";
	    current_graph_function = this.showMultiGraphOnBottomTen;
	    currentind = ind;
	    dataIndex = this.getParamsOfId(ind);

	    /**this.getData("data/peers/bottom?metric=" + currData[dataIndex].metric.name + "&year="
	            + currData[dataIndex].recent.year, function(states) {
	        var statelist = states.map(function(s) {
	            return s.abbr;
	        });
	        showMultiGraph(statelist);
	    }, 500);**/
	    
	    selected = $.parseJSON($.ajax({
			url : "data/peers/bottom?metric=" + currData[dataIndex].metric.name + "&year="
            + currData[dataIndex].recent.year,
			dataType : 'text',
			async : false,
			success : function(data) {
				return data;
			}
		}).responseText).map(function(s) {
            return s.abbr;
        });
		
    this.showMultiGraph(selected);

	}

	// gets called when doing compare to peer states, shows a multigraph for the selected states
	Chart.prototype.showMultiGraphOnPeers = function(ind) {
	    graph_title_prefix = "Compare All Peers: ";
	    current_graph_function = this.showMultiGraphOnPeers;
	    currentind = ind;
	    dataIndex = this.getParamsOfId(ind);
/*
	    this.getData("data/peers", function(states) {
	        var statelist = states.map(function(s) {
	            return s.abbr;
	        });
	        this.showMultiGraph(statelist);
	    }, 500);
*/
        selected = States.getPeers().map(function(s) {
            return s.abbr;
        });
        this.showMultiGraph(selected);
	    
	}

	Chart.prototype.showMultiGraph = function(states) {
	    d3.selectAll("#mbody svg > *").remove();
	    document.getElementById("graphTitle").innerHTML = graph_title_prefix + currData[dataIndex].metric.name;
	    document.getElementById("graphStates").innerHTML = states.join(", ");
	    
	    var query = DQ.create().addState(states).addMetric(currData[dataIndex].metric.name);
	    query.execute(function(
	            multiData) {
	        setTimeout(function() {
	            nv.addGraph(function() {
	                var chart;
	                if (current_graph == 'line') {
	                    chart = nv.models.lineChart()
	                    .useInteractiveGuideline(true);
	                } else if (current_graph == 'bar') {
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

	                var xtickvalues = currData[dataIndex].dataPoints.map(function(d) {
	                    return d["year"];
	                });
	                
	                chart.xAxis.axisLabel("Year").tickValues(xtickvalues).tickFormat(d3.format('.0f'));

	                if (currData[dataIndex].metric.type == "integer") {
	                    chart.yAxis.axisLabel("Count").tickFormat(d3.format(',.0f'));
	                } else if (currData[dataIndex].metric.type == "rank") {
	                    chart.yAxis.axisLabel("Ranking out of 50 States").tickFormat(d3.format('.0f'));
	                } else if (currData[dataIndex].metric.type == "percentage") {
	                    chart.yAxis.axisLabel("%").tickFormat(d3.format(',.2%'));
	                } else if (currData[dataIndex].metric.type == "numeric") {
	                    chart.yAxis.axisLabel("Value").tickFormat(d3.format(',.2f'));
	                } else if (currData[dataIndex].metric.type == "currency") {
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

	// a crappy implementation of $.get
	Chart.prototype.getData = function(url, callback) {
	    http = new XMLHttpRequest();
	    http.open("GET", url, true);
	    http.onreadystatechange = function() {
	        if (http.readyState == 4 && http.status == 200) {
	            callback(JSON.parse(http.responseText));
	        }
	    }
	    http.send(null);
	}

	// changes the loaded state
	Chart.prototype.loadState = function(stateAbbr) {
		
	    this.getData("data/stats/query?states=" + stateAbbr + "&metrics=all", this.loadData);
	    
	}

	// Returns the parameter of currData with the given metricid
	Chart.prototype.getParamsOfId = function(idx) {

	    for (var i = 0; i < currData.length; i++) {
	        if (currData[i].metric.id == idx)
	            return i;
	    }

	    return -1;

	}

	// pushes a new table into the page, makes sure the active tab is correct
	Chart.prototype.loadData = function(stateData) {
	    currData = stateData[0];
	    stateAbbr = currData[0].state.abbr;
	    
	    if ($("#national").hasClass("active")) {
	        current_tab = 'national';
	    } else if ($("#talent").hasClass("active")) {
	        current_tab = 'talent';
	    } else if ($("#cost").hasClass("active")) {
	        current_tab = 'cost';
	    } else if ($("#economy").hasClass("active")) {
	        current_tab = 'economy';
	    }

	    $.get("" + stateAbbr + "/table", function(data) {
	        $("#state_container").html(data);
	        if (current_tab == 'national') {
	            $("#nationalTab").addClass("active");
	            $("#national").removeClass("fade");
	            $("#national").addClass("active");
	        } else if (current_tab == 'talent') {
	            $("#talentTab").addClass("active");
	            $("#talent").removeClass("fade");
	            $("#talent").addClass("active");
	        } else if (current_tab == 'cost') {
	            $("#costTab").addClass("active");
	            $("#cost").removeClass("fade");
	            $("#cost").addClass("active");
	        } else if (current_tab == 'economy') {
	            $("#economyTab").addClass("active");
	            $("#economy").removeClass("fade");
	            $("#economy").addClass("active");
	        }
	        $("#stateTitle").text(currData[0].state.name);
	    });

	}

	
	var publicInterface = {};
	
	publicInterface.create = function() {
		return new Chart();
	}
	
	return publicInterface;
	
}($));


