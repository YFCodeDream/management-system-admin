[TOC]

<div STYLE="page-break-after: always;"></div>

# 一、需求分析

## 1.1 实验目的

1. 能够正确运用《数据库技术》课程的基本理论和知识，结合一个管理信息系统中的模拟课题，复习、巩固、提高数据库方案设计、论证和分析方法。
2. 熟悉关系数据库规范化设计理论，根据实验要求设计并建立科学合理的数据库，正确建立数据库中表与表之间的关系。
3. 进一步正确理解数据库设计思路，培养分析问题、解决问题的能力，提高查询资料和撰写书面文件的能力。

## 1.2 功能需求

考试管理系统应该具备三种使用角色：学生、教师、系统管理员。三种角色对应的权限不同。

因此，依据系统功能分类，以及不同角色功能分类，设计需求如下：

### 数据功能

#### 学生端

1. 登录：选择学生身份登录，输入正确的用户名、密码和验证码登录到系统的首页
2. 基本信息管理：更改自己的学生信息，常见的大学考试系统的学生信息是从在籍学生数据库导入，所以不存在由该考试管理系统进行注册学生的功能。因此，对于该系统的学生端，基本信息管理仅限于查询和修改学生登录该系统的密码
3. 查询考试安排：根据学生的学号，查询学生参与的所有课程的考试安排
4. 查询成绩信息：根据学生的学号，查询学生参与的所有已有的考试成绩
5. 查询课表信息：根据学生的学号，查询学生修的所有课程信息

#### 教师端

1. 登录：选择教师身份登录，输入正确的用户名、密码登录到系统的首页
2. 基本信息管理：更改自己的教师信息，常见的大学考试管理系统的教师信息是从教职工库中导入，所以不存在由该考试管理系统进行注册教师的功能。因此，对于该系统的学生端，基本信息管理仅限于查询和修改教师登录该系统的密码
3. 查询考试安排：根据教师的教职工号，查询教师发布的所有考试安排
4. 发布考试安排：对该教师的某一门课发布考试安排
5. 修改考试安排：对该教师的某一门课修改已有的考试安排
6. 查询成绩信息：根据教师的教职工号，查询教师带的学生的所有成绩
7. 查询授课信息：根据教师的教职工号，查询教师带的所有课程信息

#### 系统管理员端

1. 登录：选择系统管理员身份登录，输入正确的用户名、密码登录到系统的首页
2. 注册：需要一位管理员验证身份，验证完毕后，输入新管理员号，新密码和手机号码，点击注册即可
3. 基本信息管理：查询当前系统管理员的账号，修改当前管理员的手机号和密码
4. 对所有数据表的查询权限：以便专业人员对于数据库进行维护，以便发现异常数据时可以及时通知相关人员进行核实和修改

### 辅助功能

1. 导出当前数据表至xls文件，在本地进行存储
2. 导出所有可查看数据至xls文件，在本地进行存储
3. 将xls文件发送至指定邮箱，可导出当前数据或所有可查看数据，便于当前用户对于数据的提取

#### 学生端

1. 打印成绩单：系统将自动生成当前学生的成绩单（PDF格式），在本地进行存储
2. 将PDF成绩单发送至指定邮箱，方便学生对成绩单的需求
3. 对学生成绩进行可视化展示：系统有“可视化统计”的dashboard，包括排名占比统计、已有成绩统计、最高分和最低分统计，方便学生直观了解自己的成绩情况

#### 教师端

1. 对教师授课的考试成绩可视化展示：系统有“考试统计”的dashboard，包括授课均分统计，统计该教师所有授课中已有考试成绩的课程均分，并以直方图展示，同时给出最高和最低均分以及对应的课程名称
2. 可视化dashboard还具有单次考试可视化功能，输入该教师授课的考试代码，即可统计不同分数段的人数，并以扇形图展示，同时展示当前考试代码对应考试的最高最低分以及对应的学生学号，方便教师直观了解自己的授课考试情况

#### 系统管理员端

1. 查看系统日志：系统日志由log4j生成，为避免系统管理员查看大量与数据操作和系统功能无关的日志，该系统提供了简繁模式切换，系统管理员既可查看完整的后端日志，也可一键切换查看仅与数据操作和系统功能有关的日志，方便系统管理员对整个系统的运维与管理
2. 导出系统日志：系统管理员可导出当前系统日志至txt文件，在本地进行存储
3. 清空日志：系统管理员可一键清空后端日志，使系统重新记录日志

#### 系统快捷键

系统还配备了相应的快捷键操作，方便用户使用。

快捷键列表如下：

##### 登录页

| 功能名称 |  快捷键  |
| :--: | :---: |
|  登录  | Enter |

##### 通用功能

|     功能名称     |            快捷键            |
| :----------: | :-----------------------: |
|     修改信息     |     Ctrl + Shift + U      |
|    登出当前用户    |     Ctrl + Shift + L      |
|     退出系统     |         Ctrl + Q          |
|    显示所有数据    | F5（若F5有系统功能占用，则使用fn + F5） |
|    导出当前数据    |         Ctrl + E          |
|  导出所有可查看数据   |      Ctrl + Alt + E       |
|  分享当前数据至邮箱   |         Ctrl + M          |
| 分享所有可查看数据至邮箱 |      Ctrl + Alt + M       |

##### 系统管理员端

|  功能名称   |      快捷键       |
| :-----: | :------------: |
|  导出日志   | Ctrl + Alt + L |
| 查询考试安排表 |    Ctrl + 1    |
|  查询课程表  |    Ctrl + 2    |
|  查询成绩表  |    Ctrl + 3    |
|  查询学生表  |    Ctrl + 4    |
|  查询教师表  |    Ctrl + 5    |
|  查询选课表  |    Ctrl + 6    |

## 1.3 功能结构图

![](C:\self-documents\studying-resource\senior\management-system\experience\report\课设\考试管理系统.png)

## 1.4 ER图

ER图也称实体-联系图(Entity Relationship Diagram)，提供了表示实体类型、属性和联系的方法，用来描述现实世界的概念模型。

它是描述现实世界关系概念模型的有效方法。是表示概念关系模型的一种方式。用“矩形框”表示实体型，矩形框内写明实体名称;用“椭圆图框”或圆角矩形表示实体的属性，并用“实心线段”将其与相应关系的“实体型”连接起来；用”菱形框“表示实体型之间的联系成因，在菱形框内写明联系名，并用”实心线段“分别与有关实体型连接起来，同时在”实心线段“旁标上联系的类型（1:1,1:n或m:n）。

![](C:\self-documents\studying-resource\senior\management-system\experience\report\课设\ER.bmp)

## 1.5 实体属性表

|    表名     | 对应实体意义 |                           属性名                           |
| :---------: | :----------: | :--------------------------------------------------------: |
|    admin    |    教师表    |                  管理员号，密码，手机号码                  |
| arrangement |  考试安排表  | 考试代码，课程代码，考试日期，起始时间，结束时间，考试地点 |
|   course    |    课程表    |  课程代码，课程名称，教师号，授课地点，授课日期，授课时段  |
|    score    |    成绩表    |                  考试代码，学生学号，分数                  |
|   student   |    学生表    |   学生学号，学生姓名，性别，生日，民族，专业，年级，密码   |
|   teacher   |    教师表    |          教师号，教师姓名，性别，生日，民族，密码          |
|  timetable  |    选课表    |                     学生学号，课程代码                     |

# 二、数据库选择

本次实验选择的是MySQL，搭建数据库服务。

## 2.1 MySQL简介

MySQL 是最流行的开源 SQL 数据库管理系统，由 Oracle Corporation 开发、分发和支持。

MySQL 网站(http://www.mysql.com/)提供了有关 MySQL 软件的最新信息。

1. MySQL是一个数据库管理系统。

数据库是结构化的数据集合。它可以是任何东西，从简单的购物清单到图片库或公司网络中的大量信息。要添加、访问和处理存储在计算机数据库中的数据，您需要一个数据库管理系统，例如 MySQL Server。由于计算机非常擅长处理大量数据，因此数据库管理系统作为独立实用程序或其他应用程序的一部分在计算中发挥着核心作用。

2. MySQL 数据库是关系型的。

关系数据库将数据存储在单独的表中，而不是将所有数据放在一个大仓库中。数据库结构被组织成针对速度进行了优化的物理文件。具有数据库、表、视图、行和列等对象的逻辑模型提供了灵活的编程环境。您设置了控制不同数据字段之间关系的规则，例如一对一、一对多、唯一、必需或可选，以及 不同表之间的“指针”。数据库强制执行这些规则，因此使用设计良好的数据库，您的应用程序永远不会看到不一致、重复、孤立、过时或丢失的数据。

“ MySQL ” 的SQL部分代表 “结构化查询语言”。SQL 是最常用的用于访问数据库的标准化语言。根据您的编程环境，您可以直接输入 SQL（例如，生成报告）、将 SQL 语句嵌入到以另一种语言编写的代码中，或者使用隐藏 SQL 语法的特定语言 API。

SQL 由 ANSI/ISO SQL 标准定义。SQL 标准自 1986 年以来一直在发展，并且存在多个版本。本手册中，“ SQL-92 ”是指1992年发布的标准，“ SQL:1999 ”是指1999年发布的标准，“ SQL:2003 ”是指该标准的当前版本。我们使用短语 “ SQL 标准”来表示SQL 标准的当前版本。

3. MySQL 软件是开源的。

开源意味着任何人都可以使用和修改软件。任何人都可以从 Internet 下载 MySQL 软件并使用它，而无需支付任何费用。如果您愿意，您可以研究源代码并对其进行更改以满足您的需要。MySQL 软件使用 GPL（GNU 通用公共许可证）http://www.fsf.org/licenses/来定义在不同情况下您可以和不可以使用该软件做什么。如果您对 GPL 感到不舒服或需要将 MySQL 代码嵌入到商业应用程序中，您可以从我们这里购买商业许可版本。有关更多信息，请参阅 MySQL 许可概述(http://www.mysql.com/company/legal/licensing/）。

4. MySQL 数据库服务器非常快速、可靠、可扩展且易于使用。

如果这就是您正在寻找的，那么您应该尝试一下。MySQL Server 可以在台式机或笔记本电脑上轻松运行，与您的其他应用程序、Web 服务器等一起运行，几乎不需要关注。如果您将整台机器专用于 MySQL，您可以调整设置以利用所有可用的内存、CPU 能力和 I/O 容量。MySQL 还可以扩展到联网的机器集群。

MySQL Server 最初是为了比现有解决方案更快地处理大型数据库而开发的，并已成功用于高要求的生产环境多年。尽管不断发展，但今天的 MySQL Server 提供了一组丰富且有用的功能。其连接性、速度和安全性使 MySQL Server 非常适合访问 Internet 上的数据库。

5. MySQL 服务器在客户端/服务器或嵌入式系统中工作。

MySQL 数据库软件是一个客户端/服务器系统，它由支持不同后端的多线程 SQL 服务器、几个不同的客户端程序和库、管理工具以及广泛的应用程序编程接口 (API) 组成。

6. 有大量贡献的 MySQL 软件可用。

MySQL Server 具有一组与我们的用户密切合作开发的实用功能。您最喜欢的应用程序或语言很可能支持 MySQL 数据库服务器。

> 以上MySQL简介来源于官方文档：https://dev.mysql.com/doc/refman/8.0/en/what-is-mysql.html

## 2.2 逻辑结构设计

### 管理员表

<img src="C:\self-documents\studying-resource\senior\management-system\experience\report\课设\admin.png" style="zoom:50%;" />

### 考试安排表

<img src="C:\self-documents\studying-resource\senior\management-system\experience\report\课设\arrangement.png" style="zoom:50%;" />

### 课程表

<img src="C:\self-documents\studying-resource\senior\management-system\experience\report\课设\course.png" style="zoom:50%;" />

### 成绩表

<img src="C:\self-documents\studying-resource\senior\management-system\experience\report\课设\score.png" style="zoom:50%;" />

### 学生表

<img src="C:\self-documents\studying-resource\senior\management-system\experience\report\课设\student.png" style="zoom:50%;" />

### 教师表

<img src="C:\self-documents\studying-resource\senior\management-system\experience\report\课设\teacher.png" style="zoom:50%;" />

### 选课表

<img src="C:\self-documents\studying-resource\senior\management-system\experience\report\课设\timetable.png" style="zoom:50%;" />

# 三、开发方案选择

## 3.1 开发语言

### Java

Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程。 Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等。

### Python

Python 是一种易于学习、功能强大的编程语言。它具有高效的高级数据结构和简单而有效的面向对象编程方法。Python 优雅的语法和动态类型，加上它的解释性质，使其成为大多数平台上许多领域的脚本编写和快速应用程序开发的理想语言。

Python 解释器和广泛的标准库可以从Python网站(https://www.python.org/)以源代码或二进制形式免费提供给所有主要平台 ，并且可以免费分发。该站点还包含许多免费的第三方 Python 模块、程序和工具以及其他文档的分发和指针。

Python 解释器可以轻松地使用 C 或 C++（或可从 C 调用的其他语言）实现的新函数和数据类型进行扩展。Python 也适合作为可定制应用程序的扩展语言。

> 以上Python简介来源于官方文档：https://docs.python.org/3/tutorial/index.html

### C#

C#（读作“See Sharp”）是一种新式编程语言，不仅面向对象，还类型安全。 开发人员利用 C# 能够生成在 .NET 中运行的多种安全可靠的应用程序。 C# 源于 C 语言系列，C、C++、Java 和 JavaScript 程序员很快就可以上手使用。

C# 是面向对象的、面向组件的编程语言。 C# 提供了语言构造来直接支持这些概念，让 C# 成为一种非常自然的语言，可用于创建和使用软件组件。 自诞生之日起，C# 就添加了支持新工作负载和新兴软件设计实践的功能。 C# 本质上是面向对象的语言。_* 你需要定义类型及其行为。

多项 C# 功能有助于创建可靠且持久的应用程序。 垃圾回收自动回收不可访问的未用对象所占用的内存。 可以为 null 的类型可防范不引用已分配对象的变量。 异常处理提供了一种结构化且可扩展的方法来进行错误检测和恢复。 Lambda 表达式支持函数编程技术。 语言集成查询 (LINQ) 语法创建一个公共模式，用于处理来自任何源的数据。 异步操作语言支持提供用于构建分布式系统的语法。 C# 有统一类型系统。 所有 C# 类型（包括 int 和 double 等基元类型）均继承自一个根 object 类型。 所有类型共用一组通用运算。 任何类型的值都可以一致地进行存储、传输和处理。 此外，C# 还支持用户定义的引用类型和值类型。 C# 允许动态分配轻型结构的对象和内嵌存储。 C# 支持泛型方法和类型，因此增强了类型安全性和性能。 C# 可提供迭代器，使集合类的实现者可以定义客户端代码的自定义行为。

C# 强调版本控制，以确保程序和库以兼容方式随时间推移而变化。 C# 设计中受版本控制加强直接影响的方面包括：单独的 virtual 和 override 修饰符，关于方法重载决策的规则，以及对显式接口成员声明的支持。

> 以上C#简介来源于官方文档：https://docs.microsoft.com/zh-cn/dotnet/csharp/tour-of-csharp/

### C++

C++是C语言的继承，它既可以进行C语言的过程化程序设计，又可以进行以抽象数据类型为特点的基于对象的程序设计，还可以进行以继承和多态为特点的面向对象的程序设计。C++擅长面向对象程序设计的同时，还可以进行基于过程的程序设计，因而C++就适应的问题规模而论，大小由之。

C++不仅拥有计算机高效运行的实用性特征，同时还致力于提高大规模程序的编程质量与程序设计语言的问题描述能力。

## 3.2 项目技术栈

本系统的开发语言为Java，具体技术栈如下：

1. 桌面端构建工具：JavaFX Application（以openjfx的pom坐标引入）
2. 项目构建工具：maven
3. 数据库ORM层框架：mybatis
4. 数据库：MySQL8.0
5. 日志操作：log4j
6. excel文件操作接口：poi
7. 邮件操作接口：javax.mail
8. PDF文件操作接口：itextpdf

maven工程的pom.xml文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>management-system-admin</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>15.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>15.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.5</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version>
            <scope>provided</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/log4j/log4j -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>RELEASE</version>
            <scope>compile</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.17</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/javax.mail/javax.mail-api -->
        <dependency>
            <groupId>com.sun.mail</groupId>
            <artifactId>javax.mail</artifactId>
            <version>1.6.0</version>
        </dependency>

        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itextpdf</artifactId>
            <version>5.5.10</version>
        </dependency>
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itext-asian</artifactId>
            <version>5.2.0</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.fxml</include>
                    <include>**/*.xml</include>
                    <include>**/*.properties</include>
                    <include>**/*.css</include>
                </includes>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
            </plugin>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.5</version>
            </plugin>
            <plugin>
                <groupId>com.zenjava</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>8.8.3</version>
                <configuration>
                    <vendor>yfcod</vendor>
                    <mainClass>com.yfcod.management.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

# 四、系统功能介绍



