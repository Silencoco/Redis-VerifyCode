package com.laj.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {
//        String code = getCode();
//        System.out.println(code);
        verifyCode("13142064134");
//        getRedisCode("13142064134","9723475");
    }

    /**
     * 验证码校验
     */
    public static void getRedisCode(String phone,String code){
        //连接redis
        Jedis jedis = new Jedis("192.168.1.100",6379);
        //验证码key
        String codeKey = "VerifyCode"+phone+":code";
        //每个手机每天只能发送三次
        String redisCode = jedis.get(codeKey);
        //判断
        if (redisCode.equals(code)){
            System.out.println("验证成功");
        }else{
            System.out.println("验证失败");
        }
//        jedis.close();
    }
    /**
     * 2、让每个手机每天只能发送三次，将验证码设置到redis中，并设置过期时间
     */
    public static void verifyCode(String phone){
        //连接redis
        Jedis jedis = new Jedis("192.168.1.100",6379);
        //拼接key
        //手机发送次数
        String countKey = "VerifyCode"+phone+":count";
        //验证码key
        String codeKey = "VerifyCode"+phone+":code";
        //每个手机每天只能发送三次
        String count = jedis.get(countKey);
        if (count == null){
            //是第一次发送
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count)<=2){
            //发送次数+1
            jedis.incr(countKey);
        }else if (Integer.parseInt(count)>2){
            //已经发送三次，不能再发送了
            System.out.println("今天已经发送三次");
//            jedis.close();
        }

        //发送验证码到redis中
        String vcode = getCode();
        jedis.setex(codeKey,120,vcode);
//        jedis.close();
    }
    /**
     * 1、生成六位数字验证码
     * @return
     */
    public static String getCode(){
        Random random = new Random();
        String code="";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }
}
