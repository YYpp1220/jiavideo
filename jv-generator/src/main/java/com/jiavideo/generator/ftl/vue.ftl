<template>
    <div>
        <p>
            <button @click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>
                新增
            </button>
            &nbsp;
            <button @click="${entity}List(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="${entity}List"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <#list fieldList as field>
                    <#--<#if field.nameHump != 'createdAt' && field.nameHump != 'updatedAt' && field.nameHump != 'id'>-->
                <th>${field.nameCn}</th>
                    <#--</#if>-->
                </#list>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="${entity} in ${entity}Lists">
                <#list fieldList as field>
                   <#-- <#if field.nameHump != 'createdAt' && field.nameHump != 'updatedAt' && field.nameHump != 'id'>-->
                    <#if field.enums>
                <td>{{${field.enumsConst} | optionKV(${entity}.${field.nameHump})}}</td>
                    <#else>
                <td>{{${entity}.${field.nameHump}}}</td>
                    </#if>
                    <#--</#if>-->
                </#list>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="edit(${entity})" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button @click="del(${entity}.id)" class="btn btn-xs btn-danger">
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
                            <#list fieldList as field>
                                <#if field.nameHump != 'createdAt' && field.nameHump != 'updatedAt' && field.name != 'id'>
                                    <#if field.enums>
                                <div class="form-group">
                                    <label for="${field.nameHump}" class="col-sm-2 control-label">${field.nameCn}</label>
                                    <div class="col-sm-10">
                                        <select v-model="${entity}.${field.nameHump}" type="text" class="form-control" id="${field.nameHump}">
                                            <option v-for="o in ${field.enumsConst}" v-bind:value="o.key">{{o.value}}</option>
                                        </select>
                                    </div>
                                </div>
                                    <#else>
                                <div class="form-group">
                                    <label for="${field.nameHump}" class="col-sm-2 control-label">${field.nameCn}</label>
                                    <div class="col-sm-10">
                                        <input v-model="${entity}.${field.nameHump}" type="text" class="form-control" id="${field.nameHump}"
                                               placeholder="请输入${field.nameCn}">
                                    </div>
                                </div>
                                    </#if>
                                </#if>
                            </#list>
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

        name: "${moduleName}-${entity}",

        data: function () {
            return {
                ${entity}: {},
                ${entity}Lists: [],
                <#list fieldList as field>
                    <#if field.enums>
                ${field.enumsConst}: ${field.enumsConst},
                    </#if>
                </#list>
            };
        },

        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.${entity}List(1);
            //sidebar激活样式方法一
            //this.$parent.activeSidebar("${moduleName}-${entity}-sidebar");
        },

        methods: {
            add() {
                let _this = this;
                _this.${entity} = {};
                $("#form-modal").modal("show");
            },
            edit(${entity}) {
                let _this = this;
                _this.${entity} = $.extend({}, ${entity});
                $("#form-modal").modal("show");
            },
            ${entity}List(page) {
                let _this = this;
                Loading.show();
                _this.$http.get(process.env.VUE_APP_SERVER + "/${moduleName}/admin/${entity}/queryAll/",
                    {
                        params: {
                            page: page,
                            pageSize: _this.$refs.pagination.size,
                        }
                    }).then((response) => {
                    Loading.hide();
                    _this.${entity}Lists = response.data["generalClass"];
                    _this.$refs.pagination.render(page, response.data.total)
                })
            },
            save() {
                let _this = this;
                //保存校验
                if (1 != 1
                 <#list fieldList as field>
                    <#if field.nameHump != 'createdAt' && field.nameHump != 'updatedAt' && field.nameHump != 'sort' && field.name != 'id'>
                        <#if !field.nullAble>
                    || !Validator.require(_this.${entity}.${field.nameHump}, "${field.nameCn}")
                        </#if>
                        <#if (field.length > 0)>
                    || !Validator.length(_this.${entity}.${field.nameHump}, "${field.nameCn}", 1, "${field.length?c}")
                        </#if>
                    </#if>
                </#list>
                ) {
                    return;
                }
                Loading.show();
                //let ${entity}Str = JSON.stringify(_this.${entity});
                _this.$http.post(process.env.VUE_APP_SERVER + "/${moduleName}/admin/${entity}/save", _this.${entity})
                    .then((response) => {
                        Loading.hide();
                        if (response.statusText === "Created") {
                            $("#form-modal").modal("hide");
                            _this.${entity}List(1);
                            Toast.success("保存成功！")
                        } else if (response.statusText === "Multi-Status") {
                            let paramError = response.data;
                            Toast.warning(paramError);
                        }
                    })
            },
            del(${entity}Id) {
                let _this = this;
                Confirm.show("删除后不可恢复!确认删除？", function () {
                    Loading.show();
                    _this.$http.delete(process.env.VUE_APP_SERVER + "/${moduleName}/admin/${entity}/delete/" + ${entity}Id)
                        .then((response) => {
                            Loading.hide();
                            if (response.statusText === "No Content") {
                                _this.${entity}List(1);
                                Toast.success("删除成功！")
                            }
                        });
                });
            },
        },
    }
</script>