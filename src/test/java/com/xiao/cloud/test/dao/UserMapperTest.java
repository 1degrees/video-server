package com.xiao.cloud.test.dao;

import java.util.List;
import javax.annotation.Resource;

import org.junit.Test;

import com.xiao.core.feature.orm.mybatis.Page;
import com.xiao.core.feature.test.TestSupport;
import com.xiao.web.dao.UserMapper;
import com.xiao.web.model.User;
import com.xiao.web.model.UserExample;

public class UserMapperTest extends TestSupport {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test_selectByExampleAndPage() {
        start();
        Page<User> page = new Page<>(1, 3);
        UserExample example = new UserExample();
        example.createCriteria().andIdGreaterThan(0L);
        final List<User> users = userMapper.selectByExampleAndPage(page, example);
        for (User user : users) {
            System.err.println(user);
        }
        end();
    }
}
