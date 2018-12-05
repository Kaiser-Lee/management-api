族谱系统

java 项目 采用前后端分离的模式

后台：springboot + dubbo + zk ,使用dubbo进行rpc调用，将项目拆分成service和api两部分
本地接口测试：http://localhost:8080/swagger-ui.html#/
dubbo地址：http://122.114.110.171:8080/governance/services

建立maven远程仓库（Nexus）对公共模块进行管理,地址：http://122.114.110.171:8081/nexus/#welcome

本项目采用sso认证中心模式，登录由认证中心统一管理，该模块不提供登录接口，认证中心地址 http://122.114.110.171:8086
