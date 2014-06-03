
// list of potential inputs, customizations, variabilities
// line vs bar vs etc etc

// TODO this is just some sketchwork, convert to actual module

var Chart = (function($) {
	
	var defaults = {
			type: 'line',
			title: 'A chart',
			query: null
	}
	
	Chart = function(parameters) {
		this.options = { }
		$.extend(this.options, defaults, options);
	}
	
	Chart.prototype.display = function() {
		query.execute(function(data) {
			// display chart using data
		})
	}
	
	var publicInterface = {};
	
	publicInterface.create = function(parameters) {
		return new Chart(parameters);
	}
	
}($));


Chart.create({
	type: 'line',
	title: metric + graphtype,
	multi: 'multi',
	query: Query.create().addState('MA')
})



var aChart = Chart.create({
	type: 'bar'
});

aChart.options.type == 'bar';
aChart.options.title == 'A chart';



Chart = function() {
	type = chart = nv.models.lineChart();
	
}

Chart.create({
	type: 'line',
	values: someValues
	
});



Chart.prototype.setType = function(type) {
	if (type === 'line') {
		chart = nv.models.lineChart();
	}
	return this;
};

Chart.prototype.asLine = function() {
	chart = nv.models.lineChart();
	return this;
}

Chart.prototype.asBar = function() {
	return this;
}


Chart.create().asLine().withFancyGuidelines().withAngryFaces().display();