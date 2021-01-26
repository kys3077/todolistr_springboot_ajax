package todo.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import todo.project1.repository.CatalogRepository;
import todo.project1.repository.JpaCatalogRepository;
import todo.project1.service.CatalogService;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
//
//    private final DataSource dataSource;
//    private final EntityManager em;
//
//    public SpringConfig(DataSource dataSource, EntityManager em) {
//        this.dataSource = dataSource;
//        this.em = em;
//    }

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public CatalogService catalogService(){
        return new CatalogService(catalogRepository());
    }

    @Bean
    public CatalogRepository catalogRepository(){
        //return new MemoryCatalogRepository();
        return new JpaCatalogRepository(em);
    }

}
