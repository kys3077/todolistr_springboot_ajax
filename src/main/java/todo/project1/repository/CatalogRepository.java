package todo.project1.repository;

import todo.project1.domain.Catalog;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository{
    Catalog save(Catalog catalog);

    Catalog findById(Long id);

    List<Catalog> findAll();

    void delete(Catalog catalog);


}
