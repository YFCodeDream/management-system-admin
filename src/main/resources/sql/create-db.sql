create table admin
(
    AdminId varchar(11) charset utf8 not null,
    AdminPwd varchar(20) charset utf8 not null,
    AdminPhone varchar(11) charset utf8 not null,
    constraint admin_AdminId_uindex
        unique (AdminId)
);

alter table admin
    add primary key (AdminId);

create table arrangement
(
    ExamId int auto_increment
        primary key,
    CourseId int not null,
    ExamDate date not null,
    StartTime time not null,
    EndTime time not null,
    Address varchar(20) charset utf8 not null
);

create table course
(
    CourseId int auto_increment
        primary key,
    CourseName varchar(20) charset utf8 not null,
    TeacherId varchar(11) charset utf8 not null,
    Address varchar(20) charset utf8 not null,
    CourseDay int not null,
    CourseTimePeriod int not null
);

create table score
(
    ExamId int not null,
    StudentId varchar(14) charset utf8 not null,
    Score int not null
);

create table student
(
    StudentId varchar(14) charset utf8 not null
        primary key,
    StudentName varchar(10) charset utf8 not null,
    Gender tinyint(1) not null,
    Birthday date null,
    Nation varchar(5) charset utf8 null,
    Major varchar(15) charset utf8 not null,
    Grade int not null,
    Password varchar(20) charset utf8 not null
);

create table teacher
(
    TeacherId varchar(11) charset utf8 not null
        primary key,
    TeacherName varchar(10) charset utf8 not null,
    Gender tinyint(1) not null,
    Birthday date null,
    Nation varchar(5) charset utf8 null,
    Password varchar(20) charset utf8 not null
);

create table timetable
(
    StudentId varchar(14) charset utf8 not null,
    CourseId int not null
);

