package todo.project1.mapper;

import org.apache.ibatis.annotations.*;
import todo.project1.domain.Catalog;

import java.util.List;

@Mapper
public interface CatalogMapper {
    @Select("SELECT * FROM CATALOG WHERE id=#{id}")
    Catalog getCatalog(@Param("id") String id);

    @Select("SELECT * FROM CATALOG")
    List<Catalog> getCatalogList();

    @Insert("INSERT INTO CATALOG VALUES(#{id}, #{title}, #{content}, #{this_date}")
    int insertCatalog(@Param("id") String id, @Param("title") String title, @Param("content") String content, @Param("this_date") String this_date);

    @Update("UPDATE CATALOG SET title=#{title}, content=#{content}, this_date=#{this_date} WHERE id=#{id}")
    int updateCatalog(@Param("id") String id, @Param("title") String title, @Param("content") String content, @Param("this_date") String this_date);

    @Delete("DELETE FROM CATALOG WHERE id=#{id}")
    int deleteCatalog(@Param("id") String id);
}
