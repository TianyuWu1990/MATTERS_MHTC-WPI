


var AS= (function($) {
    function AppState() {
    	this.currData = ''; // load.js
		this.stateAbbr = "MA"; // load.js	
		this.multiMode = false; //load.js, jquery.usmap.js	
    }


AppState.prototype.loadFunction = function() {

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

AppState.prototype.getData = function(url, callback) {
    http = new XMLHttpRequest();
    http.open("GET", url, true);
    http.onreadystatechange = function() {
        if (http.readyState == 4 && http.status == 200) {
            callback(JSON.parse(http.responseText));
        }
    }
    http.send(null);
}

AppState.prototype.toggleMultiSelect = function(ind) {
	 
	
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

/*
AppState.prototype.loadNewState('MA');
AppState.prototype.currentState;
AppState.prototype.activeTab;
*/

var publicInterface = {};

publicInterface.create = function() {
	
	return new AppState();
}

return publicInterface;
}($));
