package com.example.shop.bean;

import java.util.List;


public class Product extends BaseBean{

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private int id;
        private long pid;
        private String detail_url;
        private String short_title;
        private String title;
        private int sell_count;
        private float price;
        private String description;
        private String img_url;
        private List<String> small_images;
        private float coupon_amount;
        private int coupon_total_count;
        private int coupon_remain_count;
        private String coupon_share_url;

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", pid=" + pid +
                    ", detail_url='" + detail_url + '\'' +
                    ", short_title='" + short_title + '\'' +
                    ", title='" + title + '\'' +
                    ", sell_count=" + sell_count +
                    ", price=" + price +
                    ", description='" + description + '\'' +
                    ", img_url='" + img_url + '\'' +
                    ", small_images=" + small_images +
                    ", coupon_amount=" + coupon_amount +
                    ", coupon_total_count=" + coupon_total_count +
                    ", coupon_remain_count=" + coupon_remain_count +
                    ", coupon_share_url='" + coupon_share_url + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getPid() {
            return pid;
        }

        public void setPid(long pid) {
            this.pid = pid;
        }

        public String getDetail_url() {
            return detail_url;
        }

        public void setDetail_url(String detail_url) {
            this.detail_url = detail_url;
        }

        public String getShort_title() {
            return short_title;
        }

        public void setShort_title(String short_title) {
            this.short_title = short_title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSell_count() {
            return sell_count;
        }

        public void setSell_count(int sell_count) {
            this.sell_count = sell_count;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public List<String> getSmall_images() {
            return small_images;
        }

        public void setSmall_images(List<String> small_images) {
            this.small_images = small_images;
        }

        public float getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(float coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public int getCoupon_total_count() {
            return coupon_total_count;
        }

        public void setCoupon_total_count(int coupon_total_count) {
            this.coupon_total_count = coupon_total_count;
        }

        public int getCoupon_remain_count() {
            return coupon_remain_count;
        }

        public void setCoupon_remain_count(int coupon_remain_count) {
            this.coupon_remain_count = coupon_remain_count;
        }

        public String getCoupon_share_url() {
            return coupon_share_url;
        }

        public void setCoupon_share_url(String coupon_share_url) {
            this.coupon_share_url = coupon_share_url;
        }
    }
}
