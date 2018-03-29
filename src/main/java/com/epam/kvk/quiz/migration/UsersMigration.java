package com.epam.kvk.quiz.migration;

import com.epam.kvk.quiz.entity.User;
import com.epam.kvk.quiz.entity.UserRole;
import com.epam.kvk.quiz.entity.enums.UserRoleEnum;
import com.epam.kvk.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@Profile("DB-quiz-1")
public class UsersMigration {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    private void init(){
        User user = new User("admin", "123", new ArrayList<UserRole>() {{
            add(new UserRole(UserRoleEnum.ROLE_ADMIN));
        }});
        userRepository.save(user);
    }
}
