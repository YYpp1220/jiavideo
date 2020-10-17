<template>
    <div>
        <p>
            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="courseList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="courseList"></pagination>

        <div class="row">
            <div v-for="course in courseLists" class="col-md-4">
                <div class="thumbnail search-thumbnail">
                    <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
                    <img v-show="course.image" class="media-object" v-bind:src="course.image" />
                    <div class="caption">
                        <div class="clearfix">
                            <span class="pull-right label label-primary info-label">{{COURSE_LEVEL | optionKV(course.level)}}</span>
                            <span class="pull-right label label-primary info-label">{{COURSE_CHARGE | optionKV(course.charge)}}</span>
                            <span class="pull-right label label-primary info-label">{{COURSE_STATUS | optionKV(course.status)}}</span>
                        </div>

                        <h3 class="search-title">
                            <a href="#" class="blue">{{course.name}}</a>
                        </h3>
                        <p>
                            <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>
                        </p>
                        <p>{{course.summary}}</p>
                        <p>
                            <span class="badge badge-info">{{course.id}}</span>
                            <span class="badge badge-info">排序：{{course.sort}}</span>
                            <span class="badge badge-info">时长：{{course.time | formatSecond}}</span>
                        </p>
                        <p>
                            <button @click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                                大章
                            </button>

                            <button @click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                                编辑
                            </button>

                            <button @click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                                删除
                            </button>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <!--<table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>名称</th>
                <th>概述</th>
                <th>时长</th>
                <th>价格（元）</th>
                <th>封面</th>
                <th>级别</th>
                <th>收费</th>
                <th>状态</th>
                <th>报名数</th>
                <th>顺序</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>讲师</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="course in courseLists">
                <td>{{course.id}}</td>
                <td>{{course.name}}</td>
                <td>{{course.summary}}</td>
                <td>{{course.time}}</td>
                <td>{{course.price}}</td>
                <td>{{course.image}}</td>
                <td>{{COURSE_LEVEL | optionKV(course.level)}}</td>
                <td>{{COURSE_CHARGE | optionKV(course.charge)}}</td>
                <td>{{COURSE_STATUS | optionKV(course.status)}}</td>
                <td>{{course.enroll}}</td>
                <td>{{course.sort}}</td>
                <td>{{course.createdAt}}</td>
                <td>{{course.updatedAt}}</td>
                <td>{{course.teacherId}}</td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="edit(course)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button @click="del(course.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
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
        </table>-->

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
                                <label for="name" class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="course.name" type="text" class="form-control" id="name"
                                           placeholder="请输入名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="summary" class="col-sm-2 control-label">概述</label>
                                <div class="col-sm-10">
                                    <input v-model="course.summary" type="text" class="form-control" id="summary"
                                           placeholder="请输入概述">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="time" class="col-sm-2 control-label">时长</label>
                                <div class="col-sm-10">
                                    <input v-model="course.time" type="text" class="form-control" id="time"
                                           placeholder="请输入时长">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="price" class="col-sm-2 control-label">价格（元）</label>
                                <div class="col-sm-10">
                                    <input v-model="course.price" type="text" class="form-control" id="price"
                                           placeholder="请输入价格（元）">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="image" class="col-sm-2 control-label">封面</label>
                                <div class="col-sm-10">
                                    <input v-model="course.image" type="text" class="form-control" id="image"
                                           placeholder="请输入封面">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="level" class="col-sm-2 control-label">级别</label>
                                <div class="col-sm-10">
                                    <select v-model="course.level" type="text" class="form-control" id="level">
                                        <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="charge" class="col-sm-2 control-label">收费</label>
                                <div class="col-sm-10">
                                    <select v-model="course.charge" type="text" class="form-control" id="charge">
                                        <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="status" class="col-sm-2 control-label">状态</label>
                                <div class="col-sm-10">
                                    <select v-model="course.status" type="text" class="form-control" id="status">
                                        <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="enroll" class="col-sm-2 control-label">报名数</label>
                                <div class="col-sm-10">
                                    <input v-model="course.enroll" type="text" class="form-control" id="enroll"
                                           placeholder="请输入报名数">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sort" class="col-sm-2 control-label">顺序</label>
                                <div class="col-sm-10">
                                    <input v-model="course.sort" type="text" class="form-control" id="sort"
                                           placeholder="请输入顺序">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacherId" class="col-sm-2 control-label">讲师</label>
                                <div class="col-sm-10">
                                    <input v-model="course.teacherId" type="text" class="form-control" id="teacherId"
                                           placeholder="请输入讲师">
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

        name: "business-course",

        data: function () {
            return {
                course: {},
                courseLists: [],
                COURSE_LEVEL: COURSE_LEVEL,
                COURSE_CHARGE: COURSE_CHARGE,
                COURSE_STATUS: COURSE_STATUS,
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.courseList(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("business-course-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.course = {};
                $("#form-modal").modal("show");
            },
            edit(course) {
                let _this = this;
                _this.course = $.extend({}, course);
                $("#form-modal").modal("show");
            },
            courseList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/course/queryAll/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.courseLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                if (1 != 1
                    || !Validator.require(_this.course.name, "名称")
                    || !Validator.length(_this.course.name, "名称", 1, "50")
                    || !Validator.length(_this.course.summary, "概述", 1, "2000")
                    || !Validator.length(_this.course.image, "封面", 1, "100")
                    || !Validator.require(_this.course.price, "价格")
                    || !Validator.require(_this.course.level, "级别")
                    || !Validator.require(_this.course.charge, "收费")
                    || !Validator.require(_this.course.status, "状态")
                    || !Validator.require(_this.course.time, "时长")
                    || !Validator.require(_this.course.summary, "概述")
                ) {
                    return;
                }
                //保存校验
                Loading.show();
                //let courseStr = JSON.stringify(_this.course);
                _this.$http.post(process.env.VUE_APP_SERVER + "/business/admin/course/save", _this.course)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.courseList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(courseId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/business/admin/course/delete/" + courseId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.courseList(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
            },

            toChapter(course) {
                let _this = this;
                SessionStorage.set("course", course);
                _this.$router.push("/business/chapter");
            },
        },
    }
</script>

<style scoped>
    .caption h3{
        font-size: 20px;
    }
</style>