package spring.quotes.api.author;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.quotes.api.exception.ResourceNotFoundException;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorResponse getAllAuthors(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Author> authors = authorRepository.findAll(pageable);

        List<Author> authorsList = authors.getContent();

        List<AuthorDto> content = authorsList.stream().map(author -> AuthorMapper.INSTANCE.outgoing(author)).collect(Collectors.toList());

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setContent(content);
        authorResponse.setPageNo(authors.getNumber());
        authorResponse.setPageSize(authors.getSize());
        authorResponse.setTotalElements(authors.getTotalElements());
        authorResponse.setTotalPages(authors.getTotalPages());
        authorResponse.setLast(authors.isLast());

        return authorResponse;

    }

    public AuthorDto getAuthorById(Long id) {
        Author author = checkAuthorExists(id);
        return AuthorMapper.INSTANCE.outgoing(author);
    }

    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = AuthorMapper.INSTANCE.incoming(authorDto);
        Author newAuthor = authorRepository.save(author);
        return AuthorMapper.INSTANCE.outgoing(newAuthor);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {
        Author author = checkAuthorExists(id);
        author.setId(id);
        author.setAuthorName(authorDto.getAuthorName());
        author.setAuthorDescription(authorDto.getAuthorDescription());
        Author updatedAuthor = authorRepository.save(author);
        return AuthorMapper.INSTANCE.outgoing(updatedAuthor);
    }

    public void deleteAuthorById(Long id) {
        Author author = checkAuthorExists(id);
        authorRepository.delete(author);
    }

    private Author checkAuthorExists(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
    }

}
