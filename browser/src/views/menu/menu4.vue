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
<<<<<<< HEAD
            end: '17:00',
=======
            end: '24:00',
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
          }"
        >
        </el-time-select>
        <el-time-select
          placeholder="End time"
          v-model="endTime"
          :picker-options="{
            start: '08:45',
            step: '00:45',
<<<<<<< HEAD
            end: '17:00',
=======
            end: '24:00',
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
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
<<<<<<< HEAD
=======
import dataApi from "@/api/data";
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
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
<<<<<<< HEAD
          prop: "time",
=======
          prop: "timeOffset",
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
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
<<<<<<< HEAD
          prop: "score",
          label: "Concenstration Score",
        },
=======
          prop: "attention_value",
          label: "Concenstration Score",
        },
        {
          prop: "expression_value",
          label: "Emotion",
        },
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
      ],
      //导出excel表格id及excel名称
      exportExcelInfo: {
        excelId: "record-table",
<<<<<<< HEAD
        excelName: "Concenstration Score.xlsx",
      },
      //需要导出的table数据
      tableData: [
        {
          time: "2016-05-01",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 1518 弄",
          score: "111",
        },
        {
          time: "2016-05-02",
          studentName: "王小mao",
          className: "上海市普陀区金沙江路 1518 弄",
          score: "1111",
        },
        {
          time: "2016-05-03",
          studentName: "王小2",
          className: "上海市普陀区金沙江路 151218 弄",
          score: "11331",
        },
        {
          time: "2016-05-04",
          studentName: "王小3",
          className: "上海市普陀区金沙江路 15158 弄",
          score: "11441",
        },
        {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        },
         {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        }, {
          time: "2016-05-05",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 152218 弄",
          score: "11551",
        },
      ],
=======
        excelName: "Student Performance data.xlsx",
      },
      //需要导出的table数据
      tableData: [],
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
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
<<<<<<< HEAD
    // this.queryCourse();
=======
    this.queryCourse();
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
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
      analysisApi.queryStudentCourse(this.name).then((res) => {
        for (let item of res.data.courses) {
          this.courses.push({ value: item.cid });
        }
      });
    },
    //调用接口方法
    queryData() {
      //查询本学生对应课程专注度
<<<<<<< HEAD
      analysisApi
        .queryStudentConcentration(
=======
      dataApi
        .queryStudent(
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
          this.name,
          this.course,
          this.date + " " + this.startTime,
          this.date + " " + this.endTime
        )
<<<<<<< HEAD
        .then((res) => {});
=======
        .then((res) => {
          this.tableData = res.data.performanceList;
        });
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
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