$(document).ready(function(){
  
$(".menu-close").prependTo(".menu > ul");
   
$(".menu-toggle").click(function(){
	$(".menu").slideDown();
});

$(".menu-close").click(function(){
	$(".menu").slideUp();
});

$('div.leaflet-top.leaflet-right').hide();


$(window).scroll(function(){

 if  ($('.bucket-left p').is(":in-viewport")) {
	 setTimeout(function () {
		 $('.bucket-left').addClass("animated rotateInUpLeft fadeInUp show");	 	 
	 }, 0 ); 
	 
	 }
	 
 if  ($('.bucket-center p').is(":in-viewport")) {	 
	  setTimeout(function () {
		 $('.bucket-center').addClass("animated rotateInUpLeft fadeInUp show");	 	 
	 }, 300 ); 

	 }

 if  ($('.bucket-right p').is(":in-viewport")) {
	 setTimeout(function () {
		 $('.bucket-right').addClass("animated rotateInUpLeft fadeInUp show");	 	 
	 }, 700 ); 

	 }

});


// ---------------------------
});// ---------------------------
// ---------------------------