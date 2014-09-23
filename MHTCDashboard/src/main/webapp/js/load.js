 var global_timer = null;
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
};
/**
 * 
 * Returns the value of the style selector. The color returned is used to paint the background of the modal windows and peerStates depending
 * on the tab tabbed
 */
 /**getStyleRuleValue=function(style, selector, sheet) {
    var sheets = typeof sheet !== 'undefined' ? [sheet] : document.styleSheets;
    //for (var i = 0, l = sheets.length; i < l; i++) {
        var sheet = sheets[3];
       // if( !sheet.cssRules ) { continue; }
        for (var j = 0, k = sheet.cssRules.length; j < k; j++) {
            var rule = sheet.cssRules[j];
            if (rule.selectorText && rule.selectorText.split(',').indexOf(selector) !== -1) {
            	//alert("This is style: "+rule.style[style]);
                return rule.style[style];
            }
        }
    //}
    return null;
}**/
 
getStyleRuleValue=function(styleName, className) {

	    //for (var i=0;i<document.styleSheets.length;i++) {
	        var s = document.styleSheets[3];

	        var classes = s.rules || s.cssRules
	        for(var x=0;x<classes.length;x++) {
	            if(classes[x].selectorText==className) {
	                return classes[x].style[styleName] ? classes[x].style[styleName] : classes[x].style.getPropertyValue(styleName);
	            }
	       // }
	    }
	}
 /**
  * Converts a hexadimal color into a RGB
  */
  hexToRgb=function(hex) {
	  var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
	    hex = hex.replace(shorthandRegex, function(m, r, g, b) {
	        return r + r + g + g + b + b;
	    });

	    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
	    return result ? {
	        r: parseInt(result[1], 16),
	        g: parseInt(result[2], 16),
	        b: parseInt(result[3], 16)
	    } : null;
	}
  /**
   * Converts an RGB color To HEXA
   */
  colorToHex= function (color) {
	    if (color.substr(0, 1) === '#') {
	        return color;
	    }
	    var digits = /(.*?)rgb\((\d+), (\d+), (\d+)\)/.exec(color);

	    var red = parseInt(digits[2]);
	    var green = parseInt(digits[3]);
	    var blue = parseInt(digits[4]);

	    var rgb = blue | (green << 8) | (red << 16);
	    return digits[1] + '#' + rgb.toString(16);
	}
  /**
   * Gets a new letter to get a different mix of a hexadecimal color
   */
  nextLetter=function (s){
	    return s.replace(/([a-fA-F])[^a-fA-F]*$/, function(a){
	        var c= a.charCodeAt(0);
	        switch(c){
	            case 90: return 'A';
	            case 122: return 'a';
	            default: return String.fromCharCode(++c);
	        }
	    });
	} 
/**
 * 
 * Returns a new mix of color. Currently not in use
 */  
getNewMix=function(letterNum){
	
	if(!isNaN(letterNum)){
		if(letterNum<9){
			letterNum=parseInt(letterNum);
			letterNum++;
			
		}else{
			letterNum="A";
		}
	}else{
		if((letterNum!='F')&&(letterNum!='f')){
			letterNum=nextLetter(letterNum);
		}
		
	}
	return letterNum;
}  
/**
 * 
 * @param hexcolor hexadecimal color to be degraded
 * @param value: current element of the array   
 * @param highest : last element of array
 * @returns {String} : this functions returns a new color degraded 
 */
getNewColor=function(hexcolor,value,highest){
	var red=hexcolor.substring(1, 3);
	var green=hexcolor.substring(3, 5);
	var blue=hexcolor.substring(5, 7);
	
	red=parseInt(red, 16);
	green=parseInt(green, 16);
	blue=parseInt(blue, 16);
	
	
	red=Math.floor(red*value/highest);
	green=Math.floor(green*value/highest);
	blue=Math.floor(blue*value/highest);
    red=red>255?255:red;
    green=green>255?255:green;
    blue=blue>255?255:blue;
     
	
	  
	red=red.toString(16)
	green=green.toString(16)
	blue=blue.toString(16)
	
	var thecolor="#" + (red.length < 2 ? "0"+red : red) + (green.length < 2 ? "0"+green : green) + (blue.length < 2 ? "0"+blue : blue);
	
	
	
	/*var newColor="#";
	var changed=false;
	var currentLetterNum=null;
	var i=1;
	while(i<8){
		currentLetterNum=hexcolor.substring(i, i+1);
		if(!changed){
			if(currentLetterNum!=getNewMix(currentLetterNum)){
				currentLetterNum=getNewMix(currentLetterNum);
				changed=true;
			}
		}
		newColor=newColor+currentLetterNum;
		i++;
	}*/
	
	return thecolor;		
};  

animateHeatMap=function(){
	var array_years=[2013,2012,2011];
	for (var i=0; i<array_years.length; i++) {
		//console.log("YEAR "+array_years[i]);
		as.showHeatMapGraph(as.currentind,as.current_tab_style,'#mbodyHeatMap', array_years[i]);
		
	  }

}; 
stopHeatMapAnimation=function() {
	if (!global_timer) return false;
	  	clearInterval(global_timer);
	global_timer = null;
};
// new loadfunction to be called from body-onload
loadFunction = function() {
	//$('#map').usmap({});
	$("div.tab-pane button.dropdown-toggle").click(adjustDropDown);
	$("div.tab-pane").scroll(adjustDropDown);
	$("#selectallmultiplemetric").addClass("hidden");
	$("#unselectallmultiplemetric").addClass("hidden");
	$("#selectpeerstates").addClass("hidden");
	cm=CM.create();
	as=AS.create();
	//cm.loadFunction();
	as.loadFunction();
	
	$("#chartType" ).change(function() {
		
		  cm.current_graph = this.value;
		  //alert("current_graph11"+cm.current_graph);
		  //cm.showMultiGraph(cm.selected);
		  cm.showMultiGraph(as.selected);
		});
	$("#yearHeatMap").change(function(){
		as.showHeatMapGraph(as.currentind,as.current_tab_style,'#mbodyHeatMap', this.value);
	});
	$( "#playbuttonanimation" ).click(function() {
		///$("#yearHeatMap").prop('disabled', true);
		
		$("#stopbuttonanimation").prop('disabled', false); 
		if (!global_timer)
			global_timer = setInterval(animateHeatMap,4500);

		
		//as.startPlayer(as.currentind,as.current_tab_style, '#mbodyHeatMap',  true);
	});
	$( "#stopbuttonanimation" ).click(function() {
		$("#yearHeatMap").prop('disabled', false);
		stopHeatMapAnimation();
        //alert( "The animation has ended" );
	});
	$("#yearsMultipleQuery").change(function(){
		as.showMultipleMetricsStatesYears(this.value);
	});
	
};
