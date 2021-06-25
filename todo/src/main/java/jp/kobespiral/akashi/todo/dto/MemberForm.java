package jp.kobespiral.akashi.todo.dto;

import jp.kobespiral.akashi.todo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberForm {
    String mid;
    String name;

    public Member toEntity() {
        Member m = new Member();

        m.setMid(mid);
        m.setName(name);

        return m;
    }
}
