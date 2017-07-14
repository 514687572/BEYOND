package com.stip.net.entity.base;

import com.stip.mybatis.generator.plugin.BaseCriteria;
import com.stip.mybatis.generator.plugin.BaseModelExample;
import java.util.ArrayList;
import java.util.List;

public class BSysElementExample extends BaseModelExample {
    protected List<Criteria> oredCriteria;

    public BSysElementExample() {
        oredCriteria = new ArrayList<Criteria>();
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
        super.clear();
        oredCriteria.clear();
    }

    protected abstract static class GeneratedCriteria extends BaseCriteria {

        public Criteria andEleIdIsNull() {
            addCriterion("sysElement.ELE_ID is null");
            return (Criteria) this;
        }

        public Criteria andEleIdIsNotNull() {
            addCriterion("sysElement.ELE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEleIdEqualTo(Long value) {
            addCriterion("sysElement.ELE_ID =", value, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdNotEqualTo(Long value) {
            addCriterion("sysElement.ELE_ID <>", value, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdGreaterThan(Long value) {
            addCriterion("sysElement.ELE_ID >", value, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sysElement.ELE_ID >=", value, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdLessThan(Long value) {
            addCriterion("sysElement.ELE_ID <", value, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdLessThanOrEqualTo(Long value) {
            addCriterion("sysElement.ELE_ID <=", value, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdIn(List<Long> values) {
            addCriterion("sysElement.ELE_ID in", values, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdNotIn(List<Long> values) {
            addCriterion("sysElement.ELE_ID not in", values, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdBetween(Long value1, Long value2) {
            addCriterion("sysElement.ELE_ID between", value1, value2, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleIdNotBetween(Long value1, Long value2) {
            addCriterion("sysElement.ELE_ID not between", value1, value2, "eleId");
            return (Criteria) this;
        }

        public Criteria andEleNameIsNull() {
            addCriterion("sysElement.ELE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andEleNameIsNotNull() {
            addCriterion("sysElement.ELE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andEleNameEqualTo(String value) {
            addCriterion("sysElement.ELE_NAME =", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameNotEqualTo(String value) {
            addCriterion("sysElement.ELE_NAME <>", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameGreaterThan(String value) {
            addCriterion("sysElement.ELE_NAME >", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameGreaterThanOrEqualTo(String value) {
            addCriterion("sysElement.ELE_NAME >=", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameLessThan(String value) {
            addCriterion("sysElement.ELE_NAME <", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameLessThanOrEqualTo(String value) {
            addCriterion("sysElement.ELE_NAME <=", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameLike(String value) {
            addCriterion("sysElement.ELE_NAME like", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameNotLike(String value) {
            addCriterion("sysElement.ELE_NAME not like", value, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameIn(List<String> values) {
            addCriterion("sysElement.ELE_NAME in", values, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameNotIn(List<String> values) {
            addCriterion("sysElement.ELE_NAME not in", values, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameBetween(String value1, String value2) {
            addCriterion("sysElement.ELE_NAME between", value1, value2, "eleName");
            return (Criteria) this;
        }

        public Criteria andEleNameNotBetween(String value1, String value2) {
            addCriterion("sysElement.ELE_NAME not between", value1, value2, "eleName");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("sysElement.PARENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("sysElement.PARENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("sysElement.PARENT_ID =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("sysElement.PARENT_ID <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("sysElement.PARENT_ID >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sysElement.PARENT_ID >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("sysElement.PARENT_ID <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("sysElement.PARENT_ID <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Long> values) {
            addCriterion("sysElement.PARENT_ID in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Long> values) {
            addCriterion("sysElement.PARENT_ID not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("sysElement.PARENT_ID between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("sysElement.PARENT_ID not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andEleCodeIsNull() {
            addCriterion("sysElement.ELE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andEleCodeIsNotNull() {
            addCriterion("sysElement.ELE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andEleCodeEqualTo(String value) {
            addCriterion("sysElement.ELE_CODE =", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeNotEqualTo(String value) {
            addCriterion("sysElement.ELE_CODE <>", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeGreaterThan(String value) {
            addCriterion("sysElement.ELE_CODE >", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sysElement.ELE_CODE >=", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeLessThan(String value) {
            addCriterion("sysElement.ELE_CODE <", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeLessThanOrEqualTo(String value) {
            addCriterion("sysElement.ELE_CODE <=", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeLike(String value) {
            addCriterion("sysElement.ELE_CODE like", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeNotLike(String value) {
            addCriterion("sysElement.ELE_CODE not like", value, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeIn(List<String> values) {
            addCriterion("sysElement.ELE_CODE in", values, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeNotIn(List<String> values) {
            addCriterion("sysElement.ELE_CODE not in", values, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeBetween(String value1, String value2) {
            addCriterion("sysElement.ELE_CODE between", value1, value2, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleCodeNotBetween(String value1, String value2) {
            addCriterion("sysElement.ELE_CODE not between", value1, value2, "eleCode");
            return (Criteria) this;
        }

        public Criteria andEleIndexIsNull() {
            addCriterion("sysElement.ELE_INDEX is null");
            return (Criteria) this;
        }

        public Criteria andEleIndexIsNotNull() {
            addCriterion("sysElement.ELE_INDEX is not null");
            return (Criteria) this;
        }

        public Criteria andEleIndexEqualTo(Long value) {
            addCriterion("sysElement.ELE_INDEX =", value, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexNotEqualTo(Long value) {
            addCriterion("sysElement.ELE_INDEX <>", value, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexGreaterThan(Long value) {
            addCriterion("sysElement.ELE_INDEX >", value, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexGreaterThanOrEqualTo(Long value) {
            addCriterion("sysElement.ELE_INDEX >=", value, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexLessThan(Long value) {
            addCriterion("sysElement.ELE_INDEX <", value, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexLessThanOrEqualTo(Long value) {
            addCriterion("sysElement.ELE_INDEX <=", value, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexIn(List<Long> values) {
            addCriterion("sysElement.ELE_INDEX in", values, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexNotIn(List<Long> values) {
            addCriterion("sysElement.ELE_INDEX not in", values, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexBetween(Long value1, Long value2) {
            addCriterion("sysElement.ELE_INDEX between", value1, value2, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andEleIndexNotBetween(Long value1, Long value2) {
            addCriterion("sysElement.ELE_INDEX not between", value1, value2, "eleIndex");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("sysElement.REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("sysElement.REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("sysElement.REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("sysElement.REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("sysElement.REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("sysElement.REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("sysElement.REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("sysElement.REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("sysElement.REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("sysElement.REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("sysElement.REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("sysElement.REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("sysElement.REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("sysElement.REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}