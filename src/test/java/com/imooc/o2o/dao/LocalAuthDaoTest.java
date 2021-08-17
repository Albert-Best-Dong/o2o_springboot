package com.imooc.o2o.dao;

import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest {
    @Autowired
    private LocalAuthDao localAuthDao;
    private final static String username = "root";
    private final static String password = "root";
    @Test
    public void testAInsertLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();

        localAuth.setUsername(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());

        personInfo.setUserId(1L);

        localAuth.setPersonInfo(personInfo);
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testBQueryLocalByUserNameAndPwd() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, password);
        Assert.assertEquals("测试",localAuth.getPersonInfo().getName());
    }

    @Test
    public void testCQueryLocalByUserId() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        Assert.assertEquals("测试",localAuth.getPersonInfo().getName());
    }
    @Test
    public void testDUpdateLocalAuth() {
        Date now = new Date();
        int effectedNum = localAuthDao.updateLocalAuth(1L, username, password, password + "new", now);
        Assert.assertEquals(1, effectedNum);
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        System.out.println(localAuth.getPassword());
    }
}
