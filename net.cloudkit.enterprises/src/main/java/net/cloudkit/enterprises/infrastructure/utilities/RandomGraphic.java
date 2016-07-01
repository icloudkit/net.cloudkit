package net.cloudkit.enterprises.infrastructure.utilities;

import org.springframework.session.Session;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码生成
 *
 * @author hongquanli<hongquanli@qq.com>
 * @version 1.0 2011-11-1 下午12:58:02
 */
public class RandomGraphic {

	/** 图片宽度 **/
	private static final int IMAGE_WIDTH = 80;
	/** 图片高度 **/
	private static final int IMAGE_HEIGHT = 26;
	/** 文本高度 **/
	private static final int WORD_HEIGHT = 6;
	/** 文本宽度 **/
	private static final int WORD_WIDTH = 15;
	/** 文字Left距 **/
	private static final int xinitypos = 1;
	/** 文字Top距 **/
	private static final int yinitypos = 15;
	/** 字体大小 **/
	private static final int FONT_SIZE = 30;
	/** 最大文字数 **/
	private static final int MAX_CHARCOUNT = 16;
	/** 字体 **/
	private static final String[] TEXT_FONT = { "Century", "华文彩云", "Brush Script MT", "Poor Richard" };
	/** 背景图片 **/
	private static final String[] BG_IMG = { "/untitled.png" };
	/** 背影色 **/
	private static final int BACK_COLOR = 0xFFFFFF;
	/** 文字色 **/
	private static final int[] CHAR_COLOR = { 0x0033CC };
	public static final String GRAPHIC_JPEG = "JPEG";
	public static final String GRAPHIC_PNG = "PNG";
	/** 文字数 **/
	private int charCount = 8;
	/** 随机数 **/
	private Random random = new Random();

	protected RandomGraphic(int charCount) {
		this.charCount = charCount;
	}

	public static RandomGraphic createInstance(int charCount) throws Exception {
		if (charCount < 1 || charCount > MAX_CHARCOUNT) {
			throw new Exception("Invalid parameter charCount,charCount should between in 1 and 16");
		}
		return new RandomGraphic(charCount);
	}

	public String drawAlpha(String graphicFormat, ByteArrayOutputStream out, boolean uppercaseString, boolean lowercaseString, Session session, String path) throws IOException {
		String charValue = "";
		charValue = getRandKeys(charCount, uppercaseString, lowercaseString);
		// 保存到session
		session.setAttribute("SESSION", charValue);
		return draw(charValue, graphicFormat, out, path);
	}

	private String draw(String charValue, String graphicFormat, ByteArrayOutputStream out, String path) throws IOException {

		BufferedImage bi = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bi.createGraphics();
		Color backColor = new Color(BACK_COLOR);
		g.setBackground(backColor);
		g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		g.setFont(new Font(TEXT_FONT[randomInt(0, TEXT_FONT.length)], Font.CENTER_BASELINE, FONT_SIZE));
		// System.out.println(path);
		Image image = ImageIO.read(new File(path + BG_IMG[randomInt(0, BG_IMG.length)]));
		g.drawImage(image, 0, 0, null);
		for (int i = 0; i < charCount; i++) {
			String c = charValue.substring(i, i + 1);
			Color color = new Color(CHAR_COLOR[randomInt(0, CHAR_COLOR.length)]);
			g.setColor(color);
			int xpos = (i + xinitypos) * WORD_WIDTH - 5;
			int ypos = randomInt(yinitypos + WORD_HEIGHT, yinitypos + WORD_HEIGHT * 2);
			// g.rotate(10.0 * Math.PI / 180.0, bi.getWidth() / 2.0, bi.getHeight() / 2.0);
			g.drawString(c, xpos, ypos);
		}
		g.dispose();
		bi.flush();
		ImageIO.write(bi, graphicFormat, out);

		return charValue;
	}

	public static final String getRandKeys(int intLength, boolean uppercaseString, boolean lowercaseString) {
		String retStr = "";
		String strTable = "0123456789";
		if (uppercaseString) {
			strTable += "ABCDEFGHJKLMNPQRSTUVWXYZ";
		}
		if (lowercaseString) {
			strTable += "abcdefghijkmnpqrstuvwxyz";
		}
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < intLength; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return retStr;
	}

	private int randomInt(int from, int to) {
		return from + random.nextInt(to - from);
	}
}
