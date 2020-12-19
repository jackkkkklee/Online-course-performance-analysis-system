<template>
  <div class="login-container">
    <h1 style="text-align: center">{{ pageName }}</h1>
    <el-card style="margin: 10px">
      <div id="chart" style="width: 600px; height: 400px; margin: auto"></div>
      <div style="width: 80px; height: 50px; margin: auto">
        <el-button type="primary" style="margin: auto" @click="refreshData">Refresh</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import echarts from "echarts";
import axios from "axios";
import analysisApi from '@/api/analysis';

export default {
  name: "Menu3_3",
  data() {
    return {
      pageName: "菜单3-3",
    };
  },
  mounted() {
    var myChart = echarts.init(document.getElementById("chart"));
    myChart.showLoading();

    myChart.setOption({
      title: {
        top: 16,
        text: "Student Emotion",
        textStyle: {
          fontWeight: "normal",
          fontSize: 20,
          color: "black",
        },
        left: "1%",
      },
      legend: {},
      tooltip: {},
      series: [
        {
          name: "Status",
          type: "pie",
          radius: "60%",
          data: [],
        },
      ],
      itemStyle: {
        normal: {
          shadowBlur: 100,
          shadowColor: "rgba(0, 0, 0, 0.5)",
        },
      },
    });

    //模拟数据
    axios.get("/static/mock/expression.json").then((res) => {
      // console.log(res.data);
      myChart.hideLoading();
      myChart.setOption({
        series: [
          {
            data: res.data.emotion,
          },
        ],
      });
    });

    //调用接口方法
    queryClassEmotion(teacherName).then(res => {

    });
  },
  methods: {

    //调用接口方法
    refreshData(teacherName) {
      queryClassEmotion(teacherName).then(res => {
    })
    }
  },
};
</script>

