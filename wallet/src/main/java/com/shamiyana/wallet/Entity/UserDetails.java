package com.shamiyana.wallet.Entity;

import com.shamiyana.wallet.Utility.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "user") // or @Table(name = "`user`") if you must keep it
@Getter
@Setter
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    // @Email is fine too; keeping it simple
    private String email;

//    @NotNull
//    private Roles role;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
}
