package spring.quotes.api.author;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto outgoing(Author author);

    Author incoming(AuthorDto authorDto);

}
