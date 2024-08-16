package in.bushansirgur.expensetrackerapi.mappers;

import in.bushansirgur.expensetrackerapi.dto.CategoryDTO;
import in.bushansirgur.expensetrackerapi.entity.CategoryEntity;
import in.bushansirgur.expensetrackerapi.io.CategoryRequest;
import in.bushansirgur.expensetrackerapi.io.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryEntity mapToCategoryEntity(CategoryDTO categoryDTO);
    CategoryDTO mapToCategoryDTO(CategoryEntity entity);
    @Mapping(target = "categoryIcon", source = "icon")
    CategoryDTO mapToCategoryDTO(CategoryRequest request);
    CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO);
}
