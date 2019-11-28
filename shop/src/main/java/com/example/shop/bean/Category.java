package com.example.shop.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;

import java.util.List;

public class Category extends BaseBean {
    public static class Data implements Parcelable {
        private int id;
        private String name;
        private int category_id;
        private int menu_id;
        private boolean select;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        private List<CategoryItem.Data>categoryItems;

        protected Data(Parcel in) {
            id = in.readInt();
            name = in.readString();
            category_id = in.readInt();
            menu_id = in.readInt();
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

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getMenu_id() {
            return menu_id;
        }

        public void setMenu_id(int menu_id) {
            this.menu_id = menu_id;
        }

        public List<CategoryItem.Data> getCategoryItems() {
            return categoryItems;
        }

        public void setCategoryItems(List<CategoryItem.Data> categoryItems) {
            this.categoryItems = categoryItems;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeInt(category_id);
            dest.writeInt(menu_id);
        }
    }
}
