package in.bushansirgur.expensetrackerapi.repository;

import in.bushansirgur.expensetrackerapi.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for Category entity
 * @author Bushan SC
 * */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    /**
     * finder method to retrieve the categories by user id
     * @param userId
     * @return list
     * */
    List<CategoryEntity> findByUserId(Long userId);

    /**
     * finder method fetch the category by user id and category id
     * @param id, categoryId
     * @return Optional<CategoryEntity>
     * */
    Optional<CategoryEntity> findByUserIdAndCategoryId(Long id, String categoryId);

    /**
     * It checks whether category is present or not by user id and category name
     * @param name, userId
     * @return boolean
     * */
    boolean existsByNameAndUserId(String name, Long userId);

    /**
     * It retrieves the category by name and user id
     * @param name, userId
     * @return Optional<CategoryEntity>
     * */
    Optional<CategoryEntity> findByNameAndUserId(String name, Long userId);
}
