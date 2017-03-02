package MultiSwap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 阿拉伯数字转中文数字
 * 适用于千万亿内任何整数及小数点后N位
 */
public class Num2Zh {
	static String[] units = { "千", "百", "十", ""};
	static String[] numArray = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String num = "1";
		String numStr = tryInteger(num);
		print("num= " + num + ", convert result: " + numStr);
		String decimal = "1652890004.345432";
		print("============================================================");
		String decStr = formatDecimal(decimal);
		print("decimal= " + decimal + ", decStr: " + decStr);
	}

	private static String part(String num){
		StringBuilder sb = new StringBuilder();
		List<String> numl = Arrays.asList(num.split(""));
		//System.out.println(numl);
		int l = numl.size();
		if (l == 4) { 
			for (int i = 0;i<l;i++) {
				int n4 = Integer.parseInt(numl.get(i));
				String w = numArray[n4];
				if (w.equals("零")) 
					sb.append(w);
				else
					sb.append(w).append(units[i]);
			}
		} else if (l == 3) {
			for (int i = 0;i<l;i++) {
				int n4 = Integer.parseInt(numl.get(i));
				String w = numArray[n4];
				if (w.equals("零")) 
					sb.append(w);
				else
					sb.append(w).append(units[i+1]);
			}
		} else if (l == 2) {
			for (int i = 0;i<l;i++) {
				int n4 = Integer.parseInt(numl.get(i));
				String w = numArray[n4];
				if (w.equals("零")) 
					sb.append(w);
				else
					sb.append(w).append(units[i+2]);
			}
		} else if (l == 1) {
			for (int i = 0;i<l;i++) {
				int n4 = Integer.parseInt(numl.get(i));
				String w = numArray[n4];
				if (w.equals("零")) 
					sb.append(w);
				else
					sb.append(w).append(units[i+3]);
			}
		}else
			System.out.println("The number is too big!");
		String f = sb.toString();
		f = f.replaceAll("零+", "零");
		if (f.substring(f.length()-1).equals("零"))
			f = f.substring(0,f.length()-1);
		return f;
	}
	
	//split by four each time
	public static String tryInteger(String num) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> fl = new ArrayList<String>();
		String res = null;
		List<String> numl = Arrays.asList(num.split(""));
		Integer len = numl.size();
		if (len>4){
			for (int i=len; i>0; i-=4) {
				if (i>3) {
					list.add(num.substring(i-4, i));
				}
				else {
					list.add(num.substring(0, i));
				}
			}
		}else {
			list.add(num);
		}
		
		//write final result
		Integer lenl = list.size();
		if (lenl==4) {
			for (String i:list) {
				fl.add(part(i));
			}
			res = fl.get(3)+"兆"+fl.get(2)+"亿"+fl.get(1)+"万"+fl.get(0);
		}
		if (lenl==3) {
			for (String i:list) {
				fl.add(part(i));
			}
			res = fl.get(2)+"亿"+fl.get(1)+"万"+fl.get(0);
		}
		else if (lenl==2) {
			for (String i:list) {
				fl.add(part(i));
			}
			res = fl.get(1)+"万"+fl.get(0);
		}
		else if (lenl==1) {
			res = part(list.get(0));
		}
		
		if (res.equals("一十")) {
			res = res.replace("一十", "十");
		}		
		res = res.replace("兆亿", "兆").replace("兆万", "兆").replace("亿万", "亿");
		return res;
	}

	public static String formatDecimal(String decimal) {
		decimal = decimal.replace(",","");
		String decimals = String.valueOf(decimal);
		int decIndex = decimals.indexOf(".");
		String integ = Integer.valueOf(decimals.substring(0, decIndex)).toString();
		int dec = Integer.valueOf(decimals.substring(decIndex + 1));
		String result = tryInteger(integ) + "点" + formatFractionalPart(dec);
		return result;
	}


	private static String formatFractionalPart(int decimal) {
		char[] val = String.valueOf(decimal).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int n = Integer.valueOf(val[i] + "");
			sb.append(numArray[n]);
		}
		return sb.toString();
	}


	private static void print(Object arg0) {
		System.out.println(arg0);
	}
}
