Tool = {
    /**
     * 空校验，null或者""都返回true
     * @param obj 传入参数
     * @returns {boolean} 返回布尔值
     */
    isEmpty: function (obj) {
        if ((typeof obj == 'string')) {
            return !obj || obj.replace(/\s+/g, "") == ""
        }else {
            return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0);
        }
    },

    /**
     * 非空校验
     * @param obj 校验参数
     */
    isNotEmpty: function (obj) {
        return !this.isEmpty(obj);
    },

    /**
     * 长度校验
     * @param str 校验参数
     * @param min 最大值
     * @param max 最小值
     * @returns {boolean} 返回布尔值
     */
    isLength: function (str, min, max) {
        return $.trim(str).length >= min && $.trim(str).length <= max;
    },

    /**
     * 时间格式化，date为空时取当前时间
     * @param format 时间格式
     * @param date 格式化时间
     * @returns {*} 返回格式化时间
     */
    dateFormat: function (format, date) {
        let result;
        if (!date){
            date = new Date();
        }
        const option = {
            "y+": date.getFullYear().toString(),        // 年
            "M+": (date.getMonth() + 1).toString(),     // 月
            "d+": date.getDate().toString(),            // 日
            "h+": date.getHours().toString(),           // 时
            "m+": date.getMinutes().toString(),         // 分
            "s+": date.getSeconds().toString()          // 秒
        };
        for (let i in option){
            result = new RegExp("("+i+")").exec(format);
            if (result){
                format = format.replace(result[1], (result[1].length == 1) ? (option[i]) : (option[i].padStart(result[1].length, "0")))
            }
        }
        return format;
    },

    /**
     * 移除对象数组中的对象
     * @param array 移除的数组对象
     * @param obj 移除的对象
     * @returns {number} 返回的数据
     */
    removeObj: function (array, obj) {
        let index = -1;
        for (let i = 0; i < array.length; i++) {
            if (array[i] === obj) {
                array.splice(i, 1);
                index = i;
                break;
            }
        }
        return index;
    },

    /**
     * 10进制转62进制
     * @param number 转数字
     * @returns {string} 返回转之后的数据
     * @private 私有
     */
    _10to62: function (number) {
        let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';
        let radix = chars.length;
        let arr = [];
        do {
            let mod = number % radix;
            number = (number - mod) / radix;
            arr.unshift(chars[mod]);
        }while (number);
        return arr.join('');
    },

    /**
     * 保存登录用户的信息
     * @param loginUser 登录用户
     */
    setLoginUser: function (loginUser) {
        SessionStorage.set(SESSION_KEY_LOGIN_USER, loginUser);
    },

    /**
     * 获取登录用户的信息
     */
    getLoginUser: function () {
        return SessionStorage.get(SESSION_KEY_LOGIN_USER) || {};
    },

    /**
     * 随机生成[len]长度的[radix]进制数
     * @param len
     * @param radix 默认62
     * @returns {string}
     */
    uuid: function (len, radix) {
        let chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        let uuid = [];
        radix = radix || chars.length;

        for (let i = 0; i < len; i++) {
            uuid[i] = chars[0 | Math.random() * radix];
        }

        return uuid.join('');
    },

    /**
     * 查找是否有权限
     * @param id 资源id
     */
    hasResource: function (id) {
        let _this = this;
        let resources = _this.getLoginUser().resources;
        if (_this.isEmpty(resources)) {
            return false;
        }
        for (let resource of resources) {
            if (id === resource.id) {
                return true;
            }
        }
        return false;
    },
};