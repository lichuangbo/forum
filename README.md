## 社区论坛

1. 创建Spring Boot项目，导入坐标
2. 初始化仓库，并托管到GitHub上，使用git提交

[GitHub登记入口](https://github.com/settings/applications/1184288)

[BootStrap组件](https://v3.bootcss.com/components/#navbar)

[论坛参考](https://elasticsearch.cn/)

[GitHub登记](https://github.com/settings/developers)

[OKhttp](https://square.github.io/okhttp/)  发送post请求

[GitHub API文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)


#### GitHub登录流程
    > 第三方登录，要OAuth授权。如用户想登录论坛，但是论坛让用户提供第三方网站的数据，这样用户可以不用注册新账号，就可以证明自己的身份
0. 要获取OAuth授权，必须去GitHub上登记，填写登记表
1. 用户访问论坛，点击登录，调用authorize接口去访问github（此时要在url中携带client_id和redirect_uri）
2. 用户进入github会要求用户登录，此时用户可以选择登录；登录成功后，github会询问用户，该应用在请求用户数据，询问是否授权
3. github拿到用户权限回调，跳转到指定的redirect-uri地址，同时携带授权码code
4. 论坛拿到授权码，调用access_token接口，并且携带code去访问github，此次访问是想获取请求令牌
5. github响应，并返回一段JSON数据，里面包含了令牌access_token
6. 拿到令牌，论坛携带access_token,调用GitHub api请求用户的真实数据
7. 论坛将用户信息存入数据库，更新登录状态

请求用户的github身份
GET https://github.com/login/oauth/authorize?client_id=xx&redirect_uri=xx&scope=user&state=yy

GitHub将用户重定向回站点
POST https://github.com/login/oauth/access_token    请求体:client_id=xx&client_secret=xx&code=xx
获取到的格式如`access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer`,要截取出有用的

论坛使用访问令牌访问GitHub API
GET https://api.github.com/user?access_token=xxx
获取到用户信息，是JSON格式的，很多只拿想要的信息
