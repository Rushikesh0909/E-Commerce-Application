package com.bikkadIt.repository;

import com.bikkadIt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {


    Optional<User> findByEmail(String email);

    List<User> findByAboutContaining(String keyword);


}
