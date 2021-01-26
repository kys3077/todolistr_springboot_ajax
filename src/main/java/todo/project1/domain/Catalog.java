package todo.project1.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity//JPA가 관리하게 매핑해줌
public class Catalog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")//database의 content와 매핑이 됨
    private String content;
    @Column(name = "this_date")//database의 content와 매핑이 됨
    private LocalDate this_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getThis_date() {
        return this_date;
    }

    public void setThis_date(LocalDate this_date) {
        this.this_date = this_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
