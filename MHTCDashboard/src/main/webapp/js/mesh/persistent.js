$(document).ready(function(){
  
$(".menu-close").prependTo(".menu > ul");
   
$(".menu-toggle").click(function(){
	$(".menu").slideDown();
});

$(".menu-close").click(function(){
	$(".menu").slideUp();
});

$('div.leaflet-top.leaflet-right').hide();
$("div.leaflet-control-attribution").hide();


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


$("<img src='img/state-strength.png' width='15px' height='15px' style='vertical-align:top;' />").appendTo(".state-strength");
$("<img src='img/state-weakness.png' width='15px' height='15px' style='vertical-align:top;' />").appendTo(".state-weakness");
$("<img src='img/state-neutral.png' width='15px' height='15px' style='vertical-align:top;' />").appendTo(".state-medium");


// ---------------------------
});// ---------------------------
// ---------------------------