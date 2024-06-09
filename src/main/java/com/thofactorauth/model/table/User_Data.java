package com.thofactorauth.model.table;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_DATA_QR")
public class User_Data {


    @Id
    @Column
    private String idCip;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column( length = 100)
    private String secret;

    @Column( length = 1)
    private Integer status;


}
