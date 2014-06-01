/**
 * @author salah
 */

function state()
{
    //this.states = [];
    this.states = $.parseJSON($.ajax({
    	url: 'data/states/all',
        dataType: 'text',
        async:false,
        success: function(data) {
        	$('#console1').html(data);
            return data;
        }
    }).responseText);
}


state.prototype.getStateByID = function(id){
	for( var i=0 ; i< this.states.length; i++ ){
		//$('#console2').append(this.states[i].name + "<br>");
		if (this.states[i].id == parseInt(""+id,10)) {
			return this.states[i];
		}
	}
	return null;
};


state.prototype.getStateByName = function(name){
	for( var i=0 ; i< this.states.length; i++ ){
		//$('#console2').append(this.states[i].name + "<br>");
		if (this.states[i].name===name) {
			return this.states[i];
		}
	}
	return null;
};

state.prototype.getStateByAbbreviation = function(abbr){
	for( var i=0 ; i< this.states.length; i++ ){
		//$('#console2').append(this.states[i].name + "<br>");
		if (this.states[i].abbr===abbr) {
			return this.states[i];
		}
	}
	return null;
};


state.prototype.getStateFromString = function(state){
	for( var i=0 ; i< this.states.length; i++ ){
		//$('#console2').append(this.states[i].name + "<br>");
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
			//$('#console2').append(this.states[i].name + "<br>");
			peers.push(this.states[i]);
		}
	}
	return peers;
};

state.prototype.toString = function(){

	var strStates="";
	for( var i=0 ; i< this.states.length; i++ ){
		strStates += this.states[i].id + " : " + this.states[i].name + " : " + this.states[i].abbr + " : " + this.states[i].peerState + "<br>" ;
	}
	return strStates;	
};


