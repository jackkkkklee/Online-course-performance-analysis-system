<template>
  <div class="login-container">
    <el-form class="login-form" autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm"
             label-position="left">
      <div class="title-container">
        <h3 class="title">Login</h3>
      </div>
      <el-form-item prop="username">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user"/>
        </span>
        <el-input name="username" type="text" v-model="loginForm.username" autoComplete="on" placeholder="Account"/>
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input name="password" :type="passwordType" @keyup.enter.native="handleLogin" v-model="loginForm.password"
                  autoComplete="on" placeholder="Password"/>
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye"/>
        </span>
      </el-form-item>
<<<<<<< HEAD
      <el-button type="primary" style="width:100%;margin-bottom:30px;" :loading="loading"
=======
      <el-button class="btn" type="primary" style="width:100%;margin-bottom:30px;" :loading="loading"
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
                 @click.native.prevent="handleLogin">Login
      </el-button>
      <div class="tips">
        <span>Admin Account : admin</span>
        <span>Password : 123456</span>
      </div>
      <div class="tips">
        <span style="margin-right:18px;">Student Account: stu001</span>
        <span>Password : 123456</span>
      </div>
      <div class="tips">
        <span style="margin-right:18px;">Teacher Account: tea001</span>
        <span>Password : 123456</span>
      </div>

    </el-form>


  </div>
</template>

<script>
  import {isvalidUsername} from '@/utils/validate'
  import LangSelect from '@/components/LangSelect'

  export default {
    components: {LangSelect},
    name: 'login',
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!isvalidUsername(value)) {
          callback(new Error('Please enter the correct user account'))
        } else {
          callback()
        }
      }
      const validatePassword = (rule, value, callback) => {
        if (value.length < 6) {
          callback(new Error('Password cannot be less than 6 characters'))
        } else {
          callback()
        }
      }
      return {
        loginForm: {
          username: 'admin',
          password: '123456'
        },
        loginRules: {
          username: [{required: true, /*message: '请输入帐号', */ trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePassword}]
        },
        passwordType: 'password',
        loading: false,
        showDialog: false
      }
    },
    methods: {
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true
            this.$store.dispatch('LoginByUsername', this.loginForm).then(() => {
              this.loading = false
              this.$router.push({path: '/'})
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
    },
  }
</script>

<style rel="stylesheet/scss" lang="scss">
  $bg: #2d3a3b;
  $light_gray: #eee;

  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;
      input {
<<<<<<< HEAD
        background: transparent;
=======
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
<<<<<<< HEAD
        color: $light_gray;
=======
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
        height: 47px;
        &:-webkit-autofill {
          -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: #fff !important;
        }
      }
    }
    .el-form-item {
<<<<<<< HEAD
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
=======
      background-color:  rgba(255, 255, 255, 0.5);
      border: 1px solid rgba(0, 0, 0, 0.3);
      border-radius: 5px;
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
    }
  }
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;

  .login-container {
<<<<<<< HEAD
    position: fixed;
    height: 100%;
    width: 100%;
    background-color: $bg;
=======
    background: url(../../../static/image/login_background.png) no-repeat;
    background-size:100% 100%;
    position: fixed;
    height: 100%;
    width: 100%;
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
    .login-form {
      position: absolute;
      left: 0;
      right: 0;
      width: 520px;
<<<<<<< HEAD
      padding: 35px 35px 15px 35px;
      margin: 120px auto;
    }
    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;
=======
      background-color:  rgba(255, 255, 255, 0.5);
      border: 1px solid rgba(0, 0, 0, 0.5);
      border-radius: 10px;
      padding: 35px 35px 15px 35px;
      margin: 120px auto;
    }
    .btn {
      font-weight: bold;
    }
    .tips {
      font-size: 14px;
      margin-bottom: 10px;
      font-weight: bold;
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }
    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
      &_login {
        font-size: 20px;
      }
    }
    .title-container {
      position: relative;
      .title {
        font-size: 26px;
        font-weight: 400;
<<<<<<< HEAD
        color: $light_gray;
=======
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
    }
    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
    .thirdparty-button {
      position: absolute;
      right: 35px;
      bottom: 28px;
    }
  }
</style>
