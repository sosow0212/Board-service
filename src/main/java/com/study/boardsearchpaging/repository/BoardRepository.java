package com.study.boardsearchpaging.repository;

import com.study.boardsearchpaging.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// <...> 안에는 Entity 파일의 이름과와 Entity의 primary column의 타입을 입력한다
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
    // findBy(컬럼 이름)Containing
    // --> 컬럼에서 키워드가 포함된 것을 찾겠다.
    // 즉 키워드가 포함된 모든 데이터를 검색하는 것임


}
