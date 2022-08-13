package com.softwarehouse.helpdesk.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_email")
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Can not be empty")
    private String email;
    @Column(name = "user_name")
    @NotEmpty(message = "Can not be empty")
    private String name;
    @Column(name = "user_last_name")
    @NotEmpty(message = "Can not be empty")
    private String lastName;
    @Column(name = "user_password")
    @NotEmpty(message = "Can not be empty")
    @Length(min = 5, message = "You need to provide a password that contains at least 5 characters")
    private String password;
    @Column(name = "user_active")
    private Boolean active;

    public User(String email, String name, String lastName, String password, Boolean active) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.active = active;
    }
}
