/**
 * Created by LaineyC on 2015/7/31.
 */
;(function( $, window, undefined ) {

    function sign(params, secret, request){
        for(var key in request){
            var value = request[key];
            if(value != null && value != undefined){
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

    function JopClient(url, app, secret){
        var session;
        return {
            setSession: function(sessionId){
                session = sessionId;
            },
            execute: function(request, method, version){
                var callbacks = $.Callbacks();
                var params = {
                    app: app,
                    session: session,
                    method: method,
                    version: version
                }
                var signStr = sign(params, secret, request);
                params.sign = signStr;
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
                var _url = url + "?" + uri;
                var data = {};
                for(var key in request){
                    var value = request[key];
                    if(value != null && value != undefined){
                        data[key] = value;
                    }
                }
                data = $.toJSON(data);
                $.ajax({
                    type: "post",
                    url: _url,
                    contentType: "application/json;charset=utf-8",
                    data: data,
                    success: function(json){
                        for(var key in json){
                            response[key] = json[key];
                        }
                        callbacks.fire(response);
                        //callbacks.empty();
                    }
                });
                var response = {
                    done: function(done){
                        if(done){
                            callbacks.add(done);
                        }
                    }
                };
                return response;
            }
        }
    }

    window.JopClient = JopClient;

}( jQuery, window ));