/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.jeesite.common.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;

/**
 * 验证码工具
 * @author ThinkGem
 * @version 2017年12月23日
 */
public class CaptchaUtils {

	private static Random random = new Random();
	private volatile static ConfigurableCaptchaService ccs;
	private static WobbleRippleFilterFactory wrff; 	// 摆波纹
	private static DoubleRippleFilterFactory doff; 	// 双波纹
	private static CurvesRippleFilterFactory crff; 	// 曲线波纹
	private static DiffuseRippleFilterFactory drff; // 漫纹波
	private static MarbleRippleFilterFactory mrff; 	// 大理石

	private static void initialize(){
		if (ccs == null){
        	synchronized (CaptchaUtils.class) {
        		if (ccs == null){
        			// 配置初始化
	            	ccs = new ConfigurableCaptchaService();

	            	// 设置图片大小
	        		ccs.setWidth(100);
	        		ccs.setHeight(28);

	            	// 设置文字数量
					// 随机字符生成器,去除掉容易混淆的字母和数字,如o和0等
	        		RandomWordFactory wf = new RandomWordFactory();
	        		wf.setCharacters("ABDEFGHKMNRSWX2345689");
	        		wf.setMinLength(4);
	        		wf.setMaxLength(4);
	        		ccs.setWordFactory(wf);

	        		// 设置字体大小
	        		RandomFontFactory ff = new RandomFontFactory();
	        		ff.setMinSize(28);
	        		ff.setMaxSize(28);
	        		ccs.setFontFactory(ff);

	        		// 设置文字渲染边距
	        		BestFitTextRenderer tr = new BestFitTextRenderer();
	        		tr.setTopMargin(3);
	        		tr.setRightMargin(3);
	        		tr.setBottomMargin(3);
	        		tr.setLeftMargin(3);
					ccs.setTextRenderer(tr);

	        		// 设置字体颜色
	        		ccs.setColorFactory(new ColorFactory() {
	        			@Override
	        			public Color getColor(int x) {
	        				int r = random.nextInt(90);
	        				int g = random.nextInt(90);
	        				int b = random.nextInt(90);
	        				return new Color(r, g, b);
	        			}
	        		});

	        		// 设置背景
	        		ccs.setBackgroundFactory(new BackgroundFactory() {
						@Override
						public void fillBackground(BufferedImage image) {
							Graphics graphics = image.getGraphics();
							// 验证码图片的宽高
							int imgWidth = image.getWidth();
							int imgHeight = image.getHeight();
							// 填充为白色背景
							graphics.setColor(Color.WHITE);
							graphics.fillRect(0, 0, imgWidth, imgHeight);
							// 画 50 个噪点(颜色及位置随机)
							for (int i = 0; i < 50; i++) {
								// 随机颜色
								int rInt = random.nextInt(100)+50;
								int gInt = random.nextInt(100)+50;
								int bInt = random.nextInt(100)+50;
								graphics.setColor(new Color(rInt, gInt, bInt));
								// 随机位置
								int xInt = random.nextInt(imgWidth - 3);
								int yInt = random.nextInt(imgHeight - 2);
								// 随机旋转角度
								int sAngleInt = random.nextInt(360);
								int eAngleInt = random.nextInt(360);
								// 随机大小
								int wInt = random.nextInt(6);
								int hInt = random.nextInt(6);
								// 填充背景
								graphics.fillArc(xInt, yInt, wInt, hInt, sAngleInt, eAngleInt);
								// 画5条干扰线
								if (i % 10 == 0) {
									int xInt2 = random.nextInt(imgWidth);
									int yInt2 = random.nextInt(imgHeight);
									graphics.drawLine(xInt, yInt, xInt2, yInt2);
								}
							}
						}
					});

	        		// 效果初始化
	        		wrff = new WobbleRippleFilterFactory(); 	// 摆波纹
	        		doff = new DoubleRippleFilterFactory(); 	// 双波纹
	        		crff = new CurvesRippleFilterFactory(ccs.getColorFactory()); // 曲线波纹
	        		drff = new DiffuseRippleFilterFactory(); 	// 漫纹波
	        		mrff = new MarbleRippleFilterFactory(); 	// 大理石
        		}
			}
        }
	}

	/**
	 * 生成验证码
	 * @throws IOException
	 * @return 验证码字符
	 */
	public static String generateCaptcha(OutputStream outputStream) throws IOException{
		// 初始化设置
		initialize();
        // 随机选择一个样式
        switch (random.nextInt(3)) {
		case 0:
			ccs.setFilterFactory(wrff); // 摆波纹
			break;
		case 1:
			ccs.setFilterFactory(doff); // 双波纹
			break;
		case 2:
			ccs.setFilterFactory(crff); // 曲线波纹
			break;
		case 3:
			ccs.setFilterFactory(drff); // 漫纹波
			break;
		case 4:
			ccs.setFilterFactory(mrff); // 大理石
			break;
		}
        // 生成验证码
        String s = EncoderHelper.getChallangeAndWriteImage(ccs, "png", outputStream);
//        System.out.println(s);
		return s;
	}

	/**
	 * 生成验证码
	 * @return 验证码字符
	 */
	public static Map<String,String> generateBase64Captcha(){
		Map<String,String> result = new HashMap<>();
		try {
			// 初始化设置
			initialize();
			// 随机选择一个样式
			switch (random.nextInt(3)) {
				case 0:
					ccs.setFilterFactory(wrff); // 摆波纹
					break;
				case 1:
					ccs.setFilterFactory(doff); // 双波纹
					break;
				case 2:
					ccs.setFilterFactory(crff); // 曲线波纹
					break;
				case 3:
					ccs.setFilterFactory(drff); // 漫纹波
					break;
				case 4:
					ccs.setFilterFactory(mrff); // 大理石
					break;
			}
			// 得到验证码对象,有验证码图片和验证码字符串
			Captcha captcha = ccs.getCaptcha();
			String validationCode = captcha.getChallenge();
			// 取得验证码图片并输出
			BufferedImage bufferedImage = captcha.getImage();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
			ImageIO.write(bufferedImage, "png", baos);//写入流中
			byte[] bytes = baos.toByteArray();//转换成字节
			BASE64Encoder encoder = new BASE64Encoder();
			String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
			png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
			String imgBase64 = "data:image/png;base64,"+png_base64;
			result.put("imgCode",validationCode);
			result.put("imgBase64",imgBase64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成验证码
	 */
	public static Map<String,String> generateSimpleBase64Captcha(){
		Map<String,String> result = new HashMap<>();
		try {
			CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(50, 30, 4, 5);
			captcha.createCode();
			String imgCode = captcha.getCode();
			String imgBase64 = captcha.getImageBase64Data();
			result.put("imgCode",imgCode);
			result.put("imgBase64",imgBase64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("/Users/lihai/02space/nabobsite-master/modules/core/src/main/java/com/jeesite/modules/sys/web/captcha.png");
		//String s = generateCaptcha(fos);
//		System.out.println(s);
//		fos.close();

		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(50, 30, 4,5);
		captcha.createCode();
		String imgCode = captcha.getCode();
		String imgBase64 = captcha.getImageBase64Data();
		System.out.println(imgBase64);
		System.out.println(imgCode);
	}
}
