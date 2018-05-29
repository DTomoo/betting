var bettingApp = (function() {

	var selectedTab;

	function initTabs() {
		$('#tabs').on('click', '.tablink,#prodTabs a', function(e) {
			e.preventDefault();
			if (bettingApp.selectedTab !== this) {
				bettingApp.selectedTab = this;
				var url = $(this).attr("data-url");

				if (typeof url !== "undefined") {
					var pane = $(this), href = this.hash;

					// ajax load from data-url
					$(href).load(url, function(result) {
						pane.tab('show');
					});
				} else {
					$(this).tab('show');
				}
			}
		});
	}

	function loadUser(eventTarget) {
		const userId = $(eventTarget).attr('user-id');
		loadDataToActiveTab("/ds/userBets?userId=" + userId);
	}

	function loadMatch(eventTarget) {
		const matchId = $(eventTarget).attr('match-id');
		loadDataToActiveTab("/ds/matchDetails?matchId=" + matchId);
	}

	function loadDataToActiveTab(dataUrl) {
		$(".tab-pane.active").load(dataUrl);
	}

	function addNewBet(eventTarget) {
		$.ajax({
			'url' : '/ds/matchAction/addBet',
			'type' : 'POST',
			'data' : $('#addBetForm').serialize(),
			'success' : function(result) {
				$(".tab-pane.active").html(result);
			},
			'error' : function(result) {
				console.log(result);
			},
			always : function() {
				debugger;
			}
		});
	}
	
	function openForEdit(eventTarget) {
		$(".displayMode").hide();
		$(".editMode").show();
	}

	function sendEditData(eventTarget) {
		var $editRow = $(".editMode");
		
		$.ajax({
			'url' : '/ds/matchAction/editBet',
			'type' : 'POST',
			'data' : {
				score1 : $editRow.find("[name=score1]").val(),
				score2 : $editRow.find("[name=score2]").val(),
				matchId : $editRow.find("[match-id]").attr("match-id"),
				userId : $editRow.find("[user-id]").attr("user-id")
			},
			'success' : function(result) {
				$(".tab-pane.active").html(result);
			},
			'error' : function(result) {
				console.log(result);
			},
			always : function() {
				debugger;
			}
		});
	}
	
	
	
	initTabs();

	return {
		loadUser : loadUser,
		loadMatch : loadMatch,
		bettingPage : {
			addNewBet : addNewBet,
			openForEdit : openForEdit,
			sendEditData : sendEditData
		}
	}

})();
