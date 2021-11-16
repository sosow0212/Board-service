package com.study.boardsearchpaging.repository;

import com.study.boardsearchpaging.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// <...> 안에는 Entity 파일의 이름과와 Entity의 primary column의 타입을 입력한다
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
