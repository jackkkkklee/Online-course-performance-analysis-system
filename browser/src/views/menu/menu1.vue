<template>
  <div class="menu-container">
    <el-card style="margin: 15px">
      <div style="margin: auto">
        <!--使用video标签调用摄像头-->
        <video
          id="camera"
          style="margin: auto"
          width="300"
          height="300"
          autoplay
        ></video>
        <!--创建一个cavas  用来存放图片-->
        <canvas
          id="canvas"
          style="margin: auto"
          width="300"
          height="300"
        ></canvas>
        <span>
          <p>Attention Value: {{ attentionValue }}</p>
          <p>Yawn Status: {{ yawnStatus }}</p>
          <p>Sleep Chance: {{ sleepChance }}</p>
          <p>Has Face: {{ hasFace }}</p>
          <p v-show="mode == '123'">Is Smoking: {{ isSmoking }}</p>
          <p v-show="mode == '123'">Is using phone: {{ isUsingPhone }}</p>
          <p v-show="mode == '123'">Non-learning item: {{ prohibitItem }}</p>
          <p :class="emotionClass" v-show="mode.indexOf('4') != -1">
            Emotion: {{ emotion }}
          </p>
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
          @click="changeMode"
          :disabled="!onCourse"
          >Change Mode</el-button
        >
        <el-button
          type="primary"
          style="margin: auto"
          @click="emotionMode"
          :disabled="!onCourse"
          >Emotion Mode</el-button
        >
        <el-switch
          v-model="switchState"
          active-text="Netwrok Traffic Saving Mode"
          inactive-text="Normal Mode"
          @change="changeSendMode"
        >
        </el-switch>
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
      attentionValue: 0,
      yawnStatus: "",
      sleepChance: "",
      isSmoking: "",
      isUsingPhone: "",
      prohibitItem: "",
      hasFace: "",
      mode: "1",
      sendInterval: 2000,
      switchState: false,
      emotion: "",
      emotionClass: ""
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
        this.timer = setInterval(this.uploadImage, this.sendInterval); //拍照间隔设置
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
      context.drawImage(video, 0, 0);
      var imgData = canvas.toDataURL();

      //上传到后台。
      sendImageApi
        .sendImage(
          imgData,
          this.name,
          this.course,
          new Date(),
          this.showData,
          this.mode
        )
        .then((res) => {
          this.attentionValue = res.data.attentionDetailVo.attentionValue;
          this.yawnStatus = res.data.attentionDetailVo.yawnStatus;
          this.sleepChance = res.data.attentionDetailVo.sleepChance;
          this.hasFace = res.data.attentionDetailVo.hasFace;
          if (this.mode == "123") {
            this.isSmoking = res.data.attentionDetailVo.smoking;
            this.isUsingPhone = res.data.attentionDetailVo.usingPhone;
            this.prohibitItem = res.data.attentionDetailVo.unClassRelatedItem;
          }
          if (this.mode.indexOf("4") != -1) {
            this.emotion = res.data.attentionDetailVo.expressValue;        
            this.emotionClass = this.emotion;
          }
        });
    },

    // test(){
    //   axios.get("/static/mock/test.json").then((res) => {
    //     // console.log(res.data);
    //     this.attention_Value = res.data.attentionDetailVo.attentionValue;
    //     this.yawnStatus = res.data.attentionDetailVo.yawnStatus;
    //     this.sleepChance = res.data.attentionDetailVo.sleepChance;
    //     this.hasFace = res.data.attentionDetailVo.hasFace;
    //     this.isSmoking = res.data.attentionDetailVo.isSmoking;
    //     // console.log(res.data.attentionDetailVo.isSmoking);
    //     this.isTest = res.data.attentionDetailVo.isTest;
    //   });
    // },
    changeMode() {
      if (this.mode == "1") {
        this.mode = "123";
      } else {
        this.mode = "1";
      }
    },
    changeSendMode(callback) {
      if (this.switchState) {
        this.sendInterval = 5000;
      } else {
        this.sendInterval = 2000;
      }
      // console.log(this.sendInterval);
    },
    emotionMode() {
      if (this.mode.indexOf("4") == -1) {
        this.mode += "4";
      } else {
        this.mode.replace("4", "");
      }
    },
  },
};
</script>

<style scoped>
.menu-container {
  position: absolute;
  left: 25%;
}
.unsure {
  color: blue;
}
.listening {
  color: green;
}
.confusing {
  color: brown;
}
.understanding {
  color: orange;
}
.disdain {
  color: gold;
}
.resist {
  color: red;
}
</style>
