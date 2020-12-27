<template>
    <div>
        <p>
            <button @click="resourceList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <div class="row">
            <div class="col-md-6">
                <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="10"></textarea>
                <br>
                <button id="save-btn" type="button" class="btn btn-primary" @click="save">
                    保存
                </button>
            </div>
            <div class="col-md-6">
                <ul id="tree" class="ztree"></ul>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "system-resource",

        data: function () {
            return {
                resource: {},
                resourceLists: [],
                resourceStr: "",
                tree: {},
            };
        },

        mounted: function () {
            let _this = this;
            _this.resourceList();
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("system-resource-sidebar");
        },

        methods: {
            resourceList() {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/user/admin/auth/resource/loadTree/")
                    .then((response) => {
                        Loading.hide();
                        _this.resourceLists = response.data["generalClass"][0];
                        // 初始化树
                        _this.initTree()
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (Tool.isEmpty(_this.resourceStr)) {
                    Toast.warning("资源不能为空！");
                    return;
                }
                let json = JSON.parse(this.resourceStr);
                Loading.show();
                //let resourceStr = JSON.stringify(_this.resource);
                _this.$http.post(process.env.VUE_APP_SERVER + "/user/admin/auth/resource/save", json)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.resourceList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(resourceId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/user/admin/auth/resource/delete/" + resourceId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.resourceList();
                                Toast.success("删除成功！")
                            }
                        });
                });
            },

            /**
             * 初始资源树
             */
            initTree() {
                let _this = this;
                let setting = {
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "",
                        }
                    }
                };

                _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resourceLists);
                _this.zTree.expandAll(true);
            }
        },
    }
</script>