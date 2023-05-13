package hr.algebra.surfsafly.repository;

import hr.algebra.surfsafly.model.Quiz;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> getAllBy();

    @Override
    Optional<Quiz> findById(@NotNull Long id);


}
