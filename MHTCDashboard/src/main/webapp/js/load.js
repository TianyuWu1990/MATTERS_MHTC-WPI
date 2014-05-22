

var currData = "";

// Gets called on load deals with random crap of hacks to make stuff
$(document).ready(function() {
   dataIndex = 0;
   currentind = 0;
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
   
   $("div.tab-pane button.dropdown-toggle").click(adjustDropDown);
   $("div.tab-pane").scroll(adjustDropDown);
});

// Hack that gets called when clicking on a button or when scrolling the div, flips the dropdown if it will make it display better
adjustDropDown = function(e) {
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

// Gets called when doing compare to select states or when going back, does a bunch of hacks to make that crap work
toggleMultiSelect = function(ind) {
    $("#multiSelecter").toggle("slide", {
        direction : "right"
    }, 200);
    $("#sidebar").toggle("slide", {
        direction : "left"
    }, 200);
    $("#map").usmap('toggleMultiselect');
    dataIndex = getParamsOfId(ind);

    selected = [ currData.abbr ];
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
        if ($(this).hasClass("active") && state != currData.abbr) {
            $(this).removeClass("active");
        }

        if (state == currData.abbr && !$(this).hasClass("active")) {
            $(this).addClass("active");
        }
    });
}

// Gets called when clicking on the map (i have no idea how), deals with switching a state or doing multiselect
selectState = function(state) {
    
    if (state == currData.abbr) {

        return;
    }
    $('#map').usmap('select', state, false);
    if (!multiMode)
        loadState(state);
}

// gets called on page load, sets up the map does other hacks
loadFunction = function() {
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
    getData("data/stats/query?states=MA&metrics=all", function(data) {
        currData = data[0];
    });
}

// gets called when doing display selected state data, shows a graph for a single state single metric 
showGraph = function(ind) {
    d3.selectAll("#mbody svg > *").remove();
    current_graph_function = showGraph;
    currentind = ind;
    dataIndex = getParamsOfId(ind);
    document.getElementById("graphTitle").innerHTML = currData.params[dataIndex].name;
    document.getElementById("graphStates").innerHTML = currData.name;
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

            var xtickvalues = currData.params[dataIndex].data.map(function(d) {
                return d["year"];
            });
            
            chart.xAxis.axisLabel("Year").tickValues(xtickvalues).tickFormat(d3.format('.0f'));

            if (currData.params[dataIndex].type == "integer") {
                chart.yAxis.axisLabel("Count").tickFormat(d3.format(',.0f'));
            } else if (currData.params[dataIndex].type == "rank") {
                chart.yAxis.axisLabel("Ranking out of 50 States").tickFormat(d3.format('.0f'));
            } else if (currData.params[dataIndex].type == "percentage") {
                chart.yAxis.axisLabel("%").tickFormat(d3.format(',.2%'));
            } else if (currData.params[dataIndex].type == "numeric") {
                chart.yAxis.axisLabel("Value").tickFormat(d3.format(',.2f'));
            } else if (currData.params[dataIndex].type == "currency") {
                chart.yAxis.axisLabel("$").tickFormat(d3.format('$,.2'));
                
            }
            var data = {
                key : currData.abbr
            };
            data["values"] = currData.params[dataIndex].data.map(function(d) {
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
showMultiGraphOnSelected = function() {
    graph_title_prefix = "Compare to Selected: ";
    current_graph_function = showMultiGraphOnSelected;
    currentind = ind;
    showMultiGraph(selected);
}

//gets called when compare to top ten, shows a multigraph for the top ten
showMultiGraphOnTopTen = function(ind) {
    graph_title_prefix = "Compare Top TenStates: ";
    current_graph_function = showMultiGraphOnTopTen;
    currentind = ind;
    dataIndex = getParamsOfId(ind);

    getData("data/peers/top?metric=" + currData.params[dataIndex].name + "&year="
            + currData.params[dataIndex].recent.year, function(states) {
        var statelist = states.map(function(s) {
            return s.abbr;
        });
        showMultiGraph(statelist);
    }, 500);

}

// gets called when compare to bottom ten, shows a multigraph for the bottom ten
showMultiGraphOnBottomTen = function(ind) {
    graph_title_prefix = "Compare Bottom Ten States: ";
    current_graph_function = showMultiGraphOnBottomTen;
    currentind = ind;
    dataIndex = getParamsOfId(ind);

    getData("data/peers/bottom?metric=" + currData.params[dataIndex].name + "&year="
            + currData.params[dataIndex].recent.year, function(states) {
        var statelist = states.map(function(s) {
            return s.abbr;
        });
        showMultiGraph(statelist);
    }, 500);

}

// gets called when doing compare to peer states, shows a multigraph for the selected states
showMultiGraphOnPeers = function(ind) {
    graph_title_prefix = "Compare All Peers: ";
    current_graph_function = showMultiGraphOnPeers;
    currentind = ind;
    dataIndex = getParamsOfId(ind);

    getData("data/peers", function(states) {
        var statelist = states.map(function(s) {
            return s.abbr;
        });
        showMultiGraph(statelist);
    }, 500);
}

showMultiGraph = function(states) {
    d3.selectAll("#mbody svg > *").remove();
    document.getElementById("graphTitle").innerHTML = graph_title_prefix + currData.params[dataIndex].name;
    document.getElementById("graphStates").innerHTML = states.join(", ");
    // getData("data/stats/query?states=" + states.join(",") + "&metrics=" + currData.params[dataIndex].name,
    
    var query = DQ.Query().addState(states).addMetric(currData.params[dataIndex].name);
    
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

                var xtickvalues = currData.params[dataIndex].data.map(function(d) {
                    return d["year"];
                });
                
                chart.xAxis.axisLabel("Year").tickValues(xtickvalues).tickFormat(d3.format('.0f'));

                if (currData.params[dataIndex].type == "integer") {
                    chart.yAxis.axisLabel("Count").tickFormat(d3.format(',.0f'));
                } else if (currData.params[dataIndex].type == "rank") {
                    chart.yAxis.axisLabel("Ranking out of 50 States").tickFormat(d3.format('.0f'));
                } else if (currData.params[dataIndex].type == "percentage") {
                    chart.yAxis.axisLabel("%").tickFormat(d3.format(',.2%'));
                } else if (currData.params[dataIndex].type == "numeric") {
                    chart.yAxis.axisLabel("Value").tickFormat(d3.format(',.2f'));
                } else if (currData.params[dataIndex].type == "currency") {
                    chart.yAxis.axisLabel("$").tickFormat(d3.format('$,.2'));
                }
                var data = new Array();
                for (var i = 0; i < multiData.length; i++) {
                    data[i] = {
                        key : multiData[i].abbr
                    };
                    data[i]["values"] = multiData[i].params[0].data.map(function(d) {
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
function getData(url, callback) {
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
function loadState(stateAbbr) {
    getData("data/stats/query?states=" + stateAbbr + "&metrics=all", loadData);
}

// Returns the parameter of currData with the given metricid
function getParamsOfId(idx) {

    for (var i = 0; i < currData.params.length; i++) {
        if (currData.params[i].id == idx)
            return i;
    }

    return -1;

}

// pushes a new table into the page, makes sure the active tab is correct
function loadData(stateData) {
    currData = stateData[0];
    $("#stateTitle").text(currData.name);
    
    if ($("#national").hasClass("active")) {
        current_tab = 'national';
    } else if ($("#talent").hasClass("active")) {
        current_tab = 'talent';
    } else if ($("#cost").hasClass("active")) {
        current_tab = 'cost';
    } else if ($("#economy").hasClass("active")) {
        current_tab = 'economy';
    }

    $.get("" + currData.abbr + "/table", function(data) {
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
    });

}
