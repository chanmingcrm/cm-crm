package com.platform.mesh.log.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @description ResponseWrapper
 * @author 蝉鸣
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream buffer;

	private ServletOutputStream out;

	private PrintWriter writer;

	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		buffer = new ByteArrayOutputStream();
		out = new WapperedOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, StandardCharsets.UTF_8));
	}

	/**
	 * 功能描述:
	 * 〈重载父类获取output stream的方法〉
	 * @return 正常返回:{@link ServletOutputStream}
	 * @author 蝉鸣
	 */
	@Override
	public ServletOutputStream getOutputStream() {
		return out;
	}

	@Override
	public PrintWriter getWriter() {
		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		}
		if (writer != null) {
			writer.flush();
		}
	}

	@Override
	public void reset() {
		buffer.reset();
	}

	public byte[] getResponseData() throws IOException {
		flushBuffer();// 将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
		return buffer.toByteArray();
	}

	/**
	 * 功能描述:
	 * 〈内部类，对ServletOutputStream进行包装，指定输出流的输出端〉
	 * @author 蝉鸣
	 */
	private class WapperedOutputStream extends ServletOutputStream {

		private ByteArrayOutputStream bos;

		public WapperedOutputStream(ByteArrayOutputStream stream) {
			bos = stream;
		}

		// 将指定字节写入输出流bos
		@Override
		public void write(int b) {
			bos.write(b);
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setWriteListener(WriteListener listener) {
			// TODO Auto-generated method stub

		}

	}

}
