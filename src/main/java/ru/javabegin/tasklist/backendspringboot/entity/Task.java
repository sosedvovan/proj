package ru.javabegin.tasklist.backendspringboot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class Task {

    private Long id;
    private String title;
    //будем использовать не boolean, а Integer
    private Integer completed; // 1 = true, 0 = false
    private Date date;
    //внедряем 2-а объекта
    private Priority priority;
    private Category category;

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }


    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }


    @Basic
    @Column(name = "completed")
    public Integer getCompleted() {
        return completed;
    } // 1 = true, 0 = false


    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    // ссылка на объект Priority
    // одна задача имеет ссылку на один объект
    // priority_id - поле в этой таблице
    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public Priority getPriority() {
        return priority;
    }

    // ссылка на объект Category
    // одна задача имеет ссылку на один объект
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public Category getCategory() {
        return category;
    }

}

/**
 * Тригеры в дб:
 *
 ************************************************************************************************************
 * after insert (после вставки новой строчки с таской):
 *
 * CREATE DEFINER=`root`@`localhost` TRIGGER `task_AFTER_INSERT` AFTER INSERT ON `task` FOR EACH ROW BEGIN
 *
 * 	 можно было упаковать все условия в один if-else, но тогда он становится не очень читабельным

               после сохранения в дб новой таски проверяем: (NEW - это обращение к сохраненной строчке)

               если категория НЕПУСТАЯ            и       статус задачи ЗАВЕРШЕН
         if(ifnull(NEW.category_id,0)>0&&ifnull(NEW.completed,0)=1)then
               тогда в таблице category значение поля completed_count увеличиваем на 1
         update tasklist.category set completed_count=(ifnull(completed_count,0)+1)where id=NEW.category_id;
         end if;

               категория НЕПУСТАЯ                 и       статус задачи НЕЗАВЕРШЕН
         if(ifnull(NEW.category_id,0)>0&&ifnull(NEW.completed,0)=0)then
               тогда в таблице category значение поля uncompleted_count увеличиваем на 1
         update tasklist.category c set uncompleted_count=(ifnull(uncompleted_count,0)+1)where id=NEW.category_id;
         end if;


               и те же манипуляции в таблице с общей статистикой
         if ifnull(NEW.completed,0)=1then
         update tasklist.stat set completed_total=(ifnull(completed_total,0)+1)where id=1;
         else
         update tasklist.stat set uncompleted_total=(ifnull(uncompleted_total,0)+1)where id=1;
         end if;
         END
 ************************************************************************************************************
 *
 *  after update:
 *
 *  CREATE DEFINER=`root`@`localhost` TRIGGER `task_AFTER_UPDATE` AFTER UPDATE ON `task` FOR EACH ROW BEGIN
 *
 *      после изменения в дб старой таски таски проверяем: (old - это обращение к измененной строчке до ее изменения)
 *
         *    изменили completed с 0 на 1, НЕ изменили категорию
         *IF(ifnull(old.completed,0)<> ifnull(new.completed,0)&&new.completed=1&&ifnull(old.category_id,0)=ifnull(new.category_id,0))THEN
        *
        *         в таблице category поля с кол-вом незавершенных уменьшится на 1,  кол-во завершенных увеличится на 1
        *update tasklist.category set uncompleted_count=(ifnull(uncompleted_count,0)-1),completed_count=(ifnull(completed_count,0)+1)where id=old.category_id;
        *
        *          и тоже в таблице с общей статистикой
        *update tasklist.stat set uncompleted_total=(ifnull(uncompleted_total,0)-1),completed_total=(ifnull(completed_total,0)+1)where id=1;
        *
        *
        *END IF;
        *
        *
        *      изменили completed c 1 на 0, НЕ изменили категорию
        *IF(ifnull(old.completed,0)<> ifnull(new.completed,0)&&new.completed=0&&ifnull(old.category_id,0)=ifnull(new.category_id,0))THEN
        *
        *         у категории кол-во завершенных уменьшится на 1, кол-во незавершенных увеличится на 1
        *update tasklist.category set completed_count=(ifnull(completed_count,0)-1),uncompleted_count=(ifnull(uncompleted_count,0)+1)where id=old.category_id;
        *
        *         общая статистика
        *update tasklist.stat set completed_total=(ifnull(completed_total,0)-1),uncompleted_total=(ifnull(uncompleted_total,0)+1)where id=1;
        *
        *
        *END IF;
        *
        *
        *
        *     изменили категорию для неизмененного completed=1
        *IF(ifnull(old.completed,0)=ifnull(new.completed,0)&&new.completed=1&&ifnull(old.category_id,0)<> ifnull(new.category_id,0))THEN
        *
        *         у старой категории кол-во завершенных уменьшится на 1
        *update tasklist.category set completed_count=(ifnull(completed_count,0)-1)where id=old.category_id;
        *
        *
        *         у новой категории кол-во завершенных увеличится на 1
        *update tasklist.category set completed_count=(ifnull(completed_count,0)+1)where id=new.category_id;
        *
        *
        *         общая статистика не изменяется
        *
        *END IF;
        *
        *
        *
        *
        *
        *      изменили категорию для неизменнеого completed=0
        *IF(ifnull(old.completed,0)=ifnull(new.completed,0)&&new.completed=0&&ifnull(old.category_id,0)<> ifnull(new.category_id,0))THEN
        *
        *         у старой категории кол-во незавершенных уменьшится на 1
        *update tasklist.category set uncompleted_count=(ifnull(uncompleted_count,0)-1)where id=old.category_id;
        *
        *         у новой категории кол-во незавершенных увеличится на 1
        *update tasklist.category set uncompleted_count=(ifnull(uncompleted_count,0)+1)where id=new.category_id;
        *
        *         общая статистика не изменяется
        *
        *END IF;
        *
        *
        *
        *
        *
        *
        *      изменили категорию, изменили completed с 1 на 0
        *IF(ifnull(old.completed,0)<> ifnull(new.completed,0)&&new.completed=0&&ifnull(old.category_id,0)<> ifnull(new.category_id,0))THEN
        *
        *         у старой категории кол-во завершенных уменьшится на 1
        *update tasklist.category set completed_count=(ifnull(completed_count,0)-1)where id=old.category_id;
        *
        *         у новой категории кол-во незавершенных увеличится на 1
        *update tasklist.category set uncompleted_count=(ifnull(uncompleted_count,0)+1)where id=new.category_id;
        *
        *
        *         общая статистика
        *update stat set uncompleted_total=(ifnull(uncompleted_total,0)+1),completed_total=(ifnull(completed_total,0)-1)where id=1;
        *
        *
        *END IF;
        *
        *
        *
        *      изменили категорию, изменили completed с 0 на 1
        *IF(ifnull(old.completed,0)<> ifnull(new.completed,0)&&new.completed=1&&ifnull(old.category_id,0)<> ifnull(new.category_id,0))THEN
        *
        *         у старой категории кол-во незавершенных уменьшится на 1
        *update tasklist.category set uncompleted_count=(ifnull(uncompleted_count,0)-1)where id=old.category_id;
        *
        *         у новой категории кол-во завершенных увеличится на 1
        *update tasklist.category set completed_count=(ifnull(completed_count,0)+1)where id=new.category_id;
        *
        *          общая статистика
        *update tasklist.stat set uncompleted_total=(ifnull(uncompleted_total,0)-1),completed_total=(ifnull(completed_total,0)+1)where id=1;
        *
        *END IF;
        *
        *
        *
        *END
 *
 * ************************************************************************************************************
 *
 * after delete:
 *
 * CREATE DEFINER=`root`@`localhost` TRIGGER `task_AFTER_DELETE` AFTER DELETE ON `task` FOR EACH ROW BEGIN
 *
 * 	 можно было упаковать все условия в один if-else, но тогда он становится не очень читабельным
 *
         *      после удаления в дб старой таски таски проверяем: (old - это обращение к измененной строчке до ее изменения)
         *
         *      если удаленная категория НЕПУСТАЯ (существовала) и в ней статус задачи был ЗАВЕРШЕН
         *if(ifnull(old.category_id,0)>0&&ifnull(old.completed,0)=1)then
         *     тогда в таблице category значение поля completed_count уменьшаем на 1
         *update tasklist.category set completed_count=(ifnull(completed_count,0)-1)where id=old.category_id;
         *end if;
         *
         *     если удаленная категория была НЕПУСТАЯ и в ней статус задачи был НЕЗАВЕРШЕН
         *if(ifnull(old.category_id,0)>0&&ifnull(old.completed,0)=0)then
         *      тогда в таблице category значение поля uncompleted_count уменьшаем на 1
         *update tasklist.category set uncompleted_count=(ifnull(uncompleted_count,0)-1)where id=old.category_id;
         *end if;
         *
         *
         *
         *      то же в таблице с общей статистикой
         *if ifnull(old.completed,0)=1then
         *update tasklist.stat set completed_total=(ifnull(completed_total,0)-1)where id=1;
         *else
         *update tasklist.stat set uncompleted_total=(ifnull(uncompleted_total,0)-1)where id=1;
         *end if;
         *
         *END
 */
