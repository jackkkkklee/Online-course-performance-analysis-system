package com.abc.util;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import sun.misc.BASE64Decoder;
import java.io.*;
public class Base64ToImage {

        public static String Readfile2Str(String filePath){
            String fileIOstr;
            try {
                BufferedReader in = new BufferedReader(new FileReader(filePath));
                while ((fileIOstr = in.readLine()) != null) {
                    break;
                }
            } catch (IOException e) {
                System.out.println(e);
                fileIOstr = "";
            }
            return fileIOstr;
        }
        public static void Base64ToImg(String filePath,String fileNmae,String fileType){
            String fileIOstr = Readfile2Str(filePath);
            //对内容进行base64解码
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                FileOutputStream write = new FileOutputStream(new File(fileNmae+"."+fileType));
                byte[] decodeBytes = decoder.decodeBuffer(fileIOstr);
                write.write(decodeBytes);
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     *
     * @param base64
     *            base64编码的图片信息
     * @return
     */
    public static void decodeBase64ToImage(String base64, String path,
                                           String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


