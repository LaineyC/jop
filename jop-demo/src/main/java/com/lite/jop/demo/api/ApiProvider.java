package com.lite.jop.demo.api;

import com.lite.jop.demo.api.domian.User;
import com.lite.jop.demo.api.parameter.UserLoginRequest;
import com.lite.jop.demo.api.parameter.UserLoginResponse;
import com.lite.jop.demo.api.parameter.UserLogoutRequest;
import com.lite.jop.demo.api.parameter.UserLogoutResponse;
import com.lite.jop.demo.security.SimpleSession;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.config.Provider;
import com.lite.jop.platform.config.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * ApiProvider
 *
 * @author LaineyC
 */

@Provider
public class ApiProvider {

    private static List<User> userData = new ArrayList<>();

    private static User find(String username, String password){
        for(User u : userData){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }

    static {
        for(String username: new String[]{"user1", "user2"}){
            User user = new User();
            user.setUsername(username);
            user.setPassword("888888");
            userData.add(user);
        }
    }

    @Service(method = "user.login", needSession = false)
    public UserLoginResponse userLogin(UserLoginRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        String username = request.getUsername();
        String password = request.getPassword();
        //从request取得参数 硬编码 假设已从数据库取数据
        User user = find(username, password);
        String uuid = UUID.randomUUID().toString();
        SimpleSession simpleSession = new SimpleSession();
        simpleSession.setAttribute("user", user);
        jopServiceContext.addSession(uuid, simpleSession);
        //登陆把seesionId传给客户端
        return new UserLoginResponse(uuid);
    }

    @Service(method = "user.logout")
    public UserLogoutResponse userLogout(UserLogoutRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.removeSession();
        User user = simpleSession.getAttribute("user");
        return new UserLogoutResponse();
    }

    //2.0版本的注销方法
    @Service(method = "user.logout", version = "2.0")
    public UserLogoutResponse userLogout$2_0(UserLogoutRequest request){
        JopServiceContext jopServiceContext = JopServiceContext.getContext();
        SimpleSession simpleSession = jopServiceContext.removeSession();
        User user = simpleSession.getAttribute("user");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return new UserLogoutResponse(sdf.format(new Date()));
    }

}
