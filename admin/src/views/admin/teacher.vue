<template>
    <div>
        <p>
            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="teacherList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="teacherList"></pagination>
        <div class="row">
            <div v-for="teacher in teacherLists" class="col-sm-3">
                <div>
					<span class="profile-picture">
						<img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty" src="/static/image/lecturerAvatar/头像1.jpg" v-bind:title="teacher.intro"/>
						<img v-show="teacher.image" class="editable img-responsive editable-click editable-empty" v-bind:src="teacher.image" v-bind:title="teacher.intro"/>
					</span>

                    <div class="space-4"></div>

                    <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
                        <div class="inline position-relative">
                            <a href="#" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                                <i class="ace-icon fa fa-circle light-green"></i>
                                &nbsp;
                                <span class="white">{{teacher.position}}</span>
                            </a>
                        </div>
                    </div>
                </div>

                <div class="space-6"></div>

                <div class="profile-contact-info">
                    <div class="profile-contact-links align-center">
                        <div class="text-center">
                            <a href="#" class="text-info bigger-110" v-bind:title="teacher.motto">
                                <i class="ace-icon fa fa-user"></i>
                                {{teacher.name}}【{{teacher.nickname}}】
                            </a>
                        </div>
                    </div>

                    <div class="space-6"></div>

                    <div class="profile-social-links align-center">
                        <button @click="edit(teacher)" class="btn btn-white btn-xs btn-info btn-round">
                            编辑
                        </button>
                        &nbsp;
                        <button @click="del(teacher.id)" class="btn btn-white btn-xs btn-warning btn-round">
                            删除
                        </button>
                    </div>
                </div>

                <div class="hr hr16 dotted"></div>
            </div>
        </div>

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
                                <label for="name" class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.name" type="text" class="form-control" id="name"
                                           placeholder="请输入姓名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="nickname" class="col-sm-2 control-label">昵称</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.nickname" type="text" class="form-control" id="nickname"
                                           placeholder="请输入昵称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">头像</label>
                                <div class="col-sm-10">
                                    <file v-bind:suffixs="['jpg', 'jpeg', 'png']" v-bind:text="'上传头像'" v-bind:after-upload="afterUpload" v-bind:input-id="'image-upload'" v-bind:use="FILE_USE.TEACHER.key"></file>
                                    <div v-show="teacher.image" class="row">
                                        <div class="col-md-4">
                                            <img v-bind:src="teacher.image" class="img-responsive" alt="头像">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="position" class="col-sm-2 control-label">职位</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.position" type="text" class="form-control" id="position"
                                           placeholder="请输入职位">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="motto" class="col-sm-2 control-label">座右铭</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.motto" type="text" class="form-control" id="motto"
                                           placeholder="请输入座右铭">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="intro" class="col-sm-2 control-label">简介</label>
                                <div class="col-sm-10">
                                    <textarea v-model="teacher.intro" type="text" class="form-control" id="intro"
                                              placeholder="请输入简介" rows="5"></textarea>
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
    import File from "../../components/file";

    export default {
        components: {Pagination, File},

        name: "business-teacher",

        data: function () {
            return {
                teacher: {},
                teacherLists: [],
                FILE_USE: FILE_USE,
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.teacherList(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("business-teacher-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.teacher = {};
                $("#form-modal").modal("show");
            },
            edit(teacher) {
                let _this = this;
                _this.teacher = $.extend({}, teacher);
                $("#form-modal").modal("show");
            },
            teacherList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/teacher/queryAllVice/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.teacherLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (!Validator.require(_this.teacher.name, "姓名")
                    || !Validator.length(_this.teacher.name, "姓名", 1, "50")
                    || !Validator.length(_this.teacher.nickname, "昵称", 1, "50")
                    || !Validator.length(_this.teacher.image, "头像", 1, "100")
                    || !Validator.length(_this.teacher.position, "职位", 1, "50")
                    || !Validator.length(_this.teacher.motto, "座右铭", 1, "50")
                    || !Validator.length(_this.teacher.intro, "简介", 1, "500")
                ) {
                    return;
                }
                Loading.show();
                //let teacherStr = JSON.stringify(_this.teacher);
                _this.$http.post(process.env.VUE_APP_SERVER + "/business/admin/teacher/save", _this.teacher)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.teacherList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(teacherId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/business/admin/teacher/delete/" + teacherId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.teacherList(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
            },

            afterUpload (resp) {
                let _this = this;
                _this.teacher.image = resp[0].path;
            }
        },
    }
</script>