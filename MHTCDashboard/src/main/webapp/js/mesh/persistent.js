$(document).ready(function(){
  
$(".menu-close").prependTo(".menu > ul");
   
$(".menu-toggle").click(function(){
	$(".menu").slideDown();
});

$(".menu-close").click(function(){
	$(".menu").slideUp();
});


$(window).scroll(function(){

 if  ($('.bucket-left .view-more').is(":in-viewport")) {
	 setTimeout(function () {
		 $('.bucket-left').addClass("animated rotateInUpLeft fadeInUp show");	 	 
	 }, 0 ); 
	 
	 }
	 
 if  ($('.bucket-center .view-more').is(":in-viewport")) {	 
	  setTimeout(function () {
		 $('.bucket-center').addClass("animated rotateInUpLeft fadeInUp show");	 	 
	 }, 300 ); 

	 }

 if  ($('.bucket-right .view-more').is(":in-viewport")) {
	 setTimeout(function () {
		 $('.bucket-right').addClass("animated rotateInUpLeft fadeInUp show");	 	 
	 }, 700 ); 

	 }

});


// ---------------------------
});// ---------------------------
// ---------------------------