package com.gr8erkay.goodybag.repository;

import com.gr8erkay.goodybag.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     List<User> findAll();

     User findUserByEmailAndPassword(String email, String password);

     Optional<User> findUserByUserName(String userName);
}
