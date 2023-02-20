package com.iamstevol.Task_Tracker.repository;

import com.iamstevol.Task_Tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);
}
