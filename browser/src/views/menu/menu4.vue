<template>
  <div class="menu-container">
    <el-card class="main">
      <div class="selector">
        <el-autocomplete
          class="inline-input"
          v-model="course"
          :fetch-suggestions="querySearch"
          placeholder="Course"
        ></el-autocomplete>
        <el-date-picker
          v-model="date"
          align="right"
          type="date"
          placeholder="Select date"
          :picker-options="dateOptions"
          value-format="yyyy-MM-dd"
        >
        </el-date-picker>
        <el-time-select
          placeholder="Start time"
          v-model="startTime"
          :picker-options="{
            start: '08:00',
            step: '00:45',
            end: '24:00',
          }"
        >
        </el-time-select>
        <el-time-select
          placeholder="End time"
          v-model="endTime"
          :picker-options="{
            start: '08:45',
            step: '00:45',
            end: '24:00',
            minTime: startTime,
          }"
        >
        </el-time-select>
        <el-button type="primary" style="margin: auto" @click="queryData"
          >Search</el-button
        >
        <el-button type="primary" style="margin: auto" @click="clearData"
          >Clear</el-button
        >
      </div>
      <div style="margin: 20px auto">
        <exportExcel
          ref="myChild"
          :exportExcelInfo="exportExcelInfo"
          :tableData="tableData"
          :exportExcelArr="exportExcelArr"
        ></exportExcel>
      </div>
      <div style="width: 100px; height: 40px; margin: auto">
        <el-button style="margin: auto" type="primary" @click="exportExcel"
          >Export</el-button
        >
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import echarts from "echarts";
import analysisApi from "@/api/analysis";
import dataApi from "@/api/data";
import { mapGetters } from "vuex";
import exportExcel from "@/components/ExportExcel/index";

export default {
  name: "Menu4",
  data() {
    return {
      pageName: "菜单4",
      myChart: null,
      courses: [],
      course: "",
      childName: "",
      date: "",
      startTime: "",
      endTime: "",
      dateOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "today",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "yesterday",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "before one week",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },

      exportExcelArr: [
        {
          prop: "timeOffset",
          label: "Time",
        },
        {
          prop: "sid",
          label: "Student Name",
        },
        {
          prop: "cid",
          label: "Class Name",
        },
        {
          prop: "attention_value",
          label: "Concenstration Score",
        },
        {
          prop: "expression_value",
          label: "Emotion",
        },
      ],
      //导出excel表格id及excel名称
      exportExcelInfo: {
        excelId: "record-table",
        excelName: "Student Performance data.xlsx",
      },
      //需要导出的table数据
      tableData: [],
    };
  },
  components: {
    exportExcel,
  },
  //获取用户名字
  computed: {
    ...mapGetters(["name"]),
  },
  mounted() {
    this.queryCourse();
  },
  methods: {
    querySearch(queryString, cb) {
      var courses = this.courses;
      var results = queryString
        ? courses.filter(this.createFilter(queryString))
        : courses;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (course) => {
        return (
          course.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    queryCourse() {
      if (this.name.indexOf("stu") == 0) {
        analysisApi.queryStudentCourse(this.name).then((res) => {
          let courseSet = new Set();
          for (let item of res.data.courses) {
            courseSet.add(item.cid);
          }
          for (let item of courseSet) {
            this.courses.push({ value: item });
          }
        });
      } else {
        analysisApi.queryStudentCourseByParent(this.name).then((res) => {
          this.childName = res.data.sid;
          for (let item of res.data.courseList) {
            this.courses.push({ value: item.cid });
          }
        });
      }
    },
    //调用接口方法
    queryData() {
      //查询本学生对应课程专注度
      if (this.name.indexOf("stu") == 0) {
        dataApi
          .queryStudent(
            this.name,
            this.course,
            this.date + " " + this.startTime,
            this.date + " " + this.endTime
          )
          .then((res) => {
            if (res.data.performanceList == []) {
              this.$message({
                message: "No data found",
                type: "warning",
              });
            } else {
              this.tableData = res.data.performanceList;
            }
          });
      } else {
        dataApi
          .queryStudent(
            this.childName,
            this.course,
            this.date + " " + this.startTime,
            this.date + " " + this.endTime
          )
          .then((res) => {
            if (res.data.performanceList == []) {
              this.$message({
                message: "No data found",
                type: "warning",
              });
            } else {
              this.tableData = res.data.performanceList;
            }
          });
      }
    },

    exportExcel() {
      this.$refs.myChild.exportExcel();
    },
    clearData() {
      this.tableData = [];
    },
  },
};
</script>

<style scoped>
.main {
  margin: 15px;
}
.selector {
  width: 1050px;
  margin: auto;
}
</style>