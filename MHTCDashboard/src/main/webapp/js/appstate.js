
/*
 * This module handles the Application User responses and State.
 */

var AS= (function($) {
    function AppState() {
    	this.currentind = 0; 
		this.stateAbbr = "MA";	
		this.multiMode = false; 
		this.selected = ['MA'];
		
		this.current_tab = 'national'; 
		this.stateColor="#666";
		this.stateHoverColor='purple';
		this.peerStateColor=  '#3D8AF4';   /**'MidnightBlue; #977509';**/
		this.stateSelectedColor="green";
		this.defaultStateMultimodeColor="orange";
		this.ordered_states_metrics=[];
		this.current_tab_style="#profiletab a";
		this.selected_multiple_metrics=[]; /********Array where multiple metrics selected are stored*********/
		this.selected_multiple_years=[];/********Array where multiple years selected are stored*********/
		var array_years_global=[];
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
    
      /*
       * Initializes the application. Called when page is loaded.
       */
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
	    /*
	     * Initializes the US Map with custom styles. 
	     */
	    $('#map').usmap({
	        'stateSpecificStyles' : {
	            'WA' : {
	                fill : as.peerStateColor
	            },
	            'CA' : {
	                fill : as.peerStateColor
	            },
	            'UT' : {
	                fill : as.peerStateColor
	            },
	            'CO' : {
	                fill : as.peerStateColor
	            },
	            'TX' : {
	                fill : as.peerStateColor
	            },
	            'MN' : {
	                fill : as.peerStateColor
	            },
	            'GA' : {
	                fill : as.peerStateColor
	            },
	            'NC' : {
	                fill : as.peerStateColor
	            },
	            'VA' : {
	                fill : as.peerStateColor
	            },
	            'MD' : {
	                fill : as.peerStateColor
	            },
	            'PA' : {
	                fill : as.peerStateColor
	            },
	            'NY' : {
	                fill : as.peerStateColor
	            },
	            'CT' : {
	                fill : as.peerStateColor
	            },
	            'NH' : {
	                fill : as.peerStateColor
	            },
	            'NJ' : { fill : as.peerStateColor},
	            'MA' : {
	                fill : 'green'
	            }
	        },
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
	    });
	    
	};
    /*
     * Gets the array of year in which the metric appears. 
     */
    AppState.prototype.getYearsMetric=function(multiData){
    	var array_years=[];
		var k=0;
		for(var i=0; i<multiData.length; i++){
			for(var j=0; j<multiData[i][0].dataPoints.length; j++){
				if(array_years.indexOf(multiData[i][0].dataPoints[j].year) < 0){
					array_years[k]=multiData[i][0].dataPoints[j].year;
					k++;
				}
			}
		}
		return array_years;
    };
    /*
     * This function generates Heat Map 
     * Input: Metric ID and Base Color for heat map
     */
    AppState.prototype.SetHeatMap=function (ind,baseColor, tag_id,year_in){
    	if(!this.multiMode) /** This was causing a weird error specially because the dropdown menu was heatmapping other metrics*/
    		this.currentind=ind;
    	
    	var selected_states_global= States.getAllstates().map(function(s) {
    		return s.abbr;
    	});
     	var query = DQ.create().addState(selected_states_global).addMetric(Metrics.getMetricByID(this.currentind).getName());
        query.execute(function(
                multiData) {
        		setTimeout(function() {
        			/*******************************************************************************/
        			/***GET ALL POSSIBLES YEARS IN WHICH THE METRIC APPEARS FOR AT LEAST ONE STATE**/
        			/*******************************************************************************/
        			
        			/*var array_years=[];
        			var k=0;
        			for(var i=0; i<multiData.length; i++){
        				for(var j=0; j<multiData[i][0].dataPoints.length; j++){
        					if(array_years.indexOf(multiData[i][0].dataPoints[j].year) < 0){
        						array_years[k]=multiData[i][0].dataPoints[j].year;
        						k++;
        					}
        				}
        			}*/
        			 /*var selplaybutton = $("#playbutton");
        			 selplaybutton.empty();
        			 selplaybutton.append('<div class="btn btn-info" id="#playbuttonanimation">Play</div>');
        			  */    			
						
					
        			
        			var array_years=as.getYearsMetric(multiData);
        			array_years_global=as.getYearsMetric(multiData);///HAVE TO MAKE THIS CALL. WIL ELIMINATE ONE OR THE OTHER
        			
        			/*var sel = $("#yearHeatMap");
        			sel.empty();
        			var size_tam=array_years.length-1;
        			*/
        			array_years.sort(function(a,b){return b - a});
        			array_years_global.sort(function(a,b){return a - b});
        			/*for(var k=0; k<array_years.length; k++){
        				if(year_in!=array_years[k]){
        					console.log("array_years "+array_years[k]);
        					sel.append('<option value="' + array_years[k] + '">' + array_years[k] + '</option>');
        				}   				 
        					
        				else{
        					console.log("year_in "+year_in);
        					sel.append('<option value="' + array_years[k] + '" selected>' + year_in + '</option>');
        				}
        					
        			}*/
        			/*******************************************************************************/
        			/***GET ALL POSSIBLES YEARS IN WHICH THE METRIC APPEARS FOR AT LEAST ONE STATE**/
        			/*******************************************************************************/
        			if(year_in==-1){
						year_in = array_years[0];
						//alert(year_in);
					}
        			
        			var seltimeline=$("#timeline");
					seltimeline.empty();
				
					seltimeline.append('<table ><tr>');
					for(var k=array_years.length-1; k>=0; k--){
						if(year_in!=array_years[k]){
							seltimeline.append('<td nowrap="true" valign="top"><button  class="btn btn-default" id="click'+array_years[k]+'"><li  >'+array_years[k]+'</li></button></td><td></td>');
						}else{
							seltimeline.append('<td nowrap="true" valign="top"><button  class="btn btn-primary btn-right" ><li id="click"+'+array_years[k]+'>'+year_in+'</li></button></td><td></td>');
						}
					}
					seltimeline.append('</tr></table>');
        			
						
 					
        			var j;
        			var sentinel;
        			for(var i=0; i<multiData.length; i++){
        				if((multiData[i][0].state.abbr!='US')&&(multiData[i][0].state.abbr!='DC')){
        					j=0;
        					sentinel=false;
        					while((!sentinel)&&(j<multiData[i][0].dataPoints.length)){
        						if(multiData[i][0].dataPoints[j].year==year_in){
        							sentinel=true;
        						}
        						else{
        							j++;
        						}
        							
        					}
        					if(sentinel){
        						as.ordered_states_metrics[i]={ value_element: multiData[i][0].dataPoints[j].value,
        													   state_element:multiData[i][0].state.abbr,
        													   binName_element:multiData[0][0].metric.binName,
        													   metricType: multiData[0][0].metric.type}
        					}
        					else{
        						as.ordered_states_metrics[i]={ value_element: 0.0,
	        								   				   state_element:multiData[i][0].state.abbr,
	        								   				   binName_element:multiData[0][0].metric.binName,
	        								   				   metricType: multiData[0][0].metric.type}
        					}
        							
        				}
         			}
        			
    			as.ordered_states_metrics.sort(function (a, b) {
        	    	return b.value_element-a.value_element;
        	    });
    			
        	          	    
    			$(tag_id).usmap({
    		        
    		        'stateStyles': {
    		        	
    		            fill: this.stateColor,
    		            stroke: "#FFF",
    		            "stroke-width": 1.1,
    		            "stroke-linejoin": "bevel",
    		            scale: [1.1, 1.1],
    		            
    		            
    		          },
    		          'stateHoverStyles': {
    		            fill: "#FFCCFF",
    		            stroke: "#FFF",
    		            scale: [1.1, 1.1]
    		          },
    		          
    		          'showLabels' : false,
    		        
    		          
    		          'mouseover': function(event, data){
    		        	  	  			var found = $.map(as.ordered_states_metrics, function(val,i) {
    		        	  	  			var indexcounter;
    		        	  	  			var percentage="";
    		        	  	  			var return_value_element;
    		        	  	  			if(val.state_element == States.getStateByAbbreviation(data.name).abbr){
    		        	  	  				if(as.ordered_states_metrics[0].binName_element!='National')
    		        	  	  					indexcounter=i+1;
    		        	  	  				else
    		        	  	  					indexcounter=as.ordered_states_metrics.length-i;
    		        	  	  				if(val.value_element==0.0){
    		        	  	  					return "No value for this state";  
    		        	  	  				}else{
    		        	  	  					if(as.ordered_states_metrics[0].metricType=="percentage"){
    		        	  	  						return_value_element=val.value_element*100;
    		        	  	  						return_value_element= Math.round(return_value_element*100)/100+"%";
    		        	  	  				}
	    		        				 
	    		        			  else{
	    		        				  return_value_element=Math.round( val.value_element*100)/100;
	    		        			  }
		        			    	  return "Ranking: "+indexcounter+" Value: "+return_value_element;
		        			      } 
    		        		  }
    		        		  
    		        		}); 
    		        	  	  		
    		        	  $("#graphStatesHeatMapPos0").text("State: "+States.getStateByAbbreviation(data.name).name);
    		        	  $("#graphStatesHeatMapPos1").text(found[0]);
    		            },
    		            
    		            'mouseout': function(event, data){
    		              $("#graphStatesHeatMapPos0").text("Selected States: All States");
    		              $("#graphStatesHeatMapPos1").text("Ranking");
    		            },
    		    });
    			var selheatmapmeter=$("#heatmapmeter");
				selheatmapmeter.empty();
				var arrayNewColor=[];
				for(var i=0; i<as.ordered_states_metrics.length;i++){
        	    	if(multiData[0][0].metric.binName!="National"){
        	    		newColor=getNewColor(baseColor,as.ordered_states_metrics[i].value_element,as.ordered_states_metrics[0].value_element);
        	    	}
        	    	else{
        	    		newColor=getNewColor(baseColor,as.ordered_states_metrics[0].value_element-as.ordered_states_metrics[i].value_element +1,as.ordered_states_metrics[0].value_element);
        	    	}
        	    	arrayNewColor[i]=newColor;
        	    	//selheatmapmeter.append('<td  valign="top" bgcolor="'+newColor+'">&nbsp;&nbsp;</td>');
        	    	$(tag_id).usmap('changeStateColor',  as.ordered_states_metrics[i].state_element, newColor);
        				
        		}
/****************************************************************************/
/* THIS PART COULD BE IMPROVED THIS JUST SHOWS THE BAR degraded. 
 * The only problem is the way it degrades either in increasiung order
 * or decreseing order depending on the metric**/
/****************************************************************************/
 				
				selheatmapmeter.append('<table border="1" cellpadding=0  cellspacing="0">');
				var r;
				var return_value_element;
				
        	    if(multiData[0][0].metric.binName=="National"){
        	    	selheatmapmeter.append('<tr>');
        	    	for(var i=0; i<as.ordered_states_metrics.length;i++){
        	    		if(as.ordered_states_metrics[i].metricType=="percentage")
  	  						return_value_element= Math.round((as.ordered_states_metrics[i].value_element*100)*100)/100+"%";
        	    		else
        	    			return_value_element=as.ordered_states_metrics[i].value_element;
        	    		if((i==0)||(as.ordered_states_metrics[i].state_element==as.stateAbbr)){
            	    		selheatmapmeter.append('<td><strong>'+ as.ordered_states_metrics[i].state_element+':</strong> '+return_value_element+'&nbsp;</td>');
            	    	}else{
            	    		r=i+1;
            	    		if((r==as.ordered_states_metrics.length)&&(as.ordered_states_metrics[i].state_element!=as.stateAbbr)){
            	    			selheatmapmeter.append('<td><strong>'+ as.ordered_states_metrics[i].state_element+': </strong>'+return_value_element+'&nbsp;</td>');
            	    		}else{
            	    			selheatmapmeter.append('<td  valign="top">&nbsp;&nbsp;</td>');
            	    		}
            	    		
            	    	}
        	    	} 
        	    	selheatmapmeter.append('</tr><tr>');
        	    	for(var i=0; i<as.ordered_states_metrics.length;i++){
        	    		selheatmapmeter.append('<td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td>');
        	    	}   
        	    } else{
        	    	selheatmapmeter.append('<tr>');
        	    	for(var i=as.ordered_states_metrics.length-1; i>=0;i--){
        	    		if(as.ordered_states_metrics[i].metricType=="percentage")
        	    			return_value_element= Math.round((as.ordered_states_metrics[i].value_element*100)*100)/100+"%";
        	    		else
        	    			return_value_element=as.ordered_states_metrics[i].value_element;
        	    		if((i==as.ordered_states_metrics.length-1)||(as.ordered_states_metrics[i].state_element==as.stateAbbr)){
            	    		selheatmapmeter.append('<td><strong>'+ as.ordered_states_metrics[i].state_element+':</strong> '+return_value_element+'&nbsp;</td>');
            	    	}else{
            	    		r=i;
            	    		if((r==0)&&(as.ordered_states_metrics[i].state_element!=as.stateAbbr)){
            	    			selheatmapmeter.append('<td><strong>'+ as.ordered_states_metrics[i].state_element+':</strong> '+return_value_element+'&nbsp;</td>');
            	    		}else{
            	    			selheatmapmeter.append('<td  valign="top">&nbsp;&nbsp;</td>');
            	    		}
            	    		
            	    	}
        	    	}
        	    	selheatmapmeter.append('</tr><tr>');
        	    	for(var i=as.ordered_states_metrics.length-1; i>=0;i--){
        	    		selheatmapmeter.append('<td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td>');
        	    	} 
        	    }
        	    selheatmapmeter.append('</tr></table>');
				
        	    
        	    
        	    
        	    
        	    /*************************************************************************************************************/
        	    /*********************VERTICAL HEAT MAP **********************************************************************/
        	    /*************************************************************************************************************/
        	    $("#verticalheatmapmeter").removeClass("hidden");
        	    $("#mapTitle").text("Heat map for "+year_in);
        	    	    var selverticalheatmapmeter=$("#verticalheatmapmeter");
        	    		selverticalheatmapmeter.empty();
        	    	    
        	    		selverticalheatmapmeter.append('<table border="1" cellpadding=0  cellspacing="0"  style="height: 10px;">');
        	    		var r;
        	    		var return_value_element;
        	    	    var enter=false;
        	    	    var state_name;
    	    	    	var position;
    	    	    	var written_to_detail=false;
        	    		if(multiData[0][0].metric.binName!="National"){
        	    	    	
        	    	    	
        	    	    	
        	    	    	for(var i=0; i<as.ordered_states_metrics.length;i++){
        	    	    		if((i==as.ordered_states_metrics.length-1)){
        	    	    			position=i+1;
        	    	    			selverticalheatmapmeter.append('<tr><td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td><td valign="top" align="left" nowrap="true"><strong>'+as.ordered_states_metrics[i].state_element+'</strong>Pos: '+position+'</td></tr>');
	    	    					
        	    	    		}else{
        	    	    			if((i==0)||(as.ordered_states_metrics[i].state_element==as.stateAbbr)){
            	    	    			state_name=as.ordered_states_metrics[i].state_element;
            	    	    			position=i+1;
            	    	    			written_to_detail=true;
            	    	    		}else{
            	    	    			if(!written_to_detail){
            	    	    				state_name=null;
            	    	    				position=null;
            	    	    			}
            	    	    				
            	    	    		}
            	    	    		if(i%2==0){
            	    	    			if(!enter){
            	    	    				enter=true;
            	    	    				if(written_to_detail){
            	    	    					selverticalheatmapmeter.append('<tr><td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td><td valign="top" align="left" nowrap="true"><strong>'+state_name+'</strong>Pos: '+position+'</td></tr>');
            	    	    					written_to_detail=false;
            	    	    				}else{
            	    	    					selverticalheatmapmeter.append('<tr><td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td><td valign="top" align="left"></td></tr>');
            	    	    				}
        	    	    	    		}else{
        	    	    	    			enter=false;
        	    	    	    		}
            	    	    		}
        	    	    		}
        	    	    		
        	    	    			
        	    	    	}   
        	    	    } else{
        	    	    	
        	    	    	var pivotcounter=as.ordered_states_metrics.length;
        	    	    	for(var i=as.ordered_states_metrics.length-1; i>=0;i--){
        	    	    		if((i==0)){
        	    	    			position=-1*(i-as.ordered_states_metrics.length);
        	    	    			selverticalheatmapmeter.append('<tr><td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td><td valign="top" align="left" nowrap="true"><strong>'+as.ordered_states_metrics[i].state_element+'</strong>Pos: '+position+'</td></tr>');
	    	    					
        	    	    		}else{
        	    	    			if((i==as.ordered_states_metrics.length-1)||(as.ordered_states_metrics[i].state_element==as.stateAbbr)){
            	    	    			state_name=as.ordered_states_metrics[i].state_element;
            	    	    			if(i==as.ordered_states_metrics.length-1)
            	    	    				position=-1*(pivotcounter-as.ordered_states_metrics.length-1);
            	    	    			else
            	    	    				position=-1*(i-as.ordered_states_metrics.length);
            	    	    			written_to_detail=true;
            	    	    		}else{
            	    	    			if(!written_to_detail){
            	    	    				state_name=null;
            	    	    				position=null;
            	    	    			}
            	    	    				
            	    	    		}
            	    	    		if(i%2==0){
            	    	    			if(!enter){
            	    	    				enter=true;
            	    	    				if(written_to_detail){
            	    	    					selverticalheatmapmeter.append('<tr><td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td><td valign="top" align="left" nowrap="true"><strong>'+state_name+'</strong>Pos: '+position+'</td></tr>');
            	    	    					written_to_detail=false;
            	    	    				}else{
            	    	    					selverticalheatmapmeter.append('<tr><td  valign="top" bgcolor="'+arrayNewColor[i]+'">&nbsp;&nbsp;</td><td valign="top" align="left"></td></tr>');
            	    	    				}
        	    	    	    		}else{
        	    	    	    			enter=false;
        	    	    	    		}
            	    	    		}
        	    	    		}
        	    	    		
        	    	    }
        	    	    selverticalheatmapmeter.append('</table>');
        	    	    /*************************************************************************************************************/
        	    	    /*********************VERTICAL HEAT MAP **********************************************************************/
        	    	    /*************************************************************************************************************/
        	    	    }

        	    
        	    
        		});	
        });
       
    	   
    };
/* AppState.prototype.getYearMultipleSelect=function(selectedOptions){
	 var sel=$("yearmultiplemetric");
	 sel.empty();
	 var j=0;
	 this.selected_multiple_years=[];
	 for(var i=0; i<selectedOptions.length; i++){
		 if(selectedOptions[i].selected){
			 this.selected_multiple_years[j]=selectedOptions[i].value;
			 j++;
		 }
	 }
 };  */
 AppState.prototype.getMetricMultipleSelect=function(selectedOptions){
	 var sel = $("#selectedmultiplemetrics");
     sel.empty();
     this.selected_multiple_metrics=[];
     var j=0;
	 for (var i=0; i<selectedOptions.length;i++){
		 if(selectedOptions[i].selected)
			 {
			// sel.append('<option value="' + selectedOptions[i].value + '">' + Metrics.getMetricByID(selectedOptions[i].value).getName() + '</option>');
			 //sel.append('<tr><td valign="top" align="left">'+Metrics.getMetricByID(selectedOptions[i].value).getName() +'</td></tr>');
			 this.selected_multiple_metrics[j]=selectedOptions[i].value;
			 
			 j++;
			 }
	 }
	// sel.append('<table>');
	 
 };
/**
 * 
 * @param ind Metric id
 * @param style_in  rgb color of the tabbed tab
 * @param tag_id #mbodyHeatMap to generate heat map on a modal window
 * 
 */    
AppState.prototype.showHeatMapGraph=function (ind,style_in, tag_id, year_in){
	this.current_tab_style=style_in;
	document.getElementById("graphTitleHeatMap").innerHTML ='Heat map: '+ Metrics.getMetricByID(ind).getName(); 
	//document.getElementById("graphCatHeatMap").innerHTML='Category: '+Metrics.getMetricByID(ind).binName;
	var baseColor=colorToHex(getStyleRuleValue('background-color',style_in));
	var degradedColor=getNewColor(baseColor,110,100);
	console.log("error degradedColor : "+degradedColor);
	var degradationColorWindow="rgb("+hexToRgb(degradedColor).r+","+hexToRgb(degradedColor).g+","+hexToRgb(degradedColor).b+")";
    //this.changeColorModalWindow(tag_id.substr(1),degradationColorWindow);
   // console.log("error2 : "+ind);
	if(year_in==-1){
		stopHeatMapAnimation();/**Users could left the animation running......**/
		$("#stopbuttonanimation").prop('disabled', true); 
		$("#playbuttonanimation").prop('disabled', false);
		global_timer = null;
		year_global=-1;
		globalcounter=0;
	}
		
	this.SetHeatMap(ind,baseColor, tag_id,year_in);

};
/**
 * 
 * @param ind Metric id
 * @param style_in rgb color of the tabbed tab
 * @param tag_id #map  to generate heat map on the right side of the screen
 */    
AppState.prototype.setHeatMapOnHover=function (ind,style_in, tag_id, year_in){
//	alert(getStyle('background-color',style_in));
	var baseColor=colorToHex(getStyleRuleValue('background-color',style_in));
	//console.log("error3 : "+ind);
	this.SetHeatMap(ind,baseColor, tag_id,year_in);
};
/*
     * Clears the heat map and restores the original US Map 
*/
    
 AppState.prototype.UnSetHeatMap=function (style){
	
	if(global_timer!=null)/**This is in case someone left the heatmap running**/
		stopHeatMapAnimation();
	var selected_states=States.getAllstates().map(function(s){
		return s.abbr;
	});
	var newColor=getStyleRuleValue('background-color',style);
	this.peerStateColor=newColor;

	/** Changes the of the map to a particular color according to the tab hit**/
	
		for(var i=0; i<selected_states.length;i++){
			if(i==selected_states.length-1){
	    		 band=1;
	    	}
			if((selected_states[i]!='US')&&(selected_states[i]!='DC')){
				if(this.stateAbbr==selected_states[i]){
					$("#map").usmap('changeStateColor',selected_states[i], this.stateSelectedColor);
				}
				else{
					if(States.getStateByAbbreviation(selected_states[i]).isPeerState()){
						
						$("#map").usmap('changeStateColor', selected_states[i], newColor);
						
						
					}else{
						
     					$("#map").usmap('changeStateColor', selected_states[i], this.stateColor);
							
						
					}
				}
			}
				
			
			
		}
		
		
};



 /*
  * Changes the color of a state on the US Map
  * Input: State Abbreviation and Color for the state.
  */
AppState.prototype.changeColor= function(stateAbbr, newColor){
	
	$("#map").usmap('changeStateColor', stateAbbr, newColor);
	
	
};

    
/*
 * Callback function to load the data for a clicked state. Used in click event of buttons and shapes for each states.  
 *  
*/
AppState.prototype.clickCallback = function(data){
		this.turnOffUnattendedHeatMapAnimation();//Just in case if users have left the heatmap running
		if(!as.multiMode)
	    	this.UnSetHeatMap(this.current_tab_style);
		var stateClicked=data;
		var changeToColor="";
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
			this.checkIfAllStatesChecked();
			
		}
	};

AppState.prototype.checkIfAllStatesChecked=function(){
	var indexState;
	var returnvalue;
	var counter_unselected=0;
	var size_array_states;
	var size_array_states_t;
	
	var selected_states=States.getAllstates().map(function(s){
			return s.abbr; 
	});
	size_array_states=selected_states.length-1;
	for(var i=0; i<selected_states.length;i++){
		if(selected_states[i]!='US'){
			indexState = as.selected.indexOf(selected_states[i]);
			if(indexState==-1){ /** Unselected tabs**/
				counter_unselected++;
			}
		}
	}
	
	size_array_states_t=size_array_states-2;
	
	if(size_array_states_t<counter_unselected){ /***If all states are unselected then only the SELECTALL Button may appear***/
		
		$("#selectall").prop("disabled",false);
		$("#unselectall").prop("disabled", true);
		$("#selectallmultiplemetric").prop("disabled",false);
		$("#unselectallmultiplemetric").prop("disabled", true);
		$("#selectpeerstates").prop("disabled",false);
		
	}
	else if(counter_unselected==0){/** If all states are selected then the SELECTALL button gets blocked and the UNSELECTALL gets unblocked**/
		$("#selectall").prop("disabled",true);
		$("#unselectall").prop("disabled", false);
		$("#selectallmultiplemetric").prop("disabled",true);
		$("#unselectallmultiplemetric").prop("disabled", false);
		$("#selectpeerstates").prop("disabled",false);
		
	}else{ /** some states have been selected, therefore both buttons may appear**/
		$("#selectall").prop("disabled",false);
		$("#unselectall").prop("disabled",false);
		$("#selectallmultiplemetric").prop("disabled",false);
		$("#unselectallmultiplemetric").prop("disabled",false);
		$("#selectpeerstates").prop("disabled",false);
	}
	
};
/*
 * Resetting the US Map to its previously selected state when returning from Multimode.
 * param options is kept for future use.
*/
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
  };
    

/*
 * Initializes graph title and graph function.
 */
AppState.prototype.set_initializer=function(title_prefix_in, this_graph_in ) {
	cm.graph_title_prefix = title_prefix_in;
	cm.current_graph_function = this_graph_in;
};

/*
 * This function calls the chart module for displaying the graph type for currently selected state.
 * Input: Metric ID
 */
 AppState.prototype.showGraph = function(ind) {
	this.set_initializer(" ",this.showGraph); 
    this.currentind = ind;		 
	this.selected = [ this.stateAbbr ];
	cm.showMultiGraph(this.selected);
	};

 
 /*
  * This function calls the chart module for displaying the graph type for currently selected states when in Multimode.
  * Input: Metric ID
  */
 AppState.prototype.showMultiGraphOnSelected = function() {
    this.set_initializer("Compare to Selected: ",this.showMultiGraphOnSelected);
   // console.log("error5 : "+this.currentind);
  //  alert(this.currentind);
    cm.showMultiGraph(this.selected);
	   
	}

 AppState.prototype.showMultipleMetricsStatesYears = function(year_in) {
	    this.set_initializer("Compare to Selected: ",this.showMultipleMetricsStatesYears);
	     cm.showMultipleMetricsStatesYears(this.selected,this.selected_multiple_metrics,year_in);
	    
		   
		}
 /*
  * This function calls the chart module for displaying the graph type for TOP TEN states for the current Metric.
  * Input: Metric ID
  */ 
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

 /*
  * This function calls the chart module for displaying the graph type for BOTTOM TEN states for the current Metric.
  * Input: Metric ID
  */ 
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

 /*
  * This function calls the chart module for comparative display of the Peers states with MA for current metric.
  * Input: Metric ID
  */ 
AppState.prototype.showMultiGraphOnPeers = function(ind) {
		this.set_initializer("Compare All Peers: ",this.showMultiGraphOnPeers);
	    
		
	    this.currentind = ind;
	    
	    this.selected = States.getPeers().map(function(s) {
	        return s.abbr;
	    });
	   
	   cm.showMultiGraph(this.selected);
	    
	};
AppState.prototype.SelectPeerStates=function(){
	var indexState;
	this.SelectUnSelectAllTabs(4);
	var selected_states=States.getPeers().map(function(s){
		return s.abbr; 
	});
    for(var i=0; i<selected_states.length;i++){
    	this.clickCallback(selected_states[i]);
	}
    $("#selectallmultiplemetric").prop("disabled",false);
	$("#unselectallmultiplemetric").prop("disabled", false);
	$("#selectpeerstates").prop("disabled",true);		
		
};

 	
 AppState.prototype.SelectUnSelectAllTabs=function(type_in){
	 var indexState;
	 var selected_states=States.getAllstates().map(function(s){
				return s.abbr; 
	 });
	
	 
	 
	 for(var i=0; i<selected_states.length;i++){
			if(selected_states[i]!='US'){
				indexState = as.selected.indexOf(selected_states[i]);
				if(((type_in==1)||(type_in==3))&&(indexState==-1)){/**SELECT ALL*/
					this.clickCallback(selected_states[i]);
				}
				else if( ((type_in==2)||(type_in==4)) &&(indexState!=-1)){
					this.clickCallback(selected_states[i]);
				}
			}
		}
	 switch (type_in) {
	 	case 1: $("#selectall").prop("disabled",true);
	 			$("#unselectall").prop("disabled", false);
	 			break;
	 	case 2: $("#selectall").prop("disabled",false);
		 		$("#unselectall").prop("disabled", true);
		 		break;
	 	case 3: $("#selectallmultiplemetric").prop("disabled",true);
				$("#unselectallmultiplemetric").prop("disabled", false);
				$("#selectpeerstates").prop("disabled",false);
				break;
	    case 4: $("#selectallmultiplemetric").prop("disabled",false);
 				$("#unselectallmultiplemetric").prop("disabled", true);
 				$("#selectpeerstates").prop("disabled",false);
 				break;
	  
	 		
	 }
	
 };
  /*
  * This function aids in changing color on selecting multiple states on US map and state buttons and resets to original on exit. 
  */ 
AppState.prototype.toggleMultiSelect = function(ind,option ) {
	this.currentind=ind;
	var tagoption;
	this.turnOffUnattendedHeatMapAnimation();
	if(option==-1){
		tagoption="#multiSelecterMetricState";
		
		$("#multiplequeryTab").addClass("hidden");
		$("#multiplequeryTabDisappear").removeClass("hidden");
		$("#selectallmultiplemetric").removeClass("hidden");
		$("#unselectallmultiplemetric").removeClass("hidden");
		$("#selectpeerstates").removeClass("hidden");
			
		
	}else{
		tagoption="#multiSelecter";
		$("#multiplequeryTab").addClass("hidden");
		$("#selectallmultiplemetric").addClass("hidden");
		$("#unselectallmultiplemetric").addClass("hidden");
		
	}
	
    $(tagoption).toggle("slide", {
        direction : "right"
    }, 200);
    $("#sidebar").toggle("slide", {
        direction : "left"
    }, 200);
  
    as.multiMode=!as.multiMode;
  
    if(!as.multiMode)
    	{
    	this.reset();
    	$("#selectall").prop('disabled', true); 
    	$("#unselectall").prop('disabled', true);
    	$("#selectallmultiplemetric").prop('disabled', true); 
    	$("#unselectallmultiplemetric").prop('disabled', true);
    	$("#selectpeerstates").prop("disabled",true);
    	$("#multiplequeryTab").removeClass("hidden");
    	$("#selectallmultiplemetric").addClass("hidden");
		$("#unselectallmultiplemetric").addClass("hidden");
		$("#selectpeerstates").addClass("hidden");
		
    	}
    else
    	{
    	setTimeout(function(){
    		as.UnSetHeatMap(as.current_tab_style); 
    		as.changeColor(as.stateAbbr, as.defaultStateMultimodeColor); 
    		$("#selectall").prop('disabled', false);
    		$("#selectallmultiplemetric").prop('disabled', false); 
    		$("#selectpeerstates").prop('disabled', false); 
    		}, 1500);
    	
    	}
    
    this.selected = [ this.stateAbbr ];
    
    if ($("#normallegend").hasClass("hidden")) {
    	
        $("#normallegend").removeClass("hidden");
        $("#multilegend").addClass("hidden");
    } else {
    	
        $("#normallegend").addClass("hidden");
        $("#multilegend").removeClass("hidden");
    }
};



/*
 * Initializes the active tab along with title and loads data for the currently selected state.
 */
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
        $(".statebutton").click(function (){ 
        	stateClicked = $(this).attr("id").substr(3, 2); 
        	as.clickCallback(stateClicked);
        });
    });
}

/**
 * 
 * @param tag_id modal window tag id 
 * @param color_in color from the tabs
 * This color applies to the inner window of the modal window depending on the tab tabbed
 */
AppState.prototype.changeColorModalWindow=function(tag_id,color_in){
	var divModalWindow=document.getElementById(tag_id);
	divModalWindow.style.backgroundColor =color_in;
	divModalWindow.style.borderWidth ="1px";
	
};
/* 
 * This Function changes the color of all peer states once a tab is clicked. 
 * It also changes the background color of all modal windows
 */ 
AppState.prototype.turnOffUnattendedHeatMapAnimation=function(){
	stopHeatMapAnimation();//User could have left the animation running;
	$("#verticalheatmapmeter").addClass("hidden");
	$("#mapTitle").text("Click to Select a State");
};
AppState.prototype.changeColorPeerStates = function(style){
	/*this.selected =States.getPeers().map(function(s){
		return s.abbr;
	});*/
	this.turnOffUnattendedHeatMapAnimation();
	var newColor=getStyleRuleValue('background-color',style);
	
	this.UnSetHeatMap(style);
	this.current_tab_style=style;
	var divboxcolorpeerstates = document.getElementById( 'boxcolorpeerstates' );
	divboxcolorpeerstates.style.backgroundColor =newColor;
	
	var modalcontentidstyle= style.replace("tab a", "");
   
	modalcontentidstyle=modalcontentidstyle+" tbody > tr:hover > td";
	var newColorModalWindow= getStyleRuleValue('background-color',modalcontentidstyle);
	this.changeColorModalWindow('mbody',newColor);
	this.changeColorModalWindow('mbodyMultipleQuery',newColor);
	/**var divModalWindow=document.getElementById('mbody'); 
	divModalWindow.style.backgroundColor =newColor;
	divModalWindow.style.borderWidth ="1px";**/
	
	/**var divModalWindow=document.getElementById('myTable');
	divModalWindow.style.backgroundColor =newColorModalWindow;**/
	
	
	/**this.peerStateColor=newColor;
	for(var i=0; i<this.selected.length;i++){
		if(this.stateAbbr!=this.selected[i])
			$("#map").usmap('changeStateColor', this.selected[i], newColor);
		
	}**/
		
}

var publicInterface = {};

/*
 * Initializes the default AppState parameters. 
 */
publicInterface.create = function() {
	
	return new AppState();
}

return publicInterface;
}($));
