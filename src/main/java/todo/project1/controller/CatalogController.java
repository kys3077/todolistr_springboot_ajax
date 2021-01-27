package todo.project1.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@Getter @Setter
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;
    private final RealJpaCatalogRepository realJpaCatalogRepository;

    @GetMapping(value = "/")
    public String list(Model model, @PageableDefault(size=5)Pageable pageable) {
        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);

//        int startPage = Math.max(1, catalogs.getPageable().getPageNumber() - 4);
//        int endPage = Math.min(catalogs.getTotalPages(), catalogs.getPageable().getPageNumber() + 4);
        int startPage = 1;
        int endPage = catalogs.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("catalogs", catalogs);
        return "catalogs/catalogList";
    }

}
