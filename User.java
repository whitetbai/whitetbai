package com.museum.ticketsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User")  // 确保表名与数据库中的表名一致
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")  // 指定数据库列名
    private Long userId;

    @Column(name = "name")  // 指定数据库列名
    private String name;

    @Column(name = "idNumber")  // 指定数据库列名
    private String idNumber;

    @Column(name = "phoneNumber")  // 指定数据库列名
    private String phoneNumber;
}
