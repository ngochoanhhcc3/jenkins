package com.example.jwt2.javainuse.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "userDto",
                        classes = @ConstructorResult(
                                targetClass = UserDto.class,
                                columns = {
                                        @ColumnResult(name = "userName", type = String.class),
                                        @ColumnResult(name = "password", type = String.class),
                                        @ColumnResult(name = "role", type = String.class),
                                }
                        )
                )
        })
@NamedNativeQuery(
        name = "findByUsernameDTO",
        resultSetMapping = "userDto",
        query = "select t.username as userName, t.`password` as `password`, r.role_key as role " +
                "from ( select " +
                "u.username , " +
                "u.`password`, " +
                "ur.role_id  " +
                "from " +
                "jwt.t_user u " +
                "join jwt.t_user_role ur on " +
                "u.id = ur.user_id " +
                "where " +
                "u.username = ?1 " +
                ") as t " +
                "join jwt.t_role r on " +
                "r.id = t.role_id ;"
)
@Data
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

}
