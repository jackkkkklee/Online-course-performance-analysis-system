<template>
  <div class="index_container">
    <div ref="top_menu" class="top_menu">
      <div class="container">
        <span class="title"
          >Online Classroom Student Performance Analysis System</span
        >
        <span class="login" @click="handleLogin">Login</span>
      </div>
    </div>
    <div class="poster">
      <span class="slogan">Make online course management easier</span>
    </div>
    <div class="main">
      <div class="intr">
        <div style="font-size: 30px">
          Multi-functional and easy-to-use management system
        </div>
        <div style="font-size: 20px; color: rgb(128, 128, 128)">
          Help teachers manage online courses and students, and know students'
          performance in class
        </div>
        <img src="../../../static/image/function_0.png" />
      </div>
      <div class="online" style="margin: 80px auto">
        <img
          ref="online1"
          src="../../../static/image/function_1.png"
          style="width: 450px"
          :style="{
            filter: 'opacity(' + onlineScroll + ')',
            transform: 'translate3d(0px, 0px, 0px) scale(' + onlineScroll + ')',
            transition: 'all 500ms ease 0s',
          }"
        />
        <div
          class="content"
          style="width: 450px; float: right; margin: 100px 15px"
        >
          <div
            ref="online2"
            style="font-size: 30px; margin-bottom: 30px"
            :style="{
              filter: 'opacity(' + onlineScroll + ')',
              transform:
                'translate3d(0px, ' + onlineScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Online Classroom
          </div>
          <div
            ref="online3"
            style="font-size: 20px; color: rgb(128, 128, 128)"
            :style="{
              filter: 'opacity(' + onlineScroll + ')',
              transform:
                'translate3d(0px, ' + -onlineScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Capture images of students and students can see their own
            performance in the class
          </div>
        </div>
      </div>
      <div class="analysis" style="overflow: hidden; margin: 80px auto">
        <div
          class="content"
          style="width: 450px; float: left; margin: 100px 15px"
        >
          <div
            style="font-size: 30px; margin-bottom: 30px"
            :style="{
              filter: 'opacity(' + analysisScroll + ')',
              transform:
                'translate3d(0px, ' + analysisScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Student Attentiveness and Emotion Analysis
          </div>
          <div
            style="font-size: 20px; color: rgb(128, 128, 128)"
            :style="{
              filter: 'opacity(' + analysisScroll + ')',
              transform:
                'translate3d(0px, ' + -analysisScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Analyze students' attentiveness and emotion in class through the
            processing of captured images
          </div>
        </div>
        <img
          src="../../../static/image/function_2.png"
          style="float: right; width: 450px"
          :style="{
            filter: 'opacity(' + analysisScroll + ')',
            transform:
              'translate3d(0px, 0px, 0px) scale(' + analysisScroll + ')',
            transition: 'all 500ms ease 0s',
          }"
        />
      </div>
      <div class="role" style="margin: 80px auto">
        <img
          src="../../../static/image/function_3.png"
          style="width: 450px"
          :style="{
            filter: 'opacity(' + roleScroll + ')',
            transform: 'translate3d(0px, 0px, 0px) scale(' + roleScroll + ')',
            transition: 'all 500ms ease 0s',
          }"
        />
        <div
          class="content"
          style="width: 450px; float: right; margin: 100px 15px"
        >
          <div
            style="font-size: 30px; margin-bottom: 30px"
            :style="{
              filter: 'opacity(' + roleScroll + ')',
              transform:
                'translate3d(0px, ' + roleScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            User and Role Management
          </div>
          <div
            style="font-size: 20px; color: rgb(128, 128, 128)"
            :style="{
              filter: 'opacity(' + roleScroll + ')',
              transform:
                'translate3d(0px, ' + -roleScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Easy to manage all kinds of users and their roles and permissions
          </div>
        </div>
      </div>
      <div class="course" style="overflow: hidden; margin: 80px auto">
        <div
          class="content"
          style="width: 450px; float: left; margin: 100px 15px"
        >
          <div
            style="font-size: 30px; margin-bottom: 30px"
            :style="{
              filter: 'opacity(' + courseScroll + ')',
              transform:
                'translate3d(0px, ' + courseScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Course Management
          </div>
          <div
            style="font-size: 20px; color: rgb(128, 128, 128)"
            :style="{
              filter: 'opacity(' + courseScroll + ')',
              transform:
                'translate3d(0px, ' + -courseScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Convenient to manage the courses of students and teachers
          </div>
        </div>
        <img
          src="../../../static/image/function_4.png"
          style="float: right; width: 450px"
          :style="{
            filter: 'opacity(' + courseScroll + ')',
            transform: 'translate3d(0px, 0px, 0px) scale(' + courseScroll + ')',
            transition: 'all 500ms ease 0s',
          }"
        />
      </div>
      <div class="export" style="margin: 80px auto">
        <img
          src="../../../static/image/function_5.png"
          style="width: 450px"
          :style="{
            filter: 'opacity(' + exportScroll + ')',
            transform: 'translate3d(0px, 0px, 0px) scale(' + exportScroll + ')',
            transition: 'all 500ms ease 0s',
          }"
        />
        <div
          class="content"
          style="width: 450px; float: right; margin: 100px 15px"
        >
          <div
            style="font-size: 30px; margin-bottom: 30px"
            :style="{
              filter: 'opacity(' + exportScroll + ')',
              transform:
                'translate3d(0px, ' + exportScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Data Export
          </div>
          <div
            style="font-size: 20px; color: rgb(128, 128, 128)"
            :style="{
              filter: 'opacity(' + exportScroll + ')',
              transform:
                'translate3d(0px, ' + -exportScrollText + 'px, 0px) scale(1)',
              transition: 'all 500ms ease 0s',
            }"
          >
            Export the analyzed data in Excel format for users to view and store
          </div>
        </div>
      </div>
    </div>
    <div class="footer" style="height: 300px">
      <div
        class="footer_container"
        style="overflow: hidden; width: 1170px; margin: 0 auto"
      >
        <div class="info" style="float: left">
          <p style="font-weight: 700; font-size: 20px">
            Online Classroom Student Performance Analysis
          </p>
          <p style="color: rgb(160, 160, 160)">©2021 CJLU R&D Team 3</p>
        </div>
        <div class="contact">
          <h3>Contact</h3>
          <ol>
            <li>
              <a
                href="https://gitee.com/li_congwang/online-classroom-analysis"
                target="_blank"
                >Gitee</a
              >
            </li>
          </ol>
        </div>
        <div class="more">
          <h3>More</h3>
          <ol>
            <li>Terms of service</li>
            <li>Privacy statement</li>
          </ol>
        </div>
        <div class="about">
          <h3>About</h3>
          <ol>
            <li>
              <a
                href="https://gitee.com/li_congwang/online-classroom-analysis"
                target="_blank"
                >Know us</a
              >
            </li>
            <li>
              <a
                href="https://gitee.com/li_congwang/online-classroom-analysis"
                target="_blank"
                >Join us</a
              >
            </li>
          </ol>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      scroll: 0,
      onlineScroll: 0,
      onlineScrollText: -100,
      analysisScroll: 0,
      analysisScrollText: -100,
      roleScroll: 0,
      roleScrollText: -100,
      courseScroll: 0,
      courseScrollText: -100,
      exportScroll: 0,
      exportScrollText: -100,
    };
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
  },
  methods: {
    handleScroll() {
      // console.log(this.$route);
      this.scroll = document.documentElement.scrollTop;
      // console.log(this.scroll);
      // console.log(this.$refs.top_menu.style.cssText);
      // if (this.scroll < 500) {
      //   this.$refs.top_menu.style.cssText =
      //     "background-color: rgba(255, 255, 255, 0.6);";
      // } else if (this.scroll >= 500 && this.scroll < 700) {
      //   this.$refs.top_menu.style.cssText =
      //     "box-shadow: 0px 1px 30px 0px #e5e5e5;background-color: rgba(255, 255, 255, 0.9);";
      // } else
      if (this.scroll >= 700 && this.scroll <= 1300) {
        //课堂
        this.onlineScroll = (this.scroll - 700) / 600;
        this.onlineScrollText = this.onlineScroll * 100 - 100;
      } else if (this.scroll >= 1300 && this.scroll <= 1800) {
        //分析
        this.analysisScroll = (this.scroll - 1300) / 500;
        this.analysisScrollText = this.analysisScroll * 100 - 100;
      } else if (this.scroll >= 1800 && this.scroll <= 2200) {
        //角色管理
        this.roleScroll = (this.scroll - 1800) / 400;
        this.roleScrollText = this.roleScroll * 100 - 100;
      } else if (this.scroll >= 2200 && this.scroll <= 2500) {
        //课程管理
        this.courseScroll = (this.scroll - 2200) / 300;
        this.courseScrollText = this.courseScroll * 100 - 100;
      } else if (this.scroll >= 2500 && this.scroll <= 3000) {
        //数据导出
        this.exportScroll = (this.scroll - 2500) / 500;
        this.exportScrollText = this.exportScroll * 100 - 100;
      } else {
      }
    },
    handleLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.top_menu {
  position: fixed;
  background-color: rgba(255, 255, 255, 0.8);
  height: 60px;
  width: 100%;
  box-shadow: 0px 1px 30px 0px #e5e5e5;
  z-index: 1;
}
.container {
  margin: auto 150px;
}
.container .title {
  line-height: 60px;
  float: left;
  font-weight: bold;
}
.login {
  line-height: 60px;
  float: right;
  cursor: pointer;
  padding: 0 10px;
  font-weight: bold;
}
.login:hover {
  color: rgba(0, 0, 0, 0.8);
  border-bottom: 2px solid red;
}
.poster {
  height: 800px;
  background: url(../../../static/image/index_background.png) no-repeat;
  background-size: 100%;
}
.poster .slogan {
  color: rgb(85, 85, 85);
  font-size: 35px;
  font-weight: 500;
  position: relative;
  left: 200px;
  top: 300px;
}
.main {
  width: 1140px;
  margin: 60px auto;
}
.intr {
  text-align: center;
}
.intr img {
  width: 880px;
  height: 500px;
  margin: 30px 130px 0 130px;
}
/* .online,
.analysis,
.role,
.course,
.export {
  height: 500px;
} */
.about,
.more,
.contact {
  float: right;
  margin: 0 0 0 100px;
}
li {
  list-style: none;
  margin: 20px 0;
  color: rgb(160, 160, 160);
  cursor: pointer;
}
li:hover {
  color: rgb(216, 216, 216);
}
ol {
  padding: 0;
}
</style>