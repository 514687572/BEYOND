package com.stip.net.service;

import java.util.List;

import com.stip.net.entity.MallUser;

public interface ITestService {
	public List<MallUser> select(String s);
	public List<MallUser> selectUserByPhone(String phone);

}
