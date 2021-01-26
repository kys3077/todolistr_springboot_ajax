package todo.project1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import todo.project1.domain.Catalog;
import todo.project1.repository.RealJpaCatalogRepository;
import todo.project1.service.CatalogService;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CatalogController {

    private final CatalogService catalogService;
    private final RealJpaCatalogRepository realJpaCatalogRepository;

    public CatalogController(CatalogService catalogService, RealJpaCatalogRepository realJpaCatalogRepository) {
        this.catalogService = catalogService;
        this.realJpaCatalogRepository = realJpaCatalogRepository;
    }

    @PostMapping(value = "/doRegister")
    @ResponseBody
    public Page<Catalog> create(String title, String content, @PageableDefault(size=5)Pageable pageable) {
        Catalog catalog = new Catalog();
        catalog.setTitle(title);
        catalog.setContent(content);
        catalog.setThis_date(LocalDate.now());

        catalogService.register(catalog);

        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);

        return catalogs;
    }

    @GetMapping(value = "/")
    public String list(Model model, @PageableDefault(size=5)Pageable pageable) {
        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);

        int startPage = Math.max(1, catalogs.getPageable().getPageNumber() - 4);
        int endPage = Math.min(catalogs.getTotalPages(), catalogs.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("catalogs", catalogs);
        return "catalogs/catalogList";
    }

    @PostMapping("/{catalogId}/delete")
    @ResponseBody
    public Page<Catalog> deleteCatalog(@PathVariable("catalogId") Long catalogId, @PageableDefault(size=5)Pageable pageable) {
        Catalog catalog = new Catalog();
        catalog.setTitle(catalogService.findOne(catalogId).getTitle());
        catalog.setContent(catalogService.findOne(catalogId).getContent());
        catalog.setThis_date(catalogService.findOne(catalogId).getThis_date());
        catalog.setId(catalogService.findOne(catalogId).getId());

        catalogService.delete(catalog);


        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);

        return catalogs;
    }

    @PostMapping("/{catalogId}/update")
    @ResponseBody
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

}
