package com.stip.net.utils;

public class PageUtil {
	private static Integer pageSize = 15; // 每页显示大小,默认显示15条信息
	private static int pageAll = 0; // 总页数
	private static int pageNum = 1; // 页码

	public static Integer getPageSize() {
		return pageSize;
	}

	public static void setPageSize(Integer pageSize) {
		PageUtil.pageSize = pageSize;
	}

	public static int getPageAll() {
		return pageAll;
	}

	public static void setPageAll(int pageAll) {
		PageUtil.pageAll = pageAll;
	}

	public static int getPageNum() {
		return pageNum;
	}

	public static void setPageNum(int pageNum) {
		PageUtil.pageNum = pageNum;
		if (PageUtil.pageNum > PageUtil.pageAll) {
			PageUtil.pageNum = PageUtil.pageAll;
		}
		if (PageUtil.pageNum < 1) {
			PageUtil.pageNum = 1;
		}

	}

}
