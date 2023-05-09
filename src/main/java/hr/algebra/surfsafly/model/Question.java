package hr.algebra.surfsafly.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "question_text")
    private String questionText;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade=CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        answers.add(answer);
    }
}

