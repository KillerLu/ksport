package com.killer.ksport.auth.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author ：Killer
 * @date ：Created in 19-8-15 下午2:10
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class VerifyCodeUtil {

    private int width = 100;
    private int height = 50;
    private Random random = new Random();

    private String[] fontNames = {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"};

    private String codes = "3456789abcdefghjkmnpqrstuvwxyABCDEFGHJKMNPQRSTUVWXY";

    private Color bgColor = new Color(255, 255, 255);

    private static String text;

    /**
     * 随机色
     */
    private Color randomColor()
    {
        int red = random.nextInt(100);
        int green = random.nextInt(100);
        int blue = random.nextInt(100);
        return new Color(red, green, blue);
    }

    /**
     * 随机字:字体的样式
     */
    private Font randomFont()
    {
        /*int index = random.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = random.nextInt(4);
        int size = random.nextInt(8) + 35;
        return new Font(fontName, style, size);*/

        return new Font("黑体", Font.BOLD, 38);
    }

    /**
     * 干扰线
     */
    private void drawLine(BufferedImage image)
    {
        int num = 3;
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++)
        {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(randomColor());
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 随机生成一个字符
     */
    private char randomChar()
    {
        int index = random.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 创建BufferedImage
     */
    private BufferedImage createImage()
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, width, height);
        return image;
    }

    /**
     * 得到验证码
     */
    public BufferedImage getImage()
    {
        BufferedImage image = createImage();
        //得到绘制环境
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        //用来装载生成的验证码文本
        StringBuilder sb = new StringBuilder();
        // 向图片中画4个字符
        for (int i = 0; i < 4; i++)
        {
            String s = randomChar() + "";
            sb.append(s);
            float x = i * 1.0F * width / 4;
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s, x, height - 10);
        }
        this.text = sb.toString();
        drawLine(image);
        g2.dispose();
        return image;
    }

    /**
     * 返回验证码图片上的文本
     */
    public static String getText()
    {
        return text;
    }

    /**
     * 保存图片到指定的输出流
     */
    public static void output(BufferedImage image, OutputStream out)
            throws IOException
    {
        ImageIO.write(image, "JPEG", out);
        if (out == null)
        {
            out.close();
        }
    }


    /**
     * @param out
     * @return 图片上的文本
     * @throws IOException
     */
    public static String getVerifyCodeImage(OutputStream out) throws IOException
    {
        VerifyCodeUtil utils = new VerifyCodeUtil();
        BufferedImage image = utils.getImage();
        output(image, out);
        return utils.getText();
    }


    /**
     * 生成未加密的六位密码
     *
     * @param length:密码长度
     * @return
     */
    public static String createJsessionId(int length)
    {

        String base = "abcdefghijklmnpqrstuvwxyz123456789ABCDEFGHIJKMNLPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer password = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int number = Math.abs(random.nextInt(base.length()));
            password.append(base.charAt(number));
        }
        return password.toString();
    }
}
