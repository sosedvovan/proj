package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.Task;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // учитываем, что параметр может быть null или пустым
    @Query("SELECT p FROM Task p where " +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title,'%'))) and" +
            "(:completed is null or p.completed=:completed) and " +
            "(:priorityId is null or p.priority.id=:priorityId) and " +
            "(:categoryId is null or p.category.id=:categoryId)"
    )
    // искать по всем переданным параметрам (пустые параметры учитываться не будут)
    //тк же передаем в параметры Pageable - это объект для пагинации и сортировки,
    //и из-за этого объекта из метода возвращаем Page<Task> а не List<Task>
    Page<Task> findByParams(@Param("title") String title,
                            @Param("completed") Integer completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId,
                            Pageable pageable
    );



}
