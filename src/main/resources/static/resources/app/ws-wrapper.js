window.QuizWSWrapper = function (_options) {
    var options = $.extend({
        "ws_host": "/stomp",
        "ws_stomp_status": "/app/stomp/status",
        "ws_topic_status": "/topic/status",
        "ws_topic_answer_call": "/topic/answer/call",
        "ws_stomp_answer_save": "/app/stomp/answer/save",
        "ws_topic_save_answer_result": "/topic/answer/response",


        "set_state_callback": function(){},
        "call_answer_callback": function(){},
        "save_answer_callback": function(){},
        "set_ws_status_callback": function(){},
    },_options);
    var that = this;
    var stompClient;

    var connect = function () {
        var socket = new SockJS(options.ws_host);
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe(options.ws_topic_status, function (message) {
                console.log("ws_topic_status", message);
                options.set_state_callback($.parseJSON(message.body));
            });
            stompClient.subscribe(options.ws_topic_answer_call, function (message) {
                console.log("ws_topic_answer_call", message);
                options.call_answer_callback();
            });
            stompClient.subscribe(options.ws_topic_save_answer_result, function (message) {
                console.log("ws_topic_save_answer_result", message);
                options.save_answer_callback($.parseJSON(message.body));
            });

            options.set_ws_status_callback(true);
            getStatus();
        }, () => {
            options.set_ws_status_callback(false);
        });
          
    }

    function disconnect() {
        stompClient.disconnect();
        options.set_ws_status_callback(false);
        console.log("Disconnected");
    }

    function getStatus() {
        stompClient.send(options.ws_stomp_status, {}, {});
    }

    function sendAnswer(data) {
        stompClient.send(options.ws_stomp_answer_save, {}, JSON.stringify(data));
    }

    return {
        "connect": connect,
        "disconnect": disconnect,
        "sendAnswer": sendAnswer,
        "getStatus": getStatus,
    };
}