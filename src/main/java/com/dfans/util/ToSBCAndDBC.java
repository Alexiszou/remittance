package com.dfans.util;

public class ToSBCAndDBC {
	public static void main(String[] args) {

        String QJstr = "wch";

        String QJstr1 = "ｈｅｌｌｏ";

        String result = ToSBC(QJstr);

        String result1 = ToDBC(QJstr1);

//        System.out.println(QJstr + "\n" + result); 
//
//        System.out.println(QJstr1 + "\n" + result1);

        getLetter("R-007");
}
	/**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
             char c[] = input.toCharArray();
             for (int i = 0; i < c.length; i++) {
               if (c[i] == ' ') {
                 c[i] = '\u3000';
               } else if (c[i] < '\177') {
                 c[i] = (char) (c[i] + 65248);

               }
             }
             return new String(c);
    }

    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        

             char c[] = input.toCharArray();
             for (int i = 0; i < c.length; i++) {
               if (c[i] == '\u3000') {
                 c[i] = ' ';
               } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                 c[i] = (char) (c[i] - 65248);

               }
             }
        String returnString = new String(c);
        
             return returnString;
    }
    
    /**
     * 取出字符串中所有字母
     * @param input String.
     * @return 半角字符串
     */
    //if (ch > 48 && ch < 57)
    public static String getLetter(String a) {
    	StringBuffer sb = new StringBuffer();
    	StringBuffer sb2 = new StringBuffer();
    	for(int i = 0;i<a.length();i++){
    		char c = a.charAt(i);
    			
			if((c<='z'&&c>='a')||(c<='Z'&&c>='A')){
    			sb.append(ToSBC2(c));
    		}else{
    			sb.append(c);
    		}
    	}
    	System.out.println(sb.toString());
    	return sb.toString();
    }
    
    public static char ToSBC2(char c) {
          if (c == ' ') {
            c = '\u3000';
          } else if (c< '\177') {
            c = (char) (c + 65248);
          }
        return c;
    }
}
