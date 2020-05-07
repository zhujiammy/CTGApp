package com.c.ctgapp.mvvm.model;

import java.util.List;

public class User {

    public UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }



    public static class UserBean {
        /**
         * userId : 1260
         * telephone : 18552026537
         * realName : 朱佳
         * nickName :
         * sex :
         * status : 1
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJDVEdfVVJNIiwicmVhbE5hbWUiOiLmnLHkvbMiLCJpc3MiOiJ3d3cuY3Rnb2UuY29tIiwiaWF0IjoxNTg4NzU2MTI0LCJ1c2VySUQiOjEyNjAsInBsYXRmb3JtIjoiQ1RHX1VSTSIsInVzZXJuYW1lIjoiMTg1NTIwMjY1MzciLCJpbmR1c3RyeUNvZGUiOiJBR0VOQ1kifQ.rbeSOF8qyX54w8w7vo9cpkIaRsnZQs-WinNSsAWEE7k
         * ctgId : SCD9FB
         * birth : null
         * file : @_@.jpg
         * type : 1
         * industry : BIG_DATA
         * loginCompanyIndustry :
         * defaultCompanyId : -1
         * defaultCompanyName :
         * companyList : []
         * allianceId : -1
         * allianceName :
         * allianceRole :
         * companyId : -1
         * companyName :
         * position :
         * expert :
         * allianceList : []
         * belongStatus : 0
         */

        private int userId;
        private String telephone;
        private String realName;
        private String nickName;
        private String sex;
        private int status;
        private String token;
        private String ctgId;
        private String birth;
        private String file;
        private int type;
        private String industry;
        private String loginCompanyIndustry;
        private int defaultCompanyId;
        private String defaultCompanyName;
        private int allianceId;
        private String allianceName;
        private String allianceRole;
        private int companyId;
        private String companyName;
        private String position;
        private String expert;
        private int belongStatus;
        private List<?> companyList;
        private List<?> allianceList;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCtgId() {
            return ctgId;
        }

        public void setCtgId(String ctgId) {
            this.ctgId = ctgId;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getLoginCompanyIndustry() {
            return loginCompanyIndustry;
        }

        public void setLoginCompanyIndustry(String loginCompanyIndustry) {
            this.loginCompanyIndustry = loginCompanyIndustry;
        }

        public int getDefaultCompanyId() {
            return defaultCompanyId;
        }

        public void setDefaultCompanyId(int defaultCompanyId) {
            this.defaultCompanyId = defaultCompanyId;
        }

        public String getDefaultCompanyName() {
            return defaultCompanyName;
        }

        public void setDefaultCompanyName(String defaultCompanyName) {
            this.defaultCompanyName = defaultCompanyName;
        }

        public int getAllianceId() {
            return allianceId;
        }

        public void setAllianceId(int allianceId) {
            this.allianceId = allianceId;
        }

        public String getAllianceName() {
            return allianceName;
        }

        public void setAllianceName(String allianceName) {
            this.allianceName = allianceName;
        }

        public String getAllianceRole() {
            return allianceRole;
        }

        public void setAllianceRole(String allianceRole) {
            this.allianceRole = allianceRole;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getExpert() {
            return expert;
        }

        public void setExpert(String expert) {
            this.expert = expert;
        }

        public int getBelongStatus() {
            return belongStatus;
        }

        public void setBelongStatus(int belongStatus) {
            this.belongStatus = belongStatus;
        }

        public List<?> getCompanyList() {
            return companyList;
        }

        public void setCompanyList(List<?> companyList) {
            this.companyList = companyList;
        }

        public List<?> getAllianceList() {
            return allianceList;
        }

        public void setAllianceList(List<?> allianceList) {
            this.allianceList = allianceList;
        }
    }
}
