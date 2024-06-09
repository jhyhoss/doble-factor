package com.thofactorauth.service.impl;

import com.thofactorauth.model.table.User_Data;
import com.thofactorauth.repository.IGenericRepo;
import com.thofactorauth.repository.IUser_DataRepo;
import com.thofactorauth.service.User_DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class User_DataServiceImpl extends ICRUDServiceImpl<User_Data, String> implements User_DataService {

    private final IUser_DataRepo user_DataRepo;

    @Override
    protected IGenericRepo<User_Data, String> getRepo() {
        return user_DataRepo;
    }
}
