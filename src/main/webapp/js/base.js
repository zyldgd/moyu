var MOYU_URL = {
    webapi: {
        user: "webapi/user",
        messages: "webapi/messages",
        contacts: "webapi/contacts",
        groups: "webapi/groups",
        eventSource: "webapi/eventSource",
        fileResource: "webapi/fileResource",
        login: "webapi/login"
    },
    path: {
        login: "login.html",
        chat: "vtest.html"
    }
};


String.prototype.format = function (args) {
    let result = this;
    if (arguments.length > 0) {
        if (arguments.length === 1 && typeof (args) == "object") {
            for (let key in args) {
                if (args[key] !== undefined) {
                    let reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        } else {
            for (let i = 0; i < arguments.length; i++) {
                if (arguments[i] !== undefined) {
                    let reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
};

function initSys() {
    L2Dwidget.init({
        "model": {
            jsonPath: "https://unpkg.com/live2d-widget-model-tororo@1.0.5/assets/tororo.model.json",
            "scale": 1
        },
        "display": {
            "position": "right",
            "width": 150,
            "height": 300,
            "hOffset": 0,
            "vOffset": -45
        },
        "mobile": {
            "show": true,
            "scale": 0.5
        },
        "react": {
            "opacityDefault": 1,
            "opacityOnHover": 0.2
        }
    });
}

function setCookie(key, value) {
    let expdate = new Date();   //初始化时间
    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);   //时间
    document.cookie = key + "=" + value + ";expires=" + expdate.toString() + ";path=/";

    //即document.cookie= name+"="+value+";path=/";   时间可以不要，但路径(path)必须要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！~
}

function getCookie(key) {
    if (document.cookie.length > 0) {
        let c_start = document.cookie.indexOf(key + "=");
        if (c_start !== -1) {
            c_start = c_start + key.length + 1;
            let c_end = document.cookie.indexOf(";", c_start);
            if (c_end === -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return null;
}

function checkCookie() {
    let username = getCookie('username');
    if (username != null && username !== "") {
        alert('Welcome again ' + username + '!')
    } else {
        username = prompt('Please enter your name:', "");
        if (username != null && username !== "") {
            setCookie('username', username, 365)
        }
    }
}

function dateFormat(fmt, date) {
    let ret;
    let opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length === 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
    }
    return fmt;
}


function axiosVerify() {
    if (userId == null) return;

    axios({
        method: "GET",
        url: 'webapi/login/test' + userId,
        headers: {
            // 'Content-Type': 'application/x-www-form-urlencoded'
            'userId': userCache.userId,
            'token': userCache.token
        }
    }).then(function (response) {

    }).catch(function (error) {
        console.log(error);

    });
}


/**
 * @param userCache {UserCache}
 * @param userId {String}
 */
function axiosGetUser(userCache, userId) {
    if (userId == null) return;
    axios({
        method: "GET",
        url: MOYU_URL.webapi.user + '?userId=' + userId,
        headers: {
            // 'Content-Type': 'application/x-www-form-urlencoded'
            'userId': userCache.userId,
            'token': userCache.token
        }
    }).then(function (response) {
        if (response.status === 200) {
            userCache.setUsersMap([User.builder(response.data)]);
            if (userId === userCache.userId) {
                userCache.selfUser = User.builder(response.data);
            }
        }
    }).catch(function (error) {
        console.log(error);
    });
}


/**
 * @param userCache {UserCache}
 */
function axiosGetContacts(userCache) {
    axios({
        method: "GET",
        url: MOYU_URL.webapi.contacts,
        headers: {
            'userId': userCache.userId,
            'token': userCache.token
        }
    }).then(function (response) {
        if (response.status === 200) {
            userCache.setContactMap(response.data);
        }
    }).catch(function (error) {
        console.log(error);
    });
}


/**
 * @param userCache {UserCache}
 * @param message {Message}
 * @param callback {Function}
 */
function axiosSetMessage(userCache, message, callback) {
    let data = {
        content: message.content,
        date: message.date,
        time: message.time,
        from: message.from,
        to: message.to,
        contactType: message.contactType,
        messageHash: message.messageHash,
    };
    console.log(data);
    axios({
        method: "POST",
        url: MOYU_URL.webapi.messages,
        headers: {
            'userId': userCache.userId,
            'token': userCache.token
        },
        data: data
    }).then(function (response) {
        if (response.status === 200) {
            callback();
        }
    }).catch(function (error) {
        console.log(error);
    });
}



function initEventSource(userId, token, getNewMessageCallBack) {
    if (typeof (EventSource) !== "undefined") {
        let source = new EventSource(MOYU_URL.webapi.eventSource + "?userId=" + userId + "&token=" + token);

        // 当通往服务器的连接被打开
        source.onopen = function (event) {
            console.log("eventSource opened!");
        };

        // 当接收到消息。只能是事件名称是 message
        source.onmessage = function (event) {
            let message = JSON.parse(event.data);
            app_moyu.userCache.setMessages([Message.buildFrom(message)]);

        };

        //可以是任意命名的事件名称
        // source.addEventListener('newMessage', function(event) {
        //
        // });

        // 当错误发生
        source.onerror = function (event) {
            console.log(event);
        };
        return true;
    } else {
        alert("Sorry, your browser does not support server-sent events");
        return false;
    }
}