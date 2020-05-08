package com.c.ctgapp.mvvm.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PersonalInfo {
        /**
         * nickname :昵称
         * work :职业
         * edulevel :专业特长
         * file : 头像
         * verifyStatus : 0：未认证 1:还未过审核（审核中）；2审核通过；3审核不通过
         * telphone : 电话号码
         * address :公司名称
         * orgname : 车同轨
         * realname : 真是姓名
         * companyId : 公司id
         */

        @PrimaryKey(autoGenerate = true)
        public int id;
        public String nickname;
        public String work;
        public String edulevel;
        public String file;
        public int verifyStatus;
        public String telphone;
        public String address;
        public String orgname;
        public String realname;
        public int companyId;

}
