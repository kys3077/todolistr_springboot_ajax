package todo.project1.repository;

import todo.project1.domain.Catalog;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaCatalogRepository implements CatalogRepository {
    private final EntityManager em;//JPA는 모든것이 엔티티매니저로 동작이 됨

    public JpaCatalogRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Catalog save(Catalog catalog) {
        if(catalog.getId() == null){
            em.persist(catalog);//이렇게하면 JPA가 insert 쿼리 다 집어넣어줌
            System.out.println("퍼시스트");
        }
        else{
            em.merge(catalog);
            System.out.println("머지");
        }
        return catalog;
    }

    @Override
    public Catalog findById(Long id) {
        Catalog catalog = em.find(Catalog.class, id);//조회기능 em.find(조회할 타입, 식별자)
        return catalog;
    }

    @Override
    public List<Catalog> findAll() {
        List<Catalog> result = em.createQuery("select c from Catalog c", Catalog.class)
                .getResultList();

        return result;
    }

    @Override
    public void delete(Catalog catalog) {
        Catalog c = em.find(Catalog.class, catalog.getId());
        em.remove(c);
    }
}
