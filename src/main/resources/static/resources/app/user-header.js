window.UserHeader = (function () {
    var that = this;
    var init = function () {
        $(document).on("quiz-update-state-event", function (event, state) {
            console.log("quiz-update-state-event", state)
            
        })
        $(document).on("quiz-ws-status-event", function (event, isConnected) {
            console.log("quiz-ws-status-event", isConnected)
            toggleConnectedView(isConnected);
        })
    }

    var toggleConnectedView = function(isConnected){
        $("#quiz-ws-connected").toggle(isConnected);
        $("#quiz-ws-disconnected").toggle(!isConnected);
    }

    return {
        "init": init
    };
}())