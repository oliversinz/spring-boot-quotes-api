package spring.quotes.api.quote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Page<Quote> findByAuthor_AuthorName(String author, Pageable pageable);

    Page<Quote> findByCategories_CategoryName(String category, Pageable pageable);

}
