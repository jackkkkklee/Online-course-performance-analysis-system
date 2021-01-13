<template>
  <div class="menu-container">
    <el-card style="margin: 15px">
      <div style="margin: auto auto auto 150px">
        <el-autocomplete
          class="inline-input"
          v-model="course"
          :fetch-suggestions="queryCourseSearch"
          placeholder="Course"
        ></el-autocomplete>
        <el-autocomplete
          class="inline-input"
          v-model="studentName"
          :fetch-suggestions="queryStudentSearch"
          placeholder="Student"
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
            end: '17:00',
          }"
        >
        </el-time-select>
        <el-time-select
          placeholder="End time"
          v-model="endTime"
          :picker-options="{
            start: '08:45',
            step: '00:45',
            end: '17:00',
            minTime: startTime,
          }"
        >
        </el-time-select>
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
import echarts from "echarts";
import axios from "axios";
import analysisApi from "@/api/analysis";
import { mapGetters } from "vuex";
import exportExcel from "@/components/ExportExcel/index";

export default {
  name: "Menu5_1",
  data() {
    return {
      pageName: "菜单5-1",
      myChart: null,
      courses: [],
      course: "",
      students: [],
      studentName: "",
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
          prop: "time",
          label: "Time",
        },
        {
          prop: "studentName",
          label: "Student Name",
        },
        {
          prop: "className",
          label: "Class Name",
        },
        {
          prop: "score",
          label: "Concenstration Score",
        },
      ],
      //导出excel表格id及excel名称
      exportExcelInfo: {
        excelId: "record-table",
        excelName: "Concenstration Score.xlsx",
      },
      //需要导出的table数据
      tableData: [
        {
          time: "2016-05-02",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 1518 弄",
          score: "111",
        },
        {
          time: "2016-05-02",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 1518 弄",
          score: "111",
        },
        {
          time: "2016-05-02",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 1518 弄",
          score: "111",
        },
        {
          time: "2016-05-02",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 1518 弄",
          score: "111",
        },
        {
          time: "2016-05-02",
          studentName: "王小虎",
          className: "上海市普陀区金沙江路 1518 弄",
          score: "111",
        },
      ],
    };
  },
  components: {
    exportExcel
  },
  computed: {
    ...mapGetters(["name"]),
  },
  mounted() {
    this.queryCourse();
    this.queryStudent();

    //模拟数据
    // axios.get("/static/mock/data.json").then((res) => {
    //   // console.log(res.data);
    //   myChart.hideLoading();
    //   myChart.setOption({
    //     xAxis: {
    //       data: res.data.time,
    //     },
    //     legend: {
    //       data: res.data.name,
    //     },
    //     series: [
    //       {
    //         name: res.data.name,
    //         data: res.data.attention_value,
    //       },
    //     ],
    //   });
    // });
  },
  methods: {
    queryCourseSearch(queryString, cb) {
      var courses = this.courses;
      var results = queryString
        ? courses.filter(this.createCourseFilter(queryString))
        : courses;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createCourseFilter(queryString) {
      return (course) => {
        return (
          course.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    queryStudentSearch(queryString, cb) {
      var students = this.students;
      var results = queryString
        ? students.filter(this.createStudentFilter(queryString))
        : students;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createStudentFilter(queryString) {
      return (student) => {
        return (
          student.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },

    //需要调用接口
    queryCourse() {
      analysisApi.queryTeacherCourse(this.name).then((res) => {
        for (let item of res.data.courses) {
          this.courses.push({ value: item.cid });
        }
      });
    },
    queryStudent() {
      analysisApi.queryTeacherStudent(this.name).then((res) => {
        for (let item of res.data.studentNames) {
          this.students.push({ value: item });
        }
      });
    },

    //调用接口方法
    queryData() {
      //查询单个学生对应课程专注度
      analysisApi
        .queryStudentConcentration(
          this.studentName,
          this.course,
          this.date + " " + this.startTime,
          this.date + " " + this.endTime
        )
        .then((res) => {});
    },

    exportExcel() {
      this.$refs.myChild.exportExcel();
    },
    clearData() {
      this.tableData=[];
    }
  },
};
</script>

