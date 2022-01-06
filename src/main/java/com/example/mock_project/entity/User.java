package com.example.mock_project.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "userMock")
@Data
public class User {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "userId")
    private String id;

    @Column(name = "usname",nullable = false, unique = true)
    private String username;
    @Column(name = "psword")
    private String password;
    private String role;
}
