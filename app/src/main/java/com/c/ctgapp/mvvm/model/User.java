package com.c.ctgapp.mvvm.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                status == user.status &&
                type == user.type &&
                defaultCompanyId == user.defaultCompanyId &&
                allianceId == user.allianceId &&
                companyId == user.companyId &&
                belongStatus == user.belongStatus &&
                Objects.equals(telephone, user.telephone) &&
                Objects.equals(realName, user.realName) &&
                Objects.equals(nickName, user.nickName) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(token, user.token) &&
                Objects.equals(ctgId, user.ctgId) &&
                Objects.equals(birth, user.birth) &&
                Objects.equals(file, user.file) &&
                Objects.equals(industry, user.industry) &&
                Objects.equals(loginCompanyIndustry, user.loginCompanyIndustry) &&
                Objects.equals(defaultCompanyName, user.defaultCompanyName) &&
                Objects.equals(allianceName, user.allianceName) &&
                Objects.equals(allianceRole, user.allianceRole) &&
                Objects.equals(companyName, user.companyName) &&
                Objects.equals(position, user.position) &&
                Objects.equals(expert, user.expert) &&
                Objects.equals(companyList, user.companyList) &&
                Objects.equals(allianceList, user.allianceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, telephone, realName, nickName, sex, status, token, ctgId, birth, file, type, industry, loginCompanyIndustry, defaultCompanyId, defaultCompanyName, allianceId, allianceName, allianceRole, companyId, companyName, position, expert, belongStatus, companyList, allianceList);
    }
}
