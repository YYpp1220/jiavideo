<template>
    <div>
        <p>
            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="fileList(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="fileList"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>相对路径</th>
                <th>文件名</th>
                <th>后缀</th>
                <th>大小</th>
                <th>用途</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>已上传分片</th>
                <th>分片大小</th>
                <th>分片总数</th>
                <th>文件标识</th>
                <th>vod</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="file in fileLists">
                <td>{{file.id}}</td>
                <td>{{file.path}}</td>
                <td>{{file.name}}</td>
                <td>{{file.suffix}}</td>
                <td>{{file.size | formatFileSize}}</td>
                <td>{{FILE_USE | optionKV(file.use)}}</td>
                <td>{{file.createdAt}}</td>
                <td>{{file.updatedAt}}</td>
                <td>{{file.shardIndex}}</td>
                <td>{{file.shardSize}}</td>
                <td>{{file.shardTotal}}</td>
                <td>{{file.key}}</td>
                <td>{{file.vod}}</td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="edit(file)" class="btn btn-white btn-xs btn-info btn-round">
                            编辑
                        </button>
                        &nbsp;
                        <button @click="del(file.id)" class="btn btn-white btn-xs btn-warning btn-round">
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
                                    <label for="path" class="col-sm-2 control-label">相对路径</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.path" type="text" class="form-control" id="path"
                                               placeholder="请输入相对路径">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">文件名</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.name" type="text" class="form-control" id="name"
                                               placeholder="请输入文件名">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="suffix" class="col-sm-2 control-label">后缀</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.suffix" type="text" class="form-control" id="suffix"
                                               placeholder="请输入后缀">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="size" class="col-sm-2 control-label">大小</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.size" type="text" class="form-control" id="size"
                                               placeholder="请输入大小">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="use" class="col-sm-2 control-label">用途</label>
                                    <div class="col-sm-10">
                                        <select v-model="file.use" type="text" class="form-control" id="use">
                                            <option v-for="o in FILE_USE" v-bind:value="o.key">{{o.value}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="shardIndex" class="col-sm-2 control-label">已上传分片</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.shardIndex" type="text" class="form-control" id="shardIndex"
                                               placeholder="请输入已上传分片">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="shardSize" class="col-sm-2 control-label">分片大小</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.shardSize" type="text" class="form-control" id="shardSize"
                                               placeholder="请输入分片大小">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="shardTotal" class="col-sm-2 control-label">分片总数</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.shardTotal" type="text" class="form-control" id="shardTotal"
                                               placeholder="请输入分片总数">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="key" class="col-sm-2 control-label">文件标识</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.key" type="text" class="form-control" id="key"
                                               placeholder="请输入文件标识">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="vod" class="col-sm-2 control-label">vod</label>
                                    <div class="col-sm-10">
                                        <input v-model="file.vod" type="text" class="form-control" id="vod"
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

    export default {
        components: {Pagination},

        name: "upload-file",

        data: function () {
            return {
                file: {},
                fileLists: [],
                FILE_USE: FILE_USE,
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.fileList(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("upload-file-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.file = {};
                $("#form-modal").modal("show");
            },
            edit(file) {
                let _this = this;
                _this.file = $.extend({}, file);
                $("#form-modal").modal("show");
            },
            fileList(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/file/admin/upload/file/queryAll/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.fileLists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (!Validator.require(_this.file.path, "相对路径")
                    || !Validator.length(_this.file.path, "相对路径", 1, "100")
                    || !Validator.length(_this.file.name, "文件名", 1, "100")
                    || !Validator.length(_this.file.suffix, "后缀", 1, "10")
                    || !Validator.length(_this.file.key, "文件标识", 1, "32")
                ) {
                    return;
                }
                Loading.show();
                //let fileStr = JSON.stringify(_this.file);
                _this.$http.post(process.env.VUE_APP_SERVER + "/file/admin/upload/file/save", _this.file)
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.fileList(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(fileId) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/file/admin/upload/file/delete/" + fileId)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.fileList(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
            },
        },
    }
</script>