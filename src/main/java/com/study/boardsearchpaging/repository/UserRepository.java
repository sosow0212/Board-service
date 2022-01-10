package com.study.boardsearchpaging.repository;

import com.study.boardsearchpaging.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);
    User findById(int id);

}
