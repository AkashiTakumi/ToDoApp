package jp.kobespiral.akashi.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.akashi.todo.entity.ToDo;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long>{
    // DoneがTrueの時(Done)のものを返す
    public List<ToDo> findByDoneTrue();

    // DoneがFalseの時(ToDo)のものを返す
    public List<ToDo> findByDoneFalse();

    //midとDoneで検索
    public List<ToDo> findByMidAndDoneTrue(String mid);

    //midとToDoで検索
    public List<ToDo> findByMidAndDoneFalse(String mid);

    public ToDo findByMidAndSeq(String mid, Long seq);
}
