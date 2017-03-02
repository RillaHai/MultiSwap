package MultiSwap;

import java.util.ArrayList;

public class MultiChineseNum {

	public static void main(String[] args) {
		String test = "1160000";
		ArrayList<String> tl = formatEnglish(test);
		System.out.println(tl.toString());

	}

	public static String secondFormat(String num) {
		String form = null;
		int strLen = num.length();
		if (strLen > 4 && num.substring(num.length() - 4).equals("0000")) {
			form = num.replace("0000", "万");
		} else if (strLen > 3 && num.substring(num.length() - 3).equals("000")) {
			form = num.replace("000", "千");
		} else if (strLen > 2 && num.substring(num.length() - 2).equals("00")) {
			form = num.replace("00", "百");
		}
		return form;
	}

	public static String toChinese(String num) {
		String cform = null;
		if (num.matches("\\d+\\.\\d+")) {
			String cnum = Num2Zh.formatDecimal(num);
			cform = cnum;
		} else {
			String cnum = Num2Zh.tryInteger(num);
			cform = cnum;
		}
		return cform;
	}

	public static ArrayList<String> formatEnglish(String ennum) {

		ArrayList<String> multiforms = new ArrayList<String>();
		Integer strLen = -1;
		String num = ennum.replaceAll(",", "");
		multiforms.add(num);
		String cnum = toChinese(num);
		multiforms.add(cnum);
		strLen = num.length();
		// 亿级数字
		if (strLen > 8 && num.substring(num.length() - 8).equals("00000000")) {
			String str = num.substring(0, num.length() - 8);
			str = str + "亿";
			multiforms.add(str);
			// 百亿，十亿
			String[] strl = str.split("(?<=\\d)(?=\\D)");
			String second = secondFormat(strl[0]);
			if (second != null)
				multiforms.add(second + "亿");
		}
		// 几点几亿
		else if (strLen > 8 && num.substring(num.length() - 8).equals("0000000")) {
			String replaced = num.replace("0000000", "亿");
			String newstr = new StringBuilder(replaced).insert(replaced.length() - 1, ".").toString();
			multiforms.add(newstr);
		} else if (strLen > 8 && num.substring(num.length() - 8).equals("000000")) {
			String replaced = num.replace("0000000", "亿");
			String newstr = new StringBuilder(replaced).insert(replaced.length() - 2, ".").toString();
			multiforms.add(newstr);
		}
		// 万级数字
		else if (strLen > 4 && num.substring(num.length() - 4).equals("0000")) {
			String str = num.substring(0, num.length() - 4);
			str = str + "万";
			multiforms.add(str);
			// 千万百万
			String[] strl = str.split("(?<=\\d)(?=\\D)");
			//System.out.println(strl.length);
			String second = secondFormat(strl[0]);
			if (second != null)
				multiforms.add(second + "万");
		}
		// 几点几万
		else if (strLen > 8 && num.substring(num.length() - 8).equals("000")) {
			String replaced = num.replace("000", "万");
			String newstr = new StringBuilder(replaced).insert(replaced.length() - 1, ".").toString();
			multiforms.add(newstr);
		}
		// 千级数字
		else if (strLen > 3 && num.substring(num.length() - 3).equals("000")) {
			String str = num.substring(0, num.length() - 3);
			str = str + "千";
			multiforms.add(str);
		}
		// 百级数字
		else if (strLen > 2 && num.substring(num.length() - 2).equals("00")) {
			String str = num.substring(0, num.length() - 2);
			str = str + "百";
			multiforms.add(str);
		}

		return multiforms;
	}

}
