package ru.javabegin.tasklist.backendspringboot.search;

//@NoArgsConstructor
//@Getter
//@Setter
//@AllArgsConstructor
//@Data
// возможные значения, по которым можно искать категории
public class CategorySearchValues {

    private String title;

    public CategorySearchValues(String title) {
        this.title = title;
    }

    public CategorySearchValues() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
