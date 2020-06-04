package com.c.ctgapp.mvvm.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Certificationinfo {
        /**
         * id : null
         * realName : 猪家
         * idCardNum : 3212199012231233
         * frontImgUrl : https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/null
         * backImgUrl : https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/null
         * auditStatus : 审核通过
         * verifyStatus : 2
         * reason :
         * applyDate :
         */

        public int id;
        public String realName;
        public String idCardNum;
        public String frontImgUrl;
        public String backImgUrl;
        public String auditStatus;
        public int verifyStatus;
        public String reason;
        public String applyDate;
}
