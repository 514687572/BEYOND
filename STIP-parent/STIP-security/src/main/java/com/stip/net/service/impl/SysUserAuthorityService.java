package com.stip.net.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stip.net.dao.SysUserAuthorityDao;
import com.stip.net.dao.SysUserRoleDao;
import com.stip.net.entity.SysUserAuthority;
import com.stip.net.entity.SysUserAuthorityExample;
import com.stip.net.entity.SysUserRole;
import com.stip.net.entity.SysUserRoleExample;
import com.stip.net.service.ISysUserAuthorityService;

@Service
public class SysUserAuthorityService implements ISysUserAuthorityService {
	@Resource
	private SysUserAuthorityDao sysUserAuthorityDao;
	@Resource
	private SysUserRoleDao sysUserRoleDao;

	/**
	 * 根据userId查询该用户的角色可操作的权限元素集合
	 * */
	public Map<String,Object> selectElementsByUserIds(String userId) {
		
		//取得userId对应的角色集合 by -- userId
		List<SysUserRole> roles = getRolesByUserId(userId);
		//拆分出roles包含的elements集合 by -- roles
		Set<String> elements = getElementsByRoles(roles);
		Map<String,Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("elements", elements);
		return jsonResult;
	}

	/**
	 * @param roles 角色集合
	 * @return 元素集合
	 */
	private Set<String> getElementsByRoles(List<SysUserRole> roles) {
		if(roles != null ) {
			Set<String> results = new HashSet<String>();
			for(SysUserRole sur : roles) {
				String[] str = sur.getAuthorityList().split(";");
				for (String s : str) {
					results.add(s);
				}
			}
			return results;
		}
		return null;
	}

	/**
	 * @param userId 用户id
	 * @return	角色集合
	 */
	private List<SysUserRole> getRolesByUserId(String userId) {
		SysUserAuthorityExample example = new SysUserAuthorityExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<SysUserAuthority> suas = sysUserAuthorityDao.selectByExample(example);
		if(suas != null && suas.size() > 0) {
			List<String> roleIds = Arrays.asList(suas.get(0).getRoleId().split(";"));
			SysUserRoleExample surExample = new SysUserRoleExample();
			surExample.createCriteria().andRoleIdIn(roleIds);
			List<SysUserRole> roles = sysUserRoleDao.selectByExample(surExample);
			return roles == null && roles.size() == 0 ? null : roles;
		}
		return null;
	}
	
}
