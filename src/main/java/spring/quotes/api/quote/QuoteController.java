package spring.quotes.api.quote;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.quotes.api.utils.AppConstants;

@RestController
@RequestMapping("/api")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/quotes")
    public QuoteResponse getAllQuotes(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return quoteService.getAllQuotes(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/quotes/author/{author}")
    public QuoteResponse getQuotesByAuthor(
            @PathVariable(name = "author") String author,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return quoteService.getQuotesByAuthor(author, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/quotes/category/{category}")
    public QuoteResponse getQuotesByCategory(
            @PathVariable(name = "category") String category,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return quoteService.getQuotesByCategory(category, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<QuoteDto> getQuoteById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity(quoteService.getQuoteById(id), HttpStatus.OK);
    }

    @PostMapping("/quotes")
    public ResponseEntity<QuoteDto> createQuote(@Valid @RequestBody QuoteDto quoteDto) {
        return new ResponseEntity(quoteService.createQuote(quoteDto), HttpStatus.CREATED);
    }

    @PutMapping("/quotes/{id}")
    public ResponseEntity<QuoteDto> updateQuote(@PathVariable(name = "id") Long id, @Valid @RequestBody QuoteDto quoteDto) {
        return new ResponseEntity(quoteService.updateQuote(id, quoteDto), HttpStatus.OK);
    }

    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<String> deleteQuoteById(@PathVariable(name = "id") Long id) {
        quoteService.deleteQuoteById(id);
        return new ResponseEntity("Quote deleted successfully.", HttpStatus.OK);
    }

}
