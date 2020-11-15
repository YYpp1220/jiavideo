<template>
    <div>
        <button type="button" @click="selectFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit red2"></i>
            {{text}}
        </button>
        <input class="hidden" type="file" ref="file" @change="uploadFile()" v-bind:id="inputId + '-input'">
    </div>
</template>

<script>
    export default {
        name: 'file',
        props: {
            text: {
                default: "上传文件"
            },
            inputId: {
                default: "file-upload"
            },
            suffixs: {
                default: []
            },
            afterUpload: {
                type: Function,
                default: null
            },
        },
        data: function () {
            return {

            }
        },
        methods: {
            uploadFile() {
                let _this = this;
                let formData = new window.FormData();
                let file = _this.$refs.file.files[0];

                // 判断上传图片类型
                let suffixs = _this.suffixs;
                let fileName = file.name;
                let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
                let validateSuffix = false;
                for (const suffixCondition of suffixs) {
                    if (suffixCondition.toLowerCase() === suffix) {
                        validateSuffix = true;
                        break;
                    }
                }
                if (!validateSuffix) {
                    Toast.warning("图片格式不正确！只支持上传：" + suffixs.join(","));
                    $("#" + _this.inputId + "-input").val("");
                    return;
                }

                // key，必须和后端方法参数名称一致
                formData.append('file', file);
                Loading.show();
                _this.$http.post(process.env.VUE_APP_SERVER + '/file/admin/upload/image', formData)
                    .then(response => {
                        Loading.hide();
                        let resp = response.data["generalClass"];
                        _this.afterUpload(resp);
                        $("#" + _this.inputId + "-input").val("");
                    })
            },

            selectFile() {
                let _this = this;
                $("#" + _this.inputId + "-input").trigger("click");
            },
        }
    }
</script>