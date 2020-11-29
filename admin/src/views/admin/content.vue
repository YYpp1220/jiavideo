<template>
    <div>
        <h4 class="lighter">
            <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
            <router-link to="/business/course" class="pink">{{course.name}}</router-link>
        </h4>
        <hr>

        <file v-bind:input-id="'content-file-input'"
              v-bind:text="'文件上传'"
              v-bind:suffixs="['jpg', 'jpeg', 'png', 'mp4', 'avi']"
              v-bind:use="FILE_USE.COURSE.key"
              v-bind:after-upload="afterUploadContentFile"></file>
        <br>
        <table id="file-table" class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>名称</th>
                <th>地址</th>
                <th>大小</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(f, i) in files" v-bind:key="f.id">
                <td>{{f.name}}</td>
                <td>{{f.url}}</td>
                <td>{{f.size | formatFileSize}}</td>
                <td>
                    <button @click="delFile(f)" class="btn btn-white btn-xs btn-warning btn-round">
                        <i class="ace-icon fa fa-times red2"></i>
                        删除
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-lg-12">
                    {{saveContentLabel}}
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-12">
                    <div id="content"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-12">
                    {{saveContentLabel}}
                </div>
            </div>
        </form>
        <p>
            <button type="button" class="btn btn-white btn-info btn-round" @click="saveContent()">
                <i class="ace-icon fa fa-plus blue"></i>
                保存
            </button>
            &nbsp;
            <router-link to="/business/course" type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal" aria-label="Close">
                <i class="ace-icon fa fa-times"></i>
                返回课程
            </router-link>
        </p>
    </div>
</template>

<script>
    import File from "../../components/file"
    export default {
        components: {File},
        name: "business-course-content",
        data: function () {
            return {
                course: {},
                FILE_USE: FILE_USE,
                saveContentLabel: "",
                files: [],
                saveContentInterval: {},
            }
        },

        mounted: function() {
            let _this = this;
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if (Tool.isEmpty(course)) {
                _this.$router.push("/welcome");
            }
            _this.course = course;
            _this.init();
            _this.$parent.activeSidebar("business-course-sidebar")
        },

        destroyed: function() {
            let _this = this;
            console.log("组件销毁");
            clearInterval(_this.saveContentInterval);
        },

        methods: {
            /**
             * 打开内容编辑框
             */
            init() {
                let _this = this;
                let course = _this.course;
                let id = course.id;
                $("#content").summernote({
                    focus: true,
                    height: 300
                });

                // 先清空历史文本
                $("#content").summernote('code', '');
                _this.saveContentLabel = "";
                _this.queryContentFiles();
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + '/business/admin/course/findContent/' + id)
                    .then(response => {
                        Loading.hide();
                        let content = response.data["generalClass"];
                        if (response.statusText === 'OK') {
                            if (content) {
                                $("#content").summernote('code', content[0].content);
                            }
                            // 定时自动保存
                            _this.saveContentInterval = setInterval(function() {
                                _this.saveContent();
                            }, 10000);
                        }else {
                            Toast.warning(response.message);
                        }
                    });
            },

            /**
             * 保存内容
             */
            saveContent() {
                let _this = this;
                let content = $("#content").summernote('code');
                _this.$http.post(process.env.VUE_APP_SERVER + '/business/admin/course/saveContent', {
                    id: _this.course.id,
                    content: content
                }).then(response => {
                    Loading.hide();
                    if (response.statusText === 'OK') {
                        /*Toast.success("内容保存成功");*/
                        let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
                        _this.saveContentLabel = "最后保存时间：" + now;
                    } else {
                        Toast.warning(response.message);
                    }
                });
            },

            /**
             * 加载文件内容列表
             */
            queryContentFiles () {
                let _this = this;
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/courseContentFile/queryAll/",
                    {
                        params: {
                            courseId: _this.course.id
                        }
                    }).then((response) => {
                    if (response.statusText === "OK") {
                        _this.files = response.data["generalClass"];
                    }
                });
            },

            /**
             * 上传文件内容后，保存文件内容记录
             * @param response 组件传入参数
             */
            afterUploadContentFile (response) {
                let _this = this;
                console.log("开始保存文件记录");
                let file = response[0];
                let contentFile = {};
                contentFile.courseId = _this.course.id;
                contentFile.url = file.path;
                contentFile.size = file.size;
                contentFile.name = file.name;
                _this.$http.post(process.env.VUE_APP_SERVER + "/business/admin/courseContentFile/save", contentFile)
                    .then((resp) => {
                        if (resp.statusText === "OK") {
                            Toast.success("文件上传成功");
                            _this.files.push(resp.data["generalClass"]);
                        }
                    })
            },

            /**
             * 删除文件内容记录
             * @param f 文件集合
             */
            delFile (f) {
                let _this = this;
                Confirm.show("删除文件内容后不可恢复，确认删除？", function () {
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/business/admin/courseContentFile/delete/" + f.id)
                        .then((response) => {
                            if (response.statusText === "OK") {
                                Toast.success("删除文件成功");
                                Tool.removeObj(_this.files, f);
                            }
                        })
                })
            },
        }
    }
</script>