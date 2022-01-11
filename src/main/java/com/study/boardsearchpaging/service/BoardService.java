package com.study.boardsearchpaging.service;

import com.study.boardsearchpaging.entity.Board;
import com.study.boardsearchpaging.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 글 작성 및 저장
    public void write(Board board) {

        // LocalDateTime 을 yyyyMMdd 로 변경



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
