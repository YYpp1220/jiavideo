<template>
    <div>
        <div class="row">
            <div class="col-xs-12">
                <!-- PAGE CONTENT BEGINS -->
                <h4 class="lighter">
                    <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
                    <router-link to="/business/course" class="pink">{{course.name}}</router-link>
                </h4>

                <h4 class="lighter">
                    <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
                    <router-link to="/business/chapter" class="pink">{{chapter.name}}</router-link>
                </h4>

                <div class="hr hr-18 hr-double dotted"></div>
            </div><!-- /.col -->
        </div><!-- /.row -->

        <p>
            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="sectionList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="sectionList"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>标题</th>
                <th>视频</th>
                <th>时长</th>
                <th>收费</th>
                <th>顺序</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>vod</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="section in sectionLists">
                    <td>{{section.id}}</td>
                    <td>{{section.title}}</td>
                    <td>{{section.video}}</td>
                    <td>{{section.time | formatSecond}}</td>
                    <td>{{SECTION_CHARGE | optionKV(section.charge)}}</td>
                    <td>{{section.sort}}</td>
                    <td>{{section.createdAt}}</td>
                    <td>{{section.updatedAt}}</td>
                    <td>{{section.vod}}</td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="edit(section)" class="btn btn-white btn-xs btn-info btn-round">
                            编辑
                        </button>
                        &nbsp;
                        <button @click="del(section.id)" class="btn btn-white btn-xs btn-warning btn-round">
                            删除
                        </button>
                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                                    data-position="auto">
                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                                                    <span class="blue">
                                                                        <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                                                    <span class="red">
                                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                                <div class="form-group">
                                    <label for="title" class="col-sm-2 control-label">标题</label>
                                    <div class="col-sm-10">
                                        <input v-model="section.title" type="text" class="form-control" id="title"
                                               placeholder="请输入标题">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">课程</label>
                                    <div class="col-sm-10">
                                        <p class="form-control-static">{{course.name}}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">大章</label>
                                    <div class="col-sm-10">
                                        <p class="form-control-static">{{chapter.name}}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">视频</label>
                                    <div class="col-sm-10">
                                        <big-file v-bind:suffixs="['mp4', 'avi']" v-bind:text="'上传大视频'" v-bind:after-upload="afterUpload" v-bind:input-id="'video-upload'" v-bind:use="FILE_USE.VIDEO.key"></big-file>
                                        <div v-show="section.video" class="row">
                                            <div class="col-md-9">
                                                <video v-bind:src="section.video" id="video" controls="controls"></video>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="time" class="col-sm-2 control-label">时长</label>
                                    <div class="col-sm-10">
                                        <input v-model="section.time" type="text" class="form-control" id="time"
                                               placeholder="请输入时长">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="charge" class="col-sm-2 control-label">收费</label>
                                    <div class="col-sm-10">
                                        <select v-model="section.charge" type="text" class="form-control" id="charge">
                                            <option v-for="o in SECTION_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="sort" class="col-sm-2 control-label">顺序</label>
                                    <div class="col-sm-10">
                                        <input v-model="section.sort" type="text" class="form-control" id="sort"
                                               placeholder="请输入顺序">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="vod" class="col-sm-2 control-label">vod</label>
                                    <div class="col-sm-10">
                                        <input v-model="section.vod" type="text" class="form-control" id="vod"
                                               placeholder="请输入vod">
                                    </div>
                                </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button @click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</template>

<script>
    import Pagination from "../../components/pagination.vue"
    import BigFile from "../../components/big-file.vue"

    export default {
        components: {Pagination, BigFile},

        name: "business-section",

        data: function () {
            return {
                section: {},
                sectionLists: [],
                SECTION_CHARGE: SECTION_CHARGE,
                FILE_USE: FILE_USE,
                course: {},
                chapter: {},
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if (Tool.isEmpty(chapter) || Tool.isEmpty(course)) {
                _this.$router.push("/welcome");
            }
            _this.chapter = chapter;
            _this.course = course;
            _this.sectionList(1);
            //sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.section = {};
                $("#form-modal").modal("show");
            },
            edit(section) {
                let _this = this;
                _this.section = $.extend({}, section);
                $("#form-modal").modal("show");
            },
            sectionList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/section/queryAll/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                            courseId: _this.course.id,
                            chapterId: _this.chapter.id,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.sectionLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (!Validator.require(_this.section.title, "标题")
                    || !Validator.require(_this.section.video, "视频")
                    || !Validator.length(_this.section.title, "标题", 1, "50")
                    || !Validator.length(_this.section.video, "视频", 1, "200")
                ) {
                    return;
                }
                _this.section.courseId = _this.course.id;
                _this.section.chapterId = _this.chapter.id;
                Loading.show();
                //let sectionStr = JSON.stringify(_this.section);
                _this.$http.post(process.env.VUE_APP_SERVER + "/business/admin/section/save", _this.section)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.sectionList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(sectionId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/business/admin/section/delete/" + sectionId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.sectionList(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
            },

            afterUpload (resp) {
                let _this = this;
                _this.section.video = resp[0].path;
                _this.getTime();
            },

            /**
             * 获取视频时长
             */
            getTime () {
                let _this = this;
                setTimeout(function () {
                    let ele = document.getElementById("video");
                    _this.section.time = parseInt(ele.duration, 10);
                }, 1000);
            }
        },
    }
</script>

<style scoped>
    video {
        width: 100%;
        height: auto;
        margin-top: 10px;
    }
</style>