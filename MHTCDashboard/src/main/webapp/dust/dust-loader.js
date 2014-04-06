var result;

(function() {
	load(basePath + "/dust/dust-full-0.3.0.js");
	load(basePath + "/dust/json2.js");
	
	var entries = modelMap.entrySet().iterator(),
		model = {},
		entry;
	
	// Convert model from java Map to javascript Object
	while(entries.hasNext()) {
		entry = entries.next();

		var val = entry.getValue() + "";

		if (entry.getKey().startsWith("jv_"))
			model[entry.getKey()] = eval("(function(){return " + val + ";})()");
	}

	model.funfun = [{name:"item1"}, {name:"item2"}, {name:"item3"}];
	
	var compiled = dust.compile(template, "template");
	
	dust.loadSource(compiled);
	dust.render("template", model, function(err, out) {
		result = out;
	});
})();


