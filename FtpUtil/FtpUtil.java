package com.taotao.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP上传下载文件工具类，使用apache.commons.net包
 * @author mbc1996
 *
 */
public class FtpUtil {
	/**
	 * 往Ftp服务器上传文件
	 * 
	 * @param host
	 *            ftp服务器主机
	 * @param port
	 *            ftp服务器端口
	 * @param username
	 *            ftp登录账户
	 * @param password
	 *            ftp登录密码
	 * @param basePath
	 *            ftp服务器基础目录
	 * @param filePath
	 *            ftp服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename
	 *            上传文件在ftp服务器上的命名
	 * @param inputStream
	 *            待上传文件的输入流
	 * @return
	 */
	public static boolean uploadFile(String host, int port, String username, String password, String basePath,
			String filePath, String filename, InputStream inputStream) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port); // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			// 切换到上传目录,没有就创建
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				// 切换失败说明有目录不存在，需要创建
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (dir == null || dir.equals(""))
						continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			// 设置上传文件的类型为二进制型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 上传文件
			if (!ftp.storeFile(filename, inputStream)) {
				return result;
			}
			inputStream.close();
			ftp.logout();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 从FTP服务器下载文件
	 * @param host ftp服务器主机
	 * @param port ftp服务器端口
	 * @param username 登录ftp服务器的用户名
	 * @param password 登录ftp服务器的密码
	 * @param remotePath ftp服务器上文件的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 */
	public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean result = false ;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)){
				ftp.disconnect();
				return result;
			}
			//转移到ftp服务器上文件所在路径
			ftp.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftp.listFiles();
			for(FTPFile file : fs){
				if(file.getName().equals(fileName)){
					File localFile = new File(localPath+"/"+file.getName());
					OutputStream outputStream = new FileOutputStream(localFile);
					ftp.retrieveFile(file.getName(), outputStream);
					outputStream.close();
				}
			}
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(ftp.isConnected()){
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
