package ru.javabegin.tasklist.backendspringboot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class Stat { // в этой таблице всего 1 запись, которая обновляется (но никогда не удаляется)

    private Long id;
    //кол-во всех выполненных задач, высчитывает сама дб с помощью трегеров
    private Long completedTotal;
    //кол-во всех невыполненных задач, высчитывает сама дб с помощью трегеров
    private Long uncompletedTotal;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "completed_total")
    public Long getCompletedTotal() {
        return completedTotal;
    }

    @Basic
    @Column(name = "uncompleted_total")
    public Long getUncompletedTotal() {
        return uncompletedTotal;
    }

}
