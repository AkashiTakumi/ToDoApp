package jp.kobespiral.akashi.todo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
    @Id
    String mid; // メンバーID
    String name; // 名前
    String password; // パスワード（暗号化済）
    String role; // ロール
}
