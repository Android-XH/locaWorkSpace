package com.example.shop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MCategory extends BaseBean {
    public static class Data implements Parcelable {
        private int id;
        private String name;
        private int recommend;
        private boolean select;
        private List<Category.Data> categories;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        protected Data(Parcel in) {
            id = in.readInt();
            name = in.readString();
            recommend = in.readInt();
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public List<Category.Data> getCategories() {
            return categories;
        }

        public void setCategories(List<Category.Data> categories) {
            this.categories = categories;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeInt(recommend);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", recommend=" + recommend +
                    ", categories=" + categories +
                    '}';
        }
    }
}
