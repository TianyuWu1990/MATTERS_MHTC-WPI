var Modernizr = (Modernizr) ? Modernizr : undefined;
(function($) {
  
  //============================================================================ INIT //
  var tablet = 1024,
      mobile = 768,
      smallMobile = 568,
      prevWidth = $(window).width(),
      prevHeight = $(window).height();
      
  //================================================================ COMMON FUNCTIONS //
  
  /**
   * Breakpoint
   * Checks if you're entering the expected breakpoint
   *
   * @param size int pixel width this breakpoint should be triggered at
   * @param down bool true if expecation is that the screen size is decreasing
                      false if expection is that the screen size is increasing
   * @param prev int the last known screen width before entering this breakpoint
   * 
   */
  function breakpoint(size, down, prev) {
    if (down) {
      return ($(window).width() <= size && prev > size) ? true : false;
    } else {
      return ($(window).width() > size && prev <= size) ? true : false;
    }
  }
  
  // Add more functions here to encapsulate functionality
  
  //============================================================= BREAKPOINT HANDLERS //
  
  // Edit these funtions to manipulate the DOM 
  // and bind event handlers at these breakpoints
      
  function tabletInit() {
  
  }
  
  function tabletClear() {

  }
      
  function mobileInit() {
    
  }
  
  function mobileClear() {
    
  }
  
  function smallMobileInit() {
  
  }
  
  function smallMobileClear() {
  
  }
  
  function touchInit() {
  
  }
  
  //======================================================================= ONLOAD //
  $(function() {
    
    //=========================================================== INITIALIZE STATE //
    if ($(window).width() <= tablet) {
      tabletInit();
    }
    
    if ($(window).width() <= mobile) {
      mobileInit();
    }
    
    if ($(window).width() <= smallMobile) {
      smallMobileInit();
    }
    
    if (Modernizr.touch) {
      touchInit();
    }
    
   //====================================================== WINDOW RESIZE HANDLER //
    $(window).resize(function() {
    
      // Desktop -> Tablet
      if (breakpoint(tablet, true, prevWidth)) {
        tabletInit();
      }
      
      // Tablet -> Mobile
      if (breakpoint(mobile, true, prevWidth)) {
        mobileInit();
      }
      
      // Tablet -> Small Mobile 
      if (breakpoint(smallMobile, true, prevWidth)) {
        smallMobileInit();
      }
      
      // Small Mobile -> Tablet/Desktop
      if (breakpoint(smallMobile, false, prevWidth)) {
        smallMobileClear();
      }
      
      // Mobile -> Tablet/Desktop
      if (breakpoint(mobile, false, prevWidth)) {
        mobileClear();
      }
      
      // Tablet/Mobile -> Desktop
      if (breakpoint(tablet, false, prevWidth)) {
        mobileClear();
        tabletClear();
      }
      
      // Store current sizes as prev
      prevWidth = $(window).width();
      prevHeight = $(window).height();
    });
    
  }); // End onload
  
})(jQuery);