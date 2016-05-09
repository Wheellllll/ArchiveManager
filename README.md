## 归档模块
> 这是一个简单的归档管理构件，可以自由添加归档目录，以及提供加密功能

### ChangeLog

#### v1.0
- 诞生

### 安装

#### Maven
待上传

#### 手工下载
从 https://github.com/Wheellllll/PerformanceManager/releases 下载最新的jar包添加到项目依赖里去

### 使用说明


### ArchiveManager
本模块负责定时将需要归档的目录打包并保存，并根据需要加密，首先创建一个实例：
```java
ArchiveManager am = new ArchiveManager();
```

#### 指定输出文件夹和输出文件名
输出日志的文件命名方式如下所示，注意，这里不能指定后缀名，因为我们只支持zip格式的压缩：

`${prefix} ${date}.zip`

比如说：
```
prefix=server
date=yyyy-MM-dd
```
则生成的压缩文件名称会长成这个样子：

- `server 2016-04-21.zip`
- `server 2016-04-22.zip`
- `server 2016-04-23.zip`

请注意，如果你使用的是`Windows系统`，请不要在文件名中出现`":"`字符，否则你的电脑会爆炸

前缀，后缀和日期格式可以通过以下方法指定
```java
intervalLogger.setLogDir("./archive");        //输出到当前工作目录下的archive文件夹里
intervalLogger.setLogPrefix("server");        //设置日志的前缀为server
intervalLogger.setDateFormat("yyyy-MM-dd");  //日期格式类似2016-04-21
```
更多关于日期的格式化方法请参考 [https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)

#### 指定输出的时间间隔
```java
am.setInterval(1, TimeUnit.DAYS);  //每隔1天压缩一次
am.setInitialDelay(1);             //延时1天后执行
```

#### 添加需要压缩的路径
```
am.addFolder("./log");
am.addFolder("./llog");
```

#### 选择是否需要加密（默认为否）
am.setEncrypt(true);
am.setPassword("1234");

#### 启动
```java
am.start();
```

#### 关闭
```java
am.stop();
```

###一个完整的例子
```java
ArchiveManager am = new ArchiveManager();
//初始化archiveManager
am.setDatePattern("yyyy-MM-dd");
am.addFolder("./clientarchive");
am.setInterval(7, TimeUnit.SECONDS);
am.setInitialDelay(1);
am.setEncrypt(true);
am.setPassword("aaa");
am.start();
```
