package com.amos.mybatisplusdemo;

import com.amos.mybatisplusdemo.entity.User;
import com.amos.mybatisplusdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisplusdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private User user;

    @Test
    void contextLoads() {
    }

    //查询User
    @Test
    void findUser() {

        //查询列表
        List<User> user =  userMapper.selectList(null);
        System.out.println("user:" + user);

        //根据ID查询
        User user1 = userMapper.selectById(1L);
        System.out.println("user1:" + user1);

        //通过多个ID批量查询
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);

        //简单的条件查询
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "onestar");
        map.put("age", 18);
        List<User> users2 = userMapper.selectByMap(map);
        users2.forEach(System.out::println);
    }

    //添加User
    @Test
    void addUser(){
        user.setName("2Star");
        user.setAge(18);
        user.setEmail("oneStar@163.com");
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    //修改User
    @Test
    void updateUser(){
        user.setId(1L);
        user.setAge(12);
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    //删除User
    @Test
    void deleteUser(){
        //根据ID删除
        int result = userMapper.deleteById(5L);
        System.out.println(result);

        //批量删除
        int result2 = userMapper.deleteBatchIds(Arrays.asList(1,2,3));
        System.out.println(result2);

        //简单的条件删除
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","fourstar");
        map.put("age",18);
        int result3 = userMapper.deleteByMap(map);
        System.out.println(result3);
    }

}
