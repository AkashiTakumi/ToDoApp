package jp.kobespiral.akashi.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.kobespiral.akashi.todo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberForm {
    @Pattern(regexp ="[a-z0-9_\\-]{4,16}")
    String mid;

    @NotBlank
    @Size(min = 1, max = 32)
    String name;

    public Member toEntity() {
        Member m = new Member();

        m.setMid(mid);
        m.setName(name);

        return m;
    }
}
