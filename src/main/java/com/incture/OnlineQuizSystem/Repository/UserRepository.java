package com.incture.OnlineQuizSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.OnlineQuizSystem.Entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUsername(String username);
}
