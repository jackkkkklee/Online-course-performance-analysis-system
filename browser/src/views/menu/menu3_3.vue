<template>
  <div class="login-container">
    <el-card class="main">
      <div id="chart"></div>
      <div style="width: 80px; height: 50px; margin: auto">
        <el-button type="primary" style="margin: auto" @click="refreshData"
          >Refresh</el-button
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

export default {
  name: "Menu3_3",
  data() {
    return {
      pageName: "菜单3-3",
      myChart: null,
    };
  },
  computed: {
    ...mapGetters(["name"]),
  },
  mounted() {
    this.myChart = echarts.init(document.getElementById("chart"));
    this.myChart.setOption({
      title: {
        top: 16,
        text: "Students Emotion",
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

    // 模拟数据
    // axios.get("/static/mock/expression.json").then((res) => {
    //   // console.log(res.data);
    //   myChart.hideLoading();
    //   myChart.setOption({
    //     series: [
    //       {
    //         data: res.data.emotion,
    //       },
    //     ],
    //   });
    // });
    this.refreshData();

    // let chartData = new Array();
    // let chartMap = new Map();
    // chartMap.set('happy',50)
    // chartMap.set('aa',50)
    // chartMap.set('bb',25)
    // chartMap.set('cc',60)
    // chartMap.set('dd',30)
    // for (var [key, mapValue] of chartMap) {
    //   chartData.push({ name: key, value: mapValue });
    // }
    // this.myChart.setOption({
    //   series: [
    //     {
    //       data: chartData,
    //     },
    //   ],
    // });
  },
  methods: {
    //调用接口方法
    refreshData() {
      analysisApi.queryClassEmotion(this.name).then((res) => {
        this.myChart.showLoading();
        let chartData = [];
        let chartMap = new Map();
        chartMap = res.data.emotion.emotionVoMap;
        for (let [key, mapValue] of chartMap) {
          chartData.push({ name: key, value: mapValue });
        }
        myChart.setOption({
          series: [
            {
              data: chartData,
            },
          ],
        });
        this.myChart.hideLoading();
      });
      this.tip();
    },
    tip() {
      this.$message({
        message: "Now the students emotions are poor, please make adjustment!",
        type: "warning",
        duration: 5000,
        showClose: true,
      });
      this.$notify({
        title: "Notice",
        message: "Now the students emotions are poor, please make adjustment!",
        type: "warning",
        duration: 5000,
        offset: 100,
      });
    },
  },
};
</script>

<style scoped>
.main {
  margin: 15px;
}
#chart {
  width: 600px;
  height: 400px;
  margin: auto;
}
</style>