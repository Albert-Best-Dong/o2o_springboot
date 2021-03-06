package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductSellDaily;
import com.imooc.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductSellDailyDaoTest {
    @Resource
    private ProductSellDailyDao productSellDailyDao;

    /**
     * 测试添加功能
     *
     * @throws Exception
     */
    @Test
    public void testAInsertProductSellDaily() throws Exception {
        //创建商品日销量统计
        int effectedNum = productSellDailyDao.insertProductSellDaily();
        assertEquals(3, effectedNum);
    }

    /**
     * 测试查询功能
     *
     * @throws Exception
     */
    @Test
    public void testBQueryProductSellDaily() throws Exception {
        ProductSellDaily productSellDaily = new ProductSellDaily();
        //叠加店铺去查询
        Shop shop = new Shop();
        shop.setShopId(13L);
        productSellDaily.setShop(shop);
        List<ProductSellDaily> productSellDailyList = productSellDailyDao.queryProductSellDailyList(productSellDaily, null, null);
        assertEquals(2, productSellDailyList.size());
    }

}
