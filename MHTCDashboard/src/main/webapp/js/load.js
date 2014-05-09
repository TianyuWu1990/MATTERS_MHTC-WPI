

toggleMultiSelect = function(ind) {
    $("#multiSelecter").toggle("slide", {
        direction : "right"
    }, 200);
    $("#sidebar").toggle("slide", {
        direction : "left"
    }, 200);
    $("#map").usmap('toggleMultiselect');
    dataIndex = getParamsOfId(ind);

    selected = [currData.abbr];
    
    $(".stateButton").each(function(i) {
        var state;
        $(this).find("input").each(function(checkbox) {
            var id = $(this).attr("id");
            state=id.substr(3,2);
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
            var chart = nv.models.lineChart().x(function(d) {
                return d[0]
            }).y(function(d) {
                return d[1]
            }) // adjusting, 100% is 1.00, not 100 as it is in the data
            .color(d3.scale.category10().range()).useInteractiveGuideline(true);

            chart.xAxis/*.tickValues([ 1078030800000, 1122782400000, 1167541200000, 1251691200000 ])*/.tickFormat(
                    function(d) {
                        return d3.format('1d')(d);//d3.time.format('%x')(new Date(d))
                    });
            var data = {
                key : currData.abbr
            };
            data["values"] = currData.params[dataIndex].data.map(function(d) {
                return [ d["year"], d["value"] ];
            });
            //chart.yAxis.tickFormat(d3.format(',.1%'));
            chart.yAxis.tickFormat(d3.format(',1d'));
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
    
    getData("data/peers/top?metric=" + currData.params[dataIndex].name + "&year=" + currData.params[dataIndex].recent.year, function(
            states) {
        var statelist = states.map(function(s) {
            return s.abbr;
        });
        showMultiGraph(statelist);
    }, 500);
    
}

showMultiGraphOnBottomTen = function(ind) {
    dataIndex = getParamsOfId(ind);
    
    getData("data/peers/bottom?metric=" + currData.params[dataIndex].name + "&year=" + currData.params[dataIndex].recent.year, function(
            states) {
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
                var chart = nv.models.lineChart().x(function(d) {
                    return d[0]
                }).y(function(d) {
                    return d[1] 
                }) // adjusting, 100% is 1.00, not 100 as it is in the data
                .color(d3.scale.category10().range()).useInteractiveGuideline(true);

                chart.xAxis.tickValues([ 1078030800000, 1122782400000, 1167541200000, 1251691200000 ]).tickFormat(
                        function(d) {
                            return d3.format('1d')(d);
                        });
                var data = new Array();
                for (var i = 0; i < multiData.length; i++) {
                    data[i] = {
                        key : multiData[i].abbr
                    };
                    data[i]["values"] = multiData[i].params[0].data.map(function(d) {
                        return [ d["year"], d["value"] ];
                    });
                }
                chart.yAxis.tickFormat(d3.format(',1d'));
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
    var metrics = stateData[0].params;
    document.getElementById('National-tbody').innerHTML = '';
    document.getElementById('Talent-tbody').innerHTML = '';
    document.getElementById('Economy-tbody').innerHTML = '';
    document.getElementById('Cost-tbody').innerHTML = '';
    for (var i = 0; i < metrics.length; i++) {

        var tr = document.createElement('tr');
        var name = document.createElement('td');
        name.appendChild(document.createTextNode(metrics[i].name));
        var trend = document.createElement('td');
        var trendSp = document.createElement('span');
        trendSp.setAttribute("class", "trend_icon trend_" + metrics[i].trend);
        trend.appendChild(trendSp);
        var avg = document.createElement('td');
        if (metrics[i].binName == "National") {
            var innerhtml = "<table class=\"table table-condensed\"	style=\"font-size: 13px;\"><tr><td>Rank</td>";
            for (var j = 0; j < metrics[i].data.length; j++) {
                innerhtml += "<td>" + metrics[i].data[j].value + "</td>";
            }
            innerhtml += "</tr><tr><td>Year</td>";
            for (var j = 0; j < metrics[i].data.length; j++) {
                innerhtml += "<td>" + metrics[i].data[j].year + "</td>";
            }
            innerhtml += "</tr></table>";
            avg.innerHTML = innerhtml;
            // {#data}{/data}</tr><tr><td>Year</td>{#data}d>'{year}</td>{/data}</tr>
            // </table>"
        } else {
            if (metrics[i].recent == null)
                avg.appendChild(document.createTextNode(""));
            else
                avg.appendChild(document.createTextNode(metrics[i].recent.value));
        }
        var src = document.createElement('td');
        var srcLink = document.createElement('a');
        srcLink.href = "http://" + metrics[i].urlFrom;
        srcLink.appendChild(document.createTextNode(metrics[i].sourceName));
        var dropDown = document.createElement('tr');
        
        dropDown.innerHTML = "<div class=\"btn-group btn-group-sm\"><button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"glyphicon glyphicon-chevron-down\"></span></button><ul class=\"dropdown-menu\" role=\"menu\"><li><a href=\"#\" data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showMultiGraphOnPeers(" + metrics[i].id +")\">Compare to Peer States</a></li><li><a href=\"#\" onClick=\"toggleMultiSelect("
                + metrics[i].id
                + ")\">Compare to Select States</a></li><li><a href=\"#\" data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showMultiGraphOnTopTen(" + metrics[i].id +")\">Compare to Top Ten</a></li><li><a href=\"#\" data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showMultiGraphOnBottomTen(" + metrics[i].id +")\">Compare to Bottom Ten</a></li><li><a data-toggle=\"modal\" data-target=\"#myModal\" onClick=\"showGraph("
                + metrics[i].id
                + ")\">View Graph</a></li><li class=\"divider\"></li></ul></div>";
        src.appendChild(srcLink);
        tr.appendChild(name);
        if (metrics[i].binName != "National")
            tr.appendChild(trend);
        tr.appendChild(avg);
        tr.appendChild(src);
        tr.appendChild(dropDown);
        document.getElementById(metrics[i].binName + "-tbody").appendChild(tr);
    }
}
