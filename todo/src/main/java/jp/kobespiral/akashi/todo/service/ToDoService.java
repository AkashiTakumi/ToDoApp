package jp.kobespiral.akashi.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.akashi.todo.dto.ToDoForm;
import jp.kobespiral.akashi.todo.entity.ToDo;
import jp.kobespiral.akashi.todo.exception.ToDoAppException;
import jp.kobespiral.akashi.todo.repository.ToDoRepository;

@Service
public class ToDoService {
    @Autowired
    ToDoRepository todos;

    /**
     * 新しく作ったToDoをリポジトリに保存するメソッド
     * @param mid
     * @param form
     * @return
     */
    public ToDo createToDo(String mid, ToDoForm form) {
        ToDo t = form.toEntity();
        t.setMid(mid);
        // 呼び出された時の時刻を取得してエンティティにセット
        Date date = new Date();
        t.setCreatedAt(date);
        t.setDone(false);
        return todos.save(t);
    }

    /**
     * 通し番号を指定して，ToDoを取得
     * @param seq
     * @return
     */
    public ToDo getToDo(Long seq) {
        ToDo t = todos.findById(seq).orElseThrow(
            () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such ToDo exists"));

        return t;
    }

    /**
     * doneとなった時にdoneAtを追加してリポジトリを更新するメソッド
     * @param seq
     * @return
     */
    public ToDo done(Long seq) {
        ToDo t = getToDo(seq);
        Date doneAt = new Date();
        t.setDoneAt(doneAt);
        t.setDone(true);

        return todos.save(t);
    }

    /**
     * RESTAPI用
     * midとseqを引数に取ってtodoを検索する
     * @param mid
     * @param seq
     * @return
     */
    public ToDo done(String mid, Long seq) {
        //ToDo t = todos.findByMidAndSeq(mid, seq);
        ToDo t = getToDo(seq);
        if (t.getMid().equals(mid)) {
            t.setDone(true);
            t.setDoneAt(new Date());
            return todos.save(t);
        } else {
            throw new ToDoAppException(403, "You can't done this todo");
        }
    }

    /**
     * RESTAPI用
     * todoの更新用メソッド
     * midとseqで検索して，form内のタイトルで更新
     * また，todoの作成日時も更新
     * @param mid
     * @param seq
     * @param form
     * @return
     */
    public ToDo updateToDo(String mid, Long seq, ToDoForm form){
        ToDo t = getToDo(seq);
        if (t.getMid().equals(mid)) {
            t.setTitle(form.getTitle());
            t.setCreatedAt(new Date());
            return todos.save(t);
        } else {
            throw new ToDoAppException(403, "You can't update this todo");
        }
    }

    public void deleteToDo(String mid, Long seq) {
        ToDo t = getToDo(seq);
        if (t.getMid().equals(mid)) {
            todos.deleteById(seq);
        } else {
            throw new ToDoAppException(403, "You can't delete this todo");
        }
    }

    /**
     * ある人のToDoリストを取得
     * @param mid
     * @return
     */
    public List<ToDo> getToDoList(String mid) {
        return todos.findByMidAndDoneFalse(mid);
    }

    /**
     * ある人のDoneリストを取得
     * @param mid
     * @return
     */
    public List<ToDo> getDoneList(String mid) {
        return todos.findByMidAndDoneTrue(mid);
    }

    /**
     * 全員のtodoリストを取得
     * @return
     */
    public List<ToDo> getAllToDoList() {
        return todos.findByDoneFalse();
    }

    /**
     * 全員のDoneリストを取得
     * @return
     */
    public List<ToDo> getAllDoneList() {
        return todos.findByDoneTrue();
    }
}
