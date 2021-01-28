package todo.project1.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@Getter
@Setter
@RequiredArgsConstructor
public class CatalogApiController {

    @Autowired
    private final CatalogService catalogService;
    @Autowired
    private final RealJpaCatalogRepository realJpaCatalogRepository;

    @PostMapping("/read")
    public List<Catalog> showAllList(){
        return catalogService.findCatalog();
    }
    @GetMapping("/list")
    public Page<Catalog> showList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);
        return catalogs;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Map<String, Object> model) {
        Catalog catalog = new Catalog();
        catalog.setTitle((String)model.get("title"));
        catalog.setContent((String)model.get("content"));
        catalog.setThis_date(LocalDate.now());

        catalogService.register(catalog);
        return new ResponseEntity<>("create success", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateCatalog(@RequestBody Map<String, Object> model) {
        
        Catalog catalog = new Catalog();
        catalog.setId((Long)model.get("id"));
        catalog.setTitle((String)model.get("title"));
        catalog.setContent((String)model.get("content"));
        catalog.setThis_date(catalogService.findOne(catalog.getId()).getThis_date());

        catalogService.register(catalog);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }


    @PostMapping("/delete")
    public ResponseEntity deleteCatalog(@RequestBody Map<String, Object> model) {
        System.out.println("test11");
        Catalog catalog = new Catalog();
        catalog.setId((Long) model.get("id"));

        catalogService.delete(catalog);

        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }

}
