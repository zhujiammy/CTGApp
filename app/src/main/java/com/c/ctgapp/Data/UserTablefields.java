package com.c.ctgapp.Data;

public class UserTablefields {
    /*表名*/
    public static final String TABLE_NAME_PERSON = "user";
    /*id字段*/
    private static final String _ID = "_id";
    public static final String USERID = "userId";
    public static final String Telephone = "telephone";
    public static final String RealName = "realName";
    public static final String NickName = "nickName";
    public static final String Sex = "sex";
    public static final String Status = "status";
    public static final String Token = "token";
    public static final String CtgId = "ctgId";
    public static final String Birth = "birth";
    public static final String File = "file";
    public static final String Type = "type";
    public static final String Industry = "industry";
    public static final String LoginCompanyIndustry = "loginCompanyIndustry";
    public static final String DefaultCompanyId = "defaultCompanyId";
    public static final String DefaultCompanyName = "defaultCompanyName";
    public static final String CompanyList = "companyList";
    public static final String AllianceId = "allianceId";
    public static final String AllianceName = "allianceName";
    public static final String AllianceRole = "allianceRole";
    public static final String CompanyId = "companyId";
    public static final String CompanyName = "companyName";
    public static final String Position = "position";
    public static final String Expert = "expert";
    public static final String AllianceList = "allianceList";
    public static final String BelongStatus = "belongStatus";

    /*创建表语句 语句对大小写不敏感 create table 表名(字段名 类型，字段名 类型，…)*/
    public static final String CREATE_PERSON = "create table " + TABLE_NAME_PERSON + "("+_ID+" INTEGER PRIMARY KEY, "+USERID+" INTEGER, "+Telephone+" VARCHAR, "+RealName+" VARCHAR, "+NickName+" VARCHAR, "+Sex+" VARCHAR, "+Status
            +" INTEGER, "+Token+" VARCHAR, "+CtgId+" VARCHAR, "+Birth+" VARCHAR, "+File+" VARCHAR, "+Type+" INTEGER, "+Industry+" VARCHAR, "+LoginCompanyIndustry+" VARCHAR, "+DefaultCompanyId+" VARCHAR, "+DefaultCompanyName
            +" VARCHAR, "+CompanyList+" BLOB, "+AllianceId+" VARCHAR, "+AllianceName+" VARCHAR, "+AllianceRole+" VARCHAR, "+CompanyId+" VARCHAR, "+CompanyName+" VARCHAR, "+Position+" VARCHAR, "+Expert+" VARCHAR, "+AllianceList+" BLOB, "+BelongStatus+" INTEGER)";


}
