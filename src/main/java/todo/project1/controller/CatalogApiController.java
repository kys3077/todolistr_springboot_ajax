package todo.project1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@Getter
@Setter
@RequiredArgsConstructor
public class CatalogApiController {

    private final CatalogService catalogService;
    private final RealJpaCatalogRepository realJpaCatalogRepository;

    @PostMapping("/read")
    public List<Catalog> showAllList() {
        return catalogService.findCatalog();
    }

    @GetMapping("/list")
    public Page<Catalog> showList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);
        return catalogs;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody String model) {

        JSONObject jsonObject = stringToJson(model);

        Catalog catalog = new Catalog();
        catalog.setTitle((String)jsonObject.get("title"));
        catalog.setContent((String)jsonObject.get("content"));
        catalog.setThis_date(LocalDate.now());

        catalogService.register(catalog);
        return new ResponseEntity<>("create success", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateCatalog(@RequestBody String model) {
        JSONObject jsonObject = stringToJson(model);

        Long id = Long.valueOf((int) jsonObject.get("id"));//이렇게하는 이유는 자바에서 (LONG) 지원안함

        Catalog catalog = new Catalog();
        catalog.setId(id);
        catalog.setTitle((String)jsonObject.get("title"));
        catalog.setContent((String)jsonObject.get("content"));
        catalog.setThis_date(catalogService.findOne(catalog.getId()).getThis_date());


        catalogService.register(catalog);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }


    @PostMapping("/delete")
    public ResponseEntity deleteCatalog(@RequestBody String model) {
        JSONObject jsonObject = stringToJson(model);
        Long id = Long.valueOf((int) jsonObject.get("id"));//이렇게하는 이유는 자바에서 (LONG) 지원안함

        Catalog catalog = new Catalog();
        catalog.setId(id);

        catalogService.delete(catalog);

        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }

    public JSONObject stringToJson(String model) {
        JSONObject jsonObject = new JSONObject(model);
        JSONArray jsonArray = jsonObject.getJSONArray("models");
        JSONObject explrObject = jsonArray.getJSONObject(0);
        return explrObject;
    }
}
