package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    /*@Value("${redis.node}")
    private String redisNode;

    @Bean
    @Scope(value = "prototype")
    public Jedis jedis(){

        String[] node = redisNode.split(":");
        String host = node[0];
        int port = Integer.parseInt(node[1]);
        return new Jedis(host,port);

    }

    @Value("${redis.shards}")
    private String redisShards;

    @Bean
    @Scope(value="prototype")
    public ShardedJedis shardedJedis(){
        List<JedisShardInfo> shards = new ArrayList<>();
        String[] redisNode = redisShards.split(",");
        for (String node : redisNode) {
            String[] splitNode = node.split(":");
            String host = splitNode[0];
            int port = Integer.parseInt(splitNode[1]);
            shards.add(new JedisShardInfo(host,port));
        }
        return new ShardedJedis(shards);
    }

    @Value("${redis.sentinel}")
    private String redisSentinel;

    @Bean
    public JedisSentinelPool jedisSentinel(){
        Set<String> sentinels = new HashSet<>();
        sentinels.add(redisSentinel);
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster",sentinels);
        return jedisSentinelPool;
    }*/

    @Value("${redis.cluster}")
    private String redisCluster;
    @Bean
    @Scope(value="prototype")
    public JedisCluster jedisCluster(){
        Set<HostAndPort> nodes = new HashSet<>();
        String[] splitNode = redisCluster.split(",");
        for (String jedisNode : splitNode) {
            String host = jedisNode.split(":")[0];
            int port = Integer.parseInt(jedisNode.split(":")[1]);
            nodes.add(new HostAndPort(host,port));
        }
        return new JedisCluster(nodes);
    }


}
