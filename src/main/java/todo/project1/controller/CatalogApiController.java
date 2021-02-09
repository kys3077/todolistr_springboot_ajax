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

    enum CRUD {
        CREATE, READ, UPDATE, DELETE
    }

    private final CatalogService catalogService;
    private final RealJpaCatalogRepository realJpaCatalogRepository;

    @GetMapping("/read")
    public List<Catalog> showAllList() {
        return catalogService.findCatalog();
    }

    @PostMapping("/list")
    public Page<Catalog> showList(@RequestBody HashMap<String, Object> model) {
        Pageable pageable = PageRequest.of((int)model.get("page"), (int)model.get("size"));
        Page<Catalog> catalogs = realJpaCatalogRepository.findAll(pageable);
        return catalogs;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody String model) {

        JSONObject jsonObject = stringToJson(model, CRUD.CREATE);
        System.out.println(jsonObject);

        Catalog catalog = new Catalog();
        catalog.setTitle((String) jsonObject.get("title"));
        catalog.setContent((String) jsonObject.get("content"));
        catalog.setThis_date(LocalDate.now());

        catalogService.register(catalog);
        return new ResponseEntity<>("create success", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateCatalog(@RequestBody String model) {
        JSONObject jsonObject = stringToJson(model, CRUD.UPDATE);
        System.out.println(jsonObject);

        Long id = Long.valueOf((int) jsonObject.get("id"));//이렇게하는 이유는 자바에서 (LONG) 지원안함

        System.out.println("id@@@@  = " + id);
        Catalog catalog = new Catalog();
        catalog.setId(id);
        catalog.setTitle((String) jsonObject.get("title"));
        catalog.setContent((String) jsonObject.get("content"));
        catalog.setThis_date(catalogService.findOne(catalog.getId()).getThis_date());


        catalogService.register(catalog);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity deleteCatalog(@RequestBody String model) {
        JSONObject jsonObject = stringToJson(model, CRUD.DELETE);
        Long id = Long.valueOf((int) jsonObject.get("id"));//이렇게하는 이유는 자바에서 (LONG) 지원안함

        Catalog catalog = new Catalog();
        catalog.setId(id);

        catalogService.delete(catalog);

        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }

    public JSONObject stringToJson(String model, CRUD crud) {
        JSONObject jsonObject = new JSONObject(model);
        return jsonObject;
    }
}
