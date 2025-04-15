import request from '@/utils/request.js'
export const userRegisterService = (registerData) => {
    //借助urlsearchparams 把对象转换为url编码的格式
    const params = new URLSearchParams();
    for (const key in registerData) {
        params.append(key, registerData[key])
    }
    return request.post('/user/register', params);

}

export const userLoginService = (loginData) => {
    const params = new URLSearchParams();
    for (const key in loginData) {
        params.append(key, loginData[key])
   
     } 
     return request.post('/user/login', params);
}

//获取个人信息
export const userInfoGetService = ()=>{
    return request.get('/user/info');
}

//退出登录接口
export const userLogoutService = ()=>{
    return request.get('/user/logout');
}

//修改个人信息
export const userInfoUpdateService = (userInfo)=>{
    return request.put('/user/update',userInfo)
}

//修改头像
export const userAvatarUpdateService=(avatarUrl)=>{
    let params = new URLSearchParams();
    params.append('avatarUrl',avatarUrl)
    return request.patch('/user/updateAvatar',params)
}
// 修改密码接口
export const resetPasswordService = (pwdData) => {
    return request.patch('/user/updatePwd', pwdData);
}