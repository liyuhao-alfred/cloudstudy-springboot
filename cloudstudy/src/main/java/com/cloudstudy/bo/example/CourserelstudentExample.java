package com.cloudstudy.bo.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourserelstudentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CourserelstudentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdIsNull() {
            addCriterion("courserelteacher_id is null");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdIsNotNull() {
            addCriterion("courserelteacher_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdEqualTo(Integer value) {
            addCriterion("courserelteacher_id =", value, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdNotEqualTo(Integer value) {
            addCriterion("courserelteacher_id <>", value, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdGreaterThan(Integer value) {
            addCriterion("courserelteacher_id >", value, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("courserelteacher_id >=", value, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdLessThan(Integer value) {
            addCriterion("courserelteacher_id <", value, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdLessThanOrEqualTo(Integer value) {
            addCriterion("courserelteacher_id <=", value, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdIn(List<Integer> values) {
            addCriterion("courserelteacher_id in", values, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdNotIn(List<Integer> values) {
            addCriterion("courserelteacher_id not in", values, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdBetween(Integer value1, Integer value2) {
            addCriterion("courserelteacher_id between", value1, value2, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andCourserelteacherIdNotBetween(Integer value1, Integer value2) {
            addCriterion("courserelteacher_id not between", value1, value2, "courserelteacherId");
            return (Criteria) this;
        }

        public Criteria andStudentNoIsNull() {
            addCriterion("student_no is null");
            return (Criteria) this;
        }

        public Criteria andStudentNoIsNotNull() {
            addCriterion("student_no is not null");
            return (Criteria) this;
        }

        public Criteria andStudentNoEqualTo(String value) {
            addCriterion("student_no =", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotEqualTo(String value) {
            addCriterion("student_no <>", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoGreaterThan(String value) {
            addCriterion("student_no >", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoGreaterThanOrEqualTo(String value) {
            addCriterion("student_no >=", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoLessThan(String value) {
            addCriterion("student_no <", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoLessThanOrEqualTo(String value) {
            addCriterion("student_no <=", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoLike(String value) {
            addCriterion("student_no like", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotLike(String value) {
            addCriterion("student_no not like", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoIn(List<String> values) {
            addCriterion("student_no in", values, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotIn(List<String> values) {
            addCriterion("student_no not in", values, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoBetween(String value1, String value2) {
            addCriterion("student_no between", value1, value2, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotBetween(String value1, String value2) {
            addCriterion("student_no not between", value1, value2, "studentNo");
            return (Criteria) this;
        }

        public Criteria andGradeIsNull() {
            addCriterion("grade is null");
            return (Criteria) this;
        }

        public Criteria andGradeIsNotNull() {
            addCriterion("grade is not null");
            return (Criteria) this;
        }

        public Criteria andGradeEqualTo(Integer value) {
            addCriterion("grade =", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotEqualTo(Integer value) {
            addCriterion("grade <>", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThan(Integer value) {
            addCriterion("grade >", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThanOrEqualTo(Integer value) {
            addCriterion("grade >=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThan(Integer value) {
            addCriterion("grade <", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThanOrEqualTo(Integer value) {
            addCriterion("grade <=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeIn(List<Integer> values) {
            addCriterion("grade in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotIn(List<Integer> values) {
            addCriterion("grade not in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeBetween(Integer value1, Integer value2) {
            addCriterion("grade between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotBetween(Integer value1, Integer value2) {
            addCriterion("grade not between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumIsNull() {
            addCriterion("task_declare_num is null");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumIsNotNull() {
            addCriterion("task_declare_num is not null");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumEqualTo(Integer value) {
            addCriterion("task_declare_num =", value, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumNotEqualTo(Integer value) {
            addCriterion("task_declare_num <>", value, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumGreaterThan(Integer value) {
            addCriterion("task_declare_num >", value, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_declare_num >=", value, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumLessThan(Integer value) {
            addCriterion("task_declare_num <", value, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumLessThanOrEqualTo(Integer value) {
            addCriterion("task_declare_num <=", value, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumIn(List<Integer> values) {
            addCriterion("task_declare_num in", values, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumNotIn(List<Integer> values) {
            addCriterion("task_declare_num not in", values, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumBetween(Integer value1, Integer value2) {
            addCriterion("task_declare_num between", value1, value2, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskDeclareNumNotBetween(Integer value1, Integer value2) {
            addCriterion("task_declare_num not between", value1, value2, "taskDeclareNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumIsNull() {
            addCriterion("task_accept_num is null");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumIsNotNull() {
            addCriterion("task_accept_num is not null");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumEqualTo(Integer value) {
            addCriterion("task_accept_num =", value, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumNotEqualTo(Integer value) {
            addCriterion("task_accept_num <>", value, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumGreaterThan(Integer value) {
            addCriterion("task_accept_num >", value, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_accept_num >=", value, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumLessThan(Integer value) {
            addCriterion("task_accept_num <", value, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumLessThanOrEqualTo(Integer value) {
            addCriterion("task_accept_num <=", value, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumIn(List<Integer> values) {
            addCriterion("task_accept_num in", values, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumNotIn(List<Integer> values) {
            addCriterion("task_accept_num not in", values, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumBetween(Integer value1, Integer value2) {
            addCriterion("task_accept_num between", value1, value2, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andTaskAcceptNumNotBetween(Integer value1, Integer value2) {
            addCriterion("task_accept_num not between", value1, value2, "taskAcceptNum");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIsNull() {
            addCriterion("last_modify_time is null");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIsNotNull() {
            addCriterion("last_modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeEqualTo(Date value) {
            addCriterion("last_modify_time =", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotEqualTo(Date value) {
            addCriterion("last_modify_time <>", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeGreaterThan(Date value) {
            addCriterion("last_modify_time >", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_modify_time >=", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeLessThan(Date value) {
            addCriterion("last_modify_time <", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_modify_time <=", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIn(List<Date> values) {
            addCriterion("last_modify_time in", values, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotIn(List<Date> values) {
            addCriterion("last_modify_time not in", values, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeBetween(Date value1, Date value2) {
            addCriterion("last_modify_time between", value1, value2, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_modify_time not between", value1, value2, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}