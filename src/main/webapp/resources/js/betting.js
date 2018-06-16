function page(url, data) {
	return {
		url : url,
		data : data
	}
}


var bettingApp = (function() {
	

	function loadUser(eventTarget) {
		var data = {
			userId : $(eventTarget).attr('data-user-id')
		};
		$(eventTarget).closest(".tabControl")
		tabControls.getTab(eventTarget).nextPage("/ds/userBets", data);
	}

	function loadMatch(event) {
		var data = {
			matchId : $(event.target).closest("[data-match-id]").attr("data-match-id")
		};
		tabControls.getTab(event.target).nextPage("/ds/matchDetails", data);
	}
	
	function addNewBet(event) {
		event.preventDefault();
		var data = $('#addBetForm').serialize();
		tabControls.getTab(event.target).nextPageWithoutHistory("/ds/matchAction/addBet", data);
	}

	function editBet(event) {
		event.preventDefault();
		var data = $('#addBetForm').serialize();
		tabControls.getTab(event.target).nextPageWithoutHistory("/ds/matchAction/editBet", data);
	}

	function quickFilling(event, team1Id, team2Id) {
		event.preventDefault();
		
		var scoreHome = parseInt($("[name='bet[SCORE_HOME]']").val());
		var scoreGuest = parseInt($("[name='bet[SCORE_GUEST]']").val());
		
		$("[name='resultScore1']").val(scoreHome);
		$("[name='resultScore2']").val(scoreGuest);
		$("[name='bet[SCORE_DIFFERENCE]']").val(Math.abs(scoreHome-scoreGuest));
		
		var selectedTeamId = '';
		if (scoreHome > scoreGuest) {
			selectedTeamId = team1Id;
		} else if (scoreHome < scoreGuest) {
			selectedTeamId = team2Id;
		} 
		$("[name='bet[WINNER]'][value='"+selectedTeamId+"']").prop("checked",true)
	}
	
	function fireEventOnEnter(activeEvent, fireableEvent) {
		if (activeEvent.which === 13) {
			activeEvent.preventDefault();
			fireableEvent(activeEvent.target);
		}
	}
	
	function addTeamOnEnter(event) {
		fireEventOnEnter(event, addTeam);
	}

	function addUserOnEnter(event) {
		fireEventOnEnter(event, addUser);
	}
	
	function addGroupOnEnter(event) {
		fireEventOnEnter(event, addGroup);
	}
	
	function addRoundOnEnter(event) {
		fireEventOnEnter(event, addRound);
	}
	
	function addChampionshipOnEnter(event) {
		fireEventOnEnter(event, addChampionship);
	}
	
	function addGroup(eventTarget) {
		var $input = $(eventTarget);
		var $championshipRow = $input.closest("[data-round-of-championship-id]");
		var championshipId = $championshipRow.attr("data-round-of-championship-id");
		
		var $roundRow = $input.closest("[data-round-id]");
		var roundId = $roundRow.attr("data-round-id");
		
		var groupName = $input.val();
		if (championshipId && roundId && groupName && groupName.trim().length > 0) {
			var data = {
					groupName : groupName,
					championshipId : championshipId,
					roundId : roundId
			};
	
			tabControls.getTab(eventTarget).nextPageWithoutHistory("/ds/championship/addGroup", data);
		}
		
	}
	
	function addRound(eventTarget) {
		var $input = $(eventTarget);
		var $championshipRow = $input.closest("[data-championship-id]");
		var championshipId = $championshipRow.attr("data-championship-id");
		
		var roundName = $input.val();
		if (championshipId && roundName && roundName.trim().length > 0) {
			var data = {
					roundName : roundName,
					championshipId : championshipId
			};
	
			tabControls.getTab(eventTarget).nextPageWithoutHistory("/ds/championship/addRound", data);
		}
		
	}
	
	function addChampionship(eventTarget) {
		var championshipName = $(eventTarget).val();
		if (championshipName && championshipName.trim().length > 0) {
			var data = {
				championshipName : championshipName
			};
	
			tabControls.getTab(eventTarget).nextPageWithoutHistory("/ds/championship/addChampionship", data);
		}
	}
	
	function addTeam(event) {
		event.preventDefault();
		var $eventTarget = $(event.target);
		var $form = $eventTarget.closest("form");
		if ($form.length > 0) {
			var data = {
				teamId : 			$form.find("[name=teamId]").val(),
				teamName : 			$form.find("[name=teamName]").val(),
				championshipId :	$eventTarget.attr("data-championship-id"),
				roundId : 			$eventTarget.attr("data-round-id"),
				groupId : 			$eventTarget.attr("data-group-id"),
				onlyDetails : true
			};
	
			tabControls.getTab(event.target).nextPageWithoutHistory("/ds/championship/addTeam", data);
		}
	}
	
	function addUser(eventTarget) {
		var userName = $(eventTarget).val();
		if (userName && userName.trim().length > 0) {
			var data = {
				userName : userName
			};
	
			tabControls.getTab(eventTarget).nextPageWithoutHistory("/ds/user/add", data);
		}
	}

	function addMatch(event) {
		var $newMatchRow = $(event.target).closest("tr");
		var data = {
			teamId1 : $newMatchRow.find("[name=teamId1]").val(),
			teamId2 : $newMatchRow.find("[name=teamId2]").val(),
			championshipId : $newMatchRow.find("[data-championship-id]").attr("data-championship-id"),
			roundId : $newMatchRow.find("[data-round-id]").attr("data-round-id"),
			groupId : $newMatchRow.find("[data-group-id]").attr("data-group-id")
		};
		tabControls.getTab(event.target).nextPageWithoutHistory("/ds/championship/addMatch", data);
	}
	
	var pagingHistory = (function() {
		var home = page("/home");
		var pageHistory = [];
		var $backButton = $("#backButton");
		
		function loadHomePage() {
			ajaxCall(home.url, "POST", null, function(result) {
				$("body").html(result);
			});
		}
		
		function pushPage(url, data) {
			pageHistory.push(page(url, data));
			$("#backButton").show();
		}
		
		function popPage() {
			var page = pageHistory.pop();
			page = $(pageHistory).last()[0];

			if (page) {
				$backButton.show();
				return page;
			} 
			$backButton.hide();
			return home;
		}
		
		function actualPage() {
			var page = $(pageHistory).last()[0];
			if (page) {
				return page;
			}
			return home;
		}
		
		return {
			pushPage : pushPage,
			popPage : popPage,
			actualPage : actualPage
		}
	})();
	
	var guiControls = (function() {
		
		function initDropdownGroup(groupId) {
			var $template = $("select[name="+groupId+"]");
			var $dropdowns = $("select[dropdown-group="+groupId+"]");

			var selectedValues = [];
			
			$dropdowns.each(function(index, dropdown) {
				var $dropdown = $(dropdown);
				initDropdown($dropdown, $template, selectedValues);
				
				$dropdown.on("change", function(event) {
					selectedValues = [event.target.value];
					$dropdowns.each(function(i, dd) {
						if (dd !== event.target) {
							initDropdown($(dd), $template, selectedValues);
						}
					});
				});
				
			});
		}
		
		function initDropdown($dropdown, $template, selectedValues) {
			var $selectedOption = null;
			var oldValue = $dropdown.val();
			$dropdown.find("option").remove();
			$template.find("option").each(function(index, option) {
				if (!selectedValues.includes(option.value)) {
					var $option = $(option).clone();
					$dropdown.append($option);
					
					if ($selectedOption == null) {
						$selectedOption = $option;
					}
					
					if (option.value == oldValue) {
						$selectedOption = $option;
					}
				}  
			});
			if ($selectedOption != null) {
				$selectedOption.attr("selected", true);
				selectedValues.push($selectedOption.val());
			}
		}
		
		function goBack(event) {
			var page = bettingApp.pagingHistory.popPage();
			tabControls.getTab(event.target).loadToActiveTab(page.url, page.data);
		}
		
		function refreshPage(event) {
			var page = bettingApp.pagingHistory.actualPage();
			tabControls.getTab(event.target).loadToActiveTab(page.url, page.data);
		}
		
		function nextPageWithoutHistory(event, url, data) {
			tabControls.getTab(event.target).loadToActiveTab(url, data);
		}
		
		function nextPage(event, url, data) {
			bettingApp.pagingHistory.pushPage(url, data);
			tabControls.getTab(event.target).loadToActiveTab(url, data);
		}
		


		return {
			initDropdownGroup : initDropdownGroup,
			pageNav : {
				nextPage : nextPage,
				nextPageWithoutHistory : nextPageWithoutHistory,
				goBack : goBack
			}
		}
	}());
	
	var tabControls = (function() {
		var tabs = [];
		
		function registerTab(navigationId, func) {
			var newTab = tabControl(navigationId, func);
			tabs[navigationId] = newTab;
		}
		
		function getTab(eventTarget) {
			var navigationId = $(eventTarget).closest(".tabControl").attr("id");
			if (!navigationId) {
				debugger;
			}
			return tabs[navigationId];
		}
		
		return {
			registerTab : registerTab,
			getTab : getTab
		}
	}());
	
	var championshipPage = (function() {
		
		function expandChampionship(eventTarget) {

			$("[data-championship-id]").css("background-color", "");
			$("[data-round-of-championship-id]").hide();
			
			var $championshipRow = $(eventTarget).closest("[data-championship-id]");
			var championshipId = $championshipRow.attr("data-championship-id");
			
			$championshipRow.css("background-color", "lightblue");
			$("[data-round-of-championship-id="+championshipId+"]").show();
		}
		
		function openMatchList(event, championshipId) {
			var data = {
				championshipId : championshipId
			};
			tabControls.getTab(event.target).nextPage("/ds/championship", data);
		}
		
		return {
			expandChampionship : expandChampionship,
			openMatchList : openMatchList
		}
	}());
	
	function tabControl(navigationId, withPagingHistory) {
		
		var loadedUrl = null;
		
		function initTabInner(navigationId, innerFunc) {
			var navId = navigationId;
			var f = innerFunc;
			console.log("define "+navigationId+", loadedUrl="+loadedUrl);
			console.log($("#"+navigationId+".tabControl ul:first li a"));
			$("#"+navigationId+".tabControl").on('click', "ul:first li a", function(e) {
				e.preventDefault();
				var url = $(this).attr("data-url");
			
				console.log("clicked "+navigationId+", loadedUrl="+loadedUrl+", new url:"+url);
				
				if (loadedUrl !== url) {
					loadedUrl = url;

					if (typeof url !== "undefined") {
						var pane = $(this), href = this.hash;

						innerFunc(url);
						
					} else {
						$(this).tab('show');
					}
				}
			});			
		}
		
		function nextPageWithoutHistory(_url, _data) {
			loadToActiveTab(_url, _data);
		}
		function nextPage(_url, _data) {
			bettingApp.pagingHistory.pushPage(_url, _data);
			loadToActiveTab(_url, _data);
			$("#backButton").show();
		}
		function loadToActiveTab(_url, _data) {
			ajaxCall(_url, "POST", _data, displayResultInTheActiveTab, bettingApp.logError);
			loadedUrl = _url;
		}
		function displayResultInTheActiveTab(result) {
			$("#" + navigationId + " .tab-pane.active").empty();
			$("#" + navigationId + " .tab-pane.active").html(result);
		}
		
		if (withPagingHistory) {
			initTabInner(navigationId, nextPage);
		} else {
			initTabInner(navigationId, nextPageWithoutHistory);
		}
		
		return {
			nextPageWithoutHistory : nextPageWithoutHistory,
			nextPage : nextPage,
			loadToActiveTab : loadToActiveTab
		}
	}
	
	function logError(result) {
		console.log(result);
	}
	
	function ajaxCall(_url, _type, _data, _successFunction, _errorFunction) {
		$.ajax({
			url : _url,
			type : _type,
			data : _data,
			success : _successFunction,
			error : _errorFunction
		});
	}
	
	tabControls.registerTab("topNavigation", true);
	
	return {
		ajaxCall : ajaxCall,
		loadUser : loadUser,
		loadMatch : loadMatch,
		addTeam : addTeam,
		addTeamOnEnter : addTeamOnEnter,
		addUser : addUser,
		addUserOnEnter : addUserOnEnter,
		addMatch : addMatch,
		addChampionshipOnEnter : addChampionshipOnEnter,
		addChampionship : addChampionship,
		addRound : addRound,
		addRoundOnEnter : addRoundOnEnter,
		addGroup : addGroup,
		addGroupOnEnter : addGroupOnEnter,
		championshipPage : championshipPage,
		tabControls : tabControls,
		logError : logError,
		pagingHistory : pagingHistory,
		bettingPage : {
			addNewBet : addNewBet,
			editBet : editBet,
			quickFilling : quickFilling
		},
		guiControls : guiControls
	}

})();
