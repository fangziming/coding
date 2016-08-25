SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS chapter;
DROP TABLE IF EXISTS coupon;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS course_teachers;
DROP TABLE IF EXISTS homework;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS issue_classes;
DROP TABLE IF EXISTS issue_classes_teacher;
DROP TABLE IF EXISTS issue_lock;
DROP TABLE IF EXISTS issue_teachers;
DROP TABLE IF EXISTS live_course;
DROP TABLE IF EXISTS live_course_reminder;
DROP TABLE IF EXISTS locks;
DROP TABLE IF EXISTS major;
DROP TABLE IF EXISTS quiz;
DROP TABLE IF EXISTS resourse_type;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS unit;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS vedio;
DROP TABLE IF EXISTS wclass_students;




/* Create Tables */

-- 小节表
CREATE TABLE chapter
(
	id varchar(64) NOT NULL COMMENT '小节编号',
	name varchar(64) NOT NULL COMMENT '小节名称',
	type char NOT NULL COMMENT '小节形式',
	objid varchar(64) NOT NULL COMMENT '形式对象',
	sort int(3) NOT NULL COMMENT '排序',
	unit_id varchar(64) NOT NULL COMMENT '章节编号',
	course_id varchar(64) NOT NULL COMMENT '课程编号',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '小节表';


-- 优惠卷表
CREATE TABLE coupon
(
	id varchar(64) NOT NULL COMMENT '优惠券编号',
	code varchar(25) NOT NULL COMMENT '兑换码',
	price double NOT NULL COMMENT '价格',
	expiredtime datetime NOT NULL COMMENT '到期时间',
	usedtime datetime COMMENT '使用时间',
	-- 0 未使用 
	-- 1 已使用 
	-- 2 弃用
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态',
	-- 0 优惠码 1 兑换码
	type char(1) NOT NULL COMMENT '优惠券类型',
	channel char(1) NOT NULL COMMENT '渠道',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '优惠卷表';


-- 课程表
CREATE TABLE course
(
	id varchar(64) NOT NULL COMMENT '课程编号',
	name varchar(63) NOT NULL COMMENT '课程名称',
	version varchar(8) NOT NULL COMMENT '课程版本',
	lesson_cnt int(4) DEFAULT 0 NOT NULL COMMENT '录播课程数',
	livecourse_cnt int(3) DEFAULT 0 NOT NULL COMMENT '专业直播课程数',
	homework_cnt int(3) NOT NULL COMMENT '作业数',
	lessonTime int(4) DEFAULT 0 COMMENT '课时数',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '课程表';


-- 课程讲师表
CREATE TABLE course_teachers
(
	id varchar(64) NOT NULL COMMENT '课程老师编号',
	course_id varchar(64) NOT NULL COMMENT '课程编号',
	teacher_id varchar(64) NOT NULL COMMENT '老师编号',
	PRIMARY KEY (id)
) COMMENT = '课程讲师表';


-- 家庭作业
CREATE TABLE homework
(
	id varchar(64) NOT NULL COMMENT '作业编号',
	title varchar(64) NOT NULL COMMENT '作业名称',
	attachment varchar(255) COMMENT '作业附件',
	type varchar(64) COMMENT '作业分类',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '家庭作业';


-- 专业期次表
CREATE TABLE issue
(
	id varchar(64) NOT NULL COMMENT '期次编号',
	major_id varchar(64) NOT NULL COMMENT '专业编号',
	name varchar(64) NOT NULL COMMENT '期次名称',
	num int(3) NOT NULL COMMENT '期次号',
	url varchar(255) COMMENT '详情页',
	pic_url varchar(255) COMMENT '本期封面',
	price double NOT NULL COMMENT '本期专业售价',
	-- 0：全款购买
	locknumdownpayment tinyint DEFAULT 0 NOT NULL COMMENT '购买部分关卡数',
	downpayment double COMMENT '本期专业首付',
	sellcntprepared int(6) NOT NULL COMMENT '预售数量',
	soldcnt int(6) DEFAULT 0 NOT NULL COMMENT '已售数量',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态',
	startdate datetime NOT NULL COMMENT '开班时间',
	enddate datetime COMMENT '结束时间',
	qq varchar(16) COMMENT '本期QQ群号',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '专业期次表';


-- 期次班级表
CREATE TABLE issue_classes
(
	id varchar(64) NOT NULL COMMENT '班级编号',
	name varchar(64) NOT NULL COMMENT '班级名称',
	course_id varchar(64) NOT NULL COMMENT '课程编号',
	startdate date NOT NULL COMMENT '开班时间',
	enddate date NOT NULL COMMENT '结班时间',
	studentnum int(5) DEFAULT 0 NOT NULL COMMENT '预期班级人数',
	presentstunum int(5) DEFAULT 0 NOT NULL COMMENT '当前班级人数',
	stu_rate double DEFAULT 0 NOT NULL COMMENT '当前进度',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '期次班级表';


-- 期次班级辅导老师表
CREATE TABLE issue_classes_teacher
(
	id varchar(64) NOT NULL COMMENT '班级辅导老师编号',
	class_id varchar(64) NOT NULL COMMENT '班级编号',
	teacher_id varchar(64) NOT NULL COMMENT '辅导老师编号',
	PRIMARY KEY (id)
) COMMENT = '期次班级辅导老师表';


-- 期次关卡表
CREATE TABLE issue_lock
(
	id varchar(64) NOT NULL COMMENT '编号',
	issue_id varchar(64) NOT NULL COMMENT '期次编号',
	lock_type char(1) NOT NULL COMMENT '关卡类型',
	title varchar(64) NOT NULL COMMENT '关卡标题',
	num int(3) NOT NULL COMMENT '关卡数',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '期次关卡表';


-- 期次讲师表
CREATE TABLE issue_teachers
(
	id varchar(64) NOT NULL COMMENT '课程老师编号',
	issue_id varchar(64) NOT NULL COMMENT '期次编号',
	teacher_id varchar(64) NOT NULL COMMENT '老师编号',
	PRIMARY KEY (id)
) COMMENT = '期次讲师表';


-- 直播课程
CREATE TABLE live_course
(
	id varchar(64) NOT NULL COMMENT '直播课程编号',
	name varchar(64) NOT NULL COMMENT '直播课程名称',
	sdk_id varchar(16) COMMENT '直播课堂编号',
	lci_kind char(1) NOT NULL COMMENT '直播课程类型',
	major_id varchar(64) NOT NULL COMMENT '专业编号',
	company varchar(64) NOT NULL COMMENT '主讲企业',
	lecturer varchar(64) NOT NULL COMMENT '主讲人',
	viewcnt int DEFAULT 0 NOT NULL COMMENT '观看人数',
	vedio_url varchar(255) COMMENT '直播课录播文件',
	videosrt_url varchar(255) COMMENT '直播课录播字幕',
	videopic_url varchar(255) COMMENT '直播课录播封面',
	videobigpic_url varchar(255) COMMENT '直播课轮播封面',
	number varchar(16) COMMENT '课堂编号',
	assistantToken varchar(16) COMMENT '助教口令',
	studentToken varchar(16) COMMENT '学生口令',
	teacherToken varchar(16) COMMENT '老师口令',
	studentClientToken varchar(16) COMMENT '学生客户端口令',
	startDate datetime NOT NULL COMMENT '预期开始时间',
	webJoin char(1) COMMENT '是否允许web端加入',
	clientJoin char(1) COMMENT '是否允许客户端加入',
	invalidDate datetime COMMENT '失效时间',
	teacherJoinUrl varchar(255) COMMENT '讲师/助教加入地址',
	studentJoinUrl varchar(255) COMMENT '学员加入地址',
	code char(1) COMMENT 'code',
	message varchar(255) COMMENT '结果说明',
	introduce varchar(512) NOT NULL COMMENT '课程介绍',
	lecturerpic_url varchar(255) COMMENT '讲师头像',
	lecturer_introduce varchar(512) NOT NULL COMMENT '讲师介绍',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '直播课程';


-- 直播预约表
CREATE TABLE live_course_reminder
(
	id varchar(64) NOT NULL COMMENT '预约编号',
	lci_id varchar(64) NOT NULL COMMENT '直播课编号',
	user_id varchar(64) NOT NULL COMMENT '预约用户编号',
	tel varchar(32) COMMENT '手机号',
	email varchar(32) COMMENT '邮箱',
	remindcnt tinyint(1) DEFAULT 0 NOT NULL COMMENT '提醒次数',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '直播预约表';


-- 专业关卡表
CREATE TABLE locks
(
	id varchar(64) NOT NULL COMMENT '关卡编号',
	name varchar(64) NOT NULL COMMENT '关卡名称',
	major_id varchar(64) NOT NULL COMMENT '专业编号',
	course_id varchar(64) NOT NULL COMMENT '课程编号',
	position int(3) NOT NULL COMMENT '关卡位置',
	conditions varchar(255) NOT NULL COMMENT '过关条件',
	isreward char(1) NOT NULL COMMENT '是否设有奖学金',
	quiz_id varchar(64) COMMENT '测验编号',
	passcnt int(8) DEFAULT 0 NOT NULL COMMENT '通关人数',
	startdate date NOT NULL COMMENT '最早开启时间',
	topspeed_days tinyint(2) DEFAULT 0 COMMENT '极速闯关天数',
	topspeed_scholarship double COMMENT '极速闯关奖学金',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '专业关卡表';


-- 专业表
CREATE TABLE major
(
	id varchar(64) NOT NULL COMMENT '编号',
	name varchar(64) NOT NULL COMMENT '专业名称',
	logo varchar(255) COMMENT '图标',
	url varchar(255) COMMENT '详情页',
	lock_cnt int(3) COMMENT '关卡数',
	lesson_cnt int(4) COMMENT '课时数',
	livecourse_cnt int(3) COMMENT '专业直播课程数',
	homework_cnt int(3) COMMENT '作业数',
	kind char(1) NOT NULL COMMENT '专业类型',
	college char(1) COMMENT '所属学院',
	status char(1) DEFAULT '0' NOT NULL COMMENT '专业状态',
	version varchar(8) NOT NULL COMMENT '专业版本',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '专业表';


-- 测验
CREATE TABLE quiz
(
	id varchar(64) NOT NULL COMMENT '测验编号',
	name varchar(64) NOT NULL COMMENT '测验名称',
	attachment varchar(255) COMMENT '测验附件',
	type varchar(64) COMMENT '测验分类',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '测验';


-- 资源分类表
CREATE TABLE resourse_type
(
	id varchar(64) NOT NULL COMMENT '资源分类编号',
	name varchar(64) NOT NULL COMMENT '分类名称',
	pid varchar(64) NOT NULL COMMENT '直属上级分类编号',
	pids varchar(512) NOT NULL COMMENT '级联上级分类编号',
	-- 0：视频
	-- 1：作业
	-- 2：测验
	type char(1) NOT NULL COMMENT '资源类型',
	sort int(3) NOT NULL COMMENT '排序',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '资源分类表';


-- 老师表
CREATE TABLE teachers
(
	id varchar(64) NOT NULL COMMENT '老师编号',
	user_id varchar(64) COMMENT '用户编号',
	name varchar(16) NOT NULL COMMENT '姓名',
	title varchar(64) COMMENT '头衔',
	photo varchar(255) COMMENT '头像',
	type char(1) NOT NULL COMMENT '类型',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '老师表';


-- 章节表
CREATE TABLE unit
(
	id varchar(64) NOT NULL COMMENT '章节编号',
	name varchar(64) NOT NULL COMMENT '章节名称',
	sort int(3) NOT NULL COMMENT '排序',
	course_id varchar(64) NOT NULL COMMENT '课程编号',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '章节表';


-- 用户信息表
CREATE TABLE user_info
(
	id varchar(64) NOT NULL COMMENT '用户编号',
	name varchar(16) COMMENT '真实姓名',
	nickname varchar(64) NOT NULL COMMENT '昵称',
	qq varchar(16) COMMENT 'QQ',
	unit varchar(64) COMMENT '目前学校或单位',
	university varchar(64) COMMENT '毕业院校',
	intention varchar(255) COMMENT '求职意向',
	birthday date COMMENT '出生年月',
	tel varchar(32) COMMENT '联系电话',
	telchecked char(1) DEFAULT '0' COMMENT '手机校验',
	email varchar(32) COMMENT '邮箱',
	emailchecked char(1) DEFAULT '0' COMMENT '邮箱校验',
	area varchar(64) COMMENT '所在地区',
	address varchar(255) COMMENT '详址',
	ismarried char(1) COMMENT '是否结婚',
	high_education char(1) COMMENT '最高学历',
	edu_major char(1) COMMENT '专业',
	start_workyear date COMMENT '开始工作时间',
	blog_url varchar(255) COMMENT '技术博客地址',
	currentstatus char(1) COMMENT '目前状态',
	constellation char(1) COMMENT '星座',
	photo varchar(255) COMMENT '头像',
	identity_number varchar(32) COMMENT '身份证号',
	sex char(1) COMMENT '性别',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '用户信息表';


-- 视频表
CREATE TABLE vedio
(
	id varchar(64) NOT NULL COMMENT '视频编号',
	name varchar(64) NOT NULL COMMENT '视频名称',
	duration varchar(10) COMMENT '视频时长',
	url varchar(225) COMMENT '视频地址',
	status char(1) DEFAULT '0' NOT NULL COMMENT '视频状态',
	code varchar(12) COMMENT '自定义视频编号',
	catalog varchar(64) COMMENT '分类',
	tag varchar(225) COMMENT '标签',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '视频表';


-- 学生班级表
CREATE TABLE wclass_students
(
	id varchar(64) NOT NULL COMMENT '编号',
	class_id varchar(64) NOT NULL COMMENT '班级编号',
	student_id varchar(64) NOT NULL COMMENT '学生编号',
	learningTime int DEFAULT 0 NOT NULL COMMENT '学习时长',
	level char(1) COMMENT '综合级别',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '学生班级表';



