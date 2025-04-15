// 导入项目的全局样式文件
import './assets/main.scss'
//导入路由
import router from '@/router/index.js'

// 从 'vue' 库中导入 createApp 函数，用于创建 Vue 应用实例
import { createApp } from 'vue'
// 导入 ElementPlus 组件库
import ElementPlus from 'element-plus'
// 导入 ElementPlus 组件库的样式文件
import 'element-plus/dist/index.css'
//导入pinia
import { createPinia } from 'pinia'
//导入持久化插件
import {createPersistedState} from'pinia-persistedstate-plugin'
// 导入根组件 App
import App from './App.vue'
//中文语言包
import locale from 'element-plus/dist/locale/zh-cn.js'
// 创建 Vue 应用实例
const app = createApp(App);
//创建pinia实例
const pinia = createPinia();
const persist = createPersistedState()
//pinia使用持久化插件
pinia.use(persist)
//使用pinia
app.use(pinia);
// 使用路由插件
app.use(router);
// 将 Vue 应用挂载到 id 为 'app' 的 DOM 元素上
app.mount('#app');
//使用
app.use(ElementPlus,{locale})

