package spring.quotes.api.quote;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.quotes.api.exception.ResourceNotFoundException;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public QuoteResponse getAllQuotes(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Quote> quotes = quoteRepository.findAll(pageable);

        List<Quote> quotesList = quotes.getContent();

        List<QuoteDto> content = quotesList.stream().map(quote -> QuoteMapper.INSTANCE.outgoing(quote)).collect(Collectors.toList());

        QuoteResponse quoteResponse = new QuoteResponse();
        quoteResponse.setContent(content);
        quoteResponse.setPageNo(quotes.getNumber());
        quoteResponse.setPageSize(quotes.getSize());
        quoteResponse.setTotalElements(quotes.getTotalElements());
        quoteResponse.setTotalPages(quotes.getTotalPages());
        quoteResponse.setLast(quotes.isLast());

        return quoteResponse;

    }

    public QuoteResponse getQuotesByAuthor(String author, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Quote> quotes = quoteRepository.findByAuthor_AuthorName(author, pageable);

        List<Quote> quoteList = quotes.getContent();

        List<QuoteDto> content = quoteList.stream().map(quote -> QuoteMapper.INSTANCE.outgoing(quote)).collect(Collectors.toList());

        QuoteResponse quoteResponse = new QuoteResponse();
        quoteResponse.setContent(content);
        quoteResponse.setPageNo(quotes.getNumber());
        quoteResponse.setPageSize(quotes.getSize());
        quoteResponse.setTotalElements(quotes.getTotalElements());
        quoteResponse.setTotalPages(quotes.getTotalPages());
        quoteResponse.setLast(quotes.isLast());

        return quoteResponse;

    }

    public QuoteResponse getQuotesByCategory(String category, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Quote> quotes = quoteRepository.findByCategories_CategoryName(category, pageable);

        List<Quote> quoteList = quotes.getContent();

        List<QuoteDto> content = quoteList.stream().map(quote -> QuoteMapper.INSTANCE.outgoing(quote)).collect(Collectors.toList());

        QuoteResponse quoteResponse = new QuoteResponse();
        quoteResponse.setContent(content);
        quoteResponse.setPageNo(quotes.getNumber());
        quoteResponse.setPageSize(quotes.getSize());
        quoteResponse.setTotalElements(quotes.getTotalElements());
        quoteResponse.setTotalPages(quotes.getTotalPages());
        quoteResponse.setLast(quotes.isLast());

        return quoteResponse;

    }

    public QuoteDto getQuoteById(Long id) {
        Quote quote = quoteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quote", "id", id));
        return QuoteMapper.INSTANCE.outgoing(quote);
    }

    public QuoteDto createQuote(QuoteDto quoteDto) {
        Quote quote = QuoteMapper.INSTANCE.incoming(quoteDto);
        Quote newQuote = quoteRepository.save(quote);
        return QuoteMapper.INSTANCE.outgoing(newQuote);
    }

    public QuoteDto updateQuote(Long id, QuoteDto quoteDto) {

        Quote quote = quoteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quote", "id", id));

        quote.setQuoteContent(quoteDto.getQuoteContent());

        Quote updatedQuote = quoteRepository.save(quote);

        return QuoteMapper.INSTANCE.outgoing(updatedQuote);

    }

    public void deleteQuoteById(Long id) {

        Quote quote = quoteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quote", "id", id));

        quoteRepository.delete(quote);

    }

}
