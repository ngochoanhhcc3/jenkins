package com.example.jwt2.javainuse.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRatingKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "role_id")
    Long roleId;

}
