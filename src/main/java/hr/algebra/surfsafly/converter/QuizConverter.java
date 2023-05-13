package hr.algebra.surfsafly.converter;


import hr.algebra.surfsafly.dto.AnswerDto;
import hr.algebra.surfsafly.dto.QuestionDto;
import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.Answer;
import hr.algebra.surfsafly.model.Question;
import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class QuizConverter {

    private final QuestionConverter questionConverter;
    private final CurrentUserService userService;

    public QuizDto convert(Quiz quiz) {
        return QuizDto.builder()
                .id(Objects.nonNull(quiz.getId()) ? quiz.getId() : null)
                .questionDtoList(quiz.getQuestions()
                        .stream().map(questionConverter::convert).toList())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .author(quiz.getAuthor().getUsername())
                .build();
    }

    public Quiz convert(QuizDto quizDto) throws UserNotFoundException {

        Quiz quiz = new Quiz();
        quiz.setId(Objects.nonNull(quizDto.getId()) ? quizDto.getId() : null);
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setAuthor(userService.getCurrentUser());

        for (QuestionDto questionDto : quizDto.getQuestionDtoList()) {
            Question question = new Question();
            question.setQuestionText(questionDto.getQuestionText());

            for (AnswerDto answerDto : questionDto.getAnswerDtoList()) {
                Answer answer = new Answer();
                answer.setAnswerText(answerDto.getText());
                answer.setIsCorrect(answerDto.getIsCorrect());
                question.addAnswer(answer); // Associate the answer with the question
            }

            quiz.addQuestion(question); // Associate the question with the quiz
        }

        return quiz;

//        return Quiz.builder()
//                .description(quizDto.getDescription())
//                .questions(quizDto.getQuestionDtoList()
//                        .stream().map(questionConverter::convert).toList())
//                .title(quizDto.getTitle())
//                .description(quizDto.getDescription())
//                .author(userRepository.findUserByUsername(quizDto.getAuthor()).orElseThrow())
//                .build();
    }


}
