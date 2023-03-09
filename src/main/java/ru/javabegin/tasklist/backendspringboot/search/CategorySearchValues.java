package ru.javabegin.tasklist.backendspringboot.search;

import lombok.*;

//@NoArgsConstructor
//@Getter
//@Setter
//@AllArgsConstructor
//@Data
// возможные значения, по которым можно искать категории
public class CategorySearchValues {

    private String text;

    public CategorySearchValues(String text) {
        this.text = text;
    }

    public CategorySearchValues() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
