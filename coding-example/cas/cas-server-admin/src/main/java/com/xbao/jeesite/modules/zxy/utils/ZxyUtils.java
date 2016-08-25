package com.thinkgem.jeesite.modules.zxy.utils;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Course;
import com.thinkgem.jeesite.modules.zxy.entity.Issue;
import com.thinkgem.jeesite.modules.zxy.entity.Major;
import com.thinkgem.jeesite.modules.zxy.entity.Quiz;
import com.thinkgem.jeesite.modules.zxy.entity.Unit;
import com.thinkgem.jeesite.modules.zxy.service.CourseService;
import com.thinkgem.jeesite.modules.zxy.service.IssueService;
import com.thinkgem.jeesite.modules.zxy.service.MajorService;
import com.thinkgem.jeesite.modules.zxy.service.QuizService;
import com.thinkgem.jeesite.modules.zxy.service.UnitService;

public class ZxyUtils {
	private static IssueService issueService = SpringContextHolder.getBean(IssueService.class);
	private static MajorService majorService = SpringContextHolder.getBean(MajorService.class);
	private static CourseService courseService = SpringContextHolder.getBean(CourseService.class);
	private static UnitService unitService = SpringContextHolder.getBean(UnitService.class);
	private static QuizService quizService = SpringContextHolder.getBean(QuizService.class);
	private static final String ZXY_CACHE = "zxyCache";
	
	/**
	 * 获取期次列表
	 * @return
	 */
	public static List<Issue> getIssueList(){
		@SuppressWarnings("unchecked")
		List<Issue> issueList = (List<Issue>)CacheUtils.get(ZXY_CACHE, "issueList");
		if (issueList == null){
			Page<Issue> page = new Page<Issue>(1, -1);
			page = issueService.findPage(page, new Issue());
			issueList = page.getList();
			for(Issue issue:issueList){
				issue.setName(issue.getName()+" 第"+issue.getNum()+"期");
			}
			CacheUtils.put(ZXY_CACHE, "issueList", issueList);
		}
		return issueList;
	}
	
	/**
	 * 获得期次信息
	 * @param courseId 专业编号
	 */
	public static Issue getIssue(String issueId){
		String id = "1";
		if (StringUtils.isNotBlank(issueId)){
			id = issueId;
		}
		for (Issue issue : getIssueList()){
			if (issue.getId().equals(id)){
				return issue;
			}
		}
		return new Issue(id);
	}
	
	/**
	 * 获取专业列表
	 * @return
	 */
	public static List<Major> getMajorList(){
		@SuppressWarnings("unchecked")
		List<Major> majorList = (List<Major>)CacheUtils.get(ZXY_CACHE, "majorList");
		if (majorList == null){
			Page<Major> page = new Page<Major>(1, -1);
			page = majorService.findPage(page, new Major());
			majorList = page.getList();
			for(Major major:majorList){
				major.setName(major.getName()+" V"+major.getVersion());
			}
			CacheUtils.put(ZXY_CACHE, "majorList", majorList);
		}
		return majorList;
	}
	
	/**
	 * 获取专业列表
	 * @param status 专业状态 0：现有专业 1：往期专业
	 * @return
	 */
	public static List<Major> getMajorListByStatus(String status){
		List<Major> resultList =new ArrayList<Major>();
		for(Major major:getMajorList()){
			if(major.getStatus().equals(status)){
				resultList.add(major);
			}
		}
		return resultList;
	}
	
	/**
	 * 获得专业信息
	 * @param majorId 专业编号
	 */
	public static Major getMajor(String majorId){
		String id = "1";
		if (StringUtils.isNotBlank(majorId)){
			id = majorId;
		}
		for (Major major : getMajorList()){
			if (major.getId().equals(id)){
				return major;
			}
		}
		return new Major(id);
	}

	/**
	 * 获取课程列表
	 * @return
	 */
	public static List<Course> getCourseList(){
		@SuppressWarnings("unchecked")
		List<Course> courseList = (List<Course>)CacheUtils.get(ZXY_CACHE, "courseList");
		if (courseList == null){
			Page<Course> page = new Page<Course>(1, -1);
			page = courseService.findPage(page, new Course());
			courseList = page.getList();
			for(Course course:courseList){
				course.setName(course.getName()+" V"+course.getVersion());
			}
			CacheUtils.put(ZXY_CACHE, "courseList", courseList);
		}
		return courseList;
	}
	
	/**
	 * 获得课程信息
	 * @param courseId 课程编号
	 */
	public static Course getCourse(String courseId){
		String id = "1";
		if (StringUtils.isNotBlank(courseId)){
			id = courseId;
		}
		for (Course course : getCourseList()){
			if (course.getId().equals(id)){
				return course;
			}
		}
		return new Course(id);
	}
	
	/**
	 * 获取章节列表
	 * @return
	 */
	public static List<Unit> getUnitList(){
		@SuppressWarnings("unchecked")
		List<Unit> unitList = (List<Unit>)CacheUtils.get(ZXY_CACHE, "unitList");
		if (unitList == null){
			Page<Unit> page = new Page<Unit>(1, -1);
			page = unitService.findPage(page, new Unit());
			unitList = page.getList();
			CacheUtils.put(ZXY_CACHE, "unitList", unitList);
		}
		return unitList;
	}
	
	/**
	 * 获取章节列表
	 * @param courseId 课程编号
	 * @return
	 */
	public static List<Unit> getUnitListByCourseId(String courseId){
		List<Unit> resultList =new ArrayList<Unit>();
		for(Unit unit:getUnitList()){
			if(unit.getCourseId().equals(courseId)){
				resultList.add(unit);
			}
		}
		return resultList;
	}
	
	/**
	 * 获得章节信息
	 * @param unitId 章节编号
	 */
	public static Unit getUnit(String unitId){
		String id = "1";
		if (StringUtils.isNotBlank(unitId)){
			id = unitId;
		}
		for (Unit unit : getUnitList()){
			if (unit.getId().equals(id)){
				return unit;
			}
		}
		return new Unit(id);
	}
	
	/**
	 * 获取测验列表
	 * @return
	 */
	public static List<Quiz> getQuizList(){
		@SuppressWarnings("unchecked")
		List<Quiz> quizList = (List<Quiz>)CacheUtils.get(ZXY_CACHE, "quizList");
		if (quizList == null){
			Page<Quiz> page = new Page<Quiz>(1, -1);
			page = quizService.findPage(page, new Quiz());
			quizList = page.getList();
			CacheUtils.put(ZXY_CACHE, "quizList", quizList);
		}
		return quizList;
	}
	
	/**
	 * 获得测验信息
	 * @param unitId 章节编号
	 */
	public static Quiz getQuiz(String quizId){
		String id = "1";
		if (StringUtils.isNotBlank(quizId)){
			id = quizId;
		}
		for (Quiz quiz : getQuizList()){
			if (quiz.getId().equals(id)){
				return quiz;
			}
		}
		return new Quiz(id);
	}
	
	public static Object getCache(String key) {
		return CacheUtils.get(ZXY_CACHE, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(ZXY_CACHE, key, value);
	}

	public static void removeCache(String key) {
		CacheUtils.remove(ZXY_CACHE, key);
	}
}
