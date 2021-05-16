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
      chartData: [],
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
    // this.timer = setInterval(this.refreshData, 10000);

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
        if (res.data.emotion == []) {
          this.$message({
            message: "No data found",
            type: "warning",
          });
        } else {
          this.chartData = [];
          this.myChart.showLoading();
          let chartMap = res.data.emotion;
          for (let key in chartMap) {
            this.chartData.push({ name: key, value: chartMap[key] });
          }
          this.myChart.setOption({
            series: [
              {
                data: this.chartData,
              },
            ],
          });
          this.myChart.hideLoading();
        }
      });
      this.checkEmotion();
    },
    checkEmotion() {
      if (this.chartData.length != 0) {
        let sum = 0;
        let negative = 0;
        for (let item of this.chartData) {
          sum += item.value;
          if (
            item.name == "disdain" ||
            item.name == "confusing" ||
            item.name == "resist"
          ) {
            negative += item.value;
          }
        }
        if (negative / sum > 0.5) {
          this.tip();
        }
      }
    },
    tip() {
      this.$notify({
        title: "Notice",
        message: "Now the students emotions are poor!",
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