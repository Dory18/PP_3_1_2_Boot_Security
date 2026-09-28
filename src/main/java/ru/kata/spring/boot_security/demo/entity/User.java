package ru.kata.spring.boot_security.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Фамилия должна быть заполнена")
    @Size(min = 2, max = 50, message = "Фамилия не может превышать 50 символов и быть меньше 2")
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Имя должно быть заполнено")
    @Size(min = 2, max = 30, message = "Имя не может превышать 30 символов и быть меньше 2")
    @Column(name = "first_name")
    private String firstName;
    @Size(min = 2, max = 40, message = "Отчество не может превышать 40 символов и быть меньше 2")
    @Column(name = "middle_name")
    private String middleName;
    @NotNull(message = "Укажите возраст")
    @Min(value = 14, message = "Пользователь должен быть старше 14 лет")
    @Column(name = "age")
    private int age;

    @Size(min = 4, message = "Не меньше 4 знаков")
    @Column(unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    public User() {

    }

    public User(String lastName, String firstName, String middleName, int age, String username, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public User(String lastName, String firstName, String middleName, int age) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", lastName='" + lastName + '\'' +
               ", firstName='" + firstName + '\'' +
               ", middleName='" + middleName + '\'' +
               ", age=" + age +
               '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(middleName, user.middleName) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(passwordConfirm, user.passwordConfirm) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middleName, age, username, password, passwordConfirm, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
