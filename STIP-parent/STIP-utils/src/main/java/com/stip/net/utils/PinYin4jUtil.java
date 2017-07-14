package com.stip.net.utils;

import java.io.UnsupportedEncodingException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具
 */
public class PinYin4jUtil {
	/**
	 * 获取汉字串拼音首字母，英文字符不变
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音首字母
	 */
	public static String cn2FirstSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (_t != null) {
						pybf.append(_t[0].charAt(0));
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 获取汉字串拼音，英文字符不变
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音
	 */
	public static String cn2Spell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}

	static class Hanyu {
		private HanyuPinyinOutputFormat format = null;
		private String[] pinyin;

		public Hanyu() {
			format = new HanyuPinyinOutputFormat();
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			pinyin = null;
		}

		// 转换单个字符
		public String getCharacterPinYin(char c) {
			try {
				pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			// 如果c不是汉字，toHanyuPinyinStringArray会返回null
			if (pinyin == null) return null;
			// 只取一个发音，如果是多音字，仅取第一个发音
			return pinyin[0];
		}

		// 转换一个字符串
		public String getStringPinYin(String str) {
			StringBuilder sb = new StringBuilder();
			String tempPinyin = null;
			for (int i = 0; i < str.length(); ++i) {
				tempPinyin = getCharacterPinYin(str.charAt(i));
				if (tempPinyin == null) {
					// 如果str.charAt(i)非汉字，则保持原样
					sb.append(str.charAt(i));
				} else {
					sb.append(tempPinyin);
				}
			}
			return sb.toString();
		}
	}

	public String fayin(String str) {
		StringBuilder sb = new StringBuilder();
		Hanyu hy = new Hanyu();
		String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(str.charAt(0));
		String s = hy.getCharacterPinYin(str.charAt(0));
		if (s == null) {
			sb.append(str.charAt(0));
			System.out.println(sb);
		} else {
			for (int i = 0; i < pinyinArray.length; ++i) {
				System.out.println(pinyinArray[i]);
				sb.append(s);
			}
		}
		return sb.toString();
	}

	// 测试main函数
	public static void main(String[] args) throws UnsupportedEncodingException {
		String x = "中国你好";
		System.out.println(cn2FirstSpell(x));
		System.out.println(cn2Spell(x));

		Hanyu hanyu = new Hanyu();
		// 中英文混合的一段文字
		String str = "荆溪白石出，Hello 天寒红叶稀。Android 山路元无雨，What's up? 空翠湿人衣。";
		String strPinyin = hanyu.getStringPinYin(str);
		System.out.println(strPinyin);

		PinYin4jUtil py = new PinYin4jUtil();
		py.fayin(x);
	}
}
