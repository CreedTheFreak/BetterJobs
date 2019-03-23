package com.creedfreak.common.utility;

import java.io.InputStream;

public class ResourceFinder {

	private static String RESOURCE_PREFIX = "Resource Finder";
	/**
	 * Fetch a jar resource as an input stream.
	 * @param resource The resource path to load.
	 * @return The input stream returned from the class path.
	 */
	public static InputStream fetchResource (String resource) {
		InputStream in = ResourceFinder.class.getClassLoader ().getResourceAsStream (resource);
		if (in == null) {
			Logger.Instance ().Error (RESOURCE_PREFIX, "Could not fetch resource from classpath: " + resource);
		}
		return in;
	}

}
