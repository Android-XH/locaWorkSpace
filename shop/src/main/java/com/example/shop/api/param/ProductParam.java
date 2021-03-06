package com.example.shop.api.param;


import com.example.shop.bean.array.Pagination;

public class ProductParam {
    /**
     * 分页信息
     */
    private Pagination pagination;
    /**
     * 搜索关键词
     */
    private String keyWord;
    /**
     * 条目ID
     */
    private int id;
    /**
     * 商品ID
     */
    private int pid;
    /**
     * 分类ID
     */
    private int category_id;
    /**
     * 子分类ID
     */
    private int category_item_id;
    /**
     * 推荐等级
     */
    private int recommend;

    private String sort;
    /**
     * 平台类型0 淘宝 1：天猫 2：京东 3：拼多多
     */
    private String[] types;
    /**
     * 最小价格
     */
    private String minPrice;
    /**
     * 最大价格
     */
    private String maxPrice;
    private int menu_id;

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

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
