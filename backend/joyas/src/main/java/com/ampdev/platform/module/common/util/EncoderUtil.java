package com.ampdev.platform.module.common.util;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;

public class EncoderUtil {
	
	private static Encoder secureEncoder = ESAPI.encoder();
	
	public static String HTMLEncode(String toEncode)
	{
		String result = toEncode;
		if (!Util.isEmpty(toEncode))
			result = secureEncoder.encodeForHTML(toEncode);
		
		return result;
	}

}
