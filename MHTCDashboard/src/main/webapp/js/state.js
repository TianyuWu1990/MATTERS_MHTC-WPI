/**
 * @author salah
 */

/**
 * A module that loads a list of states from the server when the page starts
 * and maintains it. Provides functionality for obtaining states from the list
 * by name, id or abbreviation.
 */
 var States = (function() {

		State = function(id, name, abbr, peerState) {
			this.id=id;
			this.name=name;
			this.abbr=abbr;
			this.peerState=peerState;
		};
		
		State.prototype.getId=function(){
			return this.id;
		}
		
		State.prototype.getName = function(){
			return this.name;
		}
		
		State.prototype.getAbbr=function(){
			return this.abbr;
		}
		
		State.prototype.isPeerState=function(){
			return this.peerState;
		}
		State.prototype.toString=function(){
			return JSON.stringify(this)
		}
		
		var states = $.parseJSON($.ajax({
			url : 'data/states/all',
			dataType : 'text',
			async : false,
			success : function(data) {
				return data;
			}
		}).responseText);
		
		
		states = states.map(function(state) {
			return new State(state.id,state.name,state.abbr,state.peerState); // TODO convert this to the state prototype
		});
		

		function returnFirstOrNull(array) {
			if (array.length == 1)
				return array[0];
			else
				return null;
		}

		var publicInterface = {};

		/**
		 * Gets a state with the given id from the list of cached states.
		 */
		publicInterface.getStateByID = function(id) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.id == id;
			}));
		};

		/**
		 * Gets a state with the given name from the list of cached states.
		 */
		publicInterface.getStateByName = function(name) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.name === name;
			}));
		};

		/**
		 * Gets a state with the given abbreviation from the list of cached states.
		 */
		publicInterface.getStateByAbbreviation  = function(abbr) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.abbr === abbr;
			}));
		};
        
		
		/**
		 * Given a string that could be name, id or abbreviation, returns a state that matches
		 * either.
		 */
		publicInterface.getStateFromString = function(state) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.id == state || each.name === state;
			}));
		};

		/**
		 * Returns all states
		 * 
		 * 
		 */
		publicInterface.getAllstates = function() {
			return states;
		};

		/**
		 * Returns all peer states 
		 * 
		 */
		publicInterface.getPeers = function() {
			return states.filter(function(each) {
				return each.peerState == true;
			});
		};

		/**
		 * Returns a string representation of the states array
		 */
		publicInterface.toString = function() {
			return JSON.stringify(states);
		};

		return publicInterface;

	}());
/**
 * @author salah
 */

/**
 * A module that loads a list of states from the server when the page starts
 * and maintains it. Provides functionality for obtaining states from the list
 * by name, id or abbreviation.
 */
 var States = (function() {

		State = function(id, name, abbr, peerState) {
			this.id=id;
			this.name=name;
			this.abbr=abbr;
			this.peerState=peerState;
		};
		
		State.prototype.getId=function(){
			return this.id;
		}
		
		State.prototype.getName = function(){
			return this.name;
		}
		
		State.prototype.getAbbr=function(){
			return this.abbr;
		}
		
		State.prototype.isPeerState=function(){
			return this.peerState;
		}
		State.prototype.toString=function(){
			return JSON.stringify(this)
		}
		
		var states = $.parseJSON($.ajax({
			url : 'data/states/all',
			dataType : 'text',
			async : false,
			success : function(data) {
				return data;
			}
		}).responseText);
		
		
		states = states.map(function(state) {
			return new State(state.id,state.name,state.abbr,state.peerState); // TODO convert this to the state prototype
		});
		

		function returnFirstOrNull(array) {
			if (array.length == 1)
				return array[0];
			else
				return null;
		}

		var publicInterface = {};

		/**
		 * Gets a state with the given id from the list of cached states.
		 */
		publicInterface.getStateByID = function(id) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.id == id;
			}));
		};
		publicInterface.getArrayStateByID = function(id) {
			var arrayReturn=[];
			var indexPos=0;
			var i=0;
			var j;
			
			var sentinel;
			while(i<states.length){
				sentinel=0;
				j=0;
				while((j<id.length)&&(sentinel==0)){
					if(id[j]==states[i].id){
						sentinel=1;
						arrayReturn[indexPos]=states[i].abbr;
						indexPos++;
					}
					j++;
				}
				
				i++;
			}
			return arrayReturn;
		};
		/**
		 * Gets a state with the given name from the list of cached states.
		 */
		publicInterface.getStateByName = function(name) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.name === name;
			}));
		};

		/**
		 * Gets a state with the given abbreviation from the list of cached states.
		 */
		publicInterface.getStateByAbbreviation  = function(abbr) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.abbr === abbr;
			}));
		};
        
		
		/**
		 * Given a string that could be name, id or abbreviation, returns a state that matches
		 * either.
		 */
		publicInterface.getStateFromString = function(state) {
			return returnFirstOrNull(states.filter(function(each) {
				return each.id == state || each.name === state;
			}));
		};

		/**
		 * Returns all states
		 * 
		 * 
		 */
		publicInterface.getAllstates = function() {
			return states;
		};

		/**
		 * Returns all peer states 
		 * 
		 */
		publicInterface.getPeers = function() {
			return states.filter(function(each) {
				return each.peerState == true;
			});
		};

		/**
		 * Returns a string representation of the states array
		 */
		publicInterface.toString = function() {
			return JSON.stringify(states);
		};

		return publicInterface;

	}());
