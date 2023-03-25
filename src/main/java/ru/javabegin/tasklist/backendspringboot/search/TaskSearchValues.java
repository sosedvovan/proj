package ru.javabegin.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
// возможные значения, по которым можно искать задачи + значения сортировки
public class TaskSearchValues {

    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    private String title;
    private Integer completed;
    private Long priorityId;//храним только id, а не весь объект приоритета
    private Long categoryId;//храним только id, а не весь объект категории

    // постраничность
    private Integer pageNumber;
    private Integer pageSize;

    // сортировка
    private String sortColumn;
    private String sortDirection;

    public TaskSearchValues(String title, Integer completed, Long priorityId, Long categoryId, Integer pageNumber, Integer pageSize, String sortColumn, String sortDirection) {
        this.title = title;
        this.completed = completed;
        this.priorityId = priorityId;
        this.categoryId = categoryId;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortColumn = sortColumn;
        this.sortDirection = sortDirection;
    }

    public TaskSearchValues() {
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
