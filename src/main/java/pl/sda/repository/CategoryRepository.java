package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
