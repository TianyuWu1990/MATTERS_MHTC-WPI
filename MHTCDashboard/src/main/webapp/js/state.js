/**
 * @author salah
 */

function state() {
	this.states = $.parseJSON($.ajax({
		url : 'data/states/all',
		dataType : 'text',
		async : false,
		success : function(data) {
			return data;
		}
	}).responseText);
}

state.prototype.getStateByID = function(id) {
	return this.states.filter(function(each) {
		return each.id == parseInt("" + id, 10);
	});
};

state.prototype.getStateByName = function(name) {
	return this.states.filter(function(each) {
		return each.name === name;
	});
};

state.prototype.getStateByAbbreviation = function(abbr) {
	return this.states.filter(function(each) {
		return each.abbr === abbr;
	});
};

state.prototype.getStateFromString = function(state) {
	return this.states.filter(function(each) {
		return each.id == parseInt("" + state, 10) || each.name === state
				|| each.abbr === state;
	});
};

state.prototype.getAllStates = function() {
	return this.states;
};

state.prototype.getPeers = function() {
	return this.states.filter(function(each) {
		return each.peerState == true;
	});
};

state.prototype.toString = function() {
	return JSON.stringify(this.states);
};
