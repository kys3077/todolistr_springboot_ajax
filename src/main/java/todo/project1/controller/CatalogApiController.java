package todo.project1.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import todo.project1.domain.Catalog;
import todo.project1.repository.RealJpaCatalogRepository;
import todo.project1.service.CatalogService;

import java.time.LocalDate;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@Getter @Setter
@RequiredArgsConstructor
public class CatalogApiController {
    
    private final CatalogService catalogService;
    private final RealJpaCatalogRepository realJpaCatalogRepository;

    @PostMapping("/")
    public Page<Catalog> create(String title, String content, @PageableDefault(size=5)Pageable pageable) {
        Catalog catalog = new Catalog();
        catalog.setTitle(title);
        catalog.setContent(content);
        catalog.setThis_date(LocalDate.now());

        catalogService.register(catalog);

        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);

        return catalogs;
    }

    @PutMapping("/{catalogId}")
    public Page<Catalog> updateCatalog(@PathVariable("catalogId") Long catalogId, String title, String content
            , @PageableDefault(size=5)Pageable pageable) {

        Catalog catalog = new Catalog();
        catalog.setId(catalogId);
        catalog.setTitle(title);
        catalog.setContent(content);
        catalog.setThis_date(catalogService.findOne(catalogId).getThis_date());

        catalogService.register(catalog);

        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);

        return catalogs;
    }


    @DeleteMapping("/{catalogId}")
    public Page<Catalog> deleteCatalog(@PathVariable("catalogId") Long catalogId, @PageableDefault(size=5) Pageable pageable) {
        Catalog catalog = new Catalog();
        catalog.setTitle(catalogService.findOne(catalogId).getTitle());
        catalog.setContent(catalogService.findOne(catalogId).getContent());
        catalog.setThis_date(catalogService.findOne(catalogId).getThis_date());
        catalog.setId(catalogService.findOne(catalogId).getId());

        catalogService.delete(catalog);


        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);

        return catalogs;
    }

}
