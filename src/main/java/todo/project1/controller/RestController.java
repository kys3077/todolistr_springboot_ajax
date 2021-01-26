package todo.project1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todo.project1.domain.Catalog;
import todo.project1.service.CatalogService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final CatalogService catalogService;

    public RestController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RequestMapping("/")
    public List<Catalog> createTable() {
        List<Catalog> catalogs = catalogService.findCatalog();

        return catalogs;
    }

}
