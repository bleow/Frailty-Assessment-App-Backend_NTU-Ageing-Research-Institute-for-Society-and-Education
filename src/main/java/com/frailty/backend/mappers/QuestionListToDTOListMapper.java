package com.frailty.backend.mappers;

import com.frailty.backend.dto.QuestionDTO;
import com.frailty.backend.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

// https://www.baeldung.com/mapstruct

@Mapper(componentModel = "spring")
public interface QuestionListToDTOListMapper {
    List<QuestionDTO> mapQuestionListToDTOList(List<Question> question);

    default QuestionDTO mapQuestionToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuestionNumber(question.getQuestionNumber());
        dto.setQuestionText(question.getQuestionText());
        dto.setStrategy(question.getStrategy());
        return dto;
    }
}
