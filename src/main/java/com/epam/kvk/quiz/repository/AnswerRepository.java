package com.epam.kvk.quiz.repository;

import com.epam.kvk.quiz.dto.UserRightAnswers;
import com.epam.kvk.quiz.entity.Answer;
import com.epam.kvk.quiz.entity.Question;
import com.epam.kvk.quiz.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.TreeSet;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long>{

    default Answer createAnswer(User user, Question question, String userAnswer){
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswer(userAnswer);
        answer.setUser(user);
        return save(answer);
    }

    Long countByUser_NameAndQuestion_id(String name, Long quiestionId);

    Long countByAnswerAndQuestion(String answer, Question question);


    @Query("SELECT NEW com.epam.kvk.quiz.dto.UserRightAnswers(a.user.name, count(с)) " +
            "FROM Answer a " +
            "LEFT JOIN a.question q " +
            "LEFT JOIN q.correct с " +
            "WHERE a.answer  = с " +
            "GROUP BY a.user.name")
    TreeSet<UserRightAnswers> getUserRightAnswers();
}
