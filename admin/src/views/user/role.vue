<template>
    <div>
        <p>
            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="roleList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="roleList"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>角色</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="role in roleLists">
                <td>{{role.id}}</td>
                <td>{{role.name}}</td>
                <td>{{role.desc}}</td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="editUser(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-user bigger-120"></i>
                        </button>
                        &nbsp;
                        <button @click="editResource(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-list bigger-120"></i>
                        </button>
                        &nbsp;
                        <button @click="edit(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        &nbsp;
                        <button @click="del(role.id)" class="btn btn-xs btn-danger">
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
                                    <label for="name" class="col-sm-2 control-label">角色</label>
                                    <div class="col-sm-10">
                                        <input v-model="role.name" type="text" class="form-control" id="name"
                                               placeholder="请输入角色">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="desc" class="col-sm-2 control-label">描述</label>
                                    <div class="col-sm-10">
                                        <input v-model="role.desc" type="text" class="form-control" id="desc"
                                               placeholder="请输入描述">
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
        <!--角色资源关联配置-->
        <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">角色资源关联配置</h4>
                    </div>
                    <div class="modal-body">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            关闭
                        </button>
                        <button type="button" class="btn btn-white btn-info btn-round" @click="saveResource()">
                            <i class="ace-icon fa fa-plus blue"></i>
                            保存
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!--角色用户关联配置-->
        <div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">角色用户关联配置</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <table id="user-table" class="table table-hover">
                                    <tbody>
                                    <tr v-for="user in userList">
                                        <td>{{user.loginName}}</td>
                                        <td class="text-right">
                                            <a @click="addUser(user)" href="javascript:;" class="">
                                                <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-6">
                                <table id="role-user-table" class="table table-hover">
                                    <tbody>
                                    <tr v-for="user in userRoleList">
                                        <td>{{user.loginName}}</td>
                                        <td class="text-right">
                                            <a @click="deleteUser(user)" href="javascript:;" class="">
                                                <i class="ace-icon fa fa-times"></i>
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            关闭
                        </button>
                        <button @click="saveUser()" type="button" class="btn btn-white btn-info btn-round">
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

        name: "system-role",

        data: function () {
            return {
                role: {},
                roleLists: [],
                resourceLists: [],
                zTree: {},
                userList: [],
                userRoleList: [],
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.roleList(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("system-role-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.role = {};
                $("#form-modal").modal("show");
            },
            edit(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                $("#form-modal").modal("show");
            },
            roleList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/user/admin/auth/role/queryAll/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.roleLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (!Validator.require(_this.role.name, "角色")
                    || !Validator.length(_this.role.name, "角色", 1, "50")
                    || !Validator.require(_this.role.desc, "描述")
                    || !Validator.length(_this.role.desc, "描述", 1, "100")
                ) {
                    return;
                }
                Loading.show();
                //let roleStr = JSON.stringify(_this.role);
                _this.$http.post(process.env.VUE_APP_SERVER + "/user/admin/auth/role/save", _this.role)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.roleList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(roleId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/user/admin/auth/role/delete/" + roleId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.roleList(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
            },

            /**
             * 点击编辑
             * @param role
             */
            editResource(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                _this.loadResource();
                $("#resource-modal").modal("show");
            },

            /**
             * 加载资源树
             */
            loadResource() {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/user/admin/auth/resource/loadTree/")
                    .then((response) => {
                        Loading.hide();
                        _this.resourceLists = response.data["generalClass"][0];
                        // 初始化树
                        _this.initTree();
                        _this.listRoleResource();
                    })
            },

            /**
             * 初始化资源树
             */
            initTree() {
                let _this = this;
                let settings = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "",
                            enable: true
                        }
                    }
                };

                _this.zTree = $.fn.zTree.init($("#tree"), settings, _this.resourceLists);
                _this.zTree.expandAll(true);
            },

            /**
             * 资源模态框点击保存
             */
            saveResource() {
                let _this = this;
                let resources = _this.zTree.getCheckedNodes();

                // 保存时只需要保存资源id，所以使用id数组进行参数传递
                let resourceIds = [];
                for (let resource of resources) {
                    resourceIds.push(resource.id);
                }

                _this.$http.post(process.env.VUE_APP_SERVER + '/user/admin/auth/role/saveResource', {
                    id: _this.role.id,
                    resourceIds: resourceIds
                }).then((response) => {
                    let resp = response.data["generalClass"];
                    if (response.statusText === "Created") {
                        $("#resource-modal").modal("hide");
                        _this.roleList(1);
                        Toast.success("保存成功！")
                    } else if (response.statusText === "Multi-Status") {
                        let paramError = response.data;
                        Toast.warning(paramError);
                    }
                })
            },

            /**
             * 加载角色资源关联记录
             */
            listRoleResource() {
                let _this = this;
                _this.$http.get(process.env.VUE_APP_SERVER + '/user/admin/auth/role/listResource/' + _this.role.id)
                    .then((response) => {
                        let resourceList = response.data["generalClass"][0];

                        // 勾选查询到的资源，先把树的所有节点清空勾选，在勾选查询到的节点
                        _this.zTree.checkAllNodes(false);
                        for (let resource of resourceList) {
                            let node = _this.zTree.getNodeByParam("id", resource);
                            _this.zTree.checkNode(node, true);
                        }
                    })
            },

            /**
             * 点击【用户】
             */
            editUser(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                _this.listUser();
                $("#user-modal").modal("show");
            },

            /**
             * 查询所有用户
             */
            listUser() {
                let _this = this;
                _this.$http.get(process.env.VUE_APP_SERVER + '/system/user/queryAll/', {
                    page: 1,
                    size: 9999
                }).then((response)=>{
                    let resp = response.data["generalClass"];
                    if (response.statusText === "OK") {
                        _this.userList = resp;
                        _this.listRoleUser();
                    } else {
                        Toast.warning(response.data);
                    }
                })
            },

            /**
             * 角色中增加用户
             */
            addUser(user) {
                let _this = this;

                // 如果当前要添加的用户在右边列表中已经存在，则不用再添加
                let users = _this.userRoleList;
                for (let userCondition of users) {
                    if (user === userCondition) {
                        return;
                    }
                }
                _this.userRoleList.push(user);
            },

            /**
             * 角色中删除用户
             */
            deleteUser(user) {
                let _this = this;
                Tool.removeObj(_this.userRoleList, user);
            },

            /**
             * 角色用户模态框点击保存
             */
            saveUser() {
                let _this = this;
                let users = _this.userRoleList;

                // 保存时，只需要保存用户id，所以使用id数组进行参数传递
                let userIds = [];
                for (let user of users) {
                    userIds.push(user.id);
                }
                _this.$http.post(process.env.VUE_APP_SERVER + '/user/admin/auth/role/saveUser', {
                    id: _this.role.id,
                    userIds: userIds,
                }).then((response) => {
                    let resp = response.data["generalClass"];
                    if (response.statusText === "OK") {
                        Toast.success("保存成功");
                    } else {
                        Toast.warning(response.data);
                    }
                })
            },

            /**
             * 加载角色用户
             */
            listRoleUser() {
                let _this = this;
                _this.userRoleList = [];
                _this.$http.get(process.env.VUE_APP_SERVER + '/user/admin/auth/role/listUser/' + _this.role.id)
                    .then((response) => {
                        let userIds = response.data["generalClass"][0];

                        // 根据加载到的用户id，到所有用户数组中userList查找用户对象，用于列表显示
                        for (let userId of userIds) {
                            for (let user of this.userList) {
                                if (userId === user.id) {
                                    _this.userRoleList.push(user);
                                }
                            }
                        }
                    })
            },
        },
    }
</script>