package com.stip.net.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stip.net.dao.MallUserDao;
import com.stip.net.entity.MallUser;
import com.stip.net.entity.MallUserExample;
import com.stip.net.service.ITestService;
@Service
public class TestService implements ITestService {
	@Resource
	private MallUserDao mallUserDao;
	
	public List<MallUser> select(String s){
		MallUserExample example=new MallUserExample();
		example.createCriteria().andUserNameEqualTo("18727577468");
		example.setPager(1, 10);
		return mallUserDao.selectByExample(example);
	}
	
	public List<MallUser> selectUserByPhone(String phone){
		MallUserExample example=new MallUserExample();
		example.createCriteria().andUserNameEqualTo(phone);
		example.setPager(1, 10);
		return mallUserDao.selectByExample(example);
	}
}
