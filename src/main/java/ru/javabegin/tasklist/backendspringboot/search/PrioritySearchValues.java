package ru.javabegin.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@NoArgsConstructor
//@Getter
//@Setter
//@AllArgsConstructor

// возможные значения, по которым можно искать приоритеты
public class PrioritySearchValues {

    private String text;

    public PrioritySearchValues(String text) {
        this.text = text;
    }

    public PrioritySearchValues() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
