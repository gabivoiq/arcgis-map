package com.isi.arcgis.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 5, max = 20)
    @NotBlank(message = "Please provide your username")
    private String username;

    @NotBlank(message = "Please provide the password")
    private String password;

    @NotNull
    @NotBlank(message = "Please provide the first name")
    private String firstName;

    @NotNull
    @NotBlank(message = "Please provide the last name")
    private String lastName;

    private int age;

    @ElementCollection
    @CollectionTable(
            name="user_interest",
            joinColumns = @JoinColumn(name="id", referencedColumnName = "id")
    )
    @Column(name = "user_interest")
    private List<String> userInterests;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String passwordConfirm;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }


    public List<String> getUserInterests() {
        return userInterests;
    }

    public void setUserInterests(List<String> userInterests) {
        this.userInterests = userInterests;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
