## weread-note-tool
 
微信读书默认导出的笔记不支持markdown格式，此项目就是支持将微信读书的笔记转为markdown格式，目前只支持转换从微信读书复制出来的笔记格式，其他的非微信读书笔记格式转换会出错，需要注意下。
 
## 如何使用

- 确保系统已经安装 JDK
- 在 [release](https://github.com/superleeyom/weread-note-tool/releases) 中下载 jar 包到本地
- 进入到 jar 包所在目录，执行命令 `java -jar weread-note-tool-0.0.1-SNAPSHOT.jar`
- 浏览器访问：`http://localhost:9370/`
- 将微信读书笔记粘贴进文本框，点击提交，然后会下载转换好的 markdown 文件
 
## 本地开发

- 用 IDEA 导入项目
- 执行`WereadNoteToolApplication.java`，启动项目
- 浏览器访问：`http://localhost:9370/`
 
## 组件
- springboot 2.2.5.RELEASE
- layui 2.5.6
 
## 截图
 
- ![](http://image.leeyom.top/blog/20200316161728.png)
- ![](https://raw.githubusercontent.com/superleeyom/blog/main/img/iShot2022-01-13%2017.42.06.png)
