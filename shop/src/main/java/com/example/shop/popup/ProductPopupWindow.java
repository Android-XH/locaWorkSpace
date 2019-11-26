package com.example.shop.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.util.StringUtil;
import com.example.worktools.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductPopupWindow extends PopupWindow implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private ViewHolder holder;
    private onDownClick onDownClick;

    public onDownClick getOnDownClick() {
        return onDownClick;
    }

    public ProductPopupWindow setOnDownClick(onDownClick onDownClick) {
        this.onDownClick = onDownClick;
        return this;
    }

    public static ProductPopupWindow newInstance(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_product_layout, null);
        ProductPopupWindow popupWindow = new ProductPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return popupWindow;
    }


    public ProductPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        holder = new ViewHolder(contentView);
        holder.tvReload.setOnClickListener(this);
        holder.tvDown.setOnClickListener(this);
        holder.radioPrice.setOnCheckedChangeListener(this);
        setAnimationStyle(R.anim.slide_in_from_top);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reload:
                holder.radioPrice.clearCheck();
                holder.cbTb.setChecked(true);
                holder.cbTmall.setChecked(true);
                holder.etMin.setText("");
                holder.etMax.setText("");
                break;
            case R.id.tv_down:
                Param param=new Param();
                String minPrice=holder.etMin.getText().toString();
                if(StringUtil.isNoEmpty(minPrice)){
                    param.setMinPrice(minPrice);
                }
                String maxPrice=holder.etMax.getText().toString();
                if(StringUtil.isNoEmpty(maxPrice)){
                    param.setMaxPrice(maxPrice);
                }
                List<String>typeList=new ArrayList<>();
                if(holder.cbTb.isChecked()){
                    typeList.add("0");
                }
                if(holder.cbTmall.isChecked()){
                    typeList.add("1");
                }
                String []array=new String[typeList.size()];
                param.setTypes(typeList.toArray(array));
                if(getOnDownClick()!=null){
                    getOnDownClick().onClick(param);
                }
                this.dismiss();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_zero:
                holder.etMin.setText("0");
                holder.etMax.setText("40");
                break;
            case R.id.radio_forty:
                holder.etMin.setText("40");
                holder.etMax.setText("60");
                break;
            case R.id.radio_sixty:
                holder.etMin.setText("60");
                holder.etMax.setText("100");
                break;
            case R.id.radio_one_hundred:
                holder.etMin.setText("100");
                holder.etMax.setText("");
                break;
        }
    }

    public interface onDownClick{
        void onClick(Param param);
    }
    static
    class ViewHolder {
        @BindView(R.id.radio_price)
        RadioGroup radioPrice;
        @BindView(R.id.et_min)
        EditText etMin;
        @BindView(R.id.et_max)
        EditText etMax;
        @BindView(R.id.cb_tmall)
        CheckBox cbTmall;
        @BindView(R.id.cb_tb)
        CheckBox cbTb;
        @BindView(R.id.cb_jd)
        CheckBox cbJd;
        @BindView(R.id.tv_reload)
        TextView tvReload;
        @BindView(R.id.tv_down)
        TextView tvDown;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public static
    class Param{
        private String minPrice;
        private String maxPrice;
        private String[] types;
        public String getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(String minPrice) {
            this.minPrice = minPrice;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
            this.maxPrice = maxPrice;
        }

        public String[] getTypes() {
            return types;
        }

        public void setTypes(String[] types) {
            this.types = types;
        }
    }
}
