window.QuizTimeline = (function () {
    var that = this;
    var quizWSWrapper;
    var quizStateRender;

    var init = function () {

        quizStateRender = new QuizStateRender();
        quizStateRender.init();

        quizWSWrapper = new window.QuizWSWrapper({
            "set_state_callback": function (newState) {
                $(document).trigger("quiz-update-state-event", [newState]);
                quizStateRender.render(newState);
            },
            "call_answer_callback": function () {
                quizWSWrapper.sendAnswer(quizStateRender.getAnswer());
            },
            "save_answer_callback": function () { },
            "set_ws_status_callback": setWsStatusCallback
        });
        quizWSWrapper.connect();
    }

    var setWsStatusCallback = function (status) {
        $(document).trigger("quiz-ws-status-event", [status]);
    }

    var QuizStateRender = function () {
        var $quizTimeline = null;
        var state = null;

        var init = function () {
            $quizTimeline = $("#quiz-timeline");
            QuizStartOverlay.toggleOverlay(true);
        }

        var render = function (newState) {
            console.log("newState", newState)
            QuizStartOverlay.toggleOverlay(newState.state == "BEGIN");

            if (newState.state == "QUESTION" || newState.state == "DISABLED") {
                QuizQuestionRender.render(newState);
            } else {
                //NOP
            }

            QuizEndOverlay.toggleOverlay(newState.state == "END", newState.user_statistic);
        }

        var QuizStartOverlay = (function () {

            var toggleOverlay = function (isVisible) {
                $("#global-pg").toggle(isVisible);
            }

            return {
                "toggleOverlay": toggleOverlay
            }
        }());

        var QuizEndOverlay = (function () {

            var toggleOverlay = function (isVisible, user_statistic) {
                $("#finish-overlay").toggle(isVisible);
                if (isVisible) {
                    renderUserStatistic(user_statistic);
                }
            }

            var renderUserStatistic = function (user_statistic) {
                $("#finish-overlay table tbody").empty();
                for (var i = 0; i < user_statistic.length; i++) {
                    var stat = user_statistic[i];
                    var $tr = $("<tr/>")
                        .append($("<td/>").text(i + 1).attr("scope", "row"))
                        .append($("<td/>").text(stat.userName))
                        .append($("<td/>").text(stat.rightAnswers));
                    if (i == 0) $tr.addClass("table-success");
                    if (i == 1) $tr.addClass("table-info");
                    if (i == 2) $tr.addClass("table-warning");
                    $("#finish-overlay table tbody").append($tr);
                }
            }

            return {
                "toggleOverlay": toggleOverlay
            }
        }());

        var QuizQuestionRender = (function () {

            var render = function (newState) {
                if (newState.question != null) renderQuestion(newState.question);
                toggleAndRenderQuestionStatistic((newState.question_statistic != null), newState.question_statistic, newState.question);
                toggleDisabledView(newState.state == "DISABLED");

                $("#current-number-question-placement").html(newState.current_question_number);
                $("#total-count-question-placement").html(newState.total_question_number);

                state = newState;
            }

            var isAllreadyRendered = function (question) {
                return (state != null
                    && state.question != null
                    && state.question.id == question.id);
            }

            var renderQuestion = function (question) {
                if (isAllreadyRendered(question)) return;
                $('[data-quiz="name"]', $quizTimeline).html(question.name);

                var text = question.text.replace(/[↵\n]/g, "<br>").replace(/\t/g, "\x09 ").replace(/\s{4}/g, "\x09 ");
                console.log(text)
                $('[data-quiz="text"]', $quizTimeline).get()[0].innerHTML = Encoder.htmlDecode(text);
                $('[data-quiz="text"] pre', $quizTimeline).each(function (i, block) {
                    hljs.highlightBlock(block);
                });

                $('[data-quiz="answers"]', $quizTimeline).empty();
                var $answerBlock = $("<form/>");
                switch (question.type) {
                    case "RADIO":
                        var $template = $($("#quiz-form-radio-block").html());
                        for (var a = 0; a < question.answers.length; a++) {
                            var $answer = $template.clone();
                            $('[data-quiz-form-input-field]', $answer)
                                .attr("name", question.id)
                                .attr("value", question.answers[a])
                            $('[data-quiz-form-input-text]', $answer).html(question.answers[a]);
                            $answerBlock.append($answer)
                        }
                        break;
                    case "CHECKBOX":
                        var template = $($("#quiz-form-checkbox-block").html());
                        break;
                    case "TEXTAREA": break;
                }
                $('[data-quiz="answers"]', $quizTimeline).append($answerBlock);
            }

            var toggleDisabledView = function (disabled) {
                $("input, textarea", $quizTimeline).attr({
                    "disabled": disabled,
                    "readonly": disabled,
                })
            }

            var toggleAndRenderQuestionStatistic = function (isEnabled, question_statistic, question) {
                toggleAnswerLink(isEnabled, isEnabled ? question_statistic.link : null);
                toggleHightlightAnswers(isEnabled, isEnabled ? question_statistic.correct : null, question.type);
                toggleInputVotes(isEnabled, isEnabled ? question_statistic.votes : null, question.type);
            }

            var toggleAnswerLink = function (isEnabled, link) {
                $('[data-quiz="link"]', $quizTimeline).empty();
                if (isEnabled && link != null) {
                    $('[data-quiz="link"]', $quizTimeline).append(
                        $("<a/>").attr({
                            "href": link,
                            "target": "_blank"
                        }).text("Link to description")
                    );
                }
            }

            var toggleHightlightAnswers = function (isEnabled, correct, question_type) {
                $(".alert, .alert-danger, .alert-success, .alert-light", $quizTimeline)
                    .removeClass("alert")
                    .removeClass("alert-danger")
                    .removeClass("alert-success")
                    .removeClass("alert-light");

                if (isEnabled) {
                    switch (question_type) {
                        case "RADIO":
                            $(".form-check", $quizTimeline).each(function (i, elem) {
                                var $field = $(elem);
                                $field.addClass("alert");
                                var $input = $("input", $field);

                                if ($input.is(":checked") && correct.indexOf($input.val()) == -1) {
                                    $field.addClass("alert-danger");
                                } else if (correct.indexOf($input.val()) != -1) {
                                    $field.addClass("alert-success");
                                } else {
                                    $field.addClass("alert-light");
                                }
                            })
                            break;
                        case "CHECKBOX":
                            break;
                        case "TEXTAREA": break;
                    }
                }
            }

            var toggleInputVotes = function (isEnabled, votes, question_type) {
                $("[data-quiz-form-input-progress]").hide();
                if (isEnabled && votes != null) {
                    switch (question_type) {
                        case "RADIO":
                            var totalVotes = 0;
                            votes.forEach(function (elem) {
                                totalVotes += elem.votes;
                            })
                            votes.forEach(function (elem) {
                                var $field = $("input[value='" + elem.answer + "']").closest(".form-check");
                                $(".progress-bar", $field).attr({
                                    "aria-valuenow": elem.votes,
                                    "aria-valuemax": totalVotes,
                                }).css({
                                    "width": parseInt(elem.votes * 100 / totalVotes) + "%"
                                })
                                $(".progress-label", $field).text(elem.votes);
                                $("[data-quiz-form-input-progress]", $field).show();
                            })

                            break;
                        case "CHECKBOX":
                            break;
                        case "TEXTAREA": break;
                    }
                }
            }

            return {
                "render": render,
            }
        }())

        var getAnswer = function () {
            question = state.question;
            var data = {
                "questionId": question.id,
                "answer": null,
            }
            switch (question.type) {
                case "RADIO":
                    var radios = $('input[type="radio"]:checked', $quizTimeline);
                    if (radios.length > 0) {
                        data.answer = radios.val();
                    } else {
                        return;
                    }
                    break;
                case "CHECKBOX":
                    break;
                case "TEXTAREA": break;
            }
            return data;
        }

        return {
            "init": init,
            "render": render,
            "getAnswer": getAnswer,
        }
    };


    var showQuestion = function (question) {
        showQuestionOverlay(question);
    }

    var showQuestionOverlay = function (question) {
        $('#question-overlay').fadeIn(1000, function () {
            renderQuestion(question);
            toogleInputAvailability(true);
            clearLink();
            clearHightlightAnswers();
            hideInputVotes();
            hideQuestionOverlay();
        });
    }

    var hideQuestionOverlay = function () {
        $('#question-overlay').fadeOut(1000);
    }


    return {
        "init": init
    };
}())