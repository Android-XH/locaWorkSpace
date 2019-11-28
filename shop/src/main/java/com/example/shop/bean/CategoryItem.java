package com.example.shop.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryItem extends BaseBean {
    public static class Data implements Parcelable {
        private int id;
        private String category_name;
        private int category_item_id;
        private String category_id;

        protected Data(Parcel in) {
            id = in.readInt();
            category_name = in.readString();
            category_item_id = in.readInt();
            category_id = in.readString();
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public int getCategory_item_id() {
            return category_item_id;
        }

        public void setCategory_item_id(int category_item_id) {
            this.category_item_id = category_item_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(category_name);
            dest.writeInt(category_item_id);
            dest.writeString(category_id);
        }
    }
}
