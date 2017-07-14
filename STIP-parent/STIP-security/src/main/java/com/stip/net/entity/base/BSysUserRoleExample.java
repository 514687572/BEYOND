package com.stip.net.entity.base;

import com.stip.mybatis.generator.plugin.BaseCriteria;
import com.stip.mybatis.generator.plugin.BaseModelExample;
import java.util.ArrayList;
import java.util.List;

public class BSysUserRoleExample extends BaseModelExample {
    protected List<Criteria> oredCriteria;

    public BSysUserRoleExample() {
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

        public Criteria andRoleIdIsNull() {
            addCriterion("sysUserRole.ROLE_ID is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("sysUserRole.ROLE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_ID =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_ID <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(String value) {
            addCriterion("sysUserRole.ROLE_ID >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_ID >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(String value) {
            addCriterion("sysUserRole.ROLE_ID <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_ID <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLike(String value) {
            addCriterion("sysUserRole.ROLE_ID like", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotLike(String value) {
            addCriterion("sysUserRole.ROLE_ID not like", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<String> values) {
            addCriterion("sysUserRole.ROLE_ID in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<String> values) {
            addCriterion("sysUserRole.ROLE_ID not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(String value1, String value2) {
            addCriterion("sysUserRole.ROLE_ID between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(String value1, String value2) {
            addCriterion("sysUserRole.ROLE_ID not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleNameIsNull() {
            addCriterion("sysUserRole.ROLE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRoleNameIsNotNull() {
            addCriterion("sysUserRole.ROLE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRoleNameEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_NAME =", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_NAME <>", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThan(String value) {
            addCriterion("sysUserRole.ROLE_NAME >", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_NAME >=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThan(String value) {
            addCriterion("sysUserRole.ROLE_NAME <", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThanOrEqualTo(String value) {
            addCriterion("sysUserRole.ROLE_NAME <=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLike(String value) {
            addCriterion("sysUserRole.ROLE_NAME like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotLike(String value) {
            addCriterion("sysUserRole.ROLE_NAME not like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameIn(List<String> values) {
            addCriterion("sysUserRole.ROLE_NAME in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotIn(List<String> values) {
            addCriterion("sysUserRole.ROLE_NAME not in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameBetween(String value1, String value2) {
            addCriterion("sysUserRole.ROLE_NAME between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotBetween(String value1, String value2) {
            addCriterion("sysUserRole.ROLE_NAME not between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andAuthorityListIsNull() {
            addCriterion("sysUserRole.AUTHORITY_LIST is null");
            return (Criteria) this;
        }

        public Criteria andAuthorityListIsNotNull() {
            addCriterion("sysUserRole.AUTHORITY_LIST is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorityListEqualTo(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST =", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotEqualTo(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST <>", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListGreaterThan(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST >", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST >=", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListLessThan(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST <", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListLessThanOrEqualTo(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST <=", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListLike(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST like", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotLike(String value) {
            addCriterion("sysUserRole.AUTHORITY_LIST not like", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListIn(List<String> values) {
            addCriterion("sysUserRole.AUTHORITY_LIST in", values, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotIn(List<String> values) {
            addCriterion("sysUserRole.AUTHORITY_LIST not in", values, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListBetween(String value1, String value2) {
            addCriterion("sysUserRole.AUTHORITY_LIST between", value1, value2, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotBetween(String value1, String value2) {
            addCriterion("sysUserRole.AUTHORITY_LIST not between", value1, value2, "authorityList");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("sysUserRole.REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("sysUserRole.REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("sysUserRole.REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("sysUserRole.REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("sysUserRole.REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserRole.REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("sysUserRole.REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("sysUserRole.REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("sysUserRole.REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("sysUserRole.REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("sysUserRole.REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("sysUserRole.REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("sysUserRole.REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("sysUserRole.REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}