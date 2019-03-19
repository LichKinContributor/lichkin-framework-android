package com.lichkin.framework.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.print.DocFlavor;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 打印机工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKPrinterUtils {

	/** 打印内容输出路径 */
	private static final String FILE_PATH = "/opt/files/prints/";

	static {
		// 使用前先创建必要的路径
		File dir = new File(FILE_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}


	/**
	 * 打印内容
	 * @param content 内容
	 * @return 打印成功返回true，发生异常时返回false。
	 */
	public static boolean print(String content) {
		File file = new File(FILE_PATH + LKDateTimeUtils.now() + "_" + LKRandomUtils.create(3));
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(content.getBytes("GBK"));
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				fos = null;
			}

			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				bos = null;
			}
		}

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			PrintServiceLookup.lookupDefaultPrintService().createPrintJob().print(new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null), null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					fis = null;
				}
			}
		}
		return true;
	}

}
