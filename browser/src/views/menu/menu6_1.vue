<template>
  <div class="app-container">
    <!--查询  -->
    <el-row>
      <el-input
        style="width: 200px"
        v-model="tableQuery.courseName"
        placeholder="Course Name"
      ></el-input>
      <span style="margin-right: 15px"></span>
      <el-tooltip class="item" content="Search" placement="top">
        <el-button
          icon="el-icon-search"
          circle
          @click="searchCourse"
        ></el-button>
      </el-tooltip>
    </el-row>
    <div style="margin-bottom: 30px"></div>
    <el-button
      type="primary"
      icon="el-icon-plus"
      size="mini"
      @click="handleCreate"
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
      <el-table-column prop="courseName" label="Course Name"></el-table-column>
      <el-table-column prop="startTime" label="Start Time"></el-table-column>
      <el-table-column prop="endTime" label="End Time"></el-table-column>
      <el-table-column label="Operate">
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
          <el-tooltip content="Delete" placement="top">
            <el-button
              @click="handleDelete(scope.$index, scope.row)"
              size="medium"
              type="danger"
              icon="el-icon-delete"
              circle
              plain
            ></el-button>
          </el-tooltip>
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
    <!--弹出窗口：新增/编辑课程-->
    <el-dialog
      :title="textMap[dialogStatus]"
      :visible.sync="dialogFormVisible"
      width="25%"
    >
      <el-form
        :rules="rules"
        ref="dataForm"
        :model="temp"
        label-position="left"
        label-width="120px"
      >
        <el-form-item label="Course Name" prop="cname">
          <el-input style="width: 220px" v-model="temp.cname"></el-input>
        </el-form-item>

        <el-form-item label="Start Time" prop="startTime">
          <el-date-picker
            v-model="temp.startTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item label="End Time" prop="endTime">
          <el-date-picker
            v-model="temp.endTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="quitForm">Cancel</el-button>
        <el-button
          v-if="dialogStatus == 'create'"
          type="primary"
          @click="createData"
          >Create</el-button
        >
        <el-button v-else type="primary" @click="updateData">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import courseApi from "@/api/course";
import debounce from "lodash/debounce";

export default {
  name: "CourseManage",

  data() {
    // let validateName = (rule, value, callback) => {
    //   if (this.dialogStatus == "create" && value === "") {
    //     callback(new Error("Required"));
    //   } else {
    //     callback();
    //   }
    // };

    // let validateTime = (rule, value, callback) => {
    //   if (value === "") {
    //     callback(new Error("Please enter course time!"));
    //   } else {
    //     callback();
    //   }
    // };

    return {
      courseData: [],
      pagedData: [],
      tableLoading: false,
      tableData: [],
      tableQuery: {
        courseName: null,
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
        cname: null,
        startTime: null,
        endTime: null,
      },
      textMap: {
        update: "Edit Course",
        create: "Create Course",
      },
      rules: {
        cname: [{ required: true, message: "Required", trigger: "blur" }],
        startTime: [{ required: true, message: "Required", trigger: "blur" }],
        endTime: [{ required: true, message: "Required", trigger: "blur" }],
      },
      checkAll: false,
      isIndeterminate: true,
      mockData: [
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "PE",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "English",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "science",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
        {
          courseName: "math",
          startTime: "2016-05-01",
          endTime: "2016-05-01",
        },
      ],
    };
  },

  created() {
    this.fetchData();
  },

  watch: {
    //延时查询
    "tableQuery.courseName": debounce(function () {
      this.searchCourse();
    }, 500),
  }, //watch

  methods: {
    //分页
    handleSizeChange(val) {
      this.tablePage.size = val;
      this.pageTable(this.courseData);
      this.tablePage.current[0];
    },
    handleCurrentChange(val) {
      this.tablePage.current = val;
      this.tableData = this.pagedData[val - 1];
    },
    pageTable(data) {
      if (this.tablePage.size == null) {
        this.tablePage.size = 10;
      }
      if (data.length > this.tablePage.size) {
        let page = 1;
        if (data.length % this.tablePage.size != 0) {
          page = Math.ceil(data.length / this.tablePage.size);
        } else {
          page = data.length / this.tablePage.size;
        }
        for (let i = 0; i < page; i++) {
          let arr = [];
          for (let j = 0; j < this.tablePage.size; j++) {
            arr.push(data[j + i * this.tablePage.size]);
            if (j + i * this.tablePage.size + 1 == data.length) {
              break;
            }
          }
          this.pagedData[i] = arr;
        }
      } else {
        this.pagedData[0] = data;
      }
    },

    // 搜索课程
    searchCourse() {
      if (this.tableQuery.courseName != "") {
        this.tablePage.current = 1;
        this.tableLoading = true;
        this.tableData = [];
        for (let item of this.courseData) {
          if (item.courseName == this.tableQuery.courseName) {
            this.tableData.push(item);
          }
        }
        this.tablePage.total = this.tableData.length;
        this.tableLoading = false;
      } else {
        this.tablePage.current = 1;
        this.tableLoading = true;
        this.tableData = this.pagedData[0];
        this.tablePage.total = this.courseData.length;
        this.tableLoading = false;
      }
    },

    // 查询
    fetchData() {
      this.tableLoading = true;
<<<<<<< HEAD
      this.courseData = this.mockData;
      this.pageTable(this.courseData);
      this.tablePage.current = 1;
      this.tablePage.total = this.courseData.length;
      this.tableData = this.pagedData[0];
      this.tableLoading = false;
      // 调用接口
      // courseApi.queryCourse(this.tableQuery).then((res) => {
      //   this.courseData = res.data;
      //   this.pageTable(this.courseData);
      //   this.tableLoading = false;
      // });
=======
      // this.courseData = this.mockData;
      // this.pageTable(this.courseData);
      // this.tablePage.current = 1;
      // this.tablePage.total = this.courseData.length;
      // this.tableData = this.pagedData[0];
      // this.tableLoading = false;

      // 调用接口
      courseApi.queryCourse().then((res) => {
        this.courseData = res.data.courses;
        this.pageTable(this.courseData);
        this.tablePage.current = 1;
        this.tablePage.total = this.courseData.length;
        this.tableData = this.pagedData[0];
        this.tableLoading = false;
      });
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
    },

    // 创建课程
    handleCreate() {
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate((valid) => {
        if (!valid) return;
        let flag = false;
<<<<<<< HEAD
        for (let item of this.tableData) {
=======
        for (let item of this.courseData) {
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
          if (this.temp.cname == item.courseName) {
            flag = true;
          }
        }
        // userApi.addUser(this.temp).then((res) => {
        //   this.temp.uid = res.data.uid; //后台传回来新增记录的id
        //   this.temp.created = res.data.created; //后台传回来新增记录的时间
        //   this.temp.roleList = [];
        //   this.tableData.unshift(Object.assign({}, this.temp));
        //   ++this.tablePage.total;
        //   this.dialogFormVisible = false;
        //   this.$message.success("Create successfully");
        // });
        if (flag) {
          this.$message.success("Course already exists");
        } else {
<<<<<<< HEAD
          this.courseData.push({
            courseName: this.temp.cname,
            startTime: this.temp.startTime,
            endTime: this.temp.endTime,
          });
          this.pageTable(this.courseData);
=======
          // this.courseData.push({
          //   courseName: this.temp.cname,
          //   startTime: this.temp.startTime,
          //   endTime: this.temp.endTime,
          // });
          courseApi.addCourse(
            this.temp.cname,
            this.temp.startTime,
            this.temp.endTime
          );
          // this.pageTable(this.courseData);
          this.fetchData();
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
          this.dialogFormVisible = false;
          this.$message.success("Create successfully");
        }
      });
    },

    // 修改课程
    handleUpdate(idx, row) {
      this.temp.idx = idx;
      this.temp.cname = row.courseName;
      this.temp.startTime = row.endTime;
      this.temp.endTime = row.endTime;
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => this.$refs["dataForm"].clearValidate());
    },
    updateData() {
      this.$refs["dataForm"].validate((valid) => {
        if (!valid) return;
        // const tempData = Object.assign({}, this.temp); //copy obj
        // userApi.updateUser(tempData).then((res) => {
        //   tempData.updated = res.data.updated;
        //   this.tableData.splice(tempData.idx, 1, tempData);
        //   this.dialogFormVisible = false;
        //   this.$message.success("Update successfully");
        // });
<<<<<<< HEAD
        this.courseData[
          (this.tablePage.current - 1) * this.tablePage.size + this.temp.idx
        ] = {
          courseName: this.temp.cname,
          startTime: this.temp.startTime,
          endTime: this.temp.endTime,
        };
        this.pageTable(this.courseData);
        this.tableData = this.pagedData[this.tablePage.current - 1];
=======

        // this.courseData[
        //   (this.tablePage.current - 1) * this.tablePage.size + this.temp.idx
        // ] = {
        //   courseName: this.temp.cname,
        //   startTime: this.temp.startTime,
        //   endTime: this.temp.endTime,
        // };
        // this.pageTable(this.courseData);
        // this.tableData = this.pagedData[this.tablePage.current - 1];

        // 调用接口
        courseApi.updateCourse(
          this.temp.cname,
          this.temp.startTime,
          this.temp.endTime
        );
        this.fetchData();
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
        this.dialogFormVisible = false;
        this.$message.success("Update successfully");
      });
    },

    // 删除课程
    handleDelete(idx, row) {
      this.$confirm("Are you sure you want to delete this course?", "Tips", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
      })
        .then(() => {
          // courseApi.deleteCourse({ cname: row.courseName }).then((res) => {
          //   this.tableData.splice(idx, 1);
          //   --this.tablePage.total;
          //   this.$message.success("Delete successfully");
          // });
<<<<<<< HEAD
=======

          courseApi.deleteCourse(row.courseName);
          this.fetchData();
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
          this.$message.success("Delete successfully");
        })
        .catch(() => {
          this.$message.info("Delete canceled");
        });
    },

    // 退出表单
    quitForm() {
      this.dialogFormVisible = false;
      this.$refs["dataForm"].resetFields();
    },
  },
};
</script>
