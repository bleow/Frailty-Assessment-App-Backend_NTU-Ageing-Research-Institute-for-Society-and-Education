package com.frailty.backend.repository;

import com.frailty.backend.entity.Question;
import com.frailty.backend.entity.QuestionType;
import com.frailty.backend.entity.QuestionnaireType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuestionType(QuestionType questionType);
    Optional<Question> findByQuestionNumberAndQuestionType(Integer questionNumber, QuestionType questionType);
    List<Question> findByQuestionTypeAndQuestionnaireType(QuestionType questionType, QuestionnaireType questionnaireType);
    Optional<Question> findByQuestionNumberAndQuestionTypeAndQuestionnaireType(Integer questionNumber, QuestionType questionType, QuestionnaireType questionnaireType);
}
