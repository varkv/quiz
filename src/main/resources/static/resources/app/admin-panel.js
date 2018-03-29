window.AdminPanel = (function () {
    var that = this;
    var init = function () {
        $('[data-admin="start"]').click(function () {
            handleAction("start");
        });
        $('[data-admin="showPrew"]').click(function () {
            handleAction("showPrew");
        });
        $('[data-admin="disable"]').click(function () {
            handleAction("disable");
        });
        $('[data-admin="showNext"]').click(function () {
            handleAction("showNext");
        });
        $('[data-admin="finish"]').click(function () {
            handleAction("finish");
        });

        $(document).on("quiz-update-state-event", function (event, state) {
            console.log("quiz-update-state-event", state)
            toggleControlButtons(state.actions, state.total_question_number, state.current_question_number);
        })
    }

    var handleAction = function (action) {
        console.log("handleAction", action);
        switch (action) {
            case "start": start(); break;
            case "showPrew": showPrev(); break;
            case "disable": disable(); break;
            case "showNext": showNext(); break;
            case "finish": finish(); break;
        }
    }

    var showNext = function () {
        var url = "/admin/next";
        sendRequest(url);
    }
    var showPrev = function () {
        var url = "/admin/prev";
        sendRequest(url);
    }
    var start = function () {
        var url = "/admin/start";
        sendRequest(url);
    }
    var disable = function () {
        var url = "/admin/disable";
        sendRequest(url);
    }
    var finish = function () {
        var url = "/admin/finish";
        sendRequest(url);
    }

    var sendRequest = function (url) {
        $.ajax({
            "url": url,
            success: function (data) {
                console.log(data);
            },
            error: function (data) {
                console.error(data);
            }
        })
    }

    var toggleControlButtons = function (actions, totalCount, currentCount) {
        $('[data-admin]').hide().removeAttr("disabled");
        for (var i = 0; i < actions.length; i++) {
            switch (actions[i]) {
                case "START":
                    $('[data-admin="start"]').show();
                    break;
                case "PREVIOUS":
                    $('[data-admin="showPrew"]').show();
                    if(currentCount <= 1){
                        $('[data-admin="showPrew"]').attr("disabled","disabled");
                    }
                    break;
                case "DISABLE":
                    $('[data-admin="disable"]').show();
                    break;
                case "FORWARD":
                    $('[data-admin="showNext"]').show();
                    if(currentCount == totalCount){
                        $('[data-admin="showNext"]').attr("disabled","disabled");
                    }
                    break;
                case "FINISH":
                    $('[data-admin="finish"]').show();
                    if(currentCount < totalCount){
                        $('[data-admin="finish"]').attr("disabled","disabled");
                    }
                    break;
            }
        }
    }

    return {
        "init": init
    };
}())