package todo.project1.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.project1.domain.Catalog;
import todo.project1.repository.RealJpaCatalogRepository;
import todo.project1.service.CatalogService;

import java.time.LocalDate;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@Getter
@Setter
@RequiredArgsConstructor
public class CatalogApiController {

    private final CatalogService catalogService;
    private final RealJpaCatalogRepository realJpaCatalogRepository;

    @GetMapping("/all")
    public List<Catalog> showAllList(){
        return catalogService.findCatalog();
    }
    @GetMapping("/list")
    public Page<Catalog> showList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);
        return catalogs;
    }

    @PostMapping("/")
    public ResponseEntity create(String title, String content, @PageableDefault(size = 5) Pageable pageable) {
        Catalog catalog = new Catalog();
        catalog.setTitle(title);
        catalog.setContent(content);
        catalog.setThis_date(LocalDate.now());

        catalogService.register(catalog);
        return new ResponseEntity<>("create success", HttpStatus.OK);
    }

    @PutMapping("/{catalogId}")
    public ResponseEntity updateCatalog(@PathVariable("catalogId") Long catalogId, String title, String content
            , @PageableDefault(size = 5) Pageable pageable) {

        Catalog catalog = new Catalog();
        catalog.setId(catalogId);
        catalog.setTitle(title);
        catalog.setContent(content);
        catalog.setThis_date(catalogService.findOne(catalogId).getThis_date());

        catalogService.register(catalog);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }


    @DeleteMapping("/{catalogId}")
    public ResponseEntity deleteCatalog(@PathVariable("catalogId") Long catalogId, @PageableDefault(size = 5) Pageable pageable) {
        Catalog catalog = new Catalog();
        catalog.setTitle(catalogService.findOne(catalogId).getTitle());
        catalog.setContent(catalogService.findOne(catalogId).getContent());
        catalog.setThis_date(catalogService.findOne(catalogId).getThis_date());
        catalog.setId(catalogService.findOne(catalogId).getId());

        catalogService.delete(catalog);

        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }

}
