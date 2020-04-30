package com.c.ctgapp.mvvm.model;

public class User {

    public UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public static class UserBean{
        /**
         * userId : 1111
         * telephone : 18913244422
         * realName : 曹远
         * nickName : null
         * sex : 男
         * status : 1
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJDVEdfVVJNIiwicmVhbE5hbWUiOiLmm7nov5wiLCJpc3MiOiJ3d3cuY3Rnb2UuY29tIiwiaWF0IjoxNTg4MTIxODY0LCJ1c2VySUQiOjExMTEsInBsYXRmb3JtIjoiQ1RHX1VSTSIsInVzZXJuYW1lIjoiMTg5MTMyNDQ0MjIiLCJpbmR1c3RyeUNvZGUiOiJBR0VOQ1kifQ.GBdCovKLw4Dfz6QoDQ3lBiQhVfoAe0ydhesH_ngma1k
         * ctgId : Q99ZVE
         * birth : null
         * file : 2b7b3b69-7929-4ec8-8d2a-650ecd9f2757.jpg
         * type : 1
         * industry : AGENCY
         * loginCompanyIndustry : null
         * defaultCompanyId : null
         * defaultCompanyName : null
         * companyList : null
         * allianceId : null
         * allianceName : null
         * allianceRole : null
         * companyId : null
         * companyName : null
         * position : null
         * expert : null
         * allianceList : null
         * belongStatus : 0
         */

        private int userId;
        private String telephone;
        private String realName;
        private Object nickName;
        private String sex;
        private int status;
        private String token;
        private String ctgId;
        private Object birth;
        private String file;
        private int type;
        private String industry;
        private Object loginCompanyIndustry;
        private Object defaultCompanyId;
        private Object defaultCompanyName;
        private Object companyList;
        private Object allianceId;
        private Object allianceName;
        private Object allianceRole;
        private Object companyId;
        private Object companyName;
        private Object position;
        private Object expert;
        private Object allianceList;
        private int belongStatus;

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

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
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

        public Object getBirth() {
            return birth;
        }

        public void setBirth(Object birth) {
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

        public Object getLoginCompanyIndustry() {
            return loginCompanyIndustry;
        }

        public void setLoginCompanyIndustry(Object loginCompanyIndustry) {
            this.loginCompanyIndustry = loginCompanyIndustry;
        }

        public Object getDefaultCompanyId() {
            return defaultCompanyId;
        }

        public void setDefaultCompanyId(Object defaultCompanyId) {
            this.defaultCompanyId = defaultCompanyId;
        }

        public Object getDefaultCompanyName() {
            return defaultCompanyName;
        }

        public void setDefaultCompanyName(Object defaultCompanyName) {
            this.defaultCompanyName = defaultCompanyName;
        }

        public Object getCompanyList() {
            return companyList;
        }

        public void setCompanyList(Object companyList) {
            this.companyList = companyList;
        }

        public Object getAllianceId() {
            return allianceId;
        }

        public void setAllianceId(Object allianceId) {
            this.allianceId = allianceId;
        }

        public Object getAllianceName() {
            return allianceName;
        }

        public void setAllianceName(Object allianceName) {
            this.allianceName = allianceName;
        }

        public Object getAllianceRole() {
            return allianceRole;
        }

        public void setAllianceRole(Object allianceRole) {
            this.allianceRole = allianceRole;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public Object getCompanyName() {
            return companyName;
        }

        public void setCompanyName(Object companyName) {
            this.companyName = companyName;
        }

        public Object getPosition() {
            return position;
        }

        public void setPosition(Object position) {
            this.position = position;
        }

        public Object getExpert() {
            return expert;
        }

        public void setExpert(Object expert) {
            this.expert = expert;
        }

        public Object getAllianceList() {
            return allianceList;
        }

        public void setAllianceList(Object allianceList) {
            this.allianceList = allianceList;
        }

        public int getBelongStatus() {
            return belongStatus;
        }

        public void setBelongStatus(int belongStatus) {
            this.belongStatus = belongStatus;
        }
    }



}
