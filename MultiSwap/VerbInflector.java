package MultiSwap;

import java.util.HashMap;

public class VerbInflector {
	final static HashMap<String, String> v = new HashMap<String, String>();  
    static {
	v.put("go","goes");  
    v.put("have","has");  
    v.put("watch","watches");  
    v.put("study","studies");  
    v.put("are","is");  
    v.put("were","was");  
    }

}
