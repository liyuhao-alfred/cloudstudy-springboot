package com.cloudstudy.bo.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperatelogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OperatelogExample() {
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

        public Criteria andOperatorNoIsNull() {
            addCriterion("operator_no is null");
            return (Criteria) this;
        }

        public Criteria andOperatorNoIsNotNull() {
            addCriterion("operator_no is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorNoEqualTo(String value) {
            addCriterion("operator_no =", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoNotEqualTo(String value) {
            addCriterion("operator_no <>", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoGreaterThan(String value) {
            addCriterion("operator_no >", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoGreaterThanOrEqualTo(String value) {
            addCriterion("operator_no >=", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoLessThan(String value) {
            addCriterion("operator_no <", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoLessThanOrEqualTo(String value) {
            addCriterion("operator_no <=", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoLike(String value) {
            addCriterion("operator_no like", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoNotLike(String value) {
            addCriterion("operator_no not like", value, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoIn(List<String> values) {
            addCriterion("operator_no in", values, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoNotIn(List<String> values) {
            addCriterion("operator_no not in", values, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoBetween(String value1, String value2) {
            addCriterion("operator_no between", value1, value2, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorNoNotBetween(String value1, String value2) {
            addCriterion("operator_no not between", value1, value2, "operatorNo");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeIsNull() {
            addCriterion("operator_type is null");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeIsNotNull() {
            addCriterion("operator_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeEqualTo(Integer value) {
            addCriterion("operator_type =", value, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeNotEqualTo(Integer value) {
            addCriterion("operator_type <>", value, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeGreaterThan(Integer value) {
            addCriterion("operator_type >", value, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("operator_type >=", value, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeLessThan(Integer value) {
            addCriterion("operator_type <", value, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeLessThanOrEqualTo(Integer value) {
            addCriterion("operator_type <=", value, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeIn(List<Integer> values) {
            addCriterion("operator_type in", values, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeNotIn(List<Integer> values) {
            addCriterion("operator_type not in", values, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeBetween(Integer value1, Integer value2) {
            addCriterion("operator_type between", value1, value2, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("operator_type not between", value1, value2, "operatorType");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIsNull() {
            addCriterion("operator_name is null");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIsNotNull() {
            addCriterion("operator_name is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorNameEqualTo(String value) {
            addCriterion("operator_name =", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotEqualTo(String value) {
            addCriterion("operator_name <>", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameGreaterThan(String value) {
            addCriterion("operator_name >", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameGreaterThanOrEqualTo(String value) {
            addCriterion("operator_name >=", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLessThan(String value) {
            addCriterion("operator_name <", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLessThanOrEqualTo(String value) {
            addCriterion("operator_name <=", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLike(String value) {
            addCriterion("operator_name like", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotLike(String value) {
            addCriterion("operator_name not like", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIn(List<String> values) {
            addCriterion("operator_name in", values, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotIn(List<String> values) {
            addCriterion("operator_name not in", values, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameBetween(String value1, String value2) {
            addCriterion("operator_name between", value1, value2, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotBetween(String value1, String value2) {
            addCriterion("operator_name not between", value1, value2, "operatorName");
            return (Criteria) this;
        }

        public Criteria andRequestIpIsNull() {
            addCriterion("request_ip is null");
            return (Criteria) this;
        }

        public Criteria andRequestIpIsNotNull() {
            addCriterion("request_ip is not null");
            return (Criteria) this;
        }

        public Criteria andRequestIpEqualTo(String value) {
            addCriterion("request_ip =", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotEqualTo(String value) {
            addCriterion("request_ip <>", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpGreaterThan(String value) {
            addCriterion("request_ip >", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpGreaterThanOrEqualTo(String value) {
            addCriterion("request_ip >=", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpLessThan(String value) {
            addCriterion("request_ip <", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpLessThanOrEqualTo(String value) {
            addCriterion("request_ip <=", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpLike(String value) {
            addCriterion("request_ip like", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotLike(String value) {
            addCriterion("request_ip not like", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpIn(List<String> values) {
            addCriterion("request_ip in", values, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotIn(List<String> values) {
            addCriterion("request_ip not in", values, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpBetween(String value1, String value2) {
            addCriterion("request_ip between", value1, value2, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotBetween(String value1, String value2) {
            addCriterion("request_ip not between", value1, value2, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestUriIsNull() {
            addCriterion("request_uri is null");
            return (Criteria) this;
        }

        public Criteria andRequestUriIsNotNull() {
            addCriterion("request_uri is not null");
            return (Criteria) this;
        }

        public Criteria andRequestUriEqualTo(String value) {
            addCriterion("request_uri =", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriNotEqualTo(String value) {
            addCriterion("request_uri <>", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriGreaterThan(String value) {
            addCriterion("request_uri >", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriGreaterThanOrEqualTo(String value) {
            addCriterion("request_uri >=", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriLessThan(String value) {
            addCriterion("request_uri <", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriLessThanOrEqualTo(String value) {
            addCriterion("request_uri <=", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriLike(String value) {
            addCriterion("request_uri like", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriNotLike(String value) {
            addCriterion("request_uri not like", value, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriIn(List<String> values) {
            addCriterion("request_uri in", values, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriNotIn(List<String> values) {
            addCriterion("request_uri not in", values, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriBetween(String value1, String value2) {
            addCriterion("request_uri between", value1, value2, "requestUri");
            return (Criteria) this;
        }

        public Criteria andRequestUriNotBetween(String value1, String value2) {
            addCriterion("request_uri not between", value1, value2, "requestUri");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeIsNull() {
            addCriterion("operation_start_time is null");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeIsNotNull() {
            addCriterion("operation_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeEqualTo(Date value) {
            addCriterion("operation_start_time =", value, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeNotEqualTo(Date value) {
            addCriterion("operation_start_time <>", value, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeGreaterThan(Date value) {
            addCriterion("operation_start_time >", value, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operation_start_time >=", value, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeLessThan(Date value) {
            addCriterion("operation_start_time <", value, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("operation_start_time <=", value, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeIn(List<Date> values) {
            addCriterion("operation_start_time in", values, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeNotIn(List<Date> values) {
            addCriterion("operation_start_time not in", values, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeBetween(Date value1, Date value2) {
            addCriterion("operation_start_time between", value1, value2, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("operation_start_time not between", value1, value2, "operationStartTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeIsNull() {
            addCriterion("operation_end_time is null");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeIsNotNull() {
            addCriterion("operation_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeEqualTo(Date value) {
            addCriterion("operation_end_time =", value, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeNotEqualTo(Date value) {
            addCriterion("operation_end_time <>", value, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeGreaterThan(Date value) {
            addCriterion("operation_end_time >", value, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operation_end_time >=", value, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeLessThan(Date value) {
            addCriterion("operation_end_time <", value, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("operation_end_time <=", value, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeIn(List<Date> values) {
            addCriterion("operation_end_time in", values, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeNotIn(List<Date> values) {
            addCriterion("operation_end_time not in", values, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeBetween(Date value1, Date value2) {
            addCriterion("operation_end_time between", value1, value2, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("operation_end_time not between", value1, value2, "operationEndTime");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionIsNull() {
            addCriterion("operation_description is null");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionIsNotNull() {
            addCriterion("operation_description is not null");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionEqualTo(String value) {
            addCriterion("operation_description =", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionNotEqualTo(String value) {
            addCriterion("operation_description <>", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionGreaterThan(String value) {
            addCriterion("operation_description >", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("operation_description >=", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionLessThan(String value) {
            addCriterion("operation_description <", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionLessThanOrEqualTo(String value) {
            addCriterion("operation_description <=", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionLike(String value) {
            addCriterion("operation_description like", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionNotLike(String value) {
            addCriterion("operation_description not like", value, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionIn(List<String> values) {
            addCriterion("operation_description in", values, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionNotIn(List<String> values) {
            addCriterion("operation_description not in", values, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionBetween(String value1, String value2) {
            addCriterion("operation_description between", value1, value2, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationDescriptionNotBetween(String value1, String value2) {
            addCriterion("operation_description not between", value1, value2, "operationDescription");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageIsNull() {
            addCriterion("operation_result_flage is null");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageIsNotNull() {
            addCriterion("operation_result_flage is not null");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageEqualTo(Integer value) {
            addCriterion("operation_result_flage =", value, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageNotEqualTo(Integer value) {
            addCriterion("operation_result_flage <>", value, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageGreaterThan(Integer value) {
            addCriterion("operation_result_flage >", value, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageGreaterThanOrEqualTo(Integer value) {
            addCriterion("operation_result_flage >=", value, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageLessThan(Integer value) {
            addCriterion("operation_result_flage <", value, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageLessThanOrEqualTo(Integer value) {
            addCriterion("operation_result_flage <=", value, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageIn(List<Integer> values) {
            addCriterion("operation_result_flage in", values, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageNotIn(List<Integer> values) {
            addCriterion("operation_result_flage not in", values, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageBetween(Integer value1, Integer value2) {
            addCriterion("operation_result_flage between", value1, value2, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationResultFlageNotBetween(Integer value1, Integer value2) {
            addCriterion("operation_result_flage not between", value1, value2, "operationResultFlage");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeIsNull() {
            addCriterion("operation_error_code is null");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeIsNotNull() {
            addCriterion("operation_error_code is not null");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeEqualTo(String value) {
            addCriterion("operation_error_code =", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeNotEqualTo(String value) {
            addCriterion("operation_error_code <>", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeGreaterThan(String value) {
            addCriterion("operation_error_code >", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("operation_error_code >=", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeLessThan(String value) {
            addCriterion("operation_error_code <", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeLessThanOrEqualTo(String value) {
            addCriterion("operation_error_code <=", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeLike(String value) {
            addCriterion("operation_error_code like", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeNotLike(String value) {
            addCriterion("operation_error_code not like", value, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeIn(List<String> values) {
            addCriterion("operation_error_code in", values, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeNotIn(List<String> values) {
            addCriterion("operation_error_code not in", values, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeBetween(String value1, String value2) {
            addCriterion("operation_error_code between", value1, value2, "operationErrorCode");
            return (Criteria) this;
        }

        public Criteria andOperationErrorCodeNotBetween(String value1, String value2) {
            addCriterion("operation_error_code not between", value1, value2, "operationErrorCode");
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