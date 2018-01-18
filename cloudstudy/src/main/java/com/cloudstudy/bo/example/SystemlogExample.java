package com.cloudstudy.bo.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemlogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SystemlogExample() {
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

        public Criteria andLogLevelIsNull() {
            addCriterion("log_level is null");
            return (Criteria) this;
        }

        public Criteria andLogLevelIsNotNull() {
            addCriterion("log_level is not null");
            return (Criteria) this;
        }

        public Criteria andLogLevelEqualTo(String value) {
            addCriterion("log_level =", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelNotEqualTo(String value) {
            addCriterion("log_level <>", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelGreaterThan(String value) {
            addCriterion("log_level >", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelGreaterThanOrEqualTo(String value) {
            addCriterion("log_level >=", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelLessThan(String value) {
            addCriterion("log_level <", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelLessThanOrEqualTo(String value) {
            addCriterion("log_level <=", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelLike(String value) {
            addCriterion("log_level like", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelNotLike(String value) {
            addCriterion("log_level not like", value, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelIn(List<String> values) {
            addCriterion("log_level in", values, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelNotIn(List<String> values) {
            addCriterion("log_level not in", values, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelBetween(String value1, String value2) {
            addCriterion("log_level between", value1, value2, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogLevelNotBetween(String value1, String value2) {
            addCriterion("log_level not between", value1, value2, "logLevel");
            return (Criteria) this;
        }

        public Criteria andLogTimeIsNull() {
            addCriterion("log_time is null");
            return (Criteria) this;
        }

        public Criteria andLogTimeIsNotNull() {
            addCriterion("log_time is not null");
            return (Criteria) this;
        }

        public Criteria andLogTimeEqualTo(Date value) {
            addCriterion("log_time =", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeNotEqualTo(Date value) {
            addCriterion("log_time <>", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeGreaterThan(Date value) {
            addCriterion("log_time >", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("log_time >=", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeLessThan(Date value) {
            addCriterion("log_time <", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeLessThanOrEqualTo(Date value) {
            addCriterion("log_time <=", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeIn(List<Date> values) {
            addCriterion("log_time in", values, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeNotIn(List<Date> values) {
            addCriterion("log_time not in", values, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeBetween(Date value1, Date value2) {
            addCriterion("log_time between", value1, value2, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeNotBetween(Date value1, Date value2) {
            addCriterion("log_time not between", value1, value2, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTypeIsNull() {
            addCriterion("log_type is null");
            return (Criteria) this;
        }

        public Criteria andLogTypeIsNotNull() {
            addCriterion("log_type is not null");
            return (Criteria) this;
        }

        public Criteria andLogTypeEqualTo(Integer value) {
            addCriterion("log_type =", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotEqualTo(Integer value) {
            addCriterion("log_type <>", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThan(Integer value) {
            addCriterion("log_type >", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("log_type >=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThan(Integer value) {
            addCriterion("log_type <", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThanOrEqualTo(Integer value) {
            addCriterion("log_type <=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeIn(List<Integer> values) {
            addCriterion("log_type in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotIn(List<Integer> values) {
            addCriterion("log_type not in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeBetween(Integer value1, Integer value2) {
            addCriterion("log_type between", value1, value2, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("log_type not between", value1, value2, "logType");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpIsNull() {
            addCriterion("remote_call_ip is null");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpIsNotNull() {
            addCriterion("remote_call_ip is not null");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpEqualTo(String value) {
            addCriterion("remote_call_ip =", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpNotEqualTo(String value) {
            addCriterion("remote_call_ip <>", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpGreaterThan(String value) {
            addCriterion("remote_call_ip >", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpGreaterThanOrEqualTo(String value) {
            addCriterion("remote_call_ip >=", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpLessThan(String value) {
            addCriterion("remote_call_ip <", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpLessThanOrEqualTo(String value) {
            addCriterion("remote_call_ip <=", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpLike(String value) {
            addCriterion("remote_call_ip like", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpNotLike(String value) {
            addCriterion("remote_call_ip not like", value, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpIn(List<String> values) {
            addCriterion("remote_call_ip in", values, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpNotIn(List<String> values) {
            addCriterion("remote_call_ip not in", values, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpBetween(String value1, String value2) {
            addCriterion("remote_call_ip between", value1, value2, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andRemoteCallIpNotBetween(String value1, String value2) {
            addCriterion("remote_call_ip not between", value1, value2, "remoteCallIp");
            return (Criteria) this;
        }

        public Criteria andThreadIdIsNull() {
            addCriterion("thread_id is null");
            return (Criteria) this;
        }

        public Criteria andThreadIdIsNotNull() {
            addCriterion("thread_id is not null");
            return (Criteria) this;
        }

        public Criteria andThreadIdEqualTo(String value) {
            addCriterion("thread_id =", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotEqualTo(String value) {
            addCriterion("thread_id <>", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdGreaterThan(String value) {
            addCriterion("thread_id >", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdGreaterThanOrEqualTo(String value) {
            addCriterion("thread_id >=", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdLessThan(String value) {
            addCriterion("thread_id <", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdLessThanOrEqualTo(String value) {
            addCriterion("thread_id <=", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdLike(String value) {
            addCriterion("thread_id like", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotLike(String value) {
            addCriterion("thread_id not like", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdIn(List<String> values) {
            addCriterion("thread_id in", values, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotIn(List<String> values) {
            addCriterion("thread_id not in", values, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdBetween(String value1, String value2) {
            addCriterion("thread_id between", value1, value2, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotBetween(String value1, String value2) {
            addCriterion("thread_id not between", value1, value2, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadNameIsNull() {
            addCriterion("thread_name is null");
            return (Criteria) this;
        }

        public Criteria andThreadNameIsNotNull() {
            addCriterion("thread_name is not null");
            return (Criteria) this;
        }

        public Criteria andThreadNameEqualTo(String value) {
            addCriterion("thread_name =", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotEqualTo(String value) {
            addCriterion("thread_name <>", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameGreaterThan(String value) {
            addCriterion("thread_name >", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameGreaterThanOrEqualTo(String value) {
            addCriterion("thread_name >=", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameLessThan(String value) {
            addCriterion("thread_name <", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameLessThanOrEqualTo(String value) {
            addCriterion("thread_name <=", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameLike(String value) {
            addCriterion("thread_name like", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotLike(String value) {
            addCriterion("thread_name not like", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameIn(List<String> values) {
            addCriterion("thread_name in", values, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotIn(List<String> values) {
            addCriterion("thread_name not in", values, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameBetween(String value1, String value2) {
            addCriterion("thread_name between", value1, value2, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotBetween(String value1, String value2) {
            addCriterion("thread_name not between", value1, value2, "threadName");
            return (Criteria) this;
        }

        public Criteria andServiceClassIsNull() {
            addCriterion("service_class is null");
            return (Criteria) this;
        }

        public Criteria andServiceClassIsNotNull() {
            addCriterion("service_class is not null");
            return (Criteria) this;
        }

        public Criteria andServiceClassEqualTo(String value) {
            addCriterion("service_class =", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassNotEqualTo(String value) {
            addCriterion("service_class <>", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassGreaterThan(String value) {
            addCriterion("service_class >", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassGreaterThanOrEqualTo(String value) {
            addCriterion("service_class >=", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassLessThan(String value) {
            addCriterion("service_class <", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassLessThanOrEqualTo(String value) {
            addCriterion("service_class <=", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassLike(String value) {
            addCriterion("service_class like", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassNotLike(String value) {
            addCriterion("service_class not like", value, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassIn(List<String> values) {
            addCriterion("service_class in", values, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassNotIn(List<String> values) {
            addCriterion("service_class not in", values, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassBetween(String value1, String value2) {
            addCriterion("service_class between", value1, value2, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceClassNotBetween(String value1, String value2) {
            addCriterion("service_class not between", value1, value2, "serviceClass");
            return (Criteria) this;
        }

        public Criteria andServiceMethodIsNull() {
            addCriterion("service_method is null");
            return (Criteria) this;
        }

        public Criteria andServiceMethodIsNotNull() {
            addCriterion("service_method is not null");
            return (Criteria) this;
        }

        public Criteria andServiceMethodEqualTo(String value) {
            addCriterion("service_method =", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotEqualTo(String value) {
            addCriterion("service_method <>", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodGreaterThan(String value) {
            addCriterion("service_method >", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodGreaterThanOrEqualTo(String value) {
            addCriterion("service_method >=", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodLessThan(String value) {
            addCriterion("service_method <", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodLessThanOrEqualTo(String value) {
            addCriterion("service_method <=", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodLike(String value) {
            addCriterion("service_method like", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotLike(String value) {
            addCriterion("service_method not like", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodIn(List<String> values) {
            addCriterion("service_method in", values, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotIn(List<String> values) {
            addCriterion("service_method not in", values, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodBetween(String value1, String value2) {
            addCriterion("service_method between", value1, value2, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotBetween(String value1, String value2) {
            addCriterion("service_method not between", value1, value2, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageIsNull() {
            addCriterion("service_result_flage is null");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageIsNotNull() {
            addCriterion("service_result_flage is not null");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageEqualTo(Integer value) {
            addCriterion("service_result_flage =", value, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageNotEqualTo(Integer value) {
            addCriterion("service_result_flage <>", value, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageGreaterThan(Integer value) {
            addCriterion("service_result_flage >", value, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_result_flage >=", value, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageLessThan(Integer value) {
            addCriterion("service_result_flage <", value, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageLessThanOrEqualTo(Integer value) {
            addCriterion("service_result_flage <=", value, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageIn(List<Integer> values) {
            addCriterion("service_result_flage in", values, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageNotIn(List<Integer> values) {
            addCriterion("service_result_flage not in", values, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageBetween(Integer value1, Integer value2) {
            addCriterion("service_result_flage between", value1, value2, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceResultFlageNotBetween(Integer value1, Integer value2) {
            addCriterion("service_result_flage not between", value1, value2, "serviceResultFlage");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeIsNull() {
            addCriterion("service_error_code is null");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeIsNotNull() {
            addCriterion("service_error_code is not null");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeEqualTo(String value) {
            addCriterion("service_error_code =", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeNotEqualTo(String value) {
            addCriterion("service_error_code <>", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeGreaterThan(String value) {
            addCriterion("service_error_code >", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("service_error_code >=", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeLessThan(String value) {
            addCriterion("service_error_code <", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeLessThanOrEqualTo(String value) {
            addCriterion("service_error_code <=", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeLike(String value) {
            addCriterion("service_error_code like", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeNotLike(String value) {
            addCriterion("service_error_code not like", value, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeIn(List<String> values) {
            addCriterion("service_error_code in", values, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeNotIn(List<String> values) {
            addCriterion("service_error_code not in", values, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeBetween(String value1, String value2) {
            addCriterion("service_error_code between", value1, value2, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceErrorCodeNotBetween(String value1, String value2) {
            addCriterion("service_error_code not between", value1, value2, "serviceErrorCode");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeIsNull() {
            addCriterion("service_running_time is null");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeIsNotNull() {
            addCriterion("service_running_time is not null");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeEqualTo(String value) {
            addCriterion("service_running_time =", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeNotEqualTo(String value) {
            addCriterion("service_running_time <>", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeGreaterThan(String value) {
            addCriterion("service_running_time >", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeGreaterThanOrEqualTo(String value) {
            addCriterion("service_running_time >=", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeLessThan(String value) {
            addCriterion("service_running_time <", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeLessThanOrEqualTo(String value) {
            addCriterion("service_running_time <=", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeLike(String value) {
            addCriterion("service_running_time like", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeNotLike(String value) {
            addCriterion("service_running_time not like", value, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeIn(List<String> values) {
            addCriterion("service_running_time in", values, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeNotIn(List<String> values) {
            addCriterion("service_running_time not in", values, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeBetween(String value1, String value2) {
            addCriterion("service_running_time between", value1, value2, "serviceRunningTime");
            return (Criteria) this;
        }

        public Criteria andServiceRunningTimeNotBetween(String value1, String value2) {
            addCriterion("service_running_time not between", value1, value2, "serviceRunningTime");
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