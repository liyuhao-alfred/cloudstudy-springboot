/******************************************************************
 *    
 *    Package:     com.cloudstudy.file
 *
 *    Filename:    package-info.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月23日 下午3:17:47
 *
 *    - first revision 
 *
 *****************************************************************/
/**
 * 
 * 
 * @ClassName package-info 
 * @author alfred 
 * @Date 2018年1月23日 下午3:17:47 
 * @version 1.0.0  
 */
package com.cloudstudy.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import com.cloudstudy.util.FileUtil;

import java.io.File;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileTest {

	@Value("${web.upload-path}")
	private String path;

	/** 文件上传测试 */
	@Test
	public void uploadTest() throws Exception {
		File oldFile = new File("C:/Users/alfred/Desktop/image1.jpg");
		File newFile = FileUtil.createFile(path + File.separator + "image1.jpg");
		FileCopyUtils.copy(oldFile, newFile);
	}

	@Test
	public void listFilesTest() {
		File file = new File(path);
		for (File f : file.listFiles()) {
			System.out.println("fileName : " + f.getName());
		}
	}
	// 由于前面已经在静态资源路径中上传了一个名为1.jpg的图片，也使用server.port=1122设置了端口号为1122，所以可以通过浏览器打开：http://localhost:1122/1.jpg访问到刚刚上传的图片
}
