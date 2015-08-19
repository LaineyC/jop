package com.lite.jop.demo.api;

import com.lite.jop.demo.api.parameter.UserLoginRequest;
import com.lite.jop.demo.api.parameter.UserLoginResponse;
import com.lite.jop.platform.JopClient;
import com.lite.jop.platform.model.Error;
import java.util.List;

/**
 * ApiTest
 *
 * @author LaineyC
 */

public class ApiTest {

    public static void main(String[] args) throws Exception{
        JopClient client = new JopClient("http://localhost:8080/api", "test", "DGFG9072ED07449345C4048F344DB93F42AFBFFC");
        while(true){
            Thread.sleep(5000);
            long start = System.currentTimeMillis();
            UserLoginRequest request = new UserLoginRequest();
            request.setUsername("user1");
            request.setPassword("888888");
            UserLoginResponse response =  client.execute(request);
            List<Error> errors = response.getErrors();
            if(errors != null && !errors.isEmpty()){
                errors.forEach( e ->{
                    System.out.println("code:" + e.getCode());
                    System.out.println("level:" + e.getLevel());
                    System.out.println("message:" + e.getMessage());
                });
            }
            else {
                String sessionId = response.getBody();
                System.out.println("sessionId:" + sessionId);
            }
            long end = System.currentTimeMillis();
            //从客户端访问到接受服务端响应的时间 走局域网
            System.out.println("响应时间：" + (end - start) + "ms");
        }
    }

}
