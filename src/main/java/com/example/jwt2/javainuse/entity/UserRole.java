package com.example.jwt2.javainuse.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user_role")
public class UserRole {

    @EmbeddedId
    private CourseRatingKey id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "role_id")
    private Role roleId;

}
