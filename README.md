# management-system-admin项目报告

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

<img src="img\考试管理系统.png" style="zoom:50%;" />

## 1.4 ER图

ER图也称实体-联系图(Entity Relationship Diagram)，提供了表示实体类型、属性和联系的方法，用来描述现实世界的概念模型。

它是描述现实世界关系概念模型的有效方法。是表示概念关系模型的一种方式。用“矩形框”表示实体型，矩形框内写明实体名称;用“椭圆图框”或圆角矩形表示实体的属性，并用“实心线段”将其与相应关系的“实体型”连接起来；用”菱形框“表示实体型之间的联系成因，在菱形框内写明联系名，并用”实心线段“分别与有关实体型连接起来，同时在”实心线段“旁标上联系的类型（1:1,1:n或m:n）。

<img src="img\ER.bmp" style="zoom:50%;" />

## 1.5 实体属性表

|     表名      | 对应实体意义 |              属性名              |
| :---------: | :----: | :---------------------------: |
|    admin    |  教师表   |         管理员号，密码，手机号码          |
| arrangement | 考试安排表  | 考试代码，课程代码，考试日期，起始时间，结束时间，考试地点 |
|   course    |  课程表   | 课程代码，课程名称，教师号，授课地点，授课日期，授课时段  |
|    score    |  成绩表   |         考试代码，学生学号，分数          |
|   student   |  学生表   |  学生学号，学生姓名，性别，生日，民族，专业，年级，密码  |
|   teacher   |  教师表   |     教师号，教师姓名，性别，生日，民族，密码      |
|  timetable  |  选课表   |           学生学号，课程代码           |

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

开源意味着任何人都可以使用和修改软件。任何人都可以从 Internet 下载 MySQL 软件并使用它，而无需支付任何费用。如果您愿意，您可以研究源代码并对其进行更改以满足您的需要。MySQL 软件使用 GPL（GNU 通用公共许可证）http://www.fsf.org/licenses/ 来定义在不同情况下您可以和不可以使用该软件做什么。如果您对 GPL 感到不舒服或需要将 MySQL 代码嵌入到商业应用程序中，您可以从我们这里购买商业许可版本。有关更多信息，请参阅 MySQL 许可概述(http://www.mysql.com/company/legal/licensing/）。

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

<img src="img\admin.png" style="zoom:50%;" />

### 考试安排表

<img src="img\arrangement.png" style="zoom:50%;" />

### 课程表

<img src="img\course.png" style="zoom:50%;" />

### 成绩表

<img src="img\score.png" style="zoom:50%;" />

### 学生表

<img src="img\student.png" style="zoom:50%;" />

### 教师表

<img src="img\teacher.png" style="zoom:50%;" />

### 选课表

<img src="img\timetable.png" style="zoom:50%;" />

# 三、开发方案选择

## 3.1 开发语言

### Java

Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程。 Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等。

### Python

Python 是一种易于学习、功能强大的编程语言。它具有高效的高级数据结构和简单而有效的面向对象编程方法。Python 优雅的语法和动态类型，加上它的解释性质，使其成为大多数平台上许多领域的脚本编写和快速应用程序开发的理想语言。

Python 解释器和广泛的标准库可以从Python网站(https://www.python.org/) 以源代码或二进制形式免费提供给所有主要平台 ，并且可以免费分发。该站点还包含许多免费的第三方 Python 模块、程序和工具以及其他文档的分发和指针。

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

## 4.1 登录页

登录页实现对应身份登录功能、系统管理员注册功能。

### 对应身份登录

<img src="img\login.png" style="zoom:60%;" />

登录时默认身份为学生。身份选项包括：学生，教师，系统管理员。

登录时，有以下情况：

1. 若用户名输入不正确，则弹出提示框提示用户“未查找到该+<当前身份>”
2. 若用户名输入正确，密码错误，则弹出提示框提示用户“密码错误，请重新输入”

以学生省份为例，演示上述情况：

<img src="img\login-1.png" style="zoom:60%;" />

<img src="img\login-2.png" style="zoom:60%;" />

登录成功，则弹出提示框“登录成功”，随即进入对应身份的系统。

<img src="img\login-14.png" style="zoom:60%;" />

### 系统管理员注册

在系统管理员进行注册时，选择系统管理员身份，此时注册按钮可以点击，否则注册按钮不允许操作。同时，必须要先输入一位管理员的管理员号与密码，以便验证管理员身份。

<img src="img\login-3.png" style="zoom:60%;" />

验证时，有以下情况：

1. 若管理员号不存在，则弹出提示框提示用户“未查找到该管理员”

<img src="img\login-4.png" style="zoom:60%;" />

2. 若管理员号输入正确，密码输入错误，则会提示用户“密码错误，请重新输入”

<img src="img\login-5.png" style="zoom:60%;" />

3. 当管理员号与密码均正确，进入注册界面

<img src="img\login-6.png" style="zoom:60%;" />

在对应的输入框中输入新用户名，新密码以及电话号码，点击提交即可完成注册。

若要放弃注册，点击提交右侧的“返回至登录页”即可返回。

在注册时，管理员号必须为11位，电话号码必须满足11位，密码不能为空，否则提示用户。

1. 若管理员号不为11位，则提示“管理员号为11位”

<img src="img\login-11.png" style="zoom:60%;" />

2. 若电话号码不为11位，则提示“电话号码的长度必须为11位”

<img src="img\login-12.png" style="zoom:60%;" />

3. 若密码为空，则提示“请输入密码”

<img src="img\login-13.png" style="zoom:60%;" />

4. 若注册的管理员号已存在，则提示“该管理员号已经存在”

<img src="img\login-10.png" style="zoom:60%;" />

下图演示注册功能：

<img src="img\login-7.png" style="zoom:60%;" />

<img src="img\login-8.png" style="zoom:60%;" />

随即自动返回登录页，且自动填充了注册时的管理员号和密码。

<img src="img\login-9.png" style="zoom:60%;" />

## 4.2 学生端

下图演示了学生端首页：

<img src="img\student-1.png" style="zoom:60%;" />

左侧为导航栏，选择对应的查询项即跳转到对应的数据表，默认初始为考试安排表。

### 考试安排查询

学生只能查询自己选的课程对应的考试安排。考试安排查询的结果如首页图。

在查询栏可以输入任何组合信息，系统根据输入的信息进行筛选，方便学生对于考试安排进行查询。

请注意：显示所有数据的选框默认勾选，若要使用查询栏输入筛选信息，请取消勾选此选框。若勾选此选框，点击查询考试安排会显示学生所有考试安排，查询栏信息不生效。

<img src="img\student-3.png" style="zoom:60%;" />

### 成绩查询

选择成绩查询，即可查询目前选课中已有所有成绩。

<img src="img\student-2.png" style="zoom:60%;" />

在查询栏可以输入任何组合信息，系统根据输入的信息进行筛选，方便学生对于考试成绩进行查询。

请注意：显示所有数据的选框默认勾选，若要使用查询栏输入筛选信息，请取消勾选此选框。若勾选此选框，点击查询成绩会显示学生所有考试成绩，查询栏信息不生效。

<img src="img\student-4.png" style="zoom:60%;" />

### 成绩可视化统计

在成绩查询项，有数据查询和可视化统计两个分项，选择可视化统计，即可显示当前学生已有考试成绩的图表统计dashboard，方便学生直观了解成绩信息。

具体可视化统计的信息参见1.2中学生端功能说明。

<img src="img\student-5.png" style="zoom:60%;" />

排名占比表示当前成绩超过了本次参与该考试的学生百分比。如上图，学号为34520182200001的学生在数据结构与算法课程考试中击败了百分之二十五的学生，在数据结构实验中击败了百分之五十的学生。

已有成绩统计表示当前学生已有成绩的统计。如上图，学号为34520182200001的学生数据结构与算法考试成绩为77分，数据结构实验为86分。

最高最低分及其课程名称显示在右下角，最高分以绿色显示，最低分以红色显示。

### 课表查询

选择课表查询，即可查询当前学生所有选课信息。

<img src="img\student-6.png" style="zoom:60%;" />

在查询栏可以输入任何组合信息，系统根据输入的信息进行筛选，方便学生对于选课进行查询。

请注意：显示所有数据的选框默认勾选，若要使用查询栏输入筛选信息，请取消勾选此选框。若勾选此选框，点击查询课程会显示学生所有选课记录，查询栏信息不生效。

<img src="img\student-7.png" style="zoom:60%;" />

### 打印成绩单

在菜单栏选择：导出→打印成绩单，或者键入快捷键“Ctrl+P”，弹出文件保存的对话框，选择文件路径，点击保存即可打印学生成绩单。

<img src="img\student-8.png" style="zoom:60%;" />

<img src="img\student-9.png" style="zoom:60%;" />

点击保存，显示导出成功，表面成绩单已打印。

<img src="img\student-10.png" style="zoom:60%;" />

下图为打印的PDF成绩单：

<img src="img\student-11.png" style="zoom:60%;" />

页眉左端显示“厦门大学考试管理系统印制 日期：+<打印日期>”，页眉右端显示页码。成绩单内容包含学生学号，学生姓名，专业，以及已有成绩的考试代码，课程名称，考试成绩。同时添加水印“XMU-MIS”进行防伪。

### 分享成绩单至邮箱

在菜单栏选择：分享→分享成绩单至邮箱，或者键入快捷键“Ctrl+Shift+P”，弹出输入邮箱对话框。

<img src="img\student-12.png" style="zoom:60%;" />

<img src="img\student-14.png" style="zoom:60%;" />

若输入邮箱格式不正确，系统弹出提示框“邮箱格式不正确，请重新输入”。

<img src="img\student-13.png" style="zoom:60%;" />

点击确定，即可将成绩单发送至指定邮箱。

<img src="img\student-15.png" style="zoom:60%;" />

在指定邮箱查看邮件。

<img src="img\student-16.png" style="zoom:60%;" />

邮件发件人昵称，发件人邮箱地址等信息在相关配置文件里，邮件正文为：厦门大学考试管理信息系统导出：学生成绩单，已发送，请查收。PDF成绩单在附件。

下图为附件预览：

<img src="img\student-17.png" style="zoom:60%;" />

## 4.3 教师端

下图演示了教师端首页。

<img src="img\teacher-1.png" style="zoom:60%;" />

教师端有两项功能区：考试统计，数据管理。

### 考试统计

考试统计dashboard提供两个分区：授课均分统计，单次考试数据统计。

授课均分统计如首页图。

单次考试数据统计分区提供对单次考试进行可视化统计的功能。在右上角输入考试代码，进行可视化统计。

在进行单次考试的可视化统计时，只能可视化当前教师发布的考试代码对应的考试信息，否则系统弹出提示框“该考试代码不为您所有，请重新输入”。

<img src="img\teacher-2.png" style="zoom:60%;" />

查询考试信息成功后，显示对应的考试科目，并给出各分段人数统计的扇形图，并给出最高最低分及对应的学生学号。

<img src="img\teacher-3.png" style="zoom:60%;" />

### 数据管理

#### 考试安排查询

教师能查询所有考试安排，方便进行预约管理。

在查询栏可以输入任何组合信息，系统根据输入的信息进行筛选，方便教师对于考试安排进行查询。

请注意：显示所有数据的选框默认勾选，若要使用查询栏输入筛选信息，请取消勾选此选框。若勾选此选框，点击查询考试安排会显示所有考试安排，查询栏信息不生效。

<img src="img\teacher-4.png" style="zoom:60%;" />

<img src="img\teacher-5.png" style="zoom:60%;" />

#### 考试安排添加

添加考试安排时，有以下情况：

1. 若输入信息不完整，则弹出提示框“请输入完整信息”
2. 若输入的课程代码不属于当前教师，则弹出提示框“您只能预约您的授课，请重新输入授课代码”
3. 若当前预约的考试地点和考试时间已有预约，则弹出提示框“您预约的考试与其余考试冲突，请重新选择”

篇幅原因，以上情况不一一给出图片演示。

下图演示成功预约考试：

先将此考试信息删除，以便演示添加操作。

<img src="img\teacher-6.png" style="zoom:60%;" />

为和之前的信息区分，将考试日期设置为2021-11-29，将起始时间和结束时间延后两小时。

<img src="img\teacher-7.png" style="zoom:60%;" />

点击添加考试安排，显示“预约成功，考试安排已添加”，说明预约成功。

<img src="img\teacher-8.png" style="zoom:60%;" />

系统立即更新信息，显示新预约的考试信息，可观察到系统自动分配了新考试代码。

<img src="img\teacher-9.png" style="zoom:60%;" />

#### 考试安排修改

以上述添加的考试安排为例，修改其考试日期为2021-11-30。

点击对应的考试安排项，进行考试安排修改。

同理，有以下情况：

1. 若输入信息不完整，则弹出提示框“请输入完整信息”
2. 若输入的课程代码不属于当前教师，则弹出提示框“您只能预约您的授课，请重新输入授课代码”
3. 若当前预约的考试地点和考试时间已有预约，则弹出提示框“您预约的考试与其余考试冲突，请重新选择”

篇幅原因，以上情况不一一给出图片演示。

下图演示成功修改考试：

<img src="img\teacher-10.png" style="zoom:60%;" />

点击修改考试安排，显示“考试安排已修改”，说明修改成功。

<img src="img\teacher-11.png" style="zoom:60%;" />

系统立即更新信息，可观察到考试日期已经修改。

<img src="img\teacher-12.png" style="zoom:60%;" />

#### 考试安排删除

删除考试安排时，有以下情况：

1. 未选择任何考试信息项，则系统显示“请选择您要删除的考试安排”
2. 若输入的课程代码不属于当前教师，则弹出提示框“您只能删除您的授课的考试安排”
3. 若该考试代码关联有学生成绩，则弹出提示框“该考试代码关联有学生成绩，不允许删除”

篇幅原因，以上情况不一一给出图片演示。

以上述添加的考试安排为例，下图演示成功删除考试：

选择之前添加的考试安排，点击删除考试安排，显示“考试安排已删除”，说明删除成功。

<img src="img\teacher-13.png" style="zoom:60%;" />

系统立即刷新信息，之前添加的考试安排已删除。

<img src="img\teacher-14.png" style="zoom:60%;" />

#### 成绩管理

成绩数据的增删改查与考试安排相似，此处不作过多详细描述，仅列出相关结果。

查询成绩时，有以下情况：

1. 若该考试代码不属于当前教师，则弹出提示框“该考试代码不为您所有，请重新输入”

添加成绩时，有以下情况：

1. 若输入信息不完整，则提示“请输入完整信息”
2. 若该考试代码不属于当前教师，则弹出提示框“该考试代码不为您所有，请重新输入”
3. 若该学生并未选择此课程，则提示“此学生未选择您的授课”
4. 若学生成绩已存在，则提示“此学生成绩信息已存在”

修改成绩时，有以下情况：

1. 若输入信息不完整，则提示“请输入完整信息”
2. 若该考试代码不属于当前教师，则弹出提示框“该考试代码不为您所有，请重新输入”
3. 若该学生并未选择此课程，则提示“此学生未选择您的授课”

删除成绩时，有以下情况：

1. 未选择任何考试信息项，则系统显示“请选择您要删除的考试安排”

#### 课表查询

选择课表查询，即可查询当前教师所有授课信息。

<img src="img\teacher-15.png" style="zoom:60%;" />

在查询栏可以输入任何组合信息，系统根据输入的信息进行筛选，方便教师对于选课进行查询。

请注意：显示所有数据的选框默认勾选，若要使用查询栏输入筛选信息，请取消勾选此选框。若勾选此选框，点击查询课程会显示所有授课，查询栏信息不生效。

<img src="img\teacher-16.png" style="zoom:60%;" />

## 4.4 系统管理员端

下图演示了系统管理员端首页。

<img src="img\admin-1.png" style="zoom:60%;" />

左侧为导航栏，选择对应的查询项即跳转到对应的数据表，默认初始为考试安排表。

中间的工作区是此系统数据库的所有数据，方便管理员进行监测。

右侧是系统日志，提供该系统的后端日志查看窗口。

#### 数据查询

数据查询与学生端和教师端相似，不同的是系统管理员可以查询所有数据。

#### 日志查看

右侧日志窗口默认选择简洁模式，仅显示数据操作与功能调用的日志信息。

点击显示完整日志，即可查看系统记录的完整日志，同时按钮文字变成显示简洁日志。

<img src="img\admin-2.png" style="zoom:60%;" />

#### 日志导出

在菜单栏选择：数据→导出日志，或者键入快捷键“Ctrl+Alt+L”，弹出文件保存的对话框，选择文件路径，点击保存即可导出日志。

<img src="img\admin-3.png" style="zoom:60%;" />

下图演示简洁日志的导出：

<img src="img\admin-4.png" style="zoom:60%;" />

点击保存，显示“导出日志成功”，说明成功导出日志。

<img src="img\admin-5.png" style="zoom:60%;" />

查看导出的日志。

<img src="img\admin-6.png" style="zoom:60%;" />

可观察到记录的日志里包含数据库SQL操作，发送邮件，学生端教师端登入信息等，方便系统管理员对该系统进行运维。

## 4.5 辅助功能

辅助功能在学生端，教师端，系统管理员端均有实现，这一部分功能的演示以系统管理员端为例。

### 显示所有数据

在菜单栏选择：数据→显示所有数据，或者键入快捷键“F5”，则刷新并重新显示所有数据。

<img src="img\extra-1.png" style="zoom:60%;" />

### 导出数据至xls

#### 当前数据

在菜单栏选择：数据→导出→导出当前数据，或者键入快捷键“Ctrl+E”，弹出文件保存的对话框，选择文件路径，点击保存即可。

<img src="img\extra-2.png" style="zoom:60%;" />

<img src="img\extra-3.png" style="zoom:60%;" />

点击保存，显示“导出数据成功”，说明已导出数据至xls。

<img src="img\extra-4.png" style="zoom:60%;" />

查看已导出的xls文件。

<img src="img\extra-5.png" style="zoom:60%;" />

可观察到已经将数据导出至xls文件，excel作为普及度更广的操作数据的方式，可以极大方便系统用户对于数据的处理。

#### 所有数据

在菜单栏选择：数据→导出→导出所有数据，或者键入快捷键“Ctrl+Alt+E”，弹出文件保存的对话框，选择文件路径，点击保存即可。

<img src="img\extra-6.png" style="zoom:60%;" />

<img src="img\extra-7.png" style="zoom:60%;" />

### 分享数据至邮箱

同理，分享数据至邮箱也分为当前数据和所有数据，对应的快捷键分别为“Ctrl+M”和“Ctrl+Alt+M”。

<img src="img\extra-8.png" style="zoom:60%;" />

下图演示分享全部至邮箱。

与学生端分享成绩单至邮箱的功能类似，系统先弹出对话框要求输入指定邮箱地址并进行校验。

在指定邮箱查看邮件。

<img src="img\extra-9.png" style="zoom:60%;" />

邮件发件人昵称，发件人邮箱地址等信息在相关配置文件里，邮件正文为：厦门大学考试管理信息系统导出：<导出的数据表名称>，已发送，请查收。xls数据表在附件。

下图为附件预览：

<img src="img\extra-10.png" style="zoom:60%;" />

### 修改个人信息

系统管理员修改的个人信息还包括手机号码，学生端和教师端仅能修改密码。

在菜单栏选择：用户/管理员→修改信息，或者键入快捷键“Ctrl+Shift+U”，则弹出修改个人信息对话框。

<img src="img\extra-11.png" style="zoom:60%;" />

修改个人信息时，有以下情况：

1. 若输入密码为空，则提示“请输入新密码”
2. 若两次输入密码不一致，则提示“两次输入密码不一致”
3. 若手机号不为空且不足11位，则提示“手机号不足11位”

若修改成功，则提示“修改信息成功”。

<img src="img\extra-12.png" style="zoom:60%;" />

### 用户登出

在菜单栏选择：用户/管理员→登出，或者键入快捷键“Ctrl+Shift+L”，则登出当前用户。

<img src="img\extra-13.png" style="zoom:60%;" />

<img src="img\extra-14.png" style="zoom:60%;" />

### 退出系统

在菜单栏选择：用户/管理员→退出，或者键入快捷键“Ctrl+Q”，则退出系统。

<img src="img\extra-15.png" style="zoom:60%;" />

# 五、项目总结

本次课程设计，从技术选型与项目架构，到完整项目开发流程，由我自己独立完成。整个项目体量较大（约有几千行代码），我花费了较多时间才可完成，这个过程极大锻炼了我的技术水平，让我体验到了工程化项目的开发过程。

# 六、附录

## 6.1 项目目录结构

项目目录树如下：

```
management-system-admin
│  pom.xml maven依赖pom坐标文件
│  README.md 项目报告文件  
├─log
│      debug.txt log4j日志文件
├─src 源代码
│  └─main
│      ├─java
│      │  └─com
│      │      └─yfcod
│      │          └─management
│      │              │  Main.java 主入口文件
│      │              ├─constant 常量包
│      │              │      TableColumnName.java 表头常量
│      │              ├─controller 控制器包
│      │              │      AdminController.java 管理员端应用控制器
│      │              │      BaseController.java 所有应用控制器的抽象基类，实现某些基本功能
│      │              │      LoginController.java 登录页应用控制器
│      │              │      MenuItemOperation.java 菜单栏公共操作的接口，所有应用控制器须实现
│      │              │      StudentController.java 学生端应用控制器
│      │              │      TeacherController.java 教师端应用控制器
│      │              ├─dao 数据库访问实现类包
│      │              │      AdminDao.java 管理员数据库访问实现类
│      │              │      ArrangementDao.java 考试安排数据库访问实现类
│      │              │      CourseDao.java 课程数据库访问实现类
│      │              │      ScoreDao.java 成绩数据库访问实现类
│      │              │      StudentDao.java 学生数据库访问实现类
│      │              │      TeacherDao.java 教师数据库访问实现类
│      │              │      TimetableDao.java 选课记录数据库访问实现类
│      │              ├─mapper 数据库映射接口包
│      │              │      AdminMapper.java 管理员数据库访问接口
│      │              │      ArrangementMapper.java 考试安排数据库访问接口
│      │              │      CourseMapper.java 课程数据库访问接口
│      │              │      ScoreMapper.java 成绩数据库访问接口
│      │              │      StudentMapper.java 学生数据库访问接口
│      │              │      TeacherMapper.java 教师数据库访问接口
│      │              │      TimetableMapper.java 选课记录数据库访问接口
│      │              ├─model Java bean对象包
│      │              │      Admin.java 管理员模型
│      │              │      Arrangement.java 考试安排模型
│      │              │      Course.java 课程模型
│      │              │      Score.java 成绩模型
│      │              │      Student.java 学生模型
│      │              │      Teacher.java 教师模型
│      │              │      Timetable.java 选课记录模型
│      │              ├─util 工具类包
│      │              │      BaseBarChartUtil.java 所有条形图绘制工具类抽象基类，完成某些基本方法
│      │              │      ExportExcelUtil.java xls导出工具类
│      │              │      GenerateAlertUtil.java 弹窗生成工具类
│      │              │      GenerateBarChartUtil.java 基本条形图绘制工具类
│      │              │      GeneratePieChartUtil.java 基本饼图绘制工具类
│      │              │      GenerateStackedBarChartUtil.java 堆叠条形图绘制工具类
│      │              │      PrintPdfUtil.java PDF导出工具类
│      │              │      SelfHeaderFooter.java 自定义页眉工具类
│      │              │      SendMailUtil.java 邮件发送工具类
│      │              │      Watermark.java 水印工具类
│      │              └─wrapper 包装类包，负责与fxml页面交互（unused）
│      │                      AdminWrapper.java
│      │                      ArrangementWrapper.java
│      │                      CourseWrapper.java
│      │                      ScoreWrapper.java
│      │                      StudentWrapper.java
│      │                      TeacherWrapper.java
│      │                      TimetableWrapper.java
│      └─resources
│          │  log4j.properties log4j配置文件
│          ├─com
│          │  └─yfcod
│          │      └─management
│          │          │  db.properties 数据库配置文件
│          │          │  mybatis-config.xml mybatis配置文件
│          │          │  smtp.properties smtp邮件服务协议配置文件
│          │          ├─img 图片资源
│          │          │      login-bg-autumn-cp.jpg
│          │          │      login-bg-autumn.jpg
│          │          │      xmu-logo.png
│          │          ├─layout 布局文件
│          │          │  ├─admin
│          │          │  │      admin.fxml 管理员页面
│          │          │  │      ComplexApplication.css 主应用页基本css文件
│          │          │  │      student.fxml 学生端页面
│          │          │  │      teacher.fxml 教师端页面
│          │          │  └─login
│          │          │          BasicApplication.css 登录页基本css文件
│          │          │          login.fxml 登录页
│          │          └─mapper ORM层SQL映射文件
│          │                  AdminMapper.xml 
│          │                  ArrangementMapper.xml
│          │                  CourseMapper.xml
│          │                  ScoreMapper.xml
│          │                  StudentMapper.xml
│          │                  TeacherMapper.xml
│          │                  TimetableMapper.xml
│          └─sql 创建数据库及数据表SQL文件
│                  create-admin.sql
│                  create-arrangement.sql
│                  create-course.sql
│                  create-db.sql 创建数据库SQL文件
│                  create-score.sql
│                  create-student.sql
│                  create-teacher.sql
│                  create-timetable.sql
├─target 编译后的字节码文件夹
└─temp 临时文件夹，临时文件会在退出系统后自动删除
```

## 6.2 项目开发IDE选择及配置方法

### 开发IDE

该项目是Jetbrains IDEA开发的maven工程。IDEA为最新版2021.3.1。

### maven版本

maven版本为3.6.3，请确保配置好MAVEN_HOME环境变量，以及在IDEA中配置好安装的maven。

运行此工程前，建议对pom.xml进行reload project操作。

<img src="img\maven-1.png" style="zoom:60%;" />

### 样例数据库

请先配置好MySQL数据库服务，关于MySQL的配置，请参阅官方文档。本报告不予赘述。

请按照运行此项目MySQL服务属性，更改db.properties配置文件。

配置完毕，请先执行create-db.sql文件，该SQL代码执行建立数据库操作。

再执行余下的SQL文件，建立数据表并添加数据。

## 6.3 项目地址及版本管理

该项目存储库由我托管在本人的GitHub下，项目地址：https://github.com/YFCodeDream/management-system-admin

关于此存储库的master branch的所有commit记录，可在：https://github.com/YFCodeDream/management-system-admin/commits/master 处查看。

由于此项目包含敏感信息，不日会将个人邮箱的smtp服务关闭，请更改smtp.properties配置文件以确保邮件服务可正常运行。

目前最新版本为1.0.6。

欢迎在issue区，pull request区留言。如果认可我的项目，请给此存储库star。
