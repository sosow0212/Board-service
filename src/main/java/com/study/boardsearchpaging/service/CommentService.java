package com.study.boardsearchpaging.service;

import com.study.boardsearchpaging.entity.Board;
import com.study.boardsearchpaging.entity.Comment;
import com.study.boardsearchpaging.entity.User;
import com.study.boardsearchpaging.repository.BoardRepository;
import com.study.boardsearchpaging.repository.CommentRepository;
import com.study.boardsearchpaging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void saveComment(int boardId, int userId, String text) {
        Board board = boardRepository.findById(boardId); // 댓글 달릴 게시물
        User commentWriter = userRepository.findById(userId); // 댓글 단 유저

        Comment comment = new Comment(board, commentWriter, text);
        commentRepository.save(comment);
    }
}
