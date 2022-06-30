package jp.kobespiral.akashi.todo.dto;

import jp.kobespiral.akashi.todo.entity.ToDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoForm {

    private String title;

    public ToDo toEntity() {
        ToDo toDo = new ToDo();

        toDo.setTitle(title);
        return toDo;
    }
}
