package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.UUID;

@Service
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void saveUser(User user) {
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password)
            .setEmail(user.getPhone())
            .setCreated(new Date())
            .setUpdated(user.getCreated());
        userMapper.insert(user);
    }

    @Override
    public String findUserByUP(User user) {
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User userDB = userMapper.selectOne(queryWrapper);
        if(userDB==null){
            return  null;
        }
        userDB.setPassword("******");
        String json = ObjectMapperUtil.toJSON(userDB);
        String ticket = UUID.randomUUID().toString();
        jedisCluster.setex(ticket,3600*24*7,json);
        return ticket;
    }
}
