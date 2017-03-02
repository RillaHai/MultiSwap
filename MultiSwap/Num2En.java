package MultiSwap;

import java.util.HashMap;

public class Num2En {
	public static final String ZERO = "zero";  
    public static final String NEGATIVE = "negative";  
    public static final String SPACE = " ";  
    public static final String MILLION = "million";  
    public static final String BILLION = "billion";
    public static final String TRILLION = "trillion";
    public static final String THOUSAND = "thousand";  
    public static final String HUNDRED = "hundred";  
    public static final String AND = "and";
    public static final String[] INDNUM = {"zero", "one", "two", "three", "four", "five", "six",  
      "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen",  
      "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};  
    public static final String[] DECNUM = {"twenty", "thirty", "forty", "fifty", "sixty",  
      "seventy", "eighty", "ninety"};  
    final static HashMap<String, String> n = new HashMap<String, String>();  
    static {
	n.put("0","zero");  
    n.put("1","one");  
    n.put("2","two");  
    n.put("3","three");  
    n.put("4","four");  
    n.put("5","five");  
    n.put("6","six");  
    n.put("7","seven");  
    n.put("8","eight");  
    n.put("9","nine"); }
    
	public Num2En() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(parse("135679.89"));
	}
	public static String parse (String str) {
		String res = null;
		if (str.contains(".")) {
			long in = Long.parseLong(str.split("\\.")[0]);
			String p1 = format(in);
			String deci = String.valueOf(str.split("\\.")[1]);
			String p2 = withPoint(deci);
			res = p1+" point"+p2;
		}else {
			int in = Integer.parseInt(str);
			String p1 = format(in);
			res = p1;
		}
		return res;
	}
	public static String withPoint(String str) {
		String d = "" ;
        String[] k1 = str.trim().split("");
  
        for(String string1 :k1) {
        	d += " "+String.valueOf(n.get(string1));
        }
        return d;  
	}
	
	public static String format(long in) {  
		  
        StringBuilder sb = new StringBuilder();  
  
        if(in == 0) {  
          return ZERO;  
        }  
  
        if(in < 0) {  
          sb.append(NEGATIVE).append(SPACE);  
          in *= -1;  
        }  
        
        if(in >= 1000000000000l) {  
            sb.append(numFormat((int) (in / 1000000000000l))).append(SPACE).append(TRILLION).append(SPACE);  
            in %= 1000000000000l;  
    
          }  
    
        if(in >= 1000000000) {  
            sb.append(numFormat((int) (in / 1000000000))).append(SPACE).append(BILLION).append(SPACE);  
            in %= 1000000000;  
    
          }  
    
  
        if(in >= 1000000) {  
          sb.append(numFormat((int) (in / 1000000))).append(SPACE).append(MILLION).append(SPACE);  
          in %= 1000000;  
  
        }  
  
        if(in >= 1000) {  
          sb.append(numFormat((int) (in / 1000))).append(SPACE).append(THOUSAND).append(SPACE);  
  
          in %= 1000;  
        }  
  
        if(in < 1000){  
          sb.append(numFormat((int) in));  
        }  
        String res = sb.toString();
        String r = res.replaceFirst("^and ", "");
        return r;  
      }  
  
      // 3位数转英文  
      public static String numFormat(int i) {  
  
        StringBuilder sb = new StringBuilder();  
  
        if(i >= 100) {  
          sb.append(INDNUM[i / 100]).append(SPACE).append(HUNDRED);  
        }  
  
        i %= 100;  
  
        if(i != 0) {  
          if(i >= 20) {  
            sb.append(SPACE).append(AND).append(SPACE).append(DECNUM[i / 10 -2]).append(SPACE);  
            if(i % 10 != 0) {  
              sb.append(INDNUM[i % 10]);  
            }  
          }else {  
            sb.append(INDNUM[i]);  
          }  
        } 
        return sb.toString();  
      }  

}
