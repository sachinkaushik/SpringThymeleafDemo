package com.spring.SpringThymeleafDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.SpringThymeleafDemo.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, Integer>{

}
