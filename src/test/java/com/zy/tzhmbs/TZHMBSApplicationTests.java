package com.zy.tzhmbs;

import com.zy.tzhmbs.mapper.DeveloperMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TZHMBSApplicationTests {

    @Autowired
    private DeveloperMapper userMapper;
    @Test
    void contextLoads() {

    }

    //测试逻辑删除
    @Test
    void testDeleted(){
        userMapper.deleteById(3);
    }

    //测试添加数据
    @Test
    void testInsert(){

    }

    @Test
    void testInsertMany(){

    }

}
