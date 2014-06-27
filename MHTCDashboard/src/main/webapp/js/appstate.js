


var AS= (function($) {
    function AppState() {
    	this.currentind = 0; // load.js
		this.stateAbbr = "MA"; // load.js	
		this.multiMode = false; //load.js, jquery.usmap.js	
		this.selected = ['MA']; //load.js, jquery.usmap.js
		this.current_tab = 'national'; // load.js
    }

    AppState.prototype.toggleMultiSelect = function(ind) {
		this.currentind=ind;		
	    $("#multiSelecter").toggle("slide", {
	        direction : "right"
	    }, 200);
	    $("#sidebar").toggle("slide", {
	        direction : "left"
	    }, 200);

	    $("#map").usmap('toggleMultiselect');
	  
	    //dataIndex = this.getParamsOfId(ind);
        
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
	        
	        if ($(this).hasClass("active") && state != as.stateAbbr) {
	            $(this).removeClass("active");
	            
	        }

	        if (state == as.stateAbbr && !$(this).hasClass("active")) {
	            $(this).addClass("active");
	           
	        }
	    });
	}

    AppState.prototype.selectState = function(state) {
		
	    if (state == stateAbbr) {
             
	        return;
	    }
	   
	    $('#map').usmap('select', state, false);
	    if (!this.multiMode)
	    	
	        this.loadState(state);
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
   
	        stateAbbr =  data[0][0].state.abbr;
	      
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
	this.currentind=ind;		
    $("#multiSelecter").toggle("slide", {
        direction : "right"
    }, 200);
    $("#sidebar").toggle("slide", {
        direction : "left"
    }, 200);

    $("#map").usmap('toggleMultiselect');
  
    //dataIndex = this.getParamsOfId(ind);
    
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

/***AppState.prototype.loadState = function(stateAbbr) {
	
    this.getData("data/stats/query?states=" + stateAbbr + "&metrics=all", this.loadData);
    
}***/

AppState.prototype.loadState = function(stateData) {
	//currData = stateData[0];
    stateAbbr = stateData;
    
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
      //  $("#stateTitle").text(currData[0].state.name);
        $("#stateTitle").text(States.getStateByAbbreviation(stateAbbr).getName());
        
    });

}


var publicInterface = {};

publicInterface.create = function() {
	
	return new AppState();
}

return publicInterface;
}($));
