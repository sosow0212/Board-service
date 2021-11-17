package com.study.boardsearchpaging.service;

import com.study.boardsearchpaging.entity.Board;
import com.study.boardsearchpaging.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired // 이 어노테이션은 스프링빈이 알아서 읽어와서 주입을해준다. -> 디펜던시 인젝션
    private BoardRepository boardRepository;

    // 글 작성 및 저장
    public void write(Board board) {
        boardRepository.save(board);
    }


    // 게시글 리스트 모두 불러오기
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }


    // 검색 기능
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }


    // 특정 게시글 불러오기
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }


    // 특정 게시글 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }



}
