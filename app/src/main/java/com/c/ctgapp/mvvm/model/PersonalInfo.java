package com.c.ctgapp.mvvm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(indices = {@Index(value = {"realname"},
        unique = true)})
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
        @ColumnInfo(name = "realname")
        public String realname;
        public int companyId;


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PersonalInfo that = (PersonalInfo) o;
                return verifyStatus == that.verifyStatus &&
                        companyId == that.companyId &&
                        Objects.equals(nickname, that.nickname) &&
                        Objects.equals(work, that.work) &&
                        Objects.equals(edulevel, that.edulevel) &&
                        Objects.equals(file, that.file) &&
                        Objects.equals(telphone, that.telphone) &&
                        Objects.equals(address, that.address) &&
                        Objects.equals(orgname, that.orgname) &&
                        Objects.equals(realname, that.realname);
        }

        @Override
        public int hashCode() {
                return Objects.hash(nickname, work, edulevel, file, verifyStatus, telphone, address, orgname, realname, companyId);
        }
}
