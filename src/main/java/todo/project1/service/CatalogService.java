package todo.project1.service;

import todo.project1.domain.Catalog;
import todo.project1.repository.CatalogRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional//JPA로 데이터 변경할때나 저장할 때는 항상 transactional이 있어야함
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    /**
     * 할일 등록
     */
    public Long register(Catalog catalog) {
        catalogRepository.save(catalog);
        return catalog.getId();
    }

    public List<Catalog> findCatalog() {
        return catalogRepository.findAll();
    }

    public Catalog findOne(Long id) {
        return catalogRepository.findById(id);
    }

    public void delete(Catalog catalog) {
        catalogRepository.delete(catalog);
    }

}
