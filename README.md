
## 技术交流

## 平台介绍

## 平台优势

## 技术选型

* 主框架：Spring Boot 2.2、Spring Framework 5.2、Apache Shiro 1.6、J2Cache
* 持久层：Apache MyBatis 3.5、Hibernate Validator 6.0、Alibaba Druid 1.1
* 视图层：Spring MVC 5.2、Beetl 3.1（替换JSP）、Bootstrap 3.3、AdminLTE 2.4
* 前端组件：jQuery 3.4、jqGrid 4.7、layer 3.1、zTree 3.5、jquery validation
* 工作流引擎：Flowable 6.5、符合 BPMN 规范、在线流程设计器、中国式工作流
## 内置功能

## 生态系统

## 快速体验

### 在线演示

### 本地运行

1. 环境准备：`JDK 1.8 or 11`、`Maven 3.6+`、`MySQL 5.7 or 8.0`
2. 执行命令：`git clone https://gitee.com/thinkgem/jeesite4.git` 下载源码
3. 打开文件：`/web/src/main/resources/config/application.yml` 配置JDBC连接
4. 执行脚本：`/web/bin/init-data.bat` 初始化数据库
5. 执行脚本：`/web/bin/run-tomcat.bat` 启动服务即可

### 开发环境

## 在线文档

## 授权协议声明

1. 已开源的代码，授权协议采用 AGPL v3 + Apache Licence v2 进行发行。
2. 您可以免费使用、修改和衍生代码，但不允许修改后和衍生的代码做为闭源软件发布。
3. 修改后和衍生的代码必须也按照AGPL协议进行流通，对修改后和衍生的代码必须向社会公开。
4. 如果您修改了代码，需要在被修改的文件中进行说明，并遵守代码格式规范，帮助他人更好的理解您的用意。
5. 在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议、版权声明和其他原作者规定
需要包含的说明（请尊重原作者的著作权，不要删除或修改文件中的`@author`信息）。
6. 您可以应用于商业软件，但必须遵循以上条款原则（请协助改进本作品）。
7. 您若套用本平台的一些代码或功能参考，需要在您的软件介绍明显位置说明出处。
8. 请知悉社区版，用户数不可超过100个，最大允许20个用户同时在线（不含匿名）。
9. 无限制版下载：<https://gitee.com/thinkgem/jeesite4/tree/v4.0_dev/>

## 技术服务与支持

* 没有资金的支撑就很难得到发展，特别是一个好的产品，如果 JeeSite 帮助了您，请为我们点赞。支持我们，您可以得到一些回报，有了这些我们会把公益事业做的更好，回报社区和社会，请给我们一些动力吧，在此非常感谢已支持我们的朋友！
* **联系方式（官方商务）QQ：[1766571055](http://wpa.qq.com/msgrd?v=3&uin=1766571055&site=qq&menu=yes)**
* 技术服务支持网页：<http://s.jeesite.com>

## 今后如何升级？

尽量不修改 web 项目以外的源码项目，如 jeesite-common、jeesite-modele-core，如果修改了，请 Pull Requests 上来，否则代码与官方不同步，会将对你的日后升级增加难度。

如果您修改了依赖模块代码，也没关系，这时你需要利用 Git 版本控制工具，与官方仓库代码进行同步，合并代码即可。

在这里，你可以看到 JeeSite 新增哪些新功能和改进，在每个版本下都有对应升级方法。

# Git 全局设置技巧

```
1、提交检出均不转换换行符

git config --global core.autocrlf false

2、拒绝提交包含混合换行符的文件

git config --global core.safecrlf true
```
