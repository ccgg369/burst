package com.webuploader.demo.upload.util;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileUtil {

	public static final String Chunk_Delimiter = "-";

	public static List<File> readChunks(File chunkDir) {
		// 读取分片文件
		File[] chunks = null;
		if (chunkDir.exists()) {
			chunks = chunkDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) {
						return false;
					}
					return true;
				}
			});
		}
		// 分片文件排序
		List<File> chunkList = null;
		if (chunks != null && chunks.length > 0) {
			chunkList = Arrays.asList(chunks);
			Collections.sort(chunkList, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
		}
		return chunkList;
	}

	public static void randomAccessFile(File in, File out, Long seek) throws IOException {
		RandomAccessFile raFile = null;
		BufferedInputStream inputStream = null;
		try {
			// 以读写的方式打开目标文件
			raFile = new RandomAccessFile(out, "rw");
			raFile.seek(seek);
			inputStream = new BufferedInputStream(new FileInputStream(in));
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				raFile.write(buf, 0, length);
			}
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (raFile != null) {
					raFile.close();
				}
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 *            test.zip
	 * @return .zip
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
}
