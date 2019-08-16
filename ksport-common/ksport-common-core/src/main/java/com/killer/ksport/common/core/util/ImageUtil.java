package com.killer.ksport.common.core.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author ：Killer
 * @date ：Created in 19-8-15 下午2:12
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class ImageUtil {

    private static BASE64Encoder base64Encoder = new BASE64Encoder();

    /**
     * 将指定路径的图片转为图像转Base64编码
     */
    public static String base64EncodeImage(String path) throws IOException
    {
        return base64EncodeImage(path, false, 0,0);
    }

    /**
     * 将指定路径的图片转为图像转Base64编码
     */
    public static String base64EncodeImage(InputStream inputStream, String format) throws IOException
    {
        return base64EncodeImage(inputStream, format, false, 0,0);
    }

    /**
     * 将指定路径的图片先生成缩略图(默认尺寸为150 * 112), 再把缩略图转为Base64编码
     */
    public static String base64EncodeImage(String path, boolean createThumb) throws IOException
    {
        return base64EncodeImage(path, createThumb, 150, 112);
    }

    /**
     * 将指定路径的图片先生成缩略图(默认尺寸为150 * 112), 再把缩略图转为Base64编码
     */
    public static String base64EncodeImage(InputStream inputStream, String format, boolean createThumb) throws IOException
    {
        return base64EncodeImage(inputStream,format, createThumb, 150, 112);
    }

    /**
     * 将指定路径的图片先生成指定尺寸的缩略图, 再把缩略图转为Base64编码
     */
    public static String base64EncodeImage(String path, boolean createThumb, int width, int height) throws IOException
    {
        FileInputStream inputStream = new FileInputStream(path);
        String suffix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        String mediaType;
        switch (suffix)
        {
            case "png":
                mediaType = "png";
                break;
            case "jpg":
            case "jpeg":
                mediaType = "jpg";
                break;
            case "bmp":
                mediaType = "bmp";
                break;
            case "gif":
                mediaType = "gif";
                break;
            case "tiff":
                mediaType = "tiff";
                break;
            default:
                mediaType = "jpg";
                break;
        }

        return base64EncodeImage(inputStream, mediaType, createThumb, width, height);
    }

    public static String base64EncodeImage(InputStream inputStream, String format, boolean createThumb, int width, int height) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String encoded = null;

        // 创建一个缩略图
        if (createThumb)
        {
            createImageThumb(inputStream, outputStream, width, height);
            encoded = base64Encoder.encode(outputStream.toByteArray());
        }
        else
        {
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            encoded = base64Encoder.encode(data);
        }
        inputStream.close();
        outputStream.close();

        return "data:image/" + format + ";base64," + encoded;
    }


    public static void createImageThumb(InputStream imageInputStream, OutputStream outputStream, int width, int height) throws IOException
    {
        BufferedImage image = ImageIO.read(imageInputStream);
        Image thumb = image.getScaledInstance(width, height, Image.SCALE_FAST);

        image = new BufferedImage(thumb.getWidth(null), thumb.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.drawImage(thumb, 0, 0, null);
        g.dispose();
        ImageIO.write(image, "jpg", outputStream);
    }
}
