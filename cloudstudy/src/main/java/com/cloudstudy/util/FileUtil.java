package com.cloudstudy.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstudy.exception.CloudStudyException;

/**
 * 文件操作类
 * 
 * @author liyuhao
 */
public class FileUtil {
	private static Log log = LogFactory.getLog(FileUtil.class);

	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	/**
	 * 获取文件夹内文件的名称
	 * 
	 * @param floderPath
	 *            文件路径
	 */
	public static List<String> getFileName(String floderPath) {
		File f = new File(floderPath);
		if (!f.exists()) {
			log.info("[" + floderPath + "]不存在");
			return null;
		}

		File fa[] = f.listFiles();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				log.info(fs.getName() + " [目录]");
			} else {
				log.info(fs.getName() + " [文件]");
				list.add(fs.getAbsolutePath());
			}
		}
		return list;
	}

	/**
	 * 读文件到字符串
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFileByStr(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			log.info(filePath + "不存在");
			return null;
		}

		String str = "";
		try {
			FileInputStream in = new FileInputStream(file);
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer, "UTF-8");
		} catch (IOException e) {
			return null;
		}
		return str;
	}

	@SuppressWarnings("resource")
	public static byte[] readFileByByte(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			log.info("文件内容过大");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtensionName(String fileName) {
		if (fileName.indexOf(".") == -1) {
			throw new CloudStudyException("该文件没有扩展名！");
		}

		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = fileName.lastIndexOf('.');
			if ((dot > -1) && (dot < (fileName.length() - 1))) {
				return fileName.substring(dot + 1);
			}
		}
		return fileName;
	}

	/**
	 * 创建多级路径的文件
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new CloudStudyException("创建文件失败！", e);
			}
		}
		return file;
	}

	/**
	 * 删除文件
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static void deleteFile(String filename) throws IOException {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 文件打散-按照日期格式
	 * 
	 * @param basePath
	 * @return
	 */
	public static String createChildDirByData(String basePath) {
		String childPath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		File childDir = new File(basePath, childPath);
		if (!childDir.exists())
			childDir.mkdirs();
		return childDir.getPath();
	}

	/**
	 * 生成文件名-按照上传时间
	 * 
	 * @return
	 */
	public static String createFileNameByTime() {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
		return fileName;
	}

	/**
	 * 复制文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile) {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));// 新建文件输入流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));// 新建文件输出流并对它进行缓冲
			byte[] b = new byte[1024 * 1024 * 5];// 缓冲数组
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();// 刷新此缓冲的输出流
		} catch (Exception e) {
			throw new CloudStudyException("复制文件失败！", e);
		} finally {
			if (inBuff != null)
				try {
					inBuff.close();
				} catch (IOException e) {
					throw new CloudStudyException("复制文件失败！", e);
				}
			if (outBuff != null)
				try {
					outBuff.close();
				} catch (IOException e) {
					throw new CloudStudyException("复制文件失败！", e);
				}
		}
	}

	/***
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/***
	 * 删除文件夹
	 * 
	 * @param folderPath文件夹完整绝对路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
