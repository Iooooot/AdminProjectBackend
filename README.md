# AdminProjectBadckend

## 项目介绍

`AdminProjectBadckend`是一套完善的后台管理系统后端项目（对应的[前端](https://github.com/Iooooot/AdminProjectWeb)），该项目集成了众多后端热门中间件技术例如：Redis、SpringSecurity、Elasticsearch、RabbitMQ等。

**系统功能：**

- 系统管理
  - 用户管理
  - 角色管理
  - 菜单管理
- 系统监控
  - 操作日志管理
  - 异常日志管理
  - Kibana可视化
- 组件管理
  - 图标库
  - 富文本编辑工具
  - markdown编辑工具
  - HuTool工具文档
- 小功能
  - 支付宝支付测试功能
  - 会议预约邮件提醒功能

## 详细技术选型

| 技术           | 说明               |
| -------------- | ------------------ |
| SpringBoot     | Web应用开发框架    |
| SpringSecurity | 认证和授权框架     |
| MyBatisPlus    | ORM框架            |
| Elasticsearch  | 搜索引擎           |
| RabbitMQ       | 消息队列           |
| Redis          | 内存数据存储       |
| MongoDB        | NoSql数据库        |
| LogStash       | 日志收集工具       |
| Kibana         | 日志可视化查看工具 |
| Nginx          | 静态资源服务器     |
| Docker         | 应用容器引擎       |
| Druid          | 数据库连接池       |
| OSS            | 对象存储           |
| JWT            | JWT登录支持        |
| Lombok         | Java语言增强库     |
| Hutool         | Java工具类库       |
| Swagger-UI     | API文档生成工具    |
| Alipay-easysdk | 支付宝SDK          |
| Java Mail      | SpringBoot邮件服务 |

## 项目结构

```
AdminProjectBadckend
├── main -- 主要源码
	├── java.com.wht -- 代码包
		├── annotation -- 自定义注解
		├── config -- 各种配置类
		├── controller -- 控制层
		├── entity -- 实体类
		├── exceptions -- 自定义异常类
		├── Filter -- 过滤器类
		├── handler -- 一些全局处理器
		├── service -- service层
		├── mapper -- dao层
		├── utils -- dao层
		└── AdminTemplateApplication.java -- 主启动类
	├── resources -- 配置文件包
		├── mapper -- mybatis响应的mapper.xml
		├── templates -- 一些页面模版
		├── application-demo.yml -- 项目主配置文件
		└── logback-spring.xml -- 日志配置文件
├── test -- 测试
```

## 开发环境

| 工具          | 版本号 | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql         | 8.0    | https://www.mysql.com/                                       |
| Redis         | 7.0    | https://redis.io/download                                    |
| MongoDB       | 5.0    | https://www.mongodb.com/download-center                      |
| RabbitMQ      | 3.10.5 | http://www.rabbitmq.com/download.html                        |
| Nginx         | 1.22   | http://nginx.org/en/download.html                            |
| Elasticsearch | 7.17.3 | https://www.elastic.co/downloads/elasticsearch               |
| Logstash      | 7.17.3 | https://www.elastic.co/cn/downloads/logstash                 |
| Kibana        | 7.17.3 | https://www.elastic.co/cn/downloads/kibana                   |

## 效果展示
![show01](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show01.png)

![show02](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show02.png)

![show03](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show03.png)

![show04](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show04.png)

![show05](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show05.png)

![show06](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show06.png)

![show07](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show07.png)

![show08](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show08.png)

![show09](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show09.png)

![show08](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show08.png)

![show09](https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/show09.png)
