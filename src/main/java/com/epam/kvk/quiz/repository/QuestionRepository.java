package com.epam.kvk.quiz.repository;

import com.epam.kvk.quiz.dto.UserRightAnswers;
import com.epam.kvk.quiz.entity.Question;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query("SELECT p FROM Question p WHERE p.id < ?1 ORDER BY p.id DESC")
    List<Question> findPrevious(Long id, Pageable pageable);

    @Query("SELECT p FROM Question p WHERE p.id > ?1 ORDER BY p.id")
    List<Question> findNext(Long id, Pageable pageable);

    default Question findPrevious(Long id) {
        List<Question> questions = findPrevious(id, new PageRequest(0, 1));
        return questions.size() > 0 ? questions.get(0) : null;
    }

    default Question findNext(Long id) {
        List<Question> questions = findNext(id, new PageRequest(0, 1));
        return questions.size() > 0 ? questions.get(0) : null;
    }
}
