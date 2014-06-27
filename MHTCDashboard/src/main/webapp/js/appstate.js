


var AS= (function($) {
    function AppState() {
    	this.currentind = 0; 
		this.stateAbbr = "MA";	
		this.multiMode = false; 
		this.selected = ['MA']; 
		this.current_tab = 'national'; 
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
	        
	        if ($(this).hasClass("active") && state != this.stateAbbr) {
	            $(this).removeClass("active");
	            
	        }

	        if (state == this.stateAbbr && !$(this).hasClass("active")) {
	            $(this).addClass("active");
	           
	        }
	    });
	}

    AppState.prototype.selectState = function(state) {
		
	    if (state == this.stateAbbr) {
             
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
	    
	
	
	    
	}
AppState.prototype.set_initializer=function(title_prefix_in, this_graph_in ) {
	cm.graph_title_prefix = title_prefix_in;
	cm.current_graph_function = this_graph_in;
}
 AppState.prototype.showGraph = function(ind) {
	this.set_initializer(" ",this.showGraph); 
    this.currentind = ind;		 
	this.selected = [ this.stateAbbr ];
	cm.showMultiGraph(this.selected);
	}
 AppState.prototype.showMultiGraphOnSelected = function() {
    this.set_initializer("Compare to Selected: ",this.showMultiGraphOnSelected);
    cm.showMultiGraph(this.selected);
	   
	}
 
 AppState.prototype.showMultiGraphOnTopTen = function(ind) {
	    this.set_initializer("Compare Top Ten States: ",this.showMultiGraphOnTopTen); 
		this.currentind = ind;
	  	this.selected = $.parseJSON($.ajax({
			url : "data/peers/top?metric=" + Metrics.getMetricByID(ind).getName() + "&year=0",
				dataType : 'text',
				async : false,
				success : function(data) {
					return data;
				}
			}).responseText).map(function(s) {
	            return s.abbr;
	        });
	    
  
	    cm.showMultiGraph( this.selected);
	}

/**AppState.prototype.getData = function(url, callback) {
    http = new XMLHttpRequest();
    http.open("GET", url, true);
    http.onreadystatechange = function() {
        if (http.readyState == 4 && http.status == 200) {
            callback(JSON.parse(http.responseText));
        }
    }
    http.send(null);
}**/

AppState.prototype.toggleMultiSelect = function(ind) {
	this.currentind=ind;		
    $("#multiSelecter").toggle("slide", {
        direction : "right"
    }, 200);
    $("#sidebar").toggle("slide", {
        direction : "left"
    }, 200);

    $("#map").usmap('toggleMultiselect');
  
    
    
    this.selected = [ this.stateAbbr ];
    
    if ($("#normallegend").hasClass("hidden")) {
        $("#normallegend").removeClass("hidden");
        $("#multilegend").addClass("hidden");
    } else {
        $("#normallegend").addClass("hidden");
        $("#multilegend").removeClass("hidden");
    }

    $(".statebutton").each(function(i) {
    	
        var state;
        $(this).find("input").each(function(checkbox) {
            var id = $(this).attr("id");
            state = id.substr(3, 2);
            
        });
        
        if ($(this).hasClass("active") && state != this.stateAbbr) {
            $(this).removeClass("active");
            
        }

        if (state == this.stateAbbr && !$(this).hasClass("active")) {
            $(this).addClass("active");
           
        }
    });
}



AppState.prototype.loadState = function(stateData) {
	
    this.stateAbbr = stateData;
    
    if ($("#national").hasClass("active")) {
        current_tab = 'national';
    } else if ($("#talent").hasClass("active")) {
        current_tab = 'talent';
    } else if ($("#cost").hasClass("active")) {
        current_tab = 'cost';
    } else if ($("#economy").hasClass("active")) {
        current_tab = 'economy';
    }

    $("#stateTitle").text(States.getStateByAbbreviation(this.stateAbbr).getName());
    
    $.get("" + this.stateAbbr + "/table", function(data) {
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
AppState.prototype.showMultiGraphOnBottomTen = function(ind) {
	this.set_initializer("Compare Bottom Ten States: ",this.showMultiGraphOnBottomTen);
    this.currentind = ind;
    this.selected = $.parseJSON($.ajax({
		url : "data/peers/bottom?metric=" + Metrics.getMetricByID(ind).getName() + "&year=0",
           
		dataType : 'text',
		async : false,
		success : function(data) {
			return data;
		}
	}).responseText).map(function(s) {
        return s.abbr;
    });
	

    cm.showMultiGraph(this.selected);
}

AppState.prototype.showMultiGraphOnPeers = function(ind) {
	this.set_initializer("Compare All Peers: ",this.showMultiGraphOnPeers);
    
	
    this.currentind = ind;
    this.selected = States.getPeers().map(function(s) {
        return s.abbr;
    });
    cm.showMultiGraph(this.selected);
    
}



var publicInterface = {};

publicInterface.create = function() {
	
	return new AppState();
}

return publicInterface;
}($));
