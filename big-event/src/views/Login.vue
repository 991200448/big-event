<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//定义数据模型
const registerData = ref({
    username: '',
    password: '',
    rePassword: ''
})
//自定义确认密码的校验函数
const rePasswordValid = (rule, value, callback) => {
    if (value == null || value === '') {
         callback(new Error('请再次确认密码'))
    }
    if ( value !== registerData.value.password ) {
         callback(new Error('输入密码不一致'))
    }else{
        callback() 
    }

}
//定义表单校验规则
const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
    ], 
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' } 
    ],
    rePassword: [
        { validator: rePasswordValid, trigger: 'blur' }

    ]
}
import { ElMessage } from 'element-plus'
//注册函数
import {userRegisterService} from "@/api/user.js"
const register =  async () => {
   let result = await userRegisterService(registerData.value);
   ElMessage.success('注册成功');
}
// 添加记住我状态
const rememberMe = ref(false)

// 登录函数
import {userLoginService} from "@/api/user.js"
import { useRouter } from 'vue-router'
// 导入token状态
import { useTokenStore } from '@/stores/token.js'

// 调用useTokenStore得到状态
const tokenStore = useTokenStore();
const router = useRouter();
const login = async () => {
    let result = await userLoginService(registerData.value);
    // 保存token
    tokenStore.setToken(result.data)
    
    // 处理记住我功能
    if (rememberMe.value) {
        // 如果勾选了记住我，将用户信息存储到本地
        localStorage.setItem('loginInfo', JSON.stringify({
            username: registerData.value.username,
            password: registerData.value.password
        }))
    } else {
        // 如果没有勾选记住我，清除本地存储
        localStorage.removeItem('loginInfo')
    }
    
    ElMessage.success('登录成功');
    // 登录成功后，跳转到首页
    router.push('/')
}

// 添加回车键登录处理函数
const handleEnterLogin = () => {
  if (!isRegister.value) {
    login()
  }
}

// 表单重置函数
const resetForm = () => {
    registerData.value = {
        username: '',
        password: '',
        rePassword: ''
    }
}

// 页面加载时检查是否有存储的登录信息
onMounted(() => {
    const loginInfo = localStorage.getItem('loginInfo')
    if (loginInfo) {
        const { username, password } = JSON.parse(loginInfo)
        registerData.value.username = username
        registerData.value.password = password
        rememberMe.value = true
    }
})
</script>
<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" >
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click ="register">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;resetForm()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules" @keyup.enter="handleEnterLogin">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop ="username">
                    <el-input name="username" :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop ="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox v-model="rememberMe">记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click = "login">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;resetForm()">
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>