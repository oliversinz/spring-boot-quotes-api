package spring.quotes.api.author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Boolean existsByAuthorName(String authorName);

    Author findByAuthorName(String authorName);

}
