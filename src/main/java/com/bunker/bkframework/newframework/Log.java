package com.bunker.bkframework.newframework;

public interface Log {
	public void log(String tag, String text);
	public void warning(String tag, String text);
	public long err(String tag, String text);
	public long err(String tag, Exception e);
}