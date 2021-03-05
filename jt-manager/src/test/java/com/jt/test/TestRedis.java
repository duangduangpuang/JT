package com.jt.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

@SpringBootTest
public class TestRedis {

//    @Autowired
    private JedisSentinelPool jedisSentinel;

    @Test
    public void testRedisSentinel(){
        Jedis jedis = jedisSentinel.getResource();
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }

    @Autowired
    private JedisCluster jedisCluster;
    @Test
    public void testRedisCluster(){
        jedisCluster.set("test at 2020/2/22 night","successful to write in");

    }


}
