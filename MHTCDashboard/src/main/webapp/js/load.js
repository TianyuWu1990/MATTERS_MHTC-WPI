
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
selectState = function(state) {
    if (state == currData.abbr) {

        return;
    }
    $('#map').usmap('select', state, false);
    if (!multiMode)
        loadState(state);
}
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
var dataIndex = 0;
showGraph = function(ind) {
    dataIndex = getParamsOfId(ind);
    document.getElementById("graphTitle").innerHTML = currData.params[dataIndex].name;
    document.getElementById("graphStates").innerHTML = currData.name;
    setTimeout(function() {
        nv.addGraph(function() {
            var chart = nv.models.lineChart().margin({
                left : 100
            }).x(function(d) {
                return d[0]
            }).y(function(d) {
                return d[1]
            }) // adjusting, 100% is 1.00, not 100 as it is in the data
            .color(d3.scale.category10().range()).useInteractiveGuideline(true);

            chart.xAxis.axisLabel("Year").tickFormat(d3.format('.0f'));

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

showMultiGraphOnSelected = function() {
    showMultiGraph(selected);
}

showMultiGraphOnTopTen = function(ind) {
    dataIndex = getParamsOfId(ind);

    getData("data/peers/top?metric=" + currData.params[dataIndex].name + "&year="
            + currData.params[dataIndex].recent.year, function(states) {
        var statelist = states.map(function(s) {
            return s.abbr;
        });
        showMultiGraph(statelist);
    }, 500);

}

showMultiGraphOnBottomTen = function(ind) {
    dataIndex = getParamsOfId(ind);

    getData("data/peers/bottom?metric=" + currData.params[dataIndex].name + "&year="
            + currData.params[dataIndex].recent.year, function(states) {
        var statelist = states.map(function(s) {
            return s.abbr;
        });
        showMultiGraph(statelist);
    }, 500);

}

showMultiGraphOnPeers = function(ind) {
    dataIndex = getParamsOfId(ind);

    getData("data/peers", function(states) {
        var statelist = states.map(function(s) {
            return s.abbr;
        });
        showMultiGraph(statelist);
    }, 500);
}

showMultiGraph = function(states) {
    document.getElementById("graphTitle").innerHTML = currData.params[dataIndex].name;
    document.getElementById("graphStates").innerHTML = states.join(", ");
    getData("data/stats/query?states=" + states.join(",") + "&metrics=" + currData.params[dataIndex].name, function(
            multiData) {
        setTimeout(function() {
            nv.addGraph(function() {
                var chart = nv.models.lineChart().margin({
                    left : 100
                }).x(function(d) {
                    return d[0]
                }).y(function(d) {
                    return d[1]
                }) // adjusting, 100% is 1.00, not 100 as it is in the data
                .color(d3.scale.category10().range()).useInteractiveGuideline(true);

                chart.xAxis.axisLabel("Year").tickFormat(d3.format('.0f'));

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

function loadState(stateAbbr) {
    getData("data/stats/query?states=" + stateAbbr + "&metrics=all", loadData);
}

function getParamsOfId(idx) {

    for (var i = 0; i < currData.params.length; i++) {
        if (currData.params[i].id == idx)
            return i;
    }

    return -1;

}

var currData = "";

function loadData(stateData) {
    currData = stateData[0];

    $.get("" + currData.abbr + "/table", function(data) {
        $("#sidebar").html(data);
        $("#nationalTab").addClass("active");
        $("#national").removeClass("fade");
        $("#national").addClass("active");
    });

}
