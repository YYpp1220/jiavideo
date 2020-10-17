<template>
    <div>
        <div class="row">
            <div class="col-xs-12">
                <!-- PAGE CONTENT BEGINS -->
                <h4 class="lighter">
                    <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
                    <router-link to="/business/course" class="pink">{{course.name}}</router-link>
                </h4>

                <div class="hr hr-18 hr-double dotted"></div>
            </div><!-- /.col -->
        </div><!-- /.row -->

        <p>
            <router-link to="/business/course" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-arrow-left red2"></i>
                返回课程
            </router-link>

            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="chapterList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="chapterList"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>视频名称</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="chapter in chapterLists">
                <td>{{chapter.id}}</td>
                <td>{{chapter.name}}</td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                            小节
                        </button>

                        <button @click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                            编辑
                        </button>

                        <button @click="del(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
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
                                <label for="videoName" class="col-sm-2 control-label">视频名称</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.name" type="text" class="form-control" id="videoName"
                                           placeholder="请输入视频名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">视频Id</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static">{{course.name}}</p>
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

    export default {
        components: {Pagination},

        name: "chapter",

        data: function () {
            return {
                chapter: {},
                chapterLists: [],
                course: {},
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            let course = SessionStorage.get("course") || {};
            if (Tool.isEmpty(course)){
                _this.$router.push("/welcome");
            }
            _this.course = course;
            _this.chapterList(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("business-chapter-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.chapter = {};
                $("#form-modal").modal("show");
            },
            edit(chapter) {
                let _this = this;
                _this.chapter = $.extend({}, chapter);
                $("#form-modal").modal("show");
            },
            chapterList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/chapter/queryAll/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                            courseId: _this.course.id,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.chapterLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (!Validator.require(_this.chapter.name, "名称")
                    || !Validator.length(_this.chapter.courseId, "课程Id", 1, 8)){
                    return
                }
                _this.chapter.courseId = _this.course.id;
                Loading.show();
                //let chapterStr = JSON.stringify(_this.chapter);
                _this.$http.post(process.env.VUE_APP_SERVER + "/business/admin/chapter/save", _this.chapter)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.chapterList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(chapterId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/business/admin/chapter/delete/" + chapterId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.chapterList(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
                /*Swal.fire({
                    title: '确认删除吗?',
                    text: "删除后不可恢复!确认删除？",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '确认!'
                }).then((result) => {
                    if (result.isConfirmed) {
                        Loading.show();
                        _this.$http.delete("http://localhost:10010/business/admin/chapter/delete/" + chapterId)
                            .then((response) => {
                                Loading.hide();
                                console.log(response);
                                if (response.statusText === "No Content"){
                                    _this.chapterList(1);
                                    Toast.success("删除成功！")
                                }
                            });
                    }
                });*/
            },
            toSection(chapter) {
                let _this = this;
                SessionStorage.set("chapter", chapter);
                _this.$router.push("/business/section");
            }
        },
    }
</script>