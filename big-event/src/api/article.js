//导入请求工具类
import request from '@/utils/request.js';
//文章列表查询
export const articleListService = (params) => {
    return request.get('/article', { params: params })
}

//添加文章
export const articleAddService = (articleModel)=>{
    return request.post('/article',articleModel)
}
//修改文章
export const articleUpdateService = (articleModel)=>{
    return request.put('/article',articleModel)
}
//删除文章
export const articleDeleteService = (id)=>{
    return request.delete('/article?id='+id)
}