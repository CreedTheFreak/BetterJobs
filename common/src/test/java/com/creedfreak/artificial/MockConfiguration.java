package com.creedfreak.artificial;

import com.creedfreak.common.IConfiguration;

import java.util.HashMap;

public class MockConfiguration implements IConfiguration {

	private HashMap<String, Object> mConfigurationMapping;

	public MockConfiguration () {
		mConfigurationMapping = new HashMap<> ();
	}

	/**
	 * Register the new mapping value
	 * @param key The new key to map to the below value
	 * @param value The value to be added.
	 */
	public void registerMapping (String key, Object value) {
		mConfigurationMapping.put (key, value);
	}

	/**
	 * Return the Integer configuration defined by key
	 */
	public Integer getInt (String key) {
		Object obValue = mConfigurationMapping.get (key);
		Integer value = null;
		if (obValue instanceof Integer) {
			value = (Integer) obValue;
		}
		return value;
	}

	/**
	 * Return the Double configuration defined by key
	 */
	public Double getDouble (String key) {
		Object obValue = mConfigurationMapping.get (key);
		Double value = null;
		if (obValue instanceof Double) {
			value = (Double) obValue;
		}
		return value;
	}

	/**
	 * Return the String configuration defined by key
	 */
	public String getString (String key) {
		Object obValue = mConfigurationMapping.get (key);
		String value = null;
		if (obValue instanceof String) {
			value = (String) obValue;
		}
		return value;
	}
}
