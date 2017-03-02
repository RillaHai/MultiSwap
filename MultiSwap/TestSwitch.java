package MultiSwap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestSwitch {
	static String testFile = "/home/bear/workspace1/differweb/src/main/java/MultiSwap/testSwitch.txt";
	static String testRes = "/home/bear/workspace1/differweb/src/main/java/MultiSwap/testRes.txt";
	static ArrayList<String> ref = new ArrayList<String>();
	static ArrayList<String> hyp = new ArrayList<String>();
	static ArrayList<String> transref = new ArrayList<String>();
	static ArrayList<String> transhyp = new ArrayList<String>();

	public TestSwitch() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException {
		// read test file
		try {
			@SuppressWarnings("resource")
			Scanner test = new Scanner(new File(testFile));
			while(test.hasNext()){
				String[] s = test.nextLine().trim().split("\\t");
				ref.add(s[0]);
				hyp.add(s[1]);
				transref.add(s[2]);
				transhyp.add(s[3]);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//test zh-en
		testZhEn();
		//test en-zh
		testEnZh();

	}
	
	private static void testEnZh() throws FileNotFoundException {
		//PrintWriter writer = new PrintWriter(testRes);
		for (int i=0;i<ref.size();i++) {
			
			String refi = UtilLang.tokenizeLang(transref.get(i), "en"); // tokenized reference
			String hypi = UtilLang.tokenizeLang(transhyp.get(i), "en"); // tokenized reference

			System.out.println("REF:" + refi);
			System.out.println("HYP:" + hypi);
			System.out.println("transref:" + ref.get(i));

			// check numerical value between ref and transref
			System.out.println("VALUE CHECK:	" + Check.translationValue(refi, ref.get(i), "en", "zh-cn"));
			System.out.println("SOURCE CHECK:	" + Check.numCheck(refi, hypi,"en"));

			// apply numCheck
			if (Check.numCheck(refi, hypi,"en") && Check.translationValue(refi, ref.get(i), "en", "zh-cn")) {
				String tranres = SwitchMulti.numSwitch(refi, hypi, ref.get(i), "en","zh-ch");
				System.out.println("transhyp: " + tranres);
			} else {
				System.out.println("Can not be auto translated!");
			}

		}

	}

	private static void testZhEn () throws FileNotFoundException {
		for (int i=0;i<ref.size();i++) {
			System.out.println(i);
			String refi = UtilLang.tokenizeLang(ref.get(i), "zh-cn"); // tokenized reference
			String hypi = UtilLang.tokenizeLang(hyp.get(i), "zh-cn"); // tokenized reference

			System.out.println("REF:" + refi);
			System.out.println("HYP:" + hypi);
			System.out.println("transref:" + transref.get(i));

			// check numerical value between ref and transref
			System.out.println("VALUE CHECK:	" + Check.translationValue(refi, transref.get(i), "zh-cn", "en"));
			System.out.println("SOURCE CHECK:	" + Check.numCheck(refi, hypi,"zh-ch"));

			// apply numCheck
			if (Check.numCheck(refi, hypi,"zh-ch") && Check.translationValue(refi, transref.get(i), "zh-cn", "en")) {
				String tranres = SwitchMulti.numSwitch(refi, hypi, transref.get(i), "zh-cn","en");
				System.out.println("transhyp: " + tranres);
			} else {
				System.out.println("Can not be auto translated!");
			}

		}
	}

}
