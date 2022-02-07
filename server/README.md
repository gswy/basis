# java-web-server

## 目录结构
```
packages
├── aop                     自定义切面
│   ├── annotation          自定义注解
│   └── aspect              自定义注解实现
├── config                  springboot配置
├── controller              控制器
│   ├── admin
│   └── home
├── entity                  数据实体类
├── exception               全局异常处理类
├── handle                  拦截器、数据库自动填充类
├── iservice                mybatis-plus iservice接口
├── mapper                  mybatis-plus mapper接口
├── service                 mybatis-plus iservice实现
├── request                 表单验证请求
│   ├── admin
│   └── home
├── response                json数据体响应
├── thread                  线程处理
└── utils                   自定义工具类
```

## 配置文件