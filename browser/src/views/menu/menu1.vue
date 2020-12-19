<template>
  <div class="menu-container">
    <h1 style="text-align: center">{{ pageName }}</h1>
    <div style="margin: auto">
      <!--使用video标签调用摄像头-->
      <video id="camera" ref="camera" width="300" height="300" autoplay></video>
      <!--创建一个cavas  用来存放图片-->
      <canvas id="canvas" width="300" height="300"></canvas>
    </div>
    <div class="button-box" style="margin: auto">
      <el-button
        type="primary"
        style="margin: auto"
        @click="openCamera"
        v-show="isShow"
      >
        Start Course</el-button
      >
      <el-button type="primary" style="margin: auto" @click="stopNavigator"
        >Finish Course</el-button
      >
    </div>
  </div>
</template>


<script>
import analysisApi from '@/api/analysis'

export default {
  name: "Menu1",
  data() {
    return {
      pageName: "Online Course",
      isShow: true,
    };
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
        this.isShow = !this.isShow;
        this.timer = setInterval(this.takePhoto, 1000);  //拍照间隔设置
      } else {
        alert("不支持访问用户媒体");
      }
    },
    takePhoto() {
      var video = document.getElementById("camera");
      var canvas = document.getElementById("canvas");
      var context = canvas.getContext("2d");
      context.drawImage(video, 0, 0, 300, 300);
      var imgData = canvas.toDataURL("image/png");
      //Canvas2Image.saveAsImage(canvas,300,300,"png","image1");
    },
    stopNavigator() {
      if (this.isShow == false) {
        var video = document.getElementById("camera");
        video.srcObject.getTracks()[0].stop();
        this.isShow = !this.isShow;
      }else{
        alert("You have not started course!");
      }
    },

    //调用接口方法
    checkCourse() {
      //查询该学生当前是否有课
      queryStudentCourse(studentName, time).then(res => {

      })
    }
    // //图片上传到服务器
    // //获取Canvas的编码。
    // function uploadImage(){
    //     canvas.width = 300;
    //     canvas.height = 300;
    //     context.drawImage(video, 0, 0, 300, 300);
    //     var imgData = canvas.toDataURL();
    //     //上传到后台。
    //     var uploadAjax = $.ajax({
    //         type: "post",
    //         //后端需要调用的地址
    //         url:"test",
    //         data: JSON.stringify({"imgData": imgData}),
    // 		contentType:"json/application",
    // 		//设置超时
    //         timeout:10000,
    //         async: true,
    //         success: function (htmlVal) {
    //         	//成功后回调
    // 		},
    //         error: function(data) {
    //         },
    //         //调用执行后调用的函数
    //         complete: function (XMLHttpRequest, textStatus) {
    //             if(textStatus == 'timeout'){
    //                 uploadAjax.abort(); //取消请求
    //                 //超时提示：请求超时，请重试
    //                 alert("请求超时，请重试")
    //                 //请求超时返回首页
    //                 closeCard();
    //             }
    //         }
    //     });
    // }
    // 	$(function(){     --需要导入jquery包
    // 		$("#photo").click(function(){
    // 			uploadImage()
    // 		})
    // 	})
  },
};
</script>

<style scoped>
.menu-container {
  position: absolute;
  left: 30%;
}
</style>


