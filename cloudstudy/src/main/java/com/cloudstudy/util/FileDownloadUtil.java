package com.cloudstudy.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadUtil {

	public static final int BUFFERSIZE = 1024;

	public static void readFileToResponse(HttpServletResponse response, HttpServletRequest request, byte[] content,
			String fileName) throws Exception {
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(content);
			readFileToResponse(response, request, in, fileName);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 向HttpServletResponse输出从指定路径+文件名读取到的文件流。
	 * 
	 * @param response
	 * @param contenType
	 *            文件类型
	 * @param pathName
	 *            路径+文件名
	 * @param fileName
	 *            相应客户端的filename
	 * @throws Exception
	 * @throws IOException
	 * @throws MagicException
	 * @throws MagicMatchNotFoundException
	 * @throws MagicParseException
	 */
	public static void readFileToResponse(HttpServletResponse response, HttpServletRequest request, InputStream in,
			String fileName) throws Exception {
		int readedSize = -1;
		byte buffer[] = new byte[BUFFERSIZE];
		OutputStream os = null;
		fileName = fileName.replace(" ", "_");
		String mimeType = new MimetypesFileTypeMap().getContentType(fileName);
		try {
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			readedSize = in.read(buffer, 0, BUFFERSIZE);
			response.setContentType(mimeType + ";charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setHeader("Expires", "0");
			os = response.getOutputStream();
			while (readedSize != -1) {
				os.write(buffer, 0, readedSize);
				readedSize = in.read(buffer, 0, BUFFERSIZE);
			}
		} catch (IOException e) {
			if (!"ClientAbortException".equals(e.getClass().getSimpleName())) {// 忽略客户端取消下载造成的异常
				throw new Exception("下载文件异常", e);
			}
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
