package spring.quotes.api.quote;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuoteMapper {

    QuoteMapper INSTANCE = Mappers.getMapper(QuoteMapper.class);

    QuoteDto outgoing(Quote quote);

    Quote incoming(QuoteDto quoteDto);

}
