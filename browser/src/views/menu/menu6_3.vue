<template>
  <div class="app-container">
    <!--查询  -->
    <el-row>
      <el-input
        style="width: 200px"
        v-model="tableQuery.studentName"
        placeholder="Student Name"
      ></el-input>
      <span style="margin-right: 15px"></span>
      <el-tooltip class="item" content="Search" placement="top">
        <el-button
          icon="el-icon-search"
          circle
          @click="searchStudent"
        ></el-button>
      </el-tooltip>
    </el-row>
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
      <el-table-column
        prop="studentName"
        label="Student Name"
      ></el-table-column>
      <el-table-column label="Courses">
        <template slot-scope="scope">
          <el-tag
            style="margin: 2px"
            v-for="course in scope.row.course"
            :key="course"
            >{{ course }}</el-tag
          >
        </template>
      </el-table-column>
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
    <!-- 弹出窗口：修改用户课程 -->
    <el-dialog
      title="Edit Course"
      :visible.sync="dialogFormVisible"
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
        <el-checkbox-group v-model="checkedCourses">
          <el-checkbox
            class="course-checkbox"
            v-for="course in courseOptions"
            :label="course"
            :key="course"
          >
            {{ course }}
          </el-checkbox>
        </el-checkbox-group>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateData">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import courseApi from "@/api/course";
import debounce from "lodash/debounce";

export default {
  name: "StudentCourseManage",

  data() {
    return {
      currentRow: null,
      studentData: [],
      pagedData: [],
      courseOptions: [],
      checkedCourses: [],
      tableLoading: false,
      tableData: [],
      tableQuery: {
        studentName: null,
      },
      tablePage: {
        current: null,
        pages: null,
        size: null,
        total: null,
      },
      dialogFormVisible: false,
      dialogStatus: "",
      temp: {
        idx: null, //tableData中的下标
        sname: null,
        course: [],
      },
      checkAll: false,
      isIndeterminate: true,
      mockData: [
        {
          studentName: "111",
          course: ["math", "history"],
        },
        {
          studentName: "222",
          course: ["math", "computer"],
        },
        {
          studentName: "333",
          course: ["math"],
        },
        {
          studentName: "444",
          course: ["math", "service"],
        },
        {
          studentName: "555",
          course: ["math", "service"],
        },
        {
          studentName: "666",
          course: ["math", "service"],
        },
        {
          studentName: "777",
          course: ["math", "service"],
        },
        {
          studentName: "888",
          course: ["math", "service"],
        },
        {
          studentName: "999",
          course: ["math", "service"],
        },
        {
          studentName: "1010111",
          course: ["math", "service"],
        },
        {
          studentName: "110111",
          course: ["math", "service"],
        },
      ],
      courseMockData: [
        "math",
        "english",
        "history",
        "computer",
        "design",
        "service",
      ],
    };
  },

  created() {
    this.fetchData();
  },

  watch: {
    //延时查询
    "tableQuery.studentName": debounce(function () {
      this.searchStudent();
    }, 500),
  }, //watch

  methods: {
    //全选
    handleCheckAllChange(val) {
      this.checkedCourses = val ? this.courseOptions : [];
      this.isIndeterminate = false;
    },

    //分页
    handleSizeChange(val) {
      this.tablePage.size = val;
      this.pageTable(this.studentData);
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

    // 搜索学生
    searchStudent() {
      if (this.tableQuery.studentName != "") {
        this.tablePage.current = 1;
        this.tableLoading = true;
        this.tableData = [];
        for (let item of this.studentData) {
          if (item.studentName == this.tableQuery.studentName) {
            this.tableData.push(item);
          }
        }
        this.tablePage.total = this.tableData.length;
        this.tableLoading = false;
      } else {
        this.tablePage.current = 1;
        this.tableLoading = true;
        this.tableData = this.pagedData[0];
        this.tablePage.total = this.studentData.length;
        this.tableLoading = false;
      }
    },

    // 查询
    fetchData() {
      this.tableLoading = true;
      // this.studentData = this.mockData;
      // this.pageTable(this.studentData);
      // this.tablePage.current = 1;
      // this.tablePage.total = this.studentData.length;
      // this.tableData = this.pagedData[0];
      // this.courseOptions = this.courseMockData;
      // this.tableLoading = false;

      // 调用接口
      courseApi.queryCourse().then((res) => {
        let courseData = res.data.courses;
        let courseArr = [];
        for (let item of courseData) {
          courseArr.push(item.course);
        }
        this.courseOptions = courseArr;
      });
      courseApi.queryStudentCourse().then((res) => {
        let dataMap = res.data.SCM;
        for (let key in dataMap) {
          if (dataMap[key] == []) {
            this.studentData.push({
              teacherName: key,
              courses: [],
            });
          } else {
            let courseArr = [];
            for (let item of dataMap) {
              courseArr.push(item.course);
            }
            this.studentData.push({
              studentName: key,
              courses: courseArr,
            });
          }
        }
        this.pageTable(this.studentData);
        this.tablePage.current = 1;
        this.tablePage.total = this.studentData.length;
        this.tableData = this.pagedData[0];
        this.tableLoading = false;
      });
    },

    // 修改课程
    handleUpdate(idx, row) {
      this.currentRow = row;
      this.temp.idx = idx;
      this.temp.sname = row.studentName;
      this.temp.course = row.course;
      this.checkedCourses = this.temp.course;
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },
    updateData() {
      // this.studentData[
      //   (this.tablePage.current - 1) * this.tablePage.size + this.temp.idx
      // ] = {
      //   studentName: this.temp.sname,
      //   course: this.checkedCourses,
      // };
      // this.pageTable(this.studentData);
      // this.tableData = this.pagedData[this.tablePage.current - 1];

      courseApi.updateStudentCourse(
        this.currentRow.studentName,
        this.checkedCourses
      );
      this.fetchData();
      this.dialogFormVisible = false;
      this.$message.success("Update successfully");
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.course-checkbox {
  margin-left: 0px;
  margin-right: 15px;
}
</style>
