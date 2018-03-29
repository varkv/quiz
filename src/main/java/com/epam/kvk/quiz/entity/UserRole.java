package com.epam.kvk.quiz.entity;

import com.epam.kvk.quiz.entity.enums.UserRoleEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "role")
    private String role;

    public UserRole(UserRoleEnum userRole) {
        this.role = userRole.toString();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
