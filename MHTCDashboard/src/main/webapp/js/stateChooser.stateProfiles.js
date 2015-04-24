var stateProfiles = {};

function retrieveStateProfiles(rawData)
{
	for (var i = 0; i < rawData.length; i++)
	{
		var stateData = rawData[i];
		
		if (stateData.length == 0)
			continue;
		
		var stateObject = stateData[0].state;
		
		
		stateProfiles[stateObject.name] = {};
		
		var stateProfilesObject = stateProfiles[stateObject.name];
		stateProfilesObject.state = stateObject.name;
		stateProfilesObject.peer = stateObject.peerState;
		stateProfilesObject.metrics = [];
		
		// Populate metrics
		for (var j = 0; j < stateData.length; j++)
		{
			var dataObj = stateData[j];
			
			var metricObj = {};
			metricObj.name = dataObj.metric.name;
			metricObj.source = dataObj.metric.sourceName;
			metricObj.trendType = dataObj.metric.trendType;
			metricObj.actualTrend = dataObj.trend;
			metricObj.type = dataObj.metric.type;
			metricObj.url = dataObj.metric.urlFrom;
			metricObj.mostRecent = dataObj.recent;
			metricObj.data = dataObj.dataPoints;
			
			stateProfilesObject.metrics.push(metricObj);
		}	
	}
}

function formatData(metricType, value)
{
	var formattedValue = "";
	
	if (value == null)
		return "N/A";
	
	if(metricType == "percentage") {
		formattedValue = (value * 100).toFixed(2)+"%";
	} else if(metricType =="currency") {
		formattedValue = "$" + value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
	}else if(metricType == "numeric"){
		formattedValue = value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
	}else if(metricType == "rank") {
		
		value = Math.floor(value); // Make sure we get a whole number
		
		var firstDigit = value % 10;
	
		var suffix = "th";
		
		if (value <= 10 || value >= 14) // 11th, 12th, 13th...
		{
			switch(firstDigit)
			{
				case 1:
					suffix = "st";
					break;
				case 2:
					suffix = "nd";
					break;
				case 3:
					suffix = "rd";
					break;
			}
		}
		
		formattedValue = value.toFixed(0);
	}else if(metricType=="integer"){
		formattedValue = value.toFixed(0);
	}else{
		formattedValue = value;
	}	
	
	return formattedValue;
}

function getTrend(trend)
{
	if (trend == "up_reversed" || trend == "up" || trend == "up_normal")
	{
		return '<i class="fa fa-arrow-up" style="color: rgb(36, 62, 27)!important;"></i>';
	}
	else if (trend == "down_reversed" || trend == "down" || trend == "down_normal")
	{
		return '<i class="fa fa-arrow-down" style="color: rgb(116, 0, 28)!important;"></i>';
	}
	else
	{
		return '<i class="fa fa-minus"></i>';
	}
}

function getStatus(rank, obj)
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
	
function populateData(stateName)
{
	$("#stateProfileTable").empty();
	
	
	var specificProfile = stateProfiles[stateName];
	
	$("#stateTitleAct").html(stateName);
	
	if (specificProfile == null)
	{
		// TODO: Say no profile available.
	}
	else
	{
		var metricData = specificProfile.metrics;
		
		var tableContents = '<tr class="ranking-header">'
				+	'<td class="rank">Rank</td>'
				+	'<td class="data">Data</td>'
				+	'<td class="status">Status</td>'
				+	'<td class="trend">Trend</td>'
				+	'<td class="year">Year</td>'
				+	'<td class="survey">Index / Survey</td>'
				+	'<td class="source">Source</td>'
				+	'</tr>';	
		
		
		for (var i = 0; i < metricData.length; i++)
		{
			var metricObj = metricData[i];
			
			var rank = "-";
			var data = formatData(metricObj.type, metricObj.mostRecent.value);
			var status = "-";
			var trend = getTrend(metricObj.actualTrend);
			var year = metricObj.mostRecent.year;
			var name = metricObj.name;
			var source = metricObj.source;
			
			tableContents +='<tr class="row">'
					+ '<td class="rank">' + rank + '</td>'
					+ '<td class="data">' + data + '</td>'
					+ '<td class="status">' + status + '</td>'
					+ '<td class="trend">' + trend + '</td>'
					+ '<td class="year">' + year + '</td>'
					+ '<td class="survey">' + name + '</td>'
					+ '<td class="source">' + source + '</td>'
					+ '</tr>';
		}		
		
		$("#stateProfileTable").html(tableContents);
	}
	
	/*
	<tr class="row" >
	<td class="rank" id="milken"></td>
	<td class="data">-</td>
	<td class="status"><div id="milken-status"></div></td>
	<td class="year">2014</td>
	<td class="survey">Milken State Science and Technology Index</td>
	<td class="source">Milken Institute</td>
</tr>*/
	
	/*
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
	}*/
}

$(document).ready(function() {	
	$.get(
			"data/stats/stateprofiles",
			function (data) 
			{
				retrieveStateProfiles(data);
				
				var stateName = window.location.search.replace("?name=", "").replace("%20", " ").trim(); 
				
				var stateNameHash = window.location.hash.replace("#profile?name=", "");
				
				var andLocale = stateNameHash.indexOf('&');
				stateNameHash = stateNameHash.substring(0, andLocale);
				stateNameHash = stateNameHash.replace("%20", " ").trim();
				
				if (stateName.length == 0 && stateNameHash.length == 0)
					stateName = "Massachusetts";
				else if (stateName.length == 0)
					stateName = stateNameHash;
				
				populateData(stateName);
				
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
					History.pushState({ name : stateName }, "MATTERS State Profiles", "profile?name=" + stateName);
				});
				
				History.Adapter.bind(window,'statechange',function(){
			        var State = History.getState();     
			        populateData(State.data.name);
			    });
			}
		);
});