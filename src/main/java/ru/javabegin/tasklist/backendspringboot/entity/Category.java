package ru.javabegin.tasklist.backendspringboot.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Setter
@Getter
public class Category {

    //id
    private Long id;
    //название категории
    private String title;
    //поле, выщитывает сама дб (кол-во выполненных задач в данной категории)
    private Long completedCount;
    //поле, выщитывает сама дб (кол-во выполненных задач в данной категории)
    private Long uncompletedCount;

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    //https://javascopes.com/jpa-basic-annotation-84b4ac41/
    //В @Basic optional = true по дефолту - значит что возможно Null,
    //и можно определить ленивую загрузку поля fetch = FetchType.LAZY
    //@Basic(optional = false, fetch = FetchType.LAZY)
    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Basic
    @Column(name = "completed_count")
    public Long getCompletedCount() {
        return completedCount;
    }

    @Basic
    @Column(name = "uncompleted_count")
    public Long getUncompletedCount() {
        return uncompletedCount;
    }

    // обратная ссылка на коллекцию Task не нужна

}
