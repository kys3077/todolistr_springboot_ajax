package todo.project1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity//JPA가 관리하게 매핑해줌
@Getter @Setter
public class Catalog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")//database의 content와 매핑이 됨
    private String content;
    @Column(name = "this_date")//database의 content와 매핑이 됨
    private LocalDate this_date;
}
