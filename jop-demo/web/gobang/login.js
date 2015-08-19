/**
 * Created by LaineyC on 2015/7/31.
 */
;(function( $, window, undefined ) {

    $(function(){
        var client = new JopClient("/api", "gobang", "971B1EC7563DA71B143EDE15D1E34AAC420C9942");
        var $username = $("#username");
        var $password = $("#password");
        var $loginBtn = $("#login-btn");
        $loginBtn.click(function(){
            $message.empty();
            var username = $username.val();
            var password = $password.val();
            if($.trim(username).length < 1 ){
                $message.text("请填写用户名");
                return;
            }
            if($.trim(password).length < 1 ){
                $message.text("请填写密码");
                return;
            }
            client.execute({
                username: username,
                password: password
            },"player.login").done(function(response){
                if(response.errors.length){
                    $message.text(response.errors[0].message);
                }
                else{
                    //$.cookie("session", null);
                    $.cookie("session", response.body, { expires: 7, path: "/gobang"});
                    window.location = "/gobang/gobang.html";
                }
            });
        });

        var $message = $("#message");
    });

}( jQuery, window ));