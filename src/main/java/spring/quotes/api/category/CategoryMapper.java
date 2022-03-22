package spring.quotes.api.category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto outgoing(Category category);

    Category incoming(CategoryDto categoryDto);

}
