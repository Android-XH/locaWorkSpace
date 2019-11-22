package com.example.shop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;


public class Product extends BaseBean{

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Parcelable {
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
        private String coupon_info;
        private String shop_title;
        private String shop_city;


        protected Data(Parcel in) {
            id = in.readInt();
            pid = in.readLong();
            detail_url = in.readString();
            short_title = in.readString();
            title = in.readString();
            sell_count = in.readInt();
            price = in.readFloat();
            description = in.readString();
            img_url = in.readString();
            small_images = in.createStringArrayList();
            coupon_amount = in.readFloat();
            coupon_total_count = in.readInt();
            coupon_remain_count = in.readInt();
            coupon_share_url = in.readString();
            coupon_info = in.readString();
            shop_title = in.readString();
            shop_city = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeLong(pid);
            dest.writeString(detail_url);
            dest.writeString(short_title);
            dest.writeString(title);
            dest.writeInt(sell_count);
            dest.writeFloat(price);
            dest.writeString(description);
            dest.writeString(img_url);
            dest.writeStringList(small_images);
            dest.writeFloat(coupon_amount);
            dest.writeInt(coupon_total_count);
            dest.writeInt(coupon_remain_count);
            dest.writeString(coupon_share_url);
            dest.writeString(coupon_info);
            dest.writeString(shop_title);
            dest.writeString(shop_city);
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", short_title='" + short_title + '\'' +
                    ", title='" + title + '\'' +
                    ", sell_count=" + sell_count +
                    ", price=" + price +
                    ", description='" + description + '\'' +
                    ", coupon_amount=" + coupon_amount +
                    ", coupon_total_count=" + coupon_total_count +
                    ", coupon_remain_count=" + coupon_remain_count +
                    ", coupon_share_url='" + coupon_share_url + '\'' +
                    ", coupon_info='" + coupon_info + '\'' +
                    ", shop_title='" + shop_title + '\'' +
                    ", shop_city='" + shop_city + '\'' +
                    '}';
        }

        public String getShop_title() {
            return shop_title;
        }

        public void setShop_title(String shop_title) {
            this.shop_title = shop_title;
        }

        public String getShop_city() {
            return shop_city;
        }

        public void setShop_city(String shop_city) {
            this.shop_city = shop_city;
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
            return small_images==null? Collections.emptyList():small_images;
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

        public String getCoupon_info() {
            return coupon_info;
        }

        public void setCoupon_info(String coupon_info) {
            this.coupon_info = coupon_info;
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
