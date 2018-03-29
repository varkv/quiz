package com.epam.kvk.quiz.repository;

import com.epam.kvk.quiz.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    List<UserRole> findAllByUser_NameAndUser_Password(String name, String password);
}
