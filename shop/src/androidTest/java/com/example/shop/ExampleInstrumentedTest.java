package com.example.shop;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.shop.bean.Product;
import com.example.shop.bean.array.Pagination;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.worktools.model.CallBack;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.shop", appContext.getPackageName());
        System.out.println("单元测试");
        ProductModeImpl productMode=new ProductModeImpl();
        Pagination pagination=new Pagination();
        pagination.setPage(1);
        pagination.setSize(20);
        productMode.getProductList(pagination, new CallBack<List<Product.Data>>() {
            @Override
            public void onSuccess(List<Product.Data> data) {
                System.out.println("获取成功");
                System.out.println(data.toString());
            }
            @Override
            public void onFail(String msg) {
                System.out.println("获取失败"+msg);
            }
        });
    }

}
