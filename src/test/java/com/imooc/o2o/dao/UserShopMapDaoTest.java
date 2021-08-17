package com.imooc.o2o.dao;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserShopMap;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserShopMapDaoTest {

    @Resource
    private UserShopMapDao userShopMapDao;

    @Test
    public void testAInsertUserShopMap() throws Exception {
        //创建用户店铺统计信息1
        UserShopMap userShopMap = new UserShopMap();
        PersonInfo customer = new PersonInfo();
        customer.setUserId(1L);
        userShopMap.setUser(customer);
        Shop shop = new Shop();
        shop.setShopId(13L);
        userShopMap.setShop(shop);

        userShopMap.setCreateTime(new Date());
        userShopMap.setPoint(1);
        int effectedNum = userShopMapDao.insertUserShopMap(userShopMap);
        //创建用户店铺统计信息2
        UserShopMap userShopMap2 = new UserShopMap();
        PersonInfo customer2 = new PersonInfo();
        customer2.setUserId(2L);
        userShopMap2.setUser(customer2);
        Shop shop2 = new Shop();
        shop2.setShopId(14L);
        userShopMap2.setShop(shop2);

        userShopMap2.setCreateTime(new Date());
        userShopMap2.setPoint(1);
        effectedNum = userShopMapDao.insertUserShopMap(userShopMap2);
        assertEquals(1, effectedNum);
    }

    /**
     * 测试查询
     */

    @Test
    public void testBQueryUserShopMapList() {
        UserShopMap userShopMap = new UserShopMap();
        List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMap, 0, 3);
        assertEquals(2, userShopMapList.size());

        int count = userShopMapDao.queryUserShopMapCount(userShopMap);
        assertEquals(2, count);
        //按店铺查询
        Shop shop = new Shop();
        shop.setShopId(13L);
        userShopMap.setShop(shop);
        userShopMapList = userShopMapDao.queryUserShopMapList(userShopMap, 0, 3);
        assertEquals(1, userShopMapList.size());
        count = userShopMapDao.queryUserShopMapCount(userShopMap);
        assertEquals(1, count);
        //按用户Id和店铺查询
        userShopMap = userShopMapDao.queryUserShopMap(1, 13);
        assertEquals("测试",userShopMap.getUser().getName());
    }

    @Test
    public void testCUpdateUserShopMapPoint() {
        UserShopMap userShopMap = new UserShopMap();
        userShopMap = userShopMapDao.queryUserShopMap(1L, 13L);
        assertTrue("Error,积分不一致", 1 == userShopMap.getPoint());
        userShopMap.setPoint(2);
        int effectedNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
        assertEquals(1, effectedNum);
    }
}
