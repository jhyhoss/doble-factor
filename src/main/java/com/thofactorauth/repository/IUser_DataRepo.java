package com.thofactorauth.repository;

import com.thofactorauth.model.table.User_Data;
import org.springframework.stereotype.Repository;

@Repository
public interface IUser_DataRepo extends IGenericRepo<User_Data, String> {


    User_Data user(String username);

}
