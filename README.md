## 社区论坛

1. 创建Spring Boot项目，导入坐标
2. 初始化仓库，并托管到GitHub上，使用git提交

[GitHub登记入口](https://github.com/settings/applications/1184288)

[BootStrap组件](https://v3.bootcss.com/components/#navbar)

[论坛参考](https://elasticsearch.cn/)

[GitHub登记](https://github.com/settings/developers)

[OKhttp](https://square.github.io/okhttp/)  发送post请求

[GitHub API文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

[Spring Boot + MyBatis](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

[flyway](https://flywaydb.org/getstarted/firststeps/maven)

[lombok坐标](https://projectlombok.org/setup/maven)

[IDEA安装lombok插件](https://www.cnblogs.com/iathanasy/p/9262689.html)

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

携带client_id和redirect_uri去请求用户的github身份
GET https://github.com/login/oauth/authorize?client_id=xx&redirect_uri=xx&scope=user&state=yy

GitHub根据redirect_uri重定向回站点，同时携带授权码code

携带授权码访问access_token接口，请求令牌
POST https://github.com/login/oauth/access_token    请求体:client_id=xx&client_secret=xx&code=xx
获取到的格式如`access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer`,要截取出有用的

携带令牌访问user接口，获取用户真实数据
GET https://api.github.com/user?access_token=xxx
获取到用户信息，是JSON格式的，很多只拿想要的信息

#### 持久化登录状态
1. 分析:先前生产环境下，每一次服务器重新启动，用户都需要去点击登录。这主要是因为在后台session中存储的是gitHubUser
这个对象，这个是根据令牌获取到的。只要服务器一重新启动，这个对象就为空了，在前台页面中他就是根据session域中的user是否
为空来做逻辑判断的。
因此必须要将登录状态持久化到数据库中！！！
    前台逻辑不去碰，还让他去判断session域; 从后台入手，铁定不能存gitHubUser了，要存自己的持久化的user对象，借助cookie去实现 
2. 实现步骤
    1. 用户登录成功，即gitHubUser不为空，就将它存储到数据库中；存完后，添加一个cookie信息，里边是自己生成的token
    2. （此时用户登录成功，我们是让他重定向到根路径/下）来到indexController，之前没有任何逻辑只是简单跳转，现在在
    方法中遍历cookie信息，查找名为token的cookie
    3. 根据value去数据库中查找该用户，如果此时查出来的user不为空，再将session中存储user(我们将第一步的存session
    延迟到了第三步，中间夹杂了持久化操作)

#### 异常处理
1. Spring Boot自动提供了一种/error的错误信息映射,出现错误会自动去找error.html，但是他不能处理404/500这样的异常
    如果我们想要利用好他的映射机制，需要自己去实现，在类上配上@ControllerAdvice注解和处理的异常类型@ExceptionHandler(xxx)，
    想要拿到信息并在客户端展示，也可以添加Model域。这样Spring Boot会自动为进行映射
2. 拦截像404/500这样的异常
    必须自己写Controller，路径也必须是/error，在指定拦截的方法上getMapping注解要加上produces明确指出是html的
    还是JSON格式的。在方法的内部通过提供的方法提取status，根据status（如is4xxClientError）在Model域中添加错误信息