package com.c.ctgapp.dao;

import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;

public interface CTGDao {
    //插入user数据
    public boolean addUserData(Response<User.UserBean> responseMutableLiveData);
    //查询user表数据
    public void queryUserData(Response<User.UserBean> responseMutableLiveData);

}
