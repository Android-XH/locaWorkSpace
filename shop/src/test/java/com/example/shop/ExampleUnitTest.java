package com.example.shop;

import com.example.shop.bean.Product;
import com.example.shop.bean.array.Pagination;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.worktools.model.CallBack;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void getProductList(){

        System.out.println("我是单元测试！");
    }
}