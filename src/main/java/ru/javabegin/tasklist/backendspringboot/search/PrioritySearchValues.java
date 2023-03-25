package ru.javabegin.tasklist.backendspringboot.search;

//@NoArgsConstructor
//@Getter
//@Setter
//@AllArgsConstructor

// возможные значения, по которым можно искать приоритеты
public class PrioritySearchValues {

    private String title;

    public PrioritySearchValues(String title) {
        this.title = title;
    }

    public PrioritySearchValues() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
