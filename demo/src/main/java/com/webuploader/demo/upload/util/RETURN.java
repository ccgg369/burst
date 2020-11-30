package com.webuploader.demo.upload.util;

import com.google.common.collect.Maps;

import java.util.Map;

public class RETURN {

	public static Map<String, Object> success(String msg) {
		Map<String, Object> value = Maps.newHashMap();
		value.put("rtn", 0);
		value.put("msg", msg);
		return value;
	}

	public static Map<String, Object> success(String msg, Object obj) {
		Map<String, Object> value = Maps.newHashMap();
		value.put("rtn", 0);
		value.put("msg", msg);
		value.put("obj", obj);
		return value;
	}

	public static Map<String, Object> fail(String msg) {
		Map<String, Object> value = Maps.newHashMap();
		value.put("rtn", 1);
		value.put("msg", msg);
		return value;
	}

	public static Map<String, Object> fail(String msg, Object obj) {
		Map<String, Object> value = Maps.newHashMap();
		value.put("rtn", 1);
		value.put("msg", msg);
		value.put("obj", obj);
		return value;
	}
}
