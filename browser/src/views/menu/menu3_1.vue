<template>
  <div class="menu-container">
    <el-card class="main">
      <div class="selector">
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
        <el-button type="primary" @click="queryData">Search </el-button>
      </div>
      <div id="chart"></div>
    </el-card>
  </div>
</template>

<script>
import echarts from "echarts";
import axios from "axios";
import analysisApi from "@/api/analysis";
import { mapGetters } from "vuex";

export default {
  name: "Menu3_1",
  data() {
    return {
      pageName: "菜单3-1",
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
    };
  },
  computed: {
    ...mapGetters(["name"]),
  },
  mounted() {
    this.queryCourse();
    this.queryStudent();
    this.myChart = echarts.init(document.getElementById("chart"));
    this.myChart.setOption({
      backgroundColor: "#394056",

      title: {
        top: 16,
        text: "Student Concentration Score",
        textStyle: {
          fontWeight: "normal",
          fontSize: 16,
          color: "#F1F1F3",
        },
        left: "1%",
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          lineStyle: {
            color: "#57617B",
          },
        },
      },
      legend: {
        top: 20,
        icon: "rect",
        itemWidth: 14,
        itemHeight: 5,
        itemGap: 13,
        data: [],
        right: "4%",
        textStyle: {
          fontSize: 12,
          color: "#F1F1F3",
        },
      },
      grid: {
        top: 100,
        left: "3%",
        right: "4%",
        bottom: "2%",
        containLabel: true,
      },
      xAxis: {
        type: "category",
        boundaryGap: false,
        axisLine: {
          lineStyle: {
            color: "#57617B",
          },
        },
        data: [],
      },
      yAxis: [
        {
          type: "value",
          name: "score",
          axisTick: {
            show: false,
          },
          axisLine: {
            lineStyle: {
              color: "#57617B",
            },
          },
          axisLabel: {
            margin: 10,
            textStyle: {
              fontSize: 14,
            },
          },
          splitLine: {
            lineStyle: {
              color: "#57617B",
            },
          },
        },
      ],
      series: [
        {
          name: "",
          type: "line",
          smooth: true,
          symbol: "circle",
          symbolSize: 5,
          showSymbol: false,
          lineStyle: {
            normal: {
              width: 1,
            },
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(
                0,
                0,
                0,
                1,
                [
                  {
                    offset: 0,
                    color: "rgba(137, 189, 27, 0.3)",
                  },
                  {
                    offset: 0.8,
                    color: "rgba(137, 189, 27, 0)",
                  },
                ],
                false
              ),
              shadowColor: "rgba(0, 0, 0, 0.1)",
              shadowBlur: 10,
            },
          },
          itemStyle: {
            normal: {
              color: "rgb(137,189,27)",
              borderColor: "rgba(137,189,2,0.27)",
              borderWidth: 12,
            },
          },
          data: [],
        },
      ],
    });
    // 模拟数据
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
        let courseSet = new Set();
        for (let item of res.data.courses) {
          courseSet.add(item);
        }
        for (let item of courseSet) {
          this.courses.push({ value: item });
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
        .then((res) => {
          if (res.data.attention_value.length == 0) {
            this.$message({
              message: "No data found",
              type: "warning",
            });
          }
          this.myChart.showLoading();
          this.myChart.setOption({
            xAxis: {
              data: res.data.time,
            },
            legend: {
              data: this.studentName,
            },
            series: [
              {
                name: this.studentName,
                data: res.data.attention_value,
              },
            ],
          });
          this.myChart.hideLoading();
        });
    },
  },
};
</script>

<style scoped>
.main {
  margin: 15px;
}
.selector {
  width: 1150px;
  margin: auto;
}
#chart {
  width: 600px;
  margin: 20px auto 20px auto;
  height: 400px;
}
</style>
