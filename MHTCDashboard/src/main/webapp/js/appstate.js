/*
 * Copyright (C) 2013 Worcester Polytechnic Institute
 * All Rights Reserved. 
 *  
 *  This module handles the Application User responses and State.
 */

var AS = (function($) {
	/**
	 * Constructor
	 */
	function AppState() {
		
		/**************************
		 * Visualization Types
		 **************************/
		this.visualizations = {
				TABLE : 0,
				LINE : 1,
				BAR : 2,
				HEATMAP : 3,
				EXCEL : 4
		}
		
		this.currentind = 0;
		this.stateAbbr = "MA";
		this.selected = [ 'MA' ];

		this.selected_multiple_metrics = [];
		this.selected_multiple_years = [];

		/**************************
		 * Error Handling
		 **************************/

		// Error codes are handled as bit flags. 
		// This way, multiple errors can easily be active at once.
		this.errorCodes = {
			NO_ERROR : 0, // 0000
			NO_METRIC_SELECTED : 1, // 0001
			NO_STATE_SELECTED : 2, // 0010
		};

		this.errorMsgs = [
				"Fatal error!<br/>If you're seeing this message, something has gone wrong.", // O: No Error (0000)
				"No metrics selected!<br/>You must select a metric to explore the visualization.", // 1: No Metric Selected (0001)
				"No states selected!<br/>You must select a state to explore the visualization.", // 2: No state selected (0010)
				"No states or metrics selected!<br/>You must select at least one state and one metric to explore the visualization." // 3: No state & no metric (0011)
		];

		this.errorCode = this.errorCodes.NO_ERROR;
	}

	/**
	 * Initializes the application. Called when page is loaded.
	 */
	AppState.prototype.loadFunction = function() {
		// Initializes all tooltips on the page.
		$(function() {
			$("[rel='tooltip']").tooltip();
		});
	};
	
	/**
	 * Returns the list of currently selected metrics.
	 * @returns {Array} The list of currently selected metrics.
	 */
	AppState.prototype.getSelectedMetrics = function() {
		return this.selected_multiple_metrics;
	};
	
	/**
	 * Returns the list of currently selected states
	 * @returns {Array} The list of currently selected states
	 */
	AppState.prototype.getSelectedStates = function() {
		return this.selected;
	};

	AppState.prototype.getBackNextMultipleMetric = function(metric_id_in) {
		var i = 0;
		var next_back;
		var sentinel = 0;
		var array_next_back = []; //element,-1 just next/ element,-2 just back// element, element: back and forth

		if (this.selected_multiple_metrics.length > 1) {
			while ((i < this.selected_multiple_metrics.length)
					&& (sentinel == 0)) {

				if (this.selected_multiple_metrics[i] == metric_id_in) {
					sentinel = 1;
					var last_pos = this.selected_multiple_metrics.length - 1;

					if (i == 0) {

						next_back = i + 1;
						array_next_back[0] = this.selected_multiple_metrics[next_back];
						array_next_back[1] = -1;
					} else if (i == last_pos) {

						next_back = i - 1;
						array_next_back[0] = this.selected_multiple_metrics[next_back];
						array_next_back[1] = -2;
					} else {
						next_back = i - 1;

						array_next_back[0] = this.selected_multiple_metrics[next_back];
						next_back = i + 1;
						array_next_back[1] = this.selected_multiple_metrics[next_back];
					}
				}
				i++;

			}
		}

		return array_next_back;
	};
	
	/**
	 * Selects or unselects the given list of metric IDs, based on the option input
	 * @param metric_id_in The metric ID lsit
	 * @param option_in The option input
	 */
	AppState.prototype.SelectUnselectMultipleMetric = function(metric_id_in,
			option_in) {
		
		sel = $("#MultipleMetricTitle");
		sel.empty();
		
		var array_next_back = [];

		if (option_in == 1) {
			/***INSERTION**/

			var pos = this.selected_multiple_metrics.length;

			if (metric_id_in instanceof Array) {
				for (j = 0; j < metric_id_in.length; j++) {
					if (this.selected_multiple_metrics.indexOf(metric_id_in[j]) == -1) {
						this.selected_multiple_metrics[pos] = metric_id_in[j];
						pos = pos + 1;
					}

				}
			} else if ((typeof metric_id_in) == "string") {
				this.selected_multiple_metrics[pos] = metric_id_in;
			}
			var lastpos = this.selected_multiple_metrics.length - 1;

			/*******************************************************/
			/***ALWAYS SHOW WHATEVER IS ON THE FIRST POSITION AND LATER LOOP THROUFGG THE METRICS IF
			 * MOR than one metric was selected
			 */
			this.currentind = this.selected_multiple_metrics[lastpos];

			if (this.selected_multiple_metrics.length == 1) {
				array_next_back = this
						.getBackNextMultipleMetric(this.currentind);

				$(".backButton").css("display", "inline");
				$(".backButton").attr("disabled", "disabled");
				$(".backButton").attr("id",
						'clickMultipleMetric' + array_next_back[0]);
				$(".nextButton").css("display", "inline");
				$(".nextButton").attr("disabled", "disabled");
				$(".nextButton").attr("id",
						'clickMultipleMetric' + array_next_back[1]);
			} else if (this.selected_multiple_metrics.length > 1) {
				array_next_back = this
						.getBackNextMultipleMetric(this.currentind);

				$(".backButton").css("display", "inline");
				$(".backButton").removeAttr("disabled");
				$(".backButton").attr("id",
						'clickMultipleMetric' + array_next_back[0]);
				$(".nextButton").css("display", "inline");
				$(".nextButton").attr("disabled", "disabled");
				$(".nextButton").attr("id",
						'clickMultipleMetric' + array_next_back[1]);
			}

			sel.append(Metrics.getMetricByID(
					this.selected_multiple_metrics[lastpos]).getName());
	
			this.refreshVisualization();

		} else if (option_in == 2) {
			/***DELETION **/

			if (metric_id_in instanceof Array) {
				for (j = 0; j < metric_id_in.length; j++) {

					var i = this.selected_multiple_metrics
							.indexOf(metric_id_in[j]);
					if (i != -1) {
						this.selected_multiple_metrics.splice(i, 1);
					}
				}
			} else if ((typeof metric_id_in) == "string") {

				var i = this.selected_multiple_metrics.indexOf(metric_id_in);

				if (i != -1) {
					this.selected_multiple_metrics.splice(i, 1);
				}
			}

			if (this.selected_multiple_metrics.length > 0) {

				var lastpos = this.selected_multiple_metrics.length - 1;
				this.currentind = this.selected_multiple_metrics[lastpos];
				array_next_back = this
						.getBackNextMultipleMetric(this.currentind);
				if (this.selected_multiple_metrics.length > 1) {

					$(".backButton").css("display", "inline");
					$(".backButton").removeAttr("disabled");
					$(".backButton").attr("id",
							'clickMultipleMetric' + array_next_back[0]);
					$(".nextButton").css("display", "inline");
					$(".nextButton").attr("disabled", "disabled");
					$(".nextButton").attr("id",
							'clickMultipleMetric' + array_next_back[1]);
				} else {
					$(".backButton").css("display", "inline");
					$(".backButton").attr("disabled", "disabled");
					$(".backButton").attr("id",
							'clickMultipleMetric' + array_next_back[0]);
					$(".nextButton").css("display", "inline");
					$(".nextButton").attr("disabled", "disabled");
					$(".nextButton").attr("id",
							'clickMultipleMetric' + array_next_back[1]);
				}

				sel.append(Metrics.getMetricByID(
						this.selected_multiple_metrics[lastpos]).getName());
				
				this.refreshVisualization();

			} else {
				this.currentind = null;
			}

		} else if (option_in == 3) {/// BACK AND FORTH BUTTON

			var i = this.selected_multiple_metrics.indexOf(metric_id_in);
			this.currentind = this.selected_multiple_metrics[i];
			var last_pos = this.selected_multiple_metrics.length - 1;
			array_next_back = this.getBackNextMultipleMetric(this.currentind);

			if (i == 0) {

				$(".backButton").css("display", "inline");
				$(".backButton").attr("disabled", "disabled");
				$(".backButton").attr("id",
						'clickMultipleMetric' + array_next_back[1]);
				$(".nextButton").css("display", "inline");
				$(".nextButton").removeAttr("disabled");
				$(".nextButton").attr("id",
						'clickMultipleMetric' + array_next_back[0]);

			} else if (i == last_pos) {
				$(".backButton").css("display", "inline");
				$(".backButton").removeAttr("disabled");
				$(".backButton").attr("id",
						'clickMultipleMetric' + array_next_back[0]);
				$(".nextButton").css("display", "inline");
				$(".nextButton").attr("disabled", "disabled");
				$(".nextButton").attr("id",
						'clickMultipleMetric' + array_next_back[1]);
			} else {
				$(".backButton").css("display", "inline");
				$(".backButton").removeAttr("disabled");
				$(".backButton").attr("id",
						'clickMultipleMetric' + array_next_back[0]);
				$(".nextButton").css("display", "inline");
				$(".nextButton").removeAttr("disabled");
				$(".nextButton").attr("id",
						'clickMultipleMetric' + array_next_back[1]);
			}
			sel.append(Metrics.getMetricByID(this.selected_multiple_metrics[i])
					.getName());

			this.refreshVisualization();
		}

		if (this.selected_multiple_metrics.length > 0) {
			this.clearError(this.errorCodes.NO_METRIC_SELECTED);
		} else {
			this.handleError(this.errorCodes.NO_METRIC_SELECTED);
		}

	};

	/**
	 * Selects the list of states given.
	 * @param states The list of states to select.
	 * @param opt If this is == 0, selects peer states instead.
	 */
	AppState.prototype.setStatesSelected = function(states, opt) {

		if (states == null || states == undefined) {
			this.selected = [];
		} 
		else 
		{
			// Opt 0 == Select Peer States
			if (opt == 0) 
			{
				this.selected = States.getPeers().map(function(s) {
					return s.abbr;
				});
			} 
			else // Otherwise, select list of states provided.
			{
				this.selected = States.getArrayStateByID(states);
			}
		}

		if (this.selected.length == 0) // If no states selected, go into error state
		{
			this.handleError(this.errorCodes.NO_STATE_SELECTED);
		} 
		else 
		{	// Otherwise, clear the error and refresh displayed charts
			this.clearError(this.errorCodes.NO_STATE_SELECTED);
			this.refreshVisualization();
		}

	};

	/**
	 * Deploys the given visualization type for the currently selected data set.
	 * @param visualizationType The type of visualization to deploy.
	 */
	AppState.prototype.visualizationDeployer = function(visualizationType) {		
		cm.resetYear();
		
		switch(visualizationType)
		{
			case this.visualizations.TABLE:				
				this.hideGraphTitle();
				cm.displayTable();				
				break;
			case this.visualizations.LINE:		
				this.showGraphTitle();
				cm.displayLineGraph();				
				break;
			case this.visualizations.BAR:
				this.showGraphTitle();
				cm.displayBarGraph();				
				break;
			case this.visualizations.HEATMAP:
				this.showGraphTitle();
				cm.displayHeatMap();
				break;
			case this.visualizations.EXCEL:
				this.exportExcelData();
				break;
			default:
				return; // Do nothing if we don't get a match
		}
		
	};

	/**
	 * Refreshes all of the visualizations.
	 */
	AppState.prototype.refreshVisualization = function() {
		cm.resetYear();
		cm.refresh();
	};
	
	/**
	 * Shows the metric title and next/previous buttons.
	 */
	AppState.prototype.showGraphTitle = function() {
		$("#metricCycleButtons").show();
	};

	/**
	 * Hides the metric title and next/previous buttons.
	 */
	AppState.prototype.hideGraphTitle = function() {
		$("#metricCycleButtons").hide();
	};
	
	/**
	 * Exports the current data selection as an excel file.
	 */
	AppState.prototype.exportExcelData = function() {
		var data = {
			year : $("ul.timelineListStyle button#tableTimeLineButton.active")
					.text(),
			rows : []
		};

		// Column heads
		var header = [];
		dt.find("thead th").each(function(index) {
			header.push($(this).text());
		});
		data.rows.push(header);
		// Table data
		$.each(dt.fnGetData(), function(key, value) {
			data.rows.push(value);
		});
		var url = "excel?data=" + encodeURIComponent(JSON.stringify(data));
		window.location = url;
	};
	
	/**
	 * Handles the given error code
	 * @param errorCode The error code to handle
	 */
	AppState.prototype.handleError = function(errorCode) {
		this.errorCode = this.errorCode | errorCode; // Make sure the error code flag is flagged

		this.refreshErrorView();
	};

	/**
	 * Clears the current error, if there is one.
	 */
	AppState.prototype.clearError = function(errorCode) {
		if (!this.inError())
			return; // Return if we aren't in error.

		this.errorCode = this.errorCode & (~errorCode); // Clear error code flag.

		this.refreshErrorView();
	};

	/**
	 * Refreshes the error view to reflect the current error code.
	 */
	AppState.prototype.refreshErrorView = function() {
		$("#errorMsg").empty();

		if (this.inError()) {
			$("#vizView").hide();
			$("#errorView").show();

			$("#errorMsg").html(this.errorMsgs[this.errorCode]);
		} else {
			$("#errorView").hide();
			$("#vizView").show();
		}
	};

	/**
	 * Returns whether or not the app is currently in an error state
	 * @returns {Boolean} Whether or not the app is in an error state
	 */
	AppState.prototype.inError = function() {
		return this.errorCode != this.errorCodes.NO_ERROR;
	};

	var publicInterface = {};

	/*
	 * Initializes the default AppState parameters. 
	 */
	publicInterface.create = function() {

		return new AppState();
	}

	return publicInterface;
}($));
