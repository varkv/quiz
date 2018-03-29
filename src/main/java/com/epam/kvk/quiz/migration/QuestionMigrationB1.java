package com.epam.kvk.quiz.migration;

import com.epam.kvk.quiz.entity.Question;
import com.epam.kvk.quiz.entity.enums.QuestionTypeEnum;
import com.epam.kvk.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

//@Component
//@Profile("DB-quiz-1")
public class QuestionMigrationB1 {

    @Autowired
    QuestionRepository questionRepository;

    private List<Question> getQuestions(){
        return new ArrayList<Question>(){{
            add(new Question(
                    "What do you think?",
                    "&lt;pre&gt; System.out.println(\"Hello world!\"); &lt;/pre&gt;",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("Wrong answer");
                        add("Wrong answer");
                        add("Correct answer");
                        add("Wrong answer");
                    }},
                    new ArrayList<String>(){{
                        add("Correct answer");
                    }},
                    null
            ));
            add(new Question(
                    "How you feel?",
                    "&lt;pre&gt; System.out.println(\"Goodbye!\"); &lt;/pre&gt;",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("Correct answer");
                        add("Wrong answer");
                        add("Wrong answer");
                        add("Wrong answer");
                    }},
                    new ArrayList<String>(){{
                        add("Correct answer");
                    }},
                    null
            ));

        }};
    }

    @PostConstruct
    private void init(){
        List<Question> questions = getQuestions();
//        Collections.shuffle(questions);
        questionRepository.save(questions);
    }

    void test(){
    }




}
