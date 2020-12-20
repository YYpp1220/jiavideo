// 用户登录记住功能key
LOCAL_KEY_REMEMBER_USER = "LOCAL_KEY_REMEMBER_USER";

LocalStorage = {
    get: function (key) {
        let v = localStorage.getItem(key);
        if (v && typeof (v) !== "undefined" && v !== "undefined") {
            return JSON.parse(v);
        }
    },

    set: function (key, value) {
        localStorage.setItem(key, JSON.stringify(value))
    },

    remove: function (key) {
        localStorage.removeItem(key);
    },

    clearAll: function () {
        localStorage.clear();
    }
};