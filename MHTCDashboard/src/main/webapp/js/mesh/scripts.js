(function($) {
  
  /**
   * Gravity Forms Placeholders
   *
   * Clones label of text fields and textareas to 
   * the corresponding input element's placeholder attr
   */
  function gformPlaceholders() {
    $('.gform_wrapper input[type="text"],'
      +'.gform_wrapper textarea,'
      +'.gform_wrapper input[type="email"]').each(function() {
      var ph = $(this).parent().prev().text();
      $(this).attr('placeholder', ph);
    });
  }

  $(function() {
    
    // Add placeholders to Gravity Forms
    gformPlaceholders();
    
    // Re-add placeholders on AJAX submit
    $(document).bind('gform_post_render', function() {
      gformPlaceholders();
    });
    
    // Simulate placeholders in older browsers
    if(!Modernizr.input.placeholder){
      $('[placeholder]').focus(function() {
        var input = $(this);
        if (input.val() === input.attr('placeholder')) {
        input.val('');
        input.removeClass('placeholder');
        }
      }).blur(function() {
        var input = $(this);
        if (input.val() === '' || input.val() === input.attr('placeholder')) {
        input.addClass('placeholder');
        input.val(input.attr('placeholder'));
        }
      }).blur();
      $('[placeholder]').parents('form').submit(function() {
        $(this).find('[placeholder]').each(function() {
        var input = $(this);
        if (input.val() === input.attr('placeholder')) {
          input.val('');
        }
        });
      });
    }
    
    // Smooth scrolling to on-page anchors
    $('a[href*=#]:not([href=#])').click(function() {
      if (location.pathname.replace(/^\//,'') === this.pathname.replace(/^\//,'') && location.hostname === this.hostname) {
        var target = $(this.hash);
        target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
        if (target.length) {
          $('html,body').animate({
            scrollTop: target.offset().top
          }, 1000);
          return false;
        }
      }
    });

  }); // END onload
})(jQuery);