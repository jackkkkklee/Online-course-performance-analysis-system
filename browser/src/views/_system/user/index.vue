<template>
  <div class="app-container">
    <!--查询  -->
    <el-row>
      <el-input
        style="width: 200px"
        v-model="tableQuery.nick"
        placeholder="Nickname"
      ></el-input>
      <span style="margin-right: 15px"></span>
      <el-tooltip class="item" content="Search" placement="top">
        <el-button
          icon="el-icon-search"
          circle
          @click="fetchData(1)"
          v-perm="'b:user:query'"
        ></el-button>
      </el-tooltip>
    </el-row>
    <div style="margin-bottom: 30px"></div>
    <el-button
      type="primary"
      icon="el-icon-plus"
      size="mini"
      @click="handleCreate"
      v-perm="'b:user:add'"
      >{{ textMap.create }}</el-button
    >
    <div style="margin-bottom: 30px"></div>
    <!--列表-->
    <el-table
      style="width: 100%"
      :data="tableData"
      v-loading.body="tableLoading"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column prop="uid" label="User ID"></el-table-column>
      <el-table-column prop="uname" label="Login Name"></el-table-column>
      <el-table-column prop="nick" label="Nickname"></el-table-column>
      <el-table-column label="Role">
        <template slot-scope="scope">
          <el-tag
            style="margin: 2px"
            v-for="role in scope.row.roleList"
            :key="role.rid"
            >{{ role.rname }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="time" label="Create Time">
        <template slot-scope="scope">
          <span v-text="parseTime(scope.row.created)"></span>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="Update Time">
        <template slot-scope="scope">
          <span v-text="parseTime(scope.row.updated)"></span>
        </template>
      </el-table-column>
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <el-tooltip content="Edit" placement="top">
            <el-button
              @click="handleUpdate(scope.$index, scope.row)"
              size="medium"
              type="info"
              icon="el-icon-edit"
              circle
              plain
            ></el-button>
          </el-tooltip>
          <el-tooltip
            content="Modify Role"
            placement="top"
            v-if="!hasAdminRole(scope.row)"
          >
            <el-button
              @click="handleUpdateUserRoles(scope.$index, scope.row)"
              size="medium"
              type="warning"
              icon="el-icon-star-off"
              circle
              plain
            ></el-button>
          </el-tooltip>
          <el-tooltip
            content="Delete"
            placement="top"
            v-if="!hasAdminRole(scope.row)"
          >
            <el-button
              @click="handleDelete(scope.$index, scope.row)"
              size="medium"
              type="danger"
              icon="el-icon-delete"
              circle
              plain
            ></el-button>
          </el-tooltip>
          <el-popover
            trigger="hover"
            placement="top"
            v-else
            style="display: inline-block"
          >
            <el-alert
              type="warning"
              :closable="false"
              title="Permission Explanation"
            >
              <div>
                To ensure the highest authority of the administrator in the
                system
              </div>
              <div>
                Administrator's own permissions are not allowed to be edited
              </div>
              <div>The administrator role is not allowed to be deleted</div>
            </el-alert>
            <div slot="reference">
              <el-tag style="margin-left: 10px" type="info">Explanation</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-bottom: 30px"></div>
    <!--分页-->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="tablePage.current"
      :page-sizes="[10, 20, 30, 40, 50]"
      :page-size="tablePage.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tablePage.total"
    >
    </el-pagination>
    <!--弹出窗口：新增/编辑用户-->
    <el-dialog
      :title="textMap[dialogStatus]"
      :visible.sync="dialogFormVisible"
      width="20%"
    >
      <el-form
        :rules="rules"
        ref="dataForm"
        :model="temp"
        label-position="left"
        label-width="120px"
      >
        <el-form-item
          label="Login Name"
          prop="uname"
          v-if="dialogStatus == 'create'"
        >
          <el-input v-model="temp.uname"></el-input>
        </el-form-item>

        <el-form-item label="Nickname" prop="nick">
          <el-input v-model="temp.nick"></el-input>
        </el-form-item>

        <el-form-item label="Password" prop="pwd">
          <el-input type="password" v-model="temp.pwd"></el-input>
        </el-form-item>

        <el-form-item label="Password" prop="pwd2">
          <el-input type="password" v-model="temp.pwd2"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button
          v-if="dialogStatus == 'create'"
          type="primary"
          @click="createData"
          >Create</el-button
        >
        <el-button v-else type="primary" @click="updateData">Confirm</el-button>
      </div>
    </el-dialog>
    <!--弹出窗口：修改用户角色-->
    <el-dialog
      title="Modify User Role"
      :visible.sync="editRolesDialogVisible"
      width="30%"
    >
      <div>
        <el-checkbox
          :indeterminate="isIndeterminate"
          v-model="checkAll"
          @change="handleCheckAllChange"
          >Select all</el-checkbox
        >
        <div style="margin: 15px 0"></div>
        <el-checkbox-group v-model="updateUserRolesData.rids">
          <el-checkbox
            class="role-checkbox"
            v-for="role in roleOptions"
            :label="role.id"
            :key="role.id"
          >
            {{ role.val }}
          </el-checkbox>
        </el-checkbox-group>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editRolesDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="checkUpdateUserRolesData"
          >Confirm</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import optionApi from "@/api/option";
import userApi from "@/api/user";
import { parseTime, resetTemp } from "@/utils";
import { root, confirm, pageParamNames } from "@/utils/constants";
import debounce from "lodash/debounce";

export default {
  name: "UserManage",

  data() {
    let validateName = (rule, value, callback) => {
      if (this.dialogStatus == "create" && value === "") {
        callback(new Error("Required"));
      } else {
        callback();
      }
    };

    let validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Please enter password"));
      } else {
        if (this.temp.pwd2 !== "") {
          this.$refs.dataForm.validateField("pwd2");
        }
        callback();
      }
    };

    let validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Please enter password again"));
      } else if (value != this.temp.pwd) {
        callback(new Error("Two passwords are different"));
      } else {
        callback();
      }
    };

    return {
      parseTime: parseTime,
      tableLoading: false,
      tableData: [],
      tableQuery: {
        nick: null,
      },
      tablePage: {
        current: null,
        pages: null,
        size: null,
        total: null,
      },
      dialogFormVisible: false,
      editRolesDialogVisible: false,
      dialogStatus: "",
      temp: {
        idx: null, //tableData中的下标
        uid: null,
        uname: null,
        nick: null,
        pwd: null,
        pwd2: null,
        created: null,
        updated: null,
      },
      textMap: {
        update: "Edit User",
        create: "Create User",
      },
      rules: {
        uname: [{ required: true,validator: validateName, trigger: "blur" }],
        pwd: [{ required: true, validator: validatePass, trigger: "blur" }],
        pwd2: [{ required: true, validator: validatePass2, trigger: "change" }],
      },
      checkAll: false,
      isIndeterminate: true,
      //所有角色(管理员除外)
      roleOptions: [],
      roleMap: new Map(),
      // 更新用户的角色的数据
      updateUserRolesData: {
        idx: null,
        uid: null,
        rids: [],
      },
    };
  },

  created() {
    this.initData();
    this.fetchData();
  },

  watch: {
    //延时查询
    "tableQuery.nick": debounce(function () {
      this.fetchData();
    }, 500),
  }, //watch

  methods: {
    initData() {
      //所有角色选项
      optionApi.listRoleOptions().then((res) => {
        res.data.options.forEach((obj) => {
          if (obj.val2 != root.rval) {
            //排除管理员
            this.roleOptions.push(obj);
            this.roleMap.set(obj.id, obj.val);
          }
        });
      });
    },

    hasAdminRole(row) {
      if (row && row.roleList) {
        return row.roleList.some((role) => role.rval == root.rval);
      }
      return false;
    },

    //全选
    handleCheckAllChange(val) {
      let allRids = this.roleOptions.map((role) => role.id);
      this.updateUserRolesData.rids = val ? allRids : [];
      this.isIndeterminate = false;
    },

    //分页
    handleSizeChange(val) {
      this.tablePage.size = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      this.tablePage.current = val;
      this.fetchData();
    },

    //查询
    fetchData(current) {
      if (current) {
        this.tablePage.current = current;
      }
      this.tableLoading = true;
      userApi.queryUser(this.tableQuery, this.tablePage).then((res) => {
        this.tableData = res.data.page.records;
        this.tableLoading = false;
        pageParamNames.forEach((name) =>
          this.$set(this.tablePage, name, res.data.page[name])
        );
      });
      console.log(this.tableData);
      console.log(this.tablePage);
    },

    //更新
    handleUpdate(idx, row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.idx = idx;
      this.temp.pwd = null;
      this.temp.pwd2 = null;
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => this.$refs["dataForm"].clearValidate());
    },
    updateData() {
      this.$refs["dataForm"].validate((valid) => {
        if (!valid) return;
        const tempData = Object.assign({}, this.temp); //copy obj
        userApi.updateUser(tempData).then((res) => {
          tempData.updated = res.data.updated;
          this.tableData.splice(tempData.idx, 1, tempData);
          this.dialogFormVisible = false;
          this.$message.success("Update successfully");
        });
      });
    },

    //更新用户的角色
    handleUpdateUserRoles(idx, row) {
      // 显示用户的角色
      this.updateUserRolesData = {
        idx: idx,
        uid: row.uid,
        rids: row.roleList.map((role) => role.rid),
      };
      // 显示弹窗
      this.editRolesDialogVisible = true;
    },

    checkUpdateUserRolesData() {
      const noRolesSelected =
        this.updateUserRolesData &&
        this.updateUserRolesData.rids &&
        this.updateUserRolesData.rids.length == 0;
      if (noRolesSelected) {
        this.$confirm(
          "No role is selected, the existing roles of the user will be cleared. Do you want to continue?",
          "Tips",
          {
            confirmButtonText: "Confirm",
            cancelButtonText: "Cancel",
          }
        )
          .then(() => {
            this.invokeUpdateUserRolesApi();
          })
          .catch(() => {
            this.$message("Edit canceled");
          });
      } else {
        this.invokeUpdateUserRolesApi();
      }
    },

    invokeUpdateUserRolesApi() {
      userApi.updateUserRoles(this.updateUserRolesData).then((res) => {
        let newRoles = this.updateUserRolesData.rids.map((rid) => {
          let rname = this.roleMap.get(rid);
          if (rname) return { rid, rname };
        });
        this.tableData[this.updateUserRolesData.idx].roleList = newRoles;
        this.editRolesDialogVisible = false;
        this.$message.success("Update successfully");
      });
    },

    //删除
    handleDelete(idx, row) {
      this.$confirm("Are you sure you want to delete this user?", "Tips", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
      })
        .then(() => {
          userApi.deleteUser({ uid: row.uid }).then((res) => {
            this.tableData.splice(idx, 1);
            --this.tablePage.total;
            this.dialogFormVisible = false;
            this.$message.success("Delete successfully");
          });
        })
        .catch(() => {
          this.$message.info("Delete canceled");
        });
    },

    //新增
    handleCreate() {
      resetTemp(this.temp);
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate((valid) => {
        if (!valid) return;
        userApi.addUser(this.temp).then((res) => {
          this.temp.uid = res.data.uid; //后台传回来新增记录的id
          this.temp.created = res.data.created; //后台传回来新增记录的时间
          this.temp.roleList = [];
          this.tableData.unshift(Object.assign({}, this.temp));
          ++this.tablePage.total;
          this.dialogFormVisible = false;
          this.$message.success("Create successfully");
        });
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.role-checkbox {
  margin-left: 0px;
  margin-right: 15px;
}
</style>
