package com.lichkin.framework.utils;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.lichkin.framework.utils.security.base64.LKBase64Decoder;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

/**
 * 图片处理工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKImageUtils {

	/**
	 * Base64转图片文件
	 * @param base64 Base64字符串
	 * @param imgFilePath 文件全名（相对地址）
	 */
	public static void base64ToImage(String base64, String imgFilePath) {
		if (StringUtils.isBlank(base64)) {
			return;
		}
		byteToImage(LKBase64Decoder.decode(base64), imgFilePath);
	}


	/**
	 * 字节码转图片文件
	 * @param bytes 字节码
	 * @param imgFilePath 文件全名（相对地址）
	 */
	public static void byteToImage(byte[] bytes, String imgFilePath) {
		try {
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					bytes[i] += 256;
				}
			}
			@Cleanup
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 图片等比例缩放
	 * @param sourcefilePath 源图片文件全名（相对地址）
	 * @param targetfilePath 目标图片文件全名（相对地址）
	 * @param width 缩放到的宽度
	 * @param height 缩放到的高度
	 */
	public static void zoomImage(String sourcefilePath, String targetfilePath, int width, int height) {
		double w = 0, h = 0;
		File sourceFile = new File(sourcefilePath);
		File targetFile = new File(targetfilePath);
		if (!sourceFile.exists() || targetFile.exists()) {
			return;
		}
		try {
			BufferedImage srcImage = ImageIO.read(sourceFile);
			int imageWidth = srcImage.getWidth(null);
			int imageHeight = srcImage.getHeight(null);
			// 按比例得到合适的压缩大小
			if (imageWidth >= imageHeight) {
				height = (int) Math.round(((imageHeight * width * 1.0) / imageWidth));
			} else {
				width = (int) Math.round(((imageWidth * height * 1.0) / imageHeight));
			}
			Image img = srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			w = (width * 1.0) / srcImage.getWidth();
			h = (height * 1.0) / srcImage.getHeight();

			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(w, h), null);
			img = ato.filter(srcImage, null);
			ImageIO.write((BufferedImage) img, targetfilePath.substring(targetfilePath.lastIndexOf(".") + 1), targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
