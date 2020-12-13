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
                default: "上传大文件"
            },
            inputId: {
                default: "file-upload"
            },
            suffixs: {
                default: []
            },
            use: {
                default: ""
            },
            shardSize: {
                default: 10 * 1024 * 1024
            },
            url: {
                default: "ossAppend"
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

                // 生成文件标识，判断多次上传的是不是同一个文件
                let key = hex_md5(file.name + file.size + file.type);
                let key10 = parseInt(key, 16);
                let key62 = Tool._10to62(key10);

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

                // 文件分片
                // 以20MB为一个分片
                // let shardSize = 10 * 1024 * 1024;
                let shardSize = _this.shardSize;
                // 分片索引 1表示第一个分片
                let shardIndex = 1;
                let size = file.size;
                // 总分片数
                let shardTotal = Math.ceil(size / shardSize);

                let param = {
                    'shardIndex': shardIndex,
                    'shardSize': shardSize,
                    'shardTotal': shardTotal,
                    'use': _this.use,
                    'name': file.name,
                    'suffix': suffix,
                    'size': size,
                    'key': key62
                };

                _this.check(param);
            },

            /**
             * 检查文件状态，是否已经上传过？上传到第几个分片？
             * @param param
             */
            check(param) {
                let _this = this;
                _this.$http.get(process.env.VUE_APP_SERVER + '/file/admin/upload/check/' + param.key )
                    .then((response) => {
                        let result = response.data["generalClass"];
                        if (response.statusText === 'OK') {
                            if (!result) {
                                param.shardIndex = 1;
                                console.log("没有找到文件记录！从分片1开始上传");
                                _this.upload(param);
                            } else if (result[0].shardIndex === result[0].shardTotal) {
                                // 已上传分片 = 分片总数，说明文件已全部上传完成，不需要再上传
                                Toast.success("文件极速秒传成功");
                                _this.afterUpload(result);
                                $("#" + _this.inputId + "-input").val("");
                            } else {
                                param.shardIndex = result[0].shardIndex + 1;
                                console.log("找到文件记录，从分片" + param.shardIndex + "开始上传");
                                _this.upload(param);
                            }
                        } else {
                            Toast.warning("文件上传失败");
                            $("#" + _this.inputId + "-input").val("");
                        }
                    });
            },

            upload: function (param) {
                let _this = this;
                let shardTotal = param.shardTotal;
                let shardIndex = param.shardIndex;
                let shardSize = param.shardSize;
                let fileShard = _this.getFileShard(shardIndex, shardSize);
                // 将文件转为base64进行传输
                let fileReader = new FileReader();
                Progress.show(parseInt((shardIndex - 1) * 100 / shardTotal) + "%");
                fileReader.onload = function (e) {
                    let base64 = e.target.result;
                    param.shard = base64;

                    _this.$http.post(process.env.VUE_APP_SERVER + '/file/admin/upload/' + _this.url, param)
                        .then(response => {

                            let resp = response.data["generalClass"];
                            Progress.show(parseInt(shardIndex * 100 / shardTotal) + "%");
                            if (shardTotal > shardIndex) {
                                // 上传下一个分片
                                param.shardIndex = param.shardIndex + 1;
                                _this.upload(param);
                            } else {
                                Progress.hide();
                                _this.afterUpload(resp);
                                $("#" + _this.inputId + "-input").val("");
                            }
                        });
                };
                fileReader.readAsDataURL(fileShard);
            },

            getFileShard: function (shardIndex, shardSize) {
                let _this = this;
                let file = _this.$refs.file.files[0];
                // 当前分片的起始位置
                let fragmentationStart = (shardIndex - 1) * shardSize;
                // 当前分片的结束位置
                let fragmentationEnd = Math.min(file.size, fragmentationStart + shardSize);
                // 从文件中截取当前的分片数据
                let fileShard = file.slice(fragmentationStart, fragmentationEnd);
                return fileShard;
            },

            selectFile() {
                let _this = this;
                $("#" + _this.inputId + "-input").trigger("click");
            },
        }
    }
</script>