/**
 * 数组过滤器
 * @param object 传入对象，例如：SECTION_CHARGE = {CHARGE:{key:'C', value:'收费'}, FREE:{key:'F', value:'免费'}};
 * @param key 传入key 例如：C
 * @returns {string} 返回结果 例如：收费
 */
let optionKV = (object, key) => {
    if (!object || !key) {
        return "";
    }
    let result = "";
    for (let enums in object) {
        // noinspection JSUnfilteredForInLoop
        if (key === object[enums]["key"]) {
            // noinspection JSUnfilteredForInLoop
            result = object[enums]["value"];
        }
    }
    return result;
};

/**
 * 数组过滤器
 * @param list 传入数组，例如：[{key:'C', value:'收费'}, {key:'F', value:'免费'}]
 * @param key key 例如：C
 * @returns {string} 返回值 例如：收费
 */
let optionKVList = (list, key) => {
    if (!list || !key) {
        return "";
    }
    let result = "";
    for (let i = 0; i < list.length; i++) {
        if (key === list[i]["key"]) {
            result = list[i]["value"];
        }
    }
    return result;
};

/**
 * 时间格式化
 * @param value 传入时间，单位秒
 * @returns {string} 返回时间字符串
 */
let formatSecond = (value) => {
    value = value || 0;
    // 秒
    let second = parseInt(value, 10);
    // 分
    let minute = 0;
    // 时
    let hour = 0;
    if (second > 60) {
        // 大于60秒的时候才做转换
        minute = Math.floor(second / 60);
        second = Math.floor(second % 60);
        if (minute > 60) {
            // 大于60分的时候才做转换
            hour = Math.floor(minute / 60);
            minute = Math.floor(minute % 60);
        }
    }else {
        // 小于60秒时，直接显示，不需要额外处理
    }
    let result = "" + PrefixInteger(second, 2) + "";
    // 拼上分钟
    result = "" + PrefixInteger(minute, 2) + ":" + result;
    // 拼上小时
    result = "" + PrefixInteger(hour, 2) + ":" + result;
    // 返回时间字符串
    return result;
};

/**
 * 格式化指定长度，前面补0
 * @param num 传入时，分，秒
 * @param length 长度
 * @returns {string} 返回参数
 * @constructor MyMrDiao
 */
function PrefixInteger(num, length) {
    return (Array(length).join("0") + num).slice(-length);
}

export default {
    optionKV,
    formatSecond
}