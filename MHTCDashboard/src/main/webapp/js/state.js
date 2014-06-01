/**
 * @author salah
 */

function state()
{
    this.states = $.parseJSON($.ajax({
    	url: 'data/states/all',
        dataType: 'text',
        async:false,
        success: function(data) {
            return data;
        }
    }).responseText);
}

state.prototype.getStateByID = function(id){
	for( var i=0 ; i< this.states.length; i++ ){
		if (this.states[i].id == parseInt(""+id,10)) {
			return this.states[i];
		}
	}
	return null;
};


state.prototype.getStateByName = function(name){
	for( var i=0 ; i< this.states.length; i++ ){
		if (this.states[i].name===name) {
			return this.states[i];
		}
	}
	return null;
};

state.prototype.getStateByAbbreviation = function(abbr){
	for( var i=0 ; i< this.states.length; i++ ){
		if (this.states[i].abbr===abbr) {
			return this.states[i];
		}
	}
	return null;
};


state.prototype.getStateFromString = function(state){
	for( var i=0 ; i< this.states.length; i++ ){
		if (this.states[i].id == parseInt(""+state,10) || 
				this.states[i].name===state || 
				this.states[i].abbr===state) {
			return this.states[i];
		}
	}
	return null;
};

state.prototype.getAllStates = function() {
	return this.states;
};

state.prototype.getPeers = function(){
	var peers = [];
	for( var i=0 ; i< this.states.length; i++ ){
		if (this.states[i].peerState == true) {
			peers.push(this.states[i]);
		}
	}
	return peers;
};

state.prototype.toString = function(){
	return JSON.stringify(this.states);
};


