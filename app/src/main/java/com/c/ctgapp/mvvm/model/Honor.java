package com.c.ctgapp.mvvm.model;

import java.util.List;

public class Honor {

        /**
         * pageNum : 1
         * pageSize : 10
         * size : 3
         * startRow : 1
         * endRow : 3
         * total : 3
         * pages : 1
         * list : [{"id":150,"fileName":"https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/c2d2993a-14f3-4999-9425-82fde849aeb4.jpeg","name":"哈哈哈哈","remark":"不知道"},{"id":151,"fileName":"https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/66dab052-f3ee-46b0-abec-c0cfc491c840.jpeg","name":"坎坎坷坷","remark":"呵呵"},{"id":152,"fileName":"https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/df054087-1600-4672-822d-0d22d6785ce0.jpeg","name":"哩哩啦啦","remark":"你你你你你"}]
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * firstPage : 1
         * lastPage : 1
         */

        public int pageNum;
    public int pageSize;
    public int size;
    public int startRow;
    public int endRow;
    public int total;
    public int pages;
    public int prePage;
    public int nextPage;
    public boolean isFirstPage;
    public boolean isLastPage;
    public boolean hasPreviousPage;
    public boolean hasNextPage;
    public int navigatePages;
    public int navigateFirstPage;
    public int navigateLastPage;
    public int firstPage;
    public int lastPage;
    public List<ListBean> list;
    public List<Integer> navigatepageNums;
    public static class ListBean {
            /**
             * id : 150
             * fileName : https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/c2d2993a-14f3-4999-9425-82fde849aeb4.jpeg
             * name : 哈哈哈哈
             * remark : 不知道
             */

            public int id;
        public String fileName;
        public String name;
        public String remark;

        }

}
