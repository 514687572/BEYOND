package com.stip.net.utils;

/**
 * 对于敏感词中存在正则表达式语法处理
 * 
 * @author SHOUSHEN LUAN
 */
public class RegexScriptUtils {

	public static String script(String keyWord) {
		if (keyWord.indexOf("\\") != -1) {
			keyWord = keyWord.replaceAll("\\\\", "\\\\\\\\");
		}
		if (keyWord.indexOf(".") != -1) {
			keyWord = keyWord.replaceAll("\\.", "\\\\.");
		}
		if (keyWord.indexOf("*") != -1) {
			keyWord = keyWord.replaceAll("\\*", "\\\\*");
		}
		if (keyWord.indexOf("+") != -1) {
			keyWord = keyWord.replaceAll("\\+", "\\\\+");
		}
		if (keyWord.indexOf("[") != -1) {
			keyWord = keyWord.replaceAll("\\[", "\\\\[");
		}
		if (keyWord.indexOf("]") != -1) {
			keyWord = keyWord.replaceAll("\\]", "\\\\]");
		}
		if (keyWord.indexOf("{") != -1) {
			keyWord = keyWord.replaceAll("\\{", "\\\\{");
		}
		if (keyWord.indexOf("}") != -1) {
			keyWord = keyWord.replaceAll("\\}", "\\\\}");
		}
		if (keyWord.indexOf("?") != -1) {
			keyWord = keyWord.replaceAll("\\?", "\\\\?");
		}
		if (keyWord.indexOf("^") != -1) {
			keyWord = keyWord.replaceAll("\\^", "\\\\^");
		}

		if (keyWord.indexOf("|") != -1) {
			keyWord = keyWord.replaceAll("\\|", "\\\\|");
		}
		if (keyWord.indexOf(":") != -1) {
			keyWord = keyWord.replaceAll("\\:", "\\\\:");
		}
		return processAll$Script(keyWord);
	}

	private static String processAll$Script(String keyWord) {
		if (keyWord.indexOf("$") != -1) {
			StringBuffer sBuffer = new StringBuffer(keyWord.length());
			char[] chat = keyWord.toCharArray();
			for (int i = 0; i < chat.length; i++) {
				if (chat[i] == '$') {
					sBuffer.append("\\$");
				} else {
					sBuffer.append(chat[i]);
				}
			}
			return sBuffer.toString();
		} else {
			return keyWord;
		}
	}

}
