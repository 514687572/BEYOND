package com.stip.net.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 敏感词检测类 敏感词检测初始化规则： 将敏感词从词库载入，按照2字、3字、4字、5字等字数各生成一个敏感词哈希表。
 * 在将这些哈希表组成一个数组banWordsList，数组下标表示该敏感词表字数 banWordsList[2] =
 * {某马:true,屏蔽:true,啦啦:true}; banWordsList[3] =
 * {某个马:true,三个字:true,啦啦啦:true,小广告:true}; banWordsList[4] =
 * {某个坏银:true,四个字符:true,哈哈哈哈:true,就爱凤姐:true}; banWordsList[5] =
 * {某个大法好:true,五个敏感字:true}; 根据上面几组组敏感词，自动生成以下索引 生成规则为，索引名是敏感词第一个字，值是一个int
 * 该int的规则为，该int转换成二进制时，第i位为1表示上面4表存在长度为i的敏感词，否则不存在长度为i的敏感词(10000) wordIndex =
 * {二:0x04,三:0x08,四:0x10,五:0x20,某:0x3c,啦:0x0c,哈:0x10,小:0x08,就:0x10};
 * 
 * 检查规则如下: 1，逐字检验，是否该字在wordIndex索引表中。 2，如果不在表中，继续检验
 * 3，如果在表中，根据索引表该键的值，取此字以及此字后的若干字检验详细表banWordsList[索引词长]。
 * 
 * 检验例子 有一段如下文字，检验其是否包含敏感词： “我就打小广告，气死版主” ——检测“我” |-不在索引表 ——检测“就” |-在索引表
 * |-“就”的索引值是0x10，表示有4字以“就”开头的敏感词 |-取“就”和后面的字共4个，组成“就打小广” |-查4字敏感词表，没有这项，继续
 * ——检测“打” |-不在索引表 ——检测“小” |-在索引表 |-索引值是0x08，表示有3字长度的敏感词
 * |-取“小”和“小”后面的字，共3个字组成一个词“小广告” |-“小广告”在3字敏感词中，此帖包含敏感词，禁止发布
 */
public class SensitiveWordCheckUtil {
	// public Logger logger = Logger.getLogger(this.getClass());
	public static final int WORDS_MAX_LENGTH = 23;

	// 敏感词列表
	public static Map[] banWordsList = null;

	// 敏感词索引
	public static Map<String, Integer> wordIndex = new HashMap<String, Integer>();

	/*
	 * 初始化敏感词库
	 */
	public static void initBanWordsList(String url) throws IOException {
		String[] senWordFilePathArray = new String[] { url + ConstantUtils.SENSITIVE_PATH1, url + ConstantUtils.SENSITIVE_PATH2, url + ConstantUtils.SENSITIVE_PATH3, url + ConstantUtils.SENSITIVE_PATH4 };

		if (banWordsList == null) {
			banWordsList = new Map[WORDS_MAX_LENGTH];

			for (int i = 0; i < banWordsList.length; i++) {
				banWordsList[i] = new HashMap<String, String>();
			}
		}

		for (int i = 0; i < senWordFilePathArray.length; i++) {
			List<String> words = FileUtils.readLines(FileUtils.getFile(senWordFilePathArray[i]));

			for (String w : words) {
				if (StringUtils.isNotBlank(w)) {
					// 将敏感词按长度存入map
					banWordsList[w.length()].put(w.toLowerCase(), "");

					Integer index = wordIndex.get(w.substring(0, 1));

					// 生成敏感词索引，存入map
					if (index == null) {
						index = 0;
					}

					int x = (int) Math.pow(2, w.length());
					index = (index | x);
					wordIndex.put(w.substring(0, 1), index);
				}
			}
		}
	}

	/**
	 * 检索敏感词
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> searchBanWords(String content, String url) {
		if (banWordsList == null) {
			try {
				initBanWordsList(url);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		List<String> result = new ArrayList<String>();

		for (int i = 0; i < content.length(); i++) {
			Integer index = wordIndex.get(content.substring(i, i + 1));
			int p = 0;

			while ((index != null) && (index > 0)) {
				p++;
				index = index >> 1;

				String sub = "";

				if ((i + p) < (content.length() - 1)) {
					sub = content.substring(i, i + p);
				} else {
					sub = content.substring(i);
				}

				if (((index % 2) == 1) && banWordsList[p].containsKey(sub)) {
					result.add(content.substring(i, i + p));
				}
			}
		}

		return result;
	}

}