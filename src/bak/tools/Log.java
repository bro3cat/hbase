package com.per.hu.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	public static final String line = "--------------------------  ";
	private static final String longLine = "----------------------------------------------------  ";

	public static void date(Object o) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String timeStr = time.format(new Date());
		System.out.println(timeStr + "  ----  " + o.toString());
		// System.out.println();
	}

	public static void noDate(Object o) {
		System.out.println("--------------------------  " + o.toString());
	}

	public static void noDateDouble(Object o1, Object o2) {

		System.out.println("--------------------------  " + o1.toString() + " : " + o2.toString());
	}

	public static final void lineStart(Object o) {
		// System.out.println(date(o));
		// noDate(longLine+longLine);
		noDate(longLine + longLine + longLine + longLine);
		date(o + longLine + line + " START ");
	}

	public static final void lineEnd(Object o) {
		// System.out.println(date(o));
		date(o + longLine + line + "  END  ");
		// noDate(longLine+longLine);
		noDate(longLine + longLine + longLine + longLine);
	}

	//
	// public static void date() {
	// }
	//
	// public static void yes() {
	// }
}
