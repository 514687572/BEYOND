package com.stip.net.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.stip.net.entity.MallUser;

public interface ISysUserAuthorityService {
	public Map<String,Object> selectElementsByUserIds(String userId);

}
