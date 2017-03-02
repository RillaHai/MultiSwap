package MultiSwap;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MultiEnglishNum {
	public final static String NUM = "一二三四五六七八九十点十百千万亿两";

	public static void main(String[] args) {
		String test = "10000008965";
		ArrayList<String> res = formatChinese(test);
		print(res);

	}

	public static String filterNum(String num) {
		ArrayList<String> allNum = new ArrayList<String>();
		String[] numl = num.split("");
		for (String n : numl) {
			if (NUM.contains(n)) {
				allNum.add(n);
			}
		}
		return String.join("", allNum);
	}
	
	public static ArrayList<String> formatChinese(String num) {
		ArrayList<String> enNum = new ArrayList<String>();
		// match 1000
		if (num.matches("\\d+")) {
			//print("test 1");
			enNum.add(num);
			Integer stn2 = Integer.parseInt(num);
			String en = NumberFormat.getInstance().format(stn2);
			enNum.add(en);
		} // match 1000.56
		else if (num.matches("\\d*\\.\\d+")) {
			//print("test 2");
			enNum.add(num);
			double stn1 = Double.parseDouble(num);
			String en = NumberFormat.getInstance().format(stn1);
			enNum.add(en);
		} // match 一点五万/一点三六 尚未加单位
		else if (num.matches("\\D+点\\D+")) {
			//print("test 3");
			num = filterNum(num);
			String en = Zh2Num.chnNum2Decimal(num);
			enNum.add(en);
			double stn1 = Double.parseDouble(en);
			enNum.add(NumberFormat.getInstance().format(stn1));
		} // match 一千 非数字汉字返回0 尚未加单位
		else if (num.matches("\\D+")) {
			//print("test 4");
			num = filterNum(num);
			String en = Zh2Num.chnNum2Digit(num);
			enNum.add(en);
			 long stn2 = Long.parseLong(en);
			enNum.add(NumberFormat.getInstance().format(stn2));
		} else if (num.matches("\\d+\\.*\\d*\\D+")) {
			//print("test 5");
			String en = formatNum(num);
			enNum.add(en);
			double stn1 = Double.parseDouble(en);
			enNum.add(NumberFormat.getInstance().format(stn1));

		}
		return enNum;
	}

	// find out the unit
	/*
	 * 1000万元 -> 1000 万 元， 万will be reserved and turned to 0000 the list become
	 * [1000,0000] ->10000000
	 */
	public static String formatNum(String number) {
		String[] list = number.split("(?<=\\d)(?=\\D)");
		// print(String.join(" ", list));
		ArrayList<String> num = new ArrayList<String>();
		num.add(list[0]); // add number part
		Integer dot = 0;
		String res = null;

		for (int t = 0; t < list.length; t++) {
			// System.out.println(list[t]);
			if (list[t].matches("\\d*\\.\\d+")) {
				num.add(list[t]);
				dot = list[t].substring(0, list[t].length() - 1).length();
				// System.out.println("dot length: "+dot);
			} else if (list[t].matches("\\D+")) {
				String[] unit = list[t].split("");
				for (int u = 0; u < unit.length; u++) {
					// System.out.println(unit[u]);
					if (unit[u].equals("十"))
						num.add("0");
					else if (unit[u].equals("百"))
						num.add("00");
					else if (unit[u].equals("千")) {
						if (dot == 0)
							num.add("000");
						else if (dot != 0) {
							Integer n = 3 - dot;
							String renum = new String(new char[n]).replace("\0", "0");
							num.add(renum);
						}
					} else if (unit[u].equals("万")) {
						if (dot == 0)
							num.add("0000");
						else if (dot != 0) {
							Integer n = 4 - dot;
							String renum = new String(new char[n]).replace("\0", "0");
							// System.out.println("renum: "+renum);
							num.add(renum);
						}
					} else if (unit[u].equals("亿")) {
						if (dot == 0)
							num.add("00000000");
						else if (dot != 0) {
							Integer n = 3 - dot;
							String renum = new String(new char[n]).replace("\0", "0");
							num.add(renum);
						}
					}
				}
			}
		}
		// System.out.println(num.toString());
		if (num.get(num.size() - 1).matches("0+")) {
			// System.out.println("yes");
			String s = String.join("", num);
			res = s.replace(".", "");
		} else {
			res = String.join("", num);
		}
		// System.out.println("res: "+res);
		return res;
	}

	private static void print(Object arg0) {
		System.out.println(arg0);
	}
}
