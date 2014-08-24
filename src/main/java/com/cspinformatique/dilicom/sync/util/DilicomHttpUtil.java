package com.cspinformatique.dilicom.sync.util;

public abstract class DilicomHttpUtil {
	public static String cleanString(String string){
		if(string != null){
			string = string.replaceAll("\n", "").replaceAll("\r", "").trim();
		}
		
		return string;
	}
}
