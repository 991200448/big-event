<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import {useRouter} from 'vue-router'
const router = useRouter();
// 假设存在密码重置的API服务
import { resetPasswordService } from '@/api/user.js'

// 表单数据
const formData = ref({
  old_pwd: '',
  new_pwd: '',
  re_pwd: ''
})

// 验证规则
const rules = {
  old_pwd: [
    { required: true, message: '请输入旧密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度需在6-20位之间', trigger: 'blur' }
  ],
  new_pwd: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度需在6-20位之间', trigger: 'blur' }
  ],
  re_pwd: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== formData.value.new_pwd) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 重置密码函数
const resetPassword = async () => {
  try {
    if (!formData.value.old_pwd || !formData.value.new_pwd || !formData.value.re_pwd) {
      ElMessage.error('请填写所有必填项')
      return 
    }
    await resetPasswordService(formData.value)
    ElMessage.success('密码重置成功')
    //重新登录
    router.push('/login')
  } catch (error) {
    ElMessage.error('密码重置失败，请检查输入')
  }
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>重置密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form 
          :model="formData" 
          :rules="rules" 
          label-width="100px" 
          size="large"
          ref="passwordForm"
        >
          <el-form-item label="旧密码" prop="old_pwd">
            <el-input 
              v-model="formData.old_pwd" 
              type="password" 
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="new_pwd">
            <el-input 
              v-model="formData.new_pwd" 
              type="password" 
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="re_pwd">
            <el-input 
              v-model="formData.re_pwd" 
              type="password" 
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button 
              type="primary" 
              @click="resetPassword"
              :loading="isLoading"
            >
              确认修改
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>
/* 沿用之前的容器样式，无需重复定义 */
</style>