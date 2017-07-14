package com.stip.net.entity.base;

import com.stip.mybatis.generator.plugin.BaseCriteria;
import com.stip.mybatis.generator.plugin.BaseModelExample;
import java.util.ArrayList;
import java.util.List;

public class BSysUserAuthorityExample extends BaseModelExample {
    protected List<Criteria> oredCriteria;

    public BSysUserAuthorityExample() {
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

        public Criteria andAuthIdIsNull() {
            addCriterion("sysUserAuthority.AUTH_ID is null");
            return (Criteria) this;
        }

        public Criteria andAuthIdIsNotNull() {
            addCriterion("sysUserAuthority.AUTH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAuthIdEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTH_ID =", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTH_ID <>", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdGreaterThan(String value) {
            addCriterion("sysUserAuthority.AUTH_ID >", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTH_ID >=", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLessThan(String value) {
            addCriterion("sysUserAuthority.AUTH_ID <", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLessThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTH_ID <=", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdLike(String value) {
            addCriterion("sysUserAuthority.AUTH_ID like", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotLike(String value) {
            addCriterion("sysUserAuthority.AUTH_ID not like", value, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdIn(List<String> values) {
            addCriterion("sysUserAuthority.AUTH_ID in", values, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotIn(List<String> values) {
            addCriterion("sysUserAuthority.AUTH_ID not in", values, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.AUTH_ID between", value1, value2, "authId");
            return (Criteria) this;
        }

        public Criteria andAuthIdNotBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.AUTH_ID not between", value1, value2, "authId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("sysUserAuthority.USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("sysUserAuthority.USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("sysUserAuthority.USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("sysUserAuthority.USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("sysUserAuthority.USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("sysUserAuthority.USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("sysUserAuthority.USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("sysUserAuthority.USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("sysUserAuthority.USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("sysUserAuthority.USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("sysUserAuthority.ROLE_ID is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("sysUserAuthority.ROLE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(String value) {
            addCriterion("sysUserAuthority.ROLE_ID =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(String value) {
            addCriterion("sysUserAuthority.ROLE_ID <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(String value) {
            addCriterion("sysUserAuthority.ROLE_ID >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.ROLE_ID >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(String value) {
            addCriterion("sysUserAuthority.ROLE_ID <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.ROLE_ID <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLike(String value) {
            addCriterion("sysUserAuthority.ROLE_ID like", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotLike(String value) {
            addCriterion("sysUserAuthority.ROLE_ID not like", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<String> values) {
            addCriterion("sysUserAuthority.ROLE_ID in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<String> values) {
            addCriterion("sysUserAuthority.ROLE_ID not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.ROLE_ID between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.ROLE_ID not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andAuthorityListIsNull() {
            addCriterion("sysUserAuthority.AUTHORITY_LIST is null");
            return (Criteria) this;
        }

        public Criteria andAuthorityListIsNotNull() {
            addCriterion("sysUserAuthority.AUTHORITY_LIST is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorityListEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST =", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST <>", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListGreaterThan(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST >", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST >=", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListLessThan(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST <", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListLessThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST <=", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListLike(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST like", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotLike(String value) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST not like", value, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListIn(List<String> values) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST in", values, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotIn(List<String> values) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST not in", values, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST between", value1, value2, "authorityList");
            return (Criteria) this;
        }

        public Criteria andAuthorityListNotBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.AUTHORITY_LIST not between", value1, value2, "authorityList");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("sysUserAuthority.REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("sysUserAuthority.REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("sysUserAuthority.REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("sysUserAuthority.REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("sysUserAuthority.REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("sysUserAuthority.REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("sysUserAuthority.REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("sysUserAuthority.REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("sysUserAuthority.REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("sysUserAuthority.REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("sysUserAuthority.REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("sysUserAuthority.REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}