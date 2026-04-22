package com.experiment.simple.domain;

public class BadAddress {
  public String l1;
  public String l2;
  public String c;
  public String st;
  public String z;
  public String i3;

  public void Address(String l1, String l2, String c, String st, String z, String i3) {
    this.l1 = l1;
    this.l2 = l2;
    this.c = c;
    this.st = st;
    this.z = z;
  }

  public int nums() {
    int totalcount = 0;
    int l1total = 0;
    int l2total = 0;
    int ccount = 0;
    int stcount = 0;
    int zttotal = 0;

    for (int i = 0; i < l1.length(); i++) {
      l1total += 1;
    }
    for (int i = 0; i < l2.length(); i++) {
      l2total+= 1;
    }
    for (int i = 0; i < c.length(); i++) {
      ccount+= 1;
    }
    for (int i = 0; i < st.length(); i++) {
      ccount+= 1;
    }
    for (int i = 0; i < z.length(); i++) {
      zttotal += 1;
    }
    return l1total + l2total + ccount + stcount +zttotal;
  }

  public String isVld() {
    if (l1 == null || l1.trim() == "" || l2 == null || l2.trim() == "" || c.trim() == "" || c == null || st == null || st.trim() == "" || z.trim() == "" || z == null) {
      return null;
    }
    return "OK";
  }
}