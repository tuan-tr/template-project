package com.tth.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tth.persistence.entity.User;
import com.tth.persistence.repository.custom.UserCustomRepository;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository {



}
