package jp.kobespiral.akashi.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.kobespiral.akashi.todo.entity.ToDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoForm {
    @NotBlank
    @Size(min = 1, max = 64)
    private String title;

    public ToDo toEntity() {
        ToDo toDo = new ToDo();

        toDo.setTitle(title);
        return toDo;
    }
}
