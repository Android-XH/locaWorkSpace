package com.example.shop.api.param;


import com.example.shop.bean.array.Pagination;

public class BaseParam {
    private Pagination pagination;
    private String keyWord;//搜索关键词
    private int id;//条目ID
    private int pid;//商品ID
    private int category_id;//分类ID
    private int category_item_id;//子分类ID
    private int recommend;//推荐等级
    private String sort;
    private String[] types;//平台类型0 淘宝 1：天猫 2：京东 3：拼多多
    private String minPrice;//最小价格
    private String maxPrice;//最大价格
    public Pagination getPagination() {
        if(pagination==null){
            pagination=new Pagination(1,20);
        }
        return pagination;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

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

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCategory_item_id() {
        return category_item_id;
    }

    public void setCategory_item_id(int category_item_id) {
        this.category_item_id = category_item_id;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
