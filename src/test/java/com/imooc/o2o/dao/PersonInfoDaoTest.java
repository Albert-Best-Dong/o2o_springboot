package com.imooc.o2o.dao;

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
public class PersonInfoDaoTest {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void testAInsertPersonInfo() throws Exception {
        //设置新增的用户信息
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("albert");
        personInfo.setGender("男");
        personInfo.setUserType(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setEnableStatus(1);
        int effectedNum = personInfoDao.insertPersonInfo(personInfo);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testBQueryPersonInfoById() {
        long userId = 1;
        //查询Id为1的用户信息
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(userId);
        System.out.println(personInfo.getName());
    }

}
