package com.github.java.common.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ConvertUtils {
	
	public static InputStream str2InputStream(String str) {
		if(str == null)
			return null;
		return new ByteArrayInputStream(str.getBytes());
	}
}
