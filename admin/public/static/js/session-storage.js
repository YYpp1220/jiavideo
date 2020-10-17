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