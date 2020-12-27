<template>
  <div class="menu-container">
    <el-card style="margin: 15px">
      <div style="margin: auto">
        <!--使用video标签调用摄像头-->
        <video
          id="camera"
          style="margin: auto; width: 300px; height: 300px"
          autoplay
        ></video>
        <!--创建一个cavas  用来存放图片-->
        <canvas
          id="canvas"
          style="margin: auto; width: 300px; height: 300px"
        ></canvas>
        <span>
            <p>Attention Value: {{ attention_value }}</p>
            <p>Yawn Status: {{ yawnStatus }}</p>
            <p>Sleep Chance: {{ sleepChance }}</p>
            <p>Has Face: {{ hasFace }}</p>
            <p>Is Smoking: {{ isSmoking }}</p>
            <p>Is using phone: {{ isUsingPhone }}</p>
            <p>Non-learning item: {{ prohibitItem }}</p>
        </span>
      </div>
      <div class="button_container" style="margin: auto">
        <el-button
          type="primary"
          style="margin: auto"
          @click="openCamera"
          :disabled="!hasCourse"
        >
          Start Course</el-button
        >
        <el-button
          type="primary"
          style="margin: auto"
          @click="stopNavigator"
          :disabled="!onCourse"
          >Finish Course</el-button
        >
        <el-button
          type="primary"
          style="margin: auto"
          @click="changeModel"
          :disabled="!onCourse"
          >Change model</el-button
        >

      </div>
    </el-card>
  </div>
</template>


<script>
import analysisApi from "@/api/analysis";
import sendImageApi from "@/api/sendImage";
import { mapGetters } from "vuex";

export default {
  name: "Menu1",
  data() {
    return {
      hasCourse: false,
      onCourse: false,
      course: null,
      showData: true,
      attentionValue: null,
      yawnStatus: null,
      sleepChance: null,
      isSmoking: null,
      isUsingPhone: null,
      prohibitItem: null,
      hasFace: null,
      model: 1
    };
  },
  computed: {
    ...mapGetters(["name"]),
  },
  mounted() {
    this.checkCourse();
  },
  methods: {
    getUserMedia(constraints, success, error) {
      if (navigator.mediaDevices.getUserMedia) {
        //最新的标准API
        navigator.mediaDevices
          .getUserMedia(constraints)
          .then(success)
          .catch(error);
      } else if (navigator.webkitGetUserMedia) {
        //webkit核心浏览器
        navigator.webkitGetUserMedia(constraints, success, error);
      } else if (navigator.mozGetUserMedia) {
        //firfox浏览器
        navigator.mozGetUserMedia(constraints, success, error);
      } else if (navigator.getUserMedia) {
        //旧版API
        navigator.getUserMedia(constraints, success, error);
      }
    },
    success(stream) {
      //兼容webkit核心浏览器
      // var CompatibleURL = window.URL || window.webkitURL;
      // this.$refs.camera.src = CompatibleURL.createObjectURL(stream);
      //将视频流设置为video元素的源
      var video = document.getElementById("camera");
      video.srcObject = stream;
      video.play();
    },
    error(error) {
      console.log("访问用户媒体设备失败${error.name}, ${error.message}");
    },

    openCamera() {
      if (
        navigator.mediaDevices.getUserMedia ||
        navigator.getUserMedia ||
        navigator.webkitGetUserMedia ||
        navigator.mozGetUserMedia
      ) {
        this.getUserMedia(
          { video: { width: 300, height: 300 } },
          this.success,
          this.error
        );
        this.hasCourse = !this.hasCourse;
        this.onCourse = !this.onCourse;
        this.timer = setInterval(this.uploadImage, 1000); //拍照间隔设置
      } else {
        alert("不支持访问用户媒体");
      }
    },

    stopNavigator() {
      clearInterval(this.timer);
      var video = document.getElementById("camera");
      video.srcObject.getTracks()[0].stop();
      this.hasCourse = !this.hasCourse;
      this.onCourse = !this.onCourse;
    },

    //调用接口方法
    checkCourse() {
      //查询该学生当前是否有课
      analysisApi.queryStudentHasCourse(this.name).then((res) => {
        this.hasCourse = res.data.result.hasCourse;
        if (this.hasCourse) {
          this.course = res.data.result.courseName;
          alert("You have " + this.course + " course right now!");
        }
      });
    },
    //图片上传到服务器
    //获取Canvas的编码。
    uploadImage() {
      var video = document.getElementById("camera");
      var canvas = document.getElementById("canvas");
      var context = canvas.getContext("2d");
      context.drawImage(video, 0, 0, 300, 300);
      var imgData = canvas.toDataURL();

      //上传到后台。
      sendImageApi.sendImage(imgData, this.name, this.course, new Date(), this.showData).then((res) => {
        this.attentionValue = res.data.AttentionDetailVo.attentiveness;
        this.yawnStatus = res.data.AttentionDetailVo.yawn_status;
        this.sleepChance = res.data.AttentionDetailVo.sleep_chance;
        this.prohibitItem = res.data.AttentionDetailVo.;
        this.hasFace = res.data.AttentionDetailVo.;
      });
    },
  },
};
</script>

<style scoped>
.menu-container {
  position: absolute;
  left: 30%;
}
.button_container {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
</style>


