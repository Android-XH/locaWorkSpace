package com.example.shop.bean;

public class Search {
    /**
     * "id":18,"keyword":"衣服","num":3,"recommend":1
     */
    public static class Data{
        private int id;
        private int recommend;
        private String keyword;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", recommend=" + recommend +
                    ", keyword='" + keyword + '\'' +
                    '}';
        }
    }
}
