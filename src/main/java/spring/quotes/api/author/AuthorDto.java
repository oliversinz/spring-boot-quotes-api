package spring.quotes.api.author;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthorDto {

    private Long id;

    @NotEmpty
    @Size(min = 4, message = "Quote author should have at least 4 characters")
    private String authorName;

    private String authorDescription;

    public AuthorDto() {
    }

    public AuthorDto(String authorName) {
        this.authorName = authorName;
    }

    public AuthorDto(String authorName, String authorDescription) {
        this.authorName = authorName;
        this.authorDescription = authorDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

}
