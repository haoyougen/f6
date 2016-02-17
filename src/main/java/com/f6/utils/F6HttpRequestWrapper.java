package com.f6.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class F6HttpRequestWrapper extends HttpServletRequestWrapper {
	// private HttpServletRequest request;
	private byte[] body;
	private Logger logger = LoggerFactory.getLogger(F6HttpRequestWrapper.class);

	public F6HttpRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		ServletInputStream is = request.getInputStream();
		String content = IOUtils.toString(is, SystemConstans.ENCODING_UTF8);
		body = content.toString().getBytes(SystemConstans.ENCODING_UTF8);

	}
 
	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener arg0) {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
