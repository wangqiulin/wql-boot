package com.wql.boot.wqlboot.common.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @auther wangqiulin
 * @date 2018/9/7
 */
public class FileUtils {

    /**
     * 创建文件
     *
     * @param path
     * @throws IOException
     */
    public static void createFile(String path) throws IOException {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        file.createNewFile();
    }


    /**
     * 字节流不带缓存流的复制
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        if (!src.exists()) {
            throw new RuntimeException("资源不存在");
        }
        File parentFile = dest.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (
             FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest);
        ) {
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        }
    }

    /**
     * 字节流带缓冲流的复制
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFile2(File src, File dest) throws IOException {
        if (!src.exists()) {
            throw new RuntimeException("资源不存在");
        }
        File parentFile = dest.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
        ) {
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
                bos.flush();
            }
        }
    }


    /**
     * 字符流的复制
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFile3(File src, File dest) throws IOException {
        if (!src.exists()) {
            throw new RuntimeException("资源不存在");
        }
        File parentFile = dest.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (
                FileReader fr = new FileReader(src);
                FileWriter fw = new FileWriter(dest);
        ) {
            char[] buf = new char[1024];
            int len = 0;
            while ((len = fr.read(buf)) != -1) { //读取文件
                fw.write(buf, 0, len); //写文件
                fw.flush(); //边写入边刷新
            }
        }
    }


    /*private void build() throws IOException {
        FileInputStream fis = new FileInputStream("");
        //字节输入流转字符输入流，带编码
        InputStreamReader reader = new InputStreamReader(fis, "utf-8");
        char[] buf = new char[1024];
        int len = 0;
        while ((len = reader.read(buf)) != -1) { //读取文件
            System.out.println(new String(buf, 0, len));
        }
    }


    private void build2() throws IOException {
        FileOutputStream fos = new FileOutputStream("");
        //字节输出流转字符输出流，带编码
        OutputStreamWriter writer = new OutputStreamWriter(fos, "utf-8");
        writer.write("hello");
        writer.close();
    }


    private void build3() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(""));

        String line = null;
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }
        br.close();
    }

    private void build4() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(""));
        bw.write("hello");
        bw.newLine();  //跨平台换行
        bw.flush();
        bw.close();
    }*/


}
