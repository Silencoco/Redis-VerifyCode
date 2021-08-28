package com.laj.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.100",6379);

        String ping = jedis.ping();
        System.out.println(ping);

    }

    /**
     * 操作String
     */
    @Test
    public void demo1(){
        Jedis jedis = new Jedis("localhost",6379);
        //获取所有key
        Set<String> keys = jedis.keys("*");
        for(String key:keys){
            System.out.println(key+"\n---");
        }
        //添加并获取
        jedis.set("name","coco");
        String name = jedis.get("name");
        System.out.println(name);
        jedis.ttl("name");
        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1", "k2");
        for (String m :mget){
            System.out.println(m);
        }
    }

    /**
     * 操作list
     */
    @Test
    public void demo2(){
        Jedis jedis = new Jedis("192.168.100",6379);
        jedis.lpush("k1","name","age","sex");
        List<String> k1 = jedis.lrange("k1", 0, -1);
        for (String ele : k1){
            System.out.println(ele);
        }
    }

    /**
     * 操作set集合
     */
    @Test
    public void demo3(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.sadd("key1","jack","lucy");
//        jedis.srem("lucy");  //删除操作
        Set<String> keys = jedis.smembers("key1");
        System.out.println(keys);

    }
    /**
     * hash操作
     */
    @Test
    public void demo4(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.hset("user","age","20");
        String hget = jedis.hget("user", "age");
        System.out.println(hget);
    }
    /**
     * zset有序集合操作
     */
    @Test
    public void demo5(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.zadd("China",100,"湖南");
        jedis.zadd("China",200,"湖北");
        jedis.zadd("China",150,"广西");
        jedis.zadd("China",250,"广东");
        Set<String> china = jedis.zrange("China", 0, -1);
        System.out.println(china);
    }

    /**
     * 测试连接
     */
    @Test
    public void testLink(){
        Jedis jedis = new Jedis("192.168.1.100",6379);
        String ping = jedis.ping();
        System.out.println(ping);
    }

}
