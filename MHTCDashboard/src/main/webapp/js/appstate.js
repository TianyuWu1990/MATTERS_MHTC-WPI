


var AS= (function($) {
    function AppState() {
    	this.currentind = 0; 
		this.stateAbbr = "MA";	
		this.multiMode = false; 
		this.selected = ['MA']; 
		this.current_tab = 'national'; 
		this.stateColor="#666";
		this.stateHoverColor='purple';
		this.peerStateColor='MidnightBlue';
		this.stateSelectedColor="green";
		this.defaultStateMultimodeColor="orange";
		
    }

      AppState.prototype.selectState = function(state) {
		
	    if (state == this.stateAbbr) {
             
	        return;
	    }
	
	  // $('#map').usmap('changeStateColor', state, 'green',false);
	    
	    $('#map').usmap('select', state, false);
	    if (!this.multiMode)
	    	{
	    	
	    	this.loadState(state);
	    	}
	    else
	      {
	    	//$('#map').usmap('click', this.stateAbbr);
		    	
	      }
	        
	}
    
    AppState.prototype.loadFunction = function() {
		
	    $('#sidebar li:eq(0) a').tab('show');
	    $(function() {
	        $("[rel='tooltip']").tooltip();
	    });
	    $("#toggle").toggle("slide");
	    $(".statebutton").click(function (){ 
	    	stateClicked = $(this).attr("id").substr(3, 2); 
	    	as.clickCallback(stateClicked);
	    });
	    
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
	            'NJ' : { fill : 'MidnightBlue'},
	            'MA' : {
	                fill : 'green'
	            }
	        },
	    ///// from usmap
	        'stateStyles': {
	            fill: this.stateColor,
	            stroke: "#FFF",
	            "stroke-width": 1,
	            "stroke-linejoin": "bevel",
	            scale: [1.1, 1.1]
	          },
	          'stateHoverStyles': {
	            fill: this.stateHoverColor,
	            stroke: "#FFF",
	            scale: [1.1, 1.1]
	          },
	          'stateHoverAnimation': 300,
	          // State specific hover styles
	          /*'stateSpecificHoverStyles': {
	              'WA': {fill: 'purple'},
	              'CA': {fill: 'purple'},
	              'UT': {fill: '#F90'},
	              'CO': {fill: '#F90'},
	              'TX': {fill: '#F90'},
	              'MN': {fill: '#F90'},
	              'GA': {fill: '#F90'},
	              'NC': {fill: '#F90'},
	              'VA': {fill: '#F90'},
	              'MD': {fill: '#F90'},
	              'PA': {fill: '#F90'},
	              'NY': {fill: '#F90'},
	              'CT': {fill: '#F90'},
	              'NH': {fill: '#F90'},
	              'MA': {fill: '#F90'},
	              'NJ': {fill: '#F90'},
	          },*/
	          'labelWidth': 30,
	          
	          'labelHeight': 25,
	          
	          'labelGap' : 12,
	          
	          'labelRadius' : 6,
	          
	          click: function (event,data) { 
	        	  as.clickCallback(data.name);
	          },
	          
	          'mouseover': function(event, data){
	              $("#mapTitle").text(States.getStateByAbbreviation(data.name).name || "Click to Select a State");
	            },
	            
	            'mouseout': function(event, data){
	              $("#mapTitle").text("Click to Select a State");
	            }

	    // end from usmap
	    });
	   // $('#map').usmap('changeStateColor','MT','RED');
	    
	}
AppState.prototype.changeColor= function(stateAbbr, newColor){
	$("#map").usmap('changeStateColor', stateAbbr, newColor);
}

    
AppState.prototype.clickCallback = function(data){
		var stateClicked=data;
		var changeToColor="";
		/*if (typeof data === 'undefined') { 
			stateClicked = $(this).attr("id").substr(3, 2); 
		}
		else**/
			//stateClicked = data.name
		if(stateClicked==as.stateAbbr)
			return;
			
		if (as.multiMode == false){
			if(as.stateAbbr!=stateClicked){
				if (States.getStateByAbbreviation(as.stateAbbr).peerState)
					changeToColor=as.peerStateColor;
				else
					changeToColor=as.stateColor;
				as.changeColor(as.stateAbbr, changeToColor);
				as.changeColor(stateClicked, as.stateSelectedColor);
				as.loadState(stateClicked);
				//as.stateAbbr=stateClicked;
			}
			
		}
		else{
	        ind = as.selected.indexOf(stateClicked);
	        if(ind === -1){
				as.selected.push(stateClicked);
				as.changeColor(stateClicked, as.stateSelectedColor);
				$("#chk"+stateClicked).addClass("active");
	        }else{
	        	as.selected.splice(ind, 1);
				if (States.getStateByAbbreviation(stateClicked).peerState)
					changeToColor=as.peerStateColor;
				else
					changeToColor=as.stateColor;
				as.changeColor(stateClicked, changeToColor);
				$("#chk"+stateClicked).removeClass("active");
	        }
			
			
		}
	}
AppState.prototype.reset= function(options){
	
    as.selected.forEach(function(val){
  	  if(val!=as.stateAbbr)
  		  {
  		if (States.getStateByAbbreviation(val).peerState)
			changeToColor=as.peerStateColor;
		else
			changeToColor=as.stateColor;
		as.changeColor(val, changeToColor);
		$("#chk"+val).removeClass("active");
  		  }
  	  else
  		  {
  		as.changeColor(as.stateAbbr, as.stateSelectedColor);
		
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
			url : "data/peers/top?metric=" + Metrics.getMetricByID(ind).getId() + "&year=0",
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

   // $("#map").usmap('toggleMultiselect');
    
    as.multiMode=!as.multiMode;
    if(!as.multiMode)
    	{
    	this.reset();
    	}
    	
    else
    	{
    	as.changeColor(as.stateAbbr, as.defaultStateMultimodeColor);
    	}
    this.selected = [ this.stateAbbr ];
    
    if ($("#normallegend").hasClass("hidden")) {
        $("#normallegend").removeClass("hidden");
        $("#multilegend").addClass("hidden");
    } else {
        $("#normallegend").addClass("hidden");
        $("#multilegend").removeClass("hidden");
    }

    /**$(".statebutton").each(function(i) {
    	
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
    });**/
}



AppState.prototype.loadState = function(stateData) {
	
    this.stateAbbr = stateData;
    
    if ($("#profile").hasClass("active")) {
        current_tab = 'profile';
    } else if ($("#national").hasClass("active")) {
        current_tab = 'national';
    } else if ($("#talent").hasClass("active")) {
        current_tab = 'talent';
    } else if ($("#cost").hasClass("active")) {
        current_tab = 'cost';
    } else if ($("#economy").hasClass("active")) {
        current_tab = 'economy';
    }

    
    $.get("" + this.stateAbbr + "/table", function(data) {
        $("#stateTitle").text(States.getStateByAbbreviation(as.stateAbbr).getName());
        $("#state_container").html(data);
        
        
        $("#"+current_tab+"Tab").addClass("active");
        $("#"+current_tab).removeClass("fade");
        $("#"+current_tab).addClass("active");
        
        
        /*if (current_tab == 'national') {
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
        }*/


        $(".statebutton").click(function (){ 
        	stateClicked = $(this).attr("id").substr(3, 2); 
        	as.clickCallback(stateClicked);
        });
     
        
    });
}
AppState.prototype.showMultiGraphOnBottomTen = function(ind) {
	this.set_initializer("Compare Bottom Ten States: ",this.showMultiGraphOnBottomTen);
    this.currentind = ind;
    this.selected = $.parseJSON($.ajax({
		url : "data/peers/bottom?metric=" + Metrics.getMetricByID(ind).getId() + "&year=0",
           
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
