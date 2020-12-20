<template>
    <div>
        <p>
            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="userList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="userList"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>登陆名</th>
                <th>昵称</th>
                <th>密码</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="user in userLists">
                <td>{{user.id}}</td>
                <td>{{user.loginName}}</td>
                <td>{{user.name}}</td>
                <td>{{user.password}}</td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="editPassword(user)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-key bigger-120"></i>
                        </button>

                        <button @click="edit(user)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button @click="del(user.id)" class="btn btn-xs btn-danger">
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
                                    <label for="loginName" class="col-sm-2 control-label">登陆名</label>
                                    <div class="col-sm-10">
                                        <input v-model="user.loginName" v-bind:disabled="user.id" type="text" class="form-control" id="loginName"
                                               placeholder="请输入登陆名">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">昵称</label>
                                    <div class="col-sm-10">
                                        <input v-model="user.name" type="text" class="form-control" id="name"
                                               placeholder="请输入昵称">
                                    </div>
                                </div>
                                <div v-show="!user.id" class="form-group">
                                    <label for="password" class="col-sm-2 control-label">密码</label>
                                    <div class="col-sm-10">
                                        <input v-model="user.password" type="password" class="form-control" id="password"
                                               placeholder="请输入密码">
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

        <div id="edit-password-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">修改密码</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label col-sm-2">
                                    密码
                                </label>
                                <div class="col-sm-10">
                                    <input class="form-control" type="password" v-model="user.password" name="password">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            取消
                        </button>
                        <button @click="savePassword()" type="button" class="btn btn-white btn-info btn-round">
                            <i class="ace-icon fa fa-plus blue"></i>
                            保存密码
                        </button>
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

        name: "system-user",

        data: function () {
            return {
                user: {},
                userLists: [],
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.userList(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("system-user-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.user = {};
                $("#form-modal").modal("show");
            },
            edit(user) {
                let _this = this;
                _this.user = $.extend({}, user);
                $("#form-modal").modal("show");
            },
            userList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/system/user/queryAll/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.userLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (!Validator.require(_this.user.loginName, "登陆名")
                    || !Validator.length(_this.user.loginName, "登陆名", 1, "50")
                    || !Validator.length(_this.user.name, "昵称", 1, "50")
                    || !Validator.require(_this.user.password, "密码")
                ) {
                    return;
                }
                _this.user.password = hex_md5(_this.user.password + KEY);
                Loading.show();
                //let userStr = JSON.stringify(_this.user);
                _this.$http.post(process.env.VUE_APP_SERVER + "/system/user/save", _this.user)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.userList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(userId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/system/user/delete/" + userId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.userList(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
            },

            /**
             * 点击修改密码
             * @param user
             */
            editPassword(user) {
                let _this = this;
                _this.user = $.extend({}, user);
                _this.user.password = null;
                $("#edit-password-modal").modal("show");
            },

            /**
             * 保存密码
             */
            savePassword() {
                let _this = this;

                _this.user.password = hex_md5(_this.user.password + KEY);
                Loading.show();
                _this.$http.post(process.env.VUE_APP_SERVER + "/system/user/savePassword", _this.user)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#edit-password-modal").modal("hide");
                            _this.userList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
        },
    }
</script>