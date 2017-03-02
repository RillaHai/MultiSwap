package MultiSwap;

import java.util.HashMap;
/*
 * 英文单词转数词，string必须删掉and再替换
 */
public class En2Num {
	final static HashMap<String, Long> hm = new HashMap<String, Long>();  
    static {
	hm.put("zero", (long) 0);  
    hm.put("one", (long) 1);  
    hm.put("two", (long) 2);  
    hm.put("three", (long) 3);  
    hm.put("four", (long) 4);  
    hm.put("five", (long) 5);  
    hm.put("six", (long) 6);  
    hm.put("seven", (long) 7);  
    hm.put("eight", (long) 8);  
    hm.put("nine", (long) 9);  
    hm.put("ten", (long) 10);  
    hm.put("eleven", (long) 11);  
    hm.put("twelve", (long) 12);  
    hm.put("thirteen", (long) 13);  
    hm.put("fourteen", (long) 14);  
    hm.put("fifteen", (long) 15);  
    hm.put("sixteen", (long) 16);  
    hm.put("seventeen", (long) 17);  
    hm.put("eighteen", (long) 18);  
    hm.put("nineteen", (long) 19);  
    hm.put("twenty", (long) 20);  
    hm.put("thirty", (long) 30);  
    hm.put("forty", (long) 40);  
    hm.put("fifty", (long) 50);  
    hm.put("sixty", (long) 60);  
    hm.put("seventy", (long) 70);  
    hm.put("eighty", (long) 80);  
    hm.put("ninety", (long) 90);  
    hm.put("hundred", (long) 100);  
    hm.put("thousand", (long) 1000);  
    hm.put("million", (long) 1000000);
    hm.put("billion", (long) 1000000000);
    hm.put("trillion", (long)1000000000000L);}
   
	public En2Num() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(parse("one hundred thirty five thousand six hundred and seventy nine point eight nine"));
	}
	
	public static String parse(String str) {  
        String res = null;
        if (str.length()==0){
        	return "";
        }
        str = str.trim().replaceAll("\\band ", "");
		if (str.contains("point")) {
        	String integerPart = str.split("point")[0];
            String decimal = str.split("point")[1];
            String in = parseInt(integerPart);
            String de = parseDec(decimal);
            res = in+"."+de;
        }else {
        	String in = parseInt(str);
        	res = in;
        }
        return res;  
      }  
	
	public static String parseInt(String str) {
		long i = 0;  
        long t = 0;  
        long m = 0; 
        long b = 0;
        long l = 0;
       
        String res = null;
        
        String[] k = str.trim().split(" ");
        for (String string : k) {  
          if("hundred".equals(string)){  
            i *= hm.get("hundred");  
          }else if("thousand".equals(string)){  
            t = i;  
            t *= hm.get("thousand");  
            i = 0;  
          }else if("million".equals(string)){  
            m = i;  
            m *= hm.get("million");  
            i = 0;  
          }else if("negative".equals(string)){  
            i = 0;  
          }else if("billion".equals(string)){
        	  b = i;  
              b *= hm.get("billion");  
              i = 0;
          }else if("trillion".equals(string)){
        	  b = i;  
              b *= hm.get("trillion");  
              i = 0;
          }
          else { 
        	//System.out.println("string part: "+string);
            i += hm.get(string);  
          }  
        }  
        i += t + b + m + l;  
        
        for (String string2 : k) {  
          if("negative".equals(string2)){  
            i = -i;  
          }  
        }
        res = String.valueOf(i);
        return res;  
	}
	
	public static String parseDec(String str) {
        String d = "" ;
        String[] k1 = str.trim().split(" ");
  
        for(String string1 :k1) {
        	d += String.valueOf(hm.get(string1));
        }
        return d;  
	}
	
}
