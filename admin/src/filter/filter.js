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

export default {
    optionKV
}