function getStateProfile(stateName)
{
	for (var prop in StateProfileData)
	{
		if (StateProfileData.hasOwnProperty(prop))
		{
			if (StateProfileData[prop]["Name"] == stateName)
				return StateProfileData[prop];
		}
	}
	
	return null;
}

function handleStatus(rank, obj)
{
	if (isNaN(rank)) {
		$(obj).html('<img src="img/state-neutral.png" width="15px" height="15px" style="vertical-align:top;">');
		return;
	}
  if (rank<=10) {
	  $(obj).html('<img src="img/state-strength.png" width="15px" height="15px" style="vertical-align:top;">');
    return;
  } else if (rank>=25) {
	  $(obj).html('<img src="img/state-weakness.png" width="15px" height="15px" style="vertical-align:top;">');
    return;
  }
  $(obj).html('<img src="img/state-neutral.png" width="15px" height="15px" style="vertical-align:top;">');
}

function refreshData(stateName)
{
	var stateProfile = getStateProfile(stateName);

	if (stateProfile == null)
	{
		// TODO: Error report
	}
	else
	{
		$("#stateTitleAct").html(stateProfile["Name"]);
		
		var milken = stateProfile["Milken Science and Tech Index"];
		$("#milken").html(milken);
		handleStatus(milken, "#milken-status");
		
		var tax_found = stateProfile["Tax Foundation Business Tax Index"];
		$("#tax-found").html(tax_found);
		handleStatus(tax_found, "#tax-found-status");
		
		var cnbc = stateProfile["CNBC Top States for Business"];
		$("#cnbc").html(cnbc);
		handleStatus(cnbc, "#cnbc-status");
		
		var techDemandData = stateProfile["Key tech demand hiring difficulty"];
		var techDemandRank = stateProfile["Key tech demand hiring rank"];
		$("#tech-demand").html(techDemandRank);
		handleStatus(techDemandRank, "#tech-demand-status");
		$("#tech-demand-data").html(techDemandData);
		
		var techEmpData = stateProfile["Percent tech employment"];
		var techEmpRank = stateProfile["Tech employment rank"];
		$("#tech-emp").html(techEmpRank);
		handleStatus(techEmpRank, "#tech-emp-status");
		$("#tech-emp-data").html(techEmpData + "%");
		
		var bachData = stateProfile["Percent bachelors degree holders"];
		var bachRank = stateProfile["Bachelors degree holders rank"];
		$("#bach").html(bachRank);
		handleStatus(bachRank, "#bach-status");
		$("#bach-data").html(bachData + "%");
		
		var unempData = stateProfile["Unempl insurance"];
		var unempRank = stateProfile["Unempl insurance rank"];
		$("#unemp").html(unempRank);
		handleStatus(unempRank, "#unemp-status");
		$("#unemp-data").html(unempData);
		
		var taxBurdenData = stateProfile["Tax burden per capita"];
		var taxBurdenRank = stateProfile["Tax burden per capita rank"];
		$("#tax-burden").html(taxBurdenRank);
		handleStatus(taxBurdenRank, "#tax-burden-status");
		$("#tax-burden-data").html(taxBurdenData);
	}
}

$(document).ready(function() {	
	var stateName = window.location.search.replace("?name=", "").replace("%20", " ").trim(); 
	
	var stateNameHash = window.location.hash.replace("#profile/?name=", "");
	
	var andLocale = stateNameHash.indexOf('&');
	stateNameHash = stateNameHash.substring(0, andLocale);
	stateNameHash = stateNameHash.replace("%20", " ").trim();
	
	if (stateName.length == 0 && stateNameHash.length == 0)
		stateName = "Massachusetts";
	else if (stateName.length == 0)
		stateName = stateNameHash;
	
	refreshData(stateName);
	
	$("#stateChooserTitle").click(function() {
		$("#stateChooser").slideToggle();
	});
	
	$(document).click(function (e) {
		var chooser = $("#stateChooser");
		var title = $("#stateChooserTitle");
		
		if (!chooser.is(e.target) && !title.is(e.target)
				&& chooser.has(e.target).length === 0 && title.has(e.target).length === 0)
		{
			$("#stateChooser").slideUp();
		}
	});
	
	$(".stateChoice").click(function (e) {
		var stateName = $(this).html();
		History.pushState({ name : stateName }, "MATTERS State Profiles", "?name=" + stateName);
	});
	
	History.Adapter.bind(window,'statechange',function(){
        var State = History.getState();     
        refreshData(State.data.name);
    });
});