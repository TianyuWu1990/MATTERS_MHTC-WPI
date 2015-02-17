var caliper = caliper || {};
caliper.profile_controller = function($scope,$element,$attrs,$rootScope,$log,$location,map_service) {
    // mix.into(this, new mix.with_events($scope,$rootScope));
    this.element_id        = "#" + $element.context.id;
    this.collection        = map_service.get_data("All_Features");
    this.features          = this.collection.features;
    this.search_field_name = (angular.isArray(this.collection.index)) ? this.collection.index[0] : null;
    this.escape            = function(name) {
      return name.toLowerCase().replace("+"," ").replace("%20"," ");
    };
    this.find_feature      = function(name) {
      if (angular.isArray(this.features) && (typeof name == "string")) {
          var pattern       = this.escape(name);
          for (var f=0;f<this.features.length;f++) {
            if (this.escape(this.features[f].properties[this.search_field_name]) == pattern) {
              return(this.features[f]);
            }
          }
      }
      return null;
    };
    // returns "state-weakness", "state-medium" or "state-strength"
    this.get_rank_class = function(rank) {
      if (isNaN(rank)) {
        return("state-medium");
      }
      if (rank<=10) {
        return "state-strength";
      } else if (rank>=25) {
        return "state-weakness";
      }
      return "state-medium";
    };
    // set the current feature to display in my state profile HTML element
    this.set_feature = function(feature_name) {
        var search         = $location.search(); // if $location.url is "http://web-server/web-page?key1=one;key2=two" then search = {key1:"one",key2:"two"}
        this.initial_name  = (typeof $attrs.initial == "string") ? $attrs.initial : this.features[0].properties[this.search_field_name];  
        var initial_name   = (typeof search.name == "string") ? search.name : this.initial_name;
        if (typeof feature_name == "string") {
            initial_name = feature_name;
        }
        this.feature       = this.find_feature(initial_name);
        if (this.feature === null) {
            this.feature = this.features[0];
        }
        this.properties = this.feature.properties;
    };
    this.set_feature();
};
/***
    AngularJS Application Module
    http://henriquat.re/basics-of-angular/services-dependency-injection/services-and-dependency-injection-in-angularjs.html
***/
caliper.app = angular.module("map_application",["ui.bootstrap"]); /*,["ui.bootstrap"]*/
/***
  Constants: You can inject the constant value "config" into any service, filter or controller, e.g.
  controller_name = function($scope,$element,config) {
    var country = config.country;
    var locale  = config.locale;
  };
***/
caliper.app.constant('config',{
  "locale":  "en-US",
  "country": "USA"
});
/***
  Services
***/
caliper.app.service("map_service",     caliper.map_service);
caliper.app.service("search_service",  caliper.search_service);
/***
  Display Filters
***/
caliper.app.filter("checkmarked",      caliper.checkmark_filter );
caliper.app.filter("formatted",        caliper.format_filter );
/***
  View Controllers
***/
caliper.app.controller("profile_controller",   caliper.profile_controller);
/***
  Configuration block, gets executed during the provider registrations and configuration phase. 
  Only providers and constants can be injected into configuration blocks
***/
caliper.app.config(function($locationProvider) { // execute this code when the page loads and on window resize
  $locationProvider.html5Mode(true);
  caliper.on_window_resize();
  jQuery(window).resize(function(){
    caliper.on_window_resize();
  });
});