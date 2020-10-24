<template>
    <div>
        <div class="row">
            <div class="col-md-4">
                <p>
                    <button @click="add1()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit red2"></i>
                        新增一级
                    </button>
                    &nbsp;
                    <button @click="categoryAll()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-refresh red2"></i>
                        刷新
                    </button>
                </p>
                <table id="level1-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="category in categoryLevel1" @click="toCategoryLevel1(category)" v-bind:class="{'active' : category.id === active.id}">
                        <td>{{category.id}}</td>
                        <td>{{category.name}}</td>
                        <td>{{category.sort}}</td>

                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button @click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>

                                <button @click="del(category.id)" class="btn btn-xs btn-danger">
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
                </table>
            </div>

            <div class="col-md-4">
                <p>
                    <button @click="add2()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit red2"></i>
                        新增二级
                    </button>
                </p>
                <table id="level2-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="category in categoryLevel2">
                        <td>{{category.id}}</td>
                        <td>{{category.name}}</td>
                        <td>{{category.sort}}</td>

                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button @click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>

                                <button @click="del(category.id)" class="btn btn-xs btn-danger">
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
                </table>
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
                                <label class="col-sm-2 control-label">父分类</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static">{{active.name || "无"}}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="category.name" type="text" class="form-control" id="name"
                                           placeholder="请输入名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sort" class="col-sm-2 control-label">顺序</label>
                                <div class="col-sm-10">
                                    <input v-model="category.sort" type="text" class="form-control" id="sort"
                                           placeholder="请输入顺序">
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
    export default {
        name: "business-category",

        data: function () {
            return {
                category: {},
                categoryLists: [],
                categoryLevel1: [],
                categoryLevel2: [],
                active: {},
            };
        },

        mounted: function () {
            let _this = this;
            _this.categoryAll();
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("business-category-sidebar");
        },

        methods: {
            add1() {
                let _this = this;
                _this.active = {};
                _this.categoryLevel2 = [];
                _this.category = {
                    parent: "00000000"
                };
                $("#form-modal").modal("show");
            },
            add2() {
                let _this = this;
                if (Tool.isEmpty(_this.active)) {
                    Toast.warning("请先点击一级分类");
                    return;
                }
                _this.category = {
                    parent: _this.active.id
                };
                $(".modal").modal("show");
            },
            edit(category) {
                let _this = this;
                _this.category = $.extend({}, category);
                $("#form-modal").modal("show");
            },
            categoryAll() {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/category/queryAll/").then((response) => {
                    Loading.hide();
                    _this.categoryLists = response.data["generalClass"];
                    // 将所有记录格式化成树形结构
                    _this.categoryLevel1 = [];
                    for (let category of _this.categoryLists) {
                        // noinspection JSUnfilteredForInLoop
                        let c = category;
                        if (c.parent === "00000000") {
                            _this.categoryLevel1.push(c);
                            for (let categoryNew of _this.categoryLists){
                                // noinspection JSUnfilteredForInLoop
                                let child = categoryNew;
                                if (child.parent === c.id) {
                                    if (Tool.isEmpty(c.children)) {
                                        // noinspection JSPrimitiveTypeWrapperUsage
                                        c.children = [];
                                    }
                                    c.children.push(child);
                                }
                            }
                        }
                    }
                    _this.categoryLevel2 = [];
                    // 对当前一级分类中选中的表格触发一次点击事件，以刷新二级菜单列表
                    // 注意：界面的渲染需要等vue绑定好变量之后才做，所以加上一个小延时
                    setTimeout(function () {
                        $("tr.active").trigger("click");
                    }, 200);
                })
            },
            /*categoryList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/business/admin/category/queryAllVice/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.categoryLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },*/
            save() {
                let _this = this;
                //保存校验
                if (1 != 1
                    || !Validator.require(_this.category.parent, "父id")
                    || !Validator.require(_this.category.name, "名称")
                    || !Validator.length(_this.category.name, "名称", 1, "50")
                ) {
                    return;
                }
                Loading.show();
                //let categoryStr = JSON.stringify(_this.category);
                _this.$http.post(process.env.VUE_APP_SERVER + "/business/admin/category/save", _this.category)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.categoryAll();
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(categoryId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/business/admin/category/delete/" + categoryId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.categoryAll();
                                Toast.success("删除成功！")
                            }
                        });
                });
            },
            toCategoryLevel1(category) {
                let _this = this;
                _this.active = category;
                _this.categoryLevel2 = category.children;
            }
        },
    }
</script>

<style scoped>
    .active td{
        background-color: #d9a9e9 !important;
    }
</style>