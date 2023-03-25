package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.Category;

import java.util.List;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    //ПОИСК КАТЕГОРИИ ПО ВХОЖДЕНИЮ БУКВ (в метод придет стринга - искомое вхождение букв)
    // если title == null или =='', то получим все значения
    @Query("SELECT c FROM Category c where " +
            //проверка title == null или =='' ИЛИ ПО ВХОЖДЕНИЮ:
                                            //c.title приводим к маленьким буквам
                                            //и ищем вхождение тоже в маленьких буквах
            //если не null, то проверяем на '', если не '', то проверяем на вхождение
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title,'%')))  " +
            "order by c.title asc")
    //@Param("title") - параметр метода передает String title в сам JPQL запрос выше
    List<Category> findByTitle(@Param("title") String title);

    // получить все значения, сортировка по названию
    List<Category> findAllByOrderByTitleAsc();

}
