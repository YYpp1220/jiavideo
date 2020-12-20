// 课程管理页面点击大章管理时，保存课程信息
SESSION_KEY_COURSE = "SESSION_KEY_COURSE";
// 大章管理页面点击小节管理时，保存大章信息
SESSION_KEY_CHAPTER = "SESSION_KEY_CHAPTER";
// 保存用户登录信息的key
SESSION_KEY_LOGIN_USER = "SESSION_KEY_LOGIN_USER";

SessionStorage = {
    get: function(key) {
        let v = sessionStorage.getItem(key);
        if (v && typeof (v) !== 'undefined' && v !== 'undefined') {
            return JSON.parse(v);
        }
    },

    set: function(key, value){
        sessionStorage.setItem(key, JSON.stringify(value));
    },

    remove: function(key) {
        sessionStorage.removeItem(key);
    },

    clearAll: function(key) {
        sessionStorage.clear();
    }
};