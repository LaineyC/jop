/**
 * Created by LaineyC on 2015/7/31.
 */
;(function( $, window, undefined ) {

    function sign(params, secret, request){
        for(var key in request){
            var value = request[key];
            if(key != "serviceMethod" && value != null && value != undefined){
                params[key] = value;
            }
        }
        var keys = [];
        var i = 0;
        for(var key in params){
            var value = params[key];
            if( value != null && value != undefined){
                keys[i] = key;
                i++;
            }
        }
        keys = keys.sort();
        var str = secret;
        for(i= 0 ; i < keys.length ; i++ ){
            var key = keys[i];
            var value = params[key];
            if( value != null && value != undefined){
                str += (key + value);
            }
        }
        str += secret;
        str = $.encoding.digests.hexSha1Str(str);
        return str;
    }

    $(function(){
        var $app = $("#app");

        var $appSelect = $("#app-select");
        $appSelect.change(function(){
            var values = $(this).val().split("-");
            $app.val(values[0]);
            $secret.val(values[1]);
        });

        var $secret = $("#secret");
        var $session = $("#session");
        var $method = $("#method");

        var $methodSelect = $("#method-select");
        var methods = {
            login:{
                method:"user.login",
                version:"",
                request:{
                    username:"user1",
                    password:"888888"
                }
            },
            logout:{
                method:"user.logout",
                version:"",
                request:{}
            },
            logout2:{
                method:"user.logout",
                version:"2.0",
                request:{}
            }
        };
        $methodSelect.change(function(){
            var rule = $(this).val();
            var param = methods[rule];
            if(param){
                $method.val(param.method);
                $version.val(param.version);
                $request.val( $.toJSON(param.request));
            }
            else{
                $method.val("");
                $version.val("");
                $request.val("");
            }
        });

        var $version = $("#version");
        var $sign = $("#sign");

        var $signBtn = $("#sign-btn");
        $signBtn.click(function(){
            var params = {
                app: $app.val(),
                session: $session.val(),
                method: $method.val(),
                version: $version.val()
            }
            var request = $request.val()
            request = request == "" ?  $.evalJSON("{}") : $.evalJSON($request.val());
            var signStr = sign(params, $secret.val(), request);
            $sign.val(signStr);
        });

        var $request = $("#request");
        var $response = $("#response");

        var $runBtn = $("#run-btn");
        $runBtn.click(function(){
            var params = {
                app: $app.val(),
                session: $session.val(),
                method: $method.val(),
                version: $version.val(),
                sign: $sign.val()
            }
            var uri = "";
            var f;
            for(var key in params){
                var value = params[key];
                if(value != null && value != undefined){
                    if(f){
                        uri += "&";
                    }
                    else{
                        f = true;
                    }
                    uri += key + "=" + value;
                }
            }
            var url = "/api?" + uri;
            var data = {};
            var request = $.evalJSON($request.val());
            for(var key in request){
                var value = request[key];
                if(value != null && value != undefined){
                    data[key] = value;
                }
            }
            data = $.toJSON(data);
            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json;charset=utf-8",
                data: data,
                success: function(json){
                    $response.val($.toJSON(json));
                }
            });
        });

    });

}( jQuery, window ));