package com.c.ctgapp.mvvm.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public String telephone;
    public String realName;
    public String nickName;
    public String sex;
    public int status;
    public String token;
    public String ctgId;
    public String birth;
    public String file;
    public int type;
    public String industry;
    public String loginCompanyIndustry;
    public int defaultCompanyId;
    public String defaultCompanyName;
    public int allianceId;
    public String allianceName;
    public String allianceRole;
    public int companyId;
    public String companyName;
    public String position;
    public String expert;
    public int belongStatus;
    public List<String> companyList;
    public List<String> allianceList;

}
