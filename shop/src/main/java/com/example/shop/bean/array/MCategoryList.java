package com.example.shop.bean.array;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.shop.bean.MCategory;

import java.util.List;

public class MCategoryList extends BaseArray implements Parcelable {
    public MCategoryList(int code) {
        super(code);
    }

    private List<MCategory.Data>data;

    protected MCategoryList(Parcel in) {
        data = in.createTypedArrayList(MCategory.Data.CREATOR);
    }

    public static final Creator<MCategoryList> CREATOR = new Creator<MCategoryList>() {
        @Override
        public MCategoryList createFromParcel(Parcel in) {
            return new MCategoryList(in);
        }

        @Override
        public MCategoryList[] newArray(int size) {
            return new MCategoryList[size];
        }
    };

    public List<MCategory.Data> getData() {
        return data;
    }

    public void setData(List<MCategory.Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MCategoryList{" +
                "data=" + data +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }
}
