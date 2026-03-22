package com.scientiamobile.wurfl.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class a {
  private int[] a;
  
  private boolean[] b;
  
  private char[][] c;
  
  private int[][] d;
  
  public a(List paramList) {
    ArrayList<b> arrayList = new ArrayList<>();
    b b = new b();
    Iterator<String> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      String str;
      if ((str = iterator.next()) != null && str.length() != 0)
        b.a(str); 
    } 
    LinkedList<b> linkedList = new LinkedList();
    b.a(b);
    arrayList.add(b);
    byte b1;
    for (b1 = 0; b1 < b.c(); b1++) {
      b b2;
      (b2 = b.a(b1)).a(b);
      linkedList.add(b2);
    } 
    while (!linkedList.isEmpty()) {
      b b2 = linkedList.poll();
      arrayList.add(b2);
      for (Character character : b2.d()) {
        b b3 = b2.a(character.charValue());
        linkedList.add(b3);
        b b4;
        for (b4 = b2.e(); b4.a(character.charValue()) == null && b4 != b; b4 = b4.e());
        if ((b4 = b4.a(character.charValue())) != null) {
          b3.a(b4);
          continue;
        } 
        b3.a(b);
      } 
    } 
    this.a = new int[arrayList.size()];
    this.b = new boolean[arrayList.size()];
    this.c = new char[arrayList.size()][];
    this.d = new int[arrayList.size()][];
    for (b1 = 0; b1 < arrayList.size(); b1++) {
      b b2 = arrayList.get(b1);
      this.a[b1] = arrayList.indexOf(b2.e());
      this.b[b1] = b2.b();
      Set set = b2.a().entrySet();
      this.c[b1] = new char[set.size()];
      this.d[b1] = new int[set.size()];
      byte b3 = 0;
      for (Map.Entry entry : set) {
        this.c[b1][b3] = ((Character)entry.getKey()).charValue();
        this.d[b1][b3] = arrayList.indexOf(entry.getValue());
        b3++;
      } 
    } 
  }
  
  public final boolean a(String paramString) {
    char[] arrayOfChar = paramString.toLowerCase().toCharArray();
    a a1 = this;
    int i = 0;
    int j = (arrayOfChar = arrayOfChar).length;
    for (byte b = 0; b < j; b++) {
      char c = arrayOfChar[b];
      label20: while (true) {
        char[] arrayOfChar1 = a1.c[i];
        for (byte b1 = 0; b1 < arrayOfChar1.length; b1++) {
          if (arrayOfChar1[b1] == c) {
            i = a1.d[i][b1];
            if (a1.b[i])
              return true; 
            break label20;
          } 
        } 
        if (i != 0) {
          i = a1.a[i];
          continue;
        } 
        break;
      } 
    } 
    return false;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\a.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
