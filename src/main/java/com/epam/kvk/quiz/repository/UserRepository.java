package com.epam.kvk.quiz.repository;

import com.epam.kvk.quiz.entity.User;
import com.epam.kvk.quiz.entity.UserRole;
import com.epam.kvk.quiz.entity.enums.UserRoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndPassword(String name, String password);

    @Transactional
    default User createUser(String name, String password) {
        User user = new User(name, password);
        user.setRoles(new ArrayList<UserRole>() {{
            new UserRole(UserRoleEnum.ROLE_USER);
        }});
        return save(user);
    }

    User findByName(String name);
}
