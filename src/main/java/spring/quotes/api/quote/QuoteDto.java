package spring.quotes.api.quote;

import spring.quotes.api.author.AuthorDto;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import spring.quotes.api.category.CategoryDto;

public class QuoteDto {

    private Long id;

    @NotEmpty
    @Size(min = 10, message = "Quote content should have at least 10 characters")
    private String quoteContent;

    private AuthorDto author;

    private Set<CategoryDto> categories = new HashSet<>();

    public QuoteDto() {
    }

    public QuoteDto(String quoteContent) {
        this.quoteContent = quoteContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuoteContent() {
        return quoteContent;
    }

    public void setQuoteContent(String quoteContent) {
        this.quoteContent = quoteContent;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDto> categories) {
        this.categories = categories;
    }

}
