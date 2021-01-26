package todo.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.project1.domain.Catalog;

public interface RealJpaCatalogRepository extends JpaRepository<Catalog, Long> {


}
