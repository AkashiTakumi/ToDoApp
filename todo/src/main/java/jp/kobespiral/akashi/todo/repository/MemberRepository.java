package jp.kobespiral.akashi.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.akashi.todo.entity.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, String>{
    List<Member> findAll();
}
