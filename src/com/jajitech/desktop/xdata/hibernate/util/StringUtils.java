/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.hibernate.util;

/**
 *
 * @author Jaji
 */
public class StringUtils {
    
    public static final char CR = '\r';
    public static final char LF = '\n';
    
    public String chop(String str) {
      if (str == null) {
          return null;
      }
      int strLen = str.length();
      if (strLen < 2) {
          return "";
      }
      int lastIdx = strLen - 1;
      String ret = str.substring(0, lastIdx);
      char last = str.charAt(lastIdx);
      if (last == LF) {
          if (ret.charAt(lastIdx - 1) == CR) {
              return ret.substring(0, lastIdx - 1);
          }
      }
      return ret;
  }
    
    public String clean(String str)
    {
        StringBuffer sb = new StringBuffer(str);
        String r = sb.insert(0, sb.subSequence(sb.length()-1, sb.length())).delete(sb.length()-1, sb.length()).toString();
        return r;
    }
    
}
