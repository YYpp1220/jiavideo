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
                            &nbsp;
                            <button @click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                                内容
                            </button>
                            &nbsp;
                            <button @click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                                排序
                            </button>
                            &nbsp;
                            <button @click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                                编辑
                            </button>
                            &nbsp;
                            <button @click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                                删除
                            </button>
                        </p>
                    </div>
                </div>
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
                                <label class="col-sm-2 control-label">分类</label>
                                <div class="col-sm-10">
                                    <ul id="tree" class="ztree"></ul>
                                </div>
                            </div>
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
                                           placeholder="请输入顺序" disabled>
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
        <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <i class="ace-icon fa fa-times"></i>
                        </button>
                        <h4 class="modal-title">排序</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label col-lg-3">
                                    当前排序
                                </label>
                                <div class="col-lg-9">
                                    <label>
                                        <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3">
                                    新排序
                                </label>
                                <div class="col-lg-9">
                                    <label>
                                        <input class="form-control" v-model="sort.newSort" name="newSort">
                                    </label>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal" aria-label="Close">
                            <i class="ace-icon fa fa-times"></i>
                            取消
                        </button>
                        <button type="button" class="btn btn-white btn-info btn-round" @click="updateSort()">
                            <i class="ace-icon fa fa-plus blue"></i>
                            保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <i class="ace-icon fa fa-times"></i>
                        </button>
                        <h4 class="modal-title">内容编辑</h4>
                    </div>
                    <div class="modal-body">
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
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal" aria-label="Close">
                            <i class="ace-icon fa fa-times"></i>
                            取消
                        </button>
                        <button type="button" class="btn btn-white btn-info btn-round" @click="saveContent()">
                            <i class="ace-icon fa fa-plus blue"></i>
                            保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
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
                categorys: [],
                tree: {},
                saveContentLabel: "",
                sort: {
                    id: "",
                    oldSort: 0,
                    newSort: 0,
                },
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.categoryList();
            _this.courseList(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("business-course-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.course = {
                    sort: _this.$refs.pagination.total + 1,
                };
                _this.tree.checkAllNodes(false);
                $("#form-modal").modal("show");
            },
            edit(course) {
                let _this = this;
                _this.course = $.extend({}, course);
                _this.courseCategoryList(course.id);
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
                if (Tool.isEmpty(_this.tree.getCheckedNodes())) {
                    Toast.warning("请选择分类！");
                    return;
                }

                _this.course.categorys = _this.tree.getCheckedNodes();
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
                SessionStorage.set(SESSION_KEY_COURSE, course);
                _this.$router.push("/business/chapter");
            },

            categoryList() {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/category/queryAll/").then((response) => {
                    Loading.hide();
                    _this.categorys = response.data["generalClass"];
                    _this.initTree();
                })
            },

            initTree() {
                let _this = this;

                let setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "00000000",
                            enable: true
                        }
                    }
                };

                let zNodes = _this.categorys;

                _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
            },

            courseCategoryList(courseId) {
                let _this = this;
                Loading.show();
                _this.$http.post(process.env.VUE_APP_SERVER + '/business/admin/course/categoryList/' + courseId)
                    .then(response => {
                        Loading.hide();
                        let categorys = response.data["generalClass"];

                        // 勾选查询到的分类
                        _this.tree.checkAllNodes(false);
                        for (let category of categorys) {
                            let node = _this.tree.getNodeByParam("id", category.categoryId);
                            _this.tree.checkNode(node, true);
                        }
                    })
            },

            editContent(course) {
                let _this = this;
                let id = course.id;
                _this.course = course;
                $("#content").summernote({
                    focus: true,
                    height: 300
                });

                // 先清空历史文本
                $("#content").summernote('code', '');
                _this.saveContentLabel = "";
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + '/business/admin/course/findContent/' + id)
                    .then(response => {
                        Loading.hide();
                        let content = response.data["generalClass"];
                        if (response.statusText === 'OK') {
                            $("#course-content-modal").modal({backdrop: 'static', keyboard: false});
                            if (content) {
                                $("#content").summernote('code', content[0].content);
                            }
                            // 定时自动保存
                            let saveContentInterval = setInterval(function() {
                                _this.saveContent();
                            }, 10000);
                            // 关闭内容框时，自动清除定时任务
                            $("#course-content-modal").on('hidden.bs.modal', function (e) {
                                clearInterval(saveContentInterval);
                            });
                        }else {
                            Toast.warning(response.message);
                        }
                    });
            },

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

            openSortModal(course) {
                let _this = this;
                _this.sort = {
                    id: course.id,
                    oldSort: course.sort,
                    newSort: course.sort,
                };
                $("#course-sort-modal").modal("show");
            },

            updateSort() {
                let _this = this;
                if (_this.sort.newSort === _this.sort.oldSort) {
                    Toast.warning("排序没有变化");
                    return;
                }
                Loading.show();
                _this.$http.post(process.env.VUE_APP_SERVER + '/business/admin/course/sort/', _this.sort)
                    .then(response => {
                        let sort = response.data["generalClass"];
                        if (response.statusText === "OK") {
                            Toast.warning("更新排序成功");
                            $("#course-sort-modal").modal("hide");
                            _this.courseList(1);
                        }else {
                            Toast.warning("更新排序失败");
                        }
                    })
            },
        },
    }
</script>

<style scoped>
    .caption h3{
        font-size: 20px;
    }
</style>