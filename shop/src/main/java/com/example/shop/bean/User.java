package com.example.shop.bean;

public class User extends BaseBean{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private String user_name;
        private int id;
        private String token;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "user_name='" + user_name + '\'' +
                    ", id=" + id +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
