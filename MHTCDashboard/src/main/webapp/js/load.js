 
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


// new loadfunction to be called from body-onload
loadFunction = function() {
	//$('#map').usmap({});
	$("div.tab-pane button.dropdown-toggle").click(adjustDropDown);
	$("div.tab-pane").scroll(adjustDropDown);

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

};
