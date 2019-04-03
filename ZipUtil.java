package com.cathaynav.dms.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @Author Dengyh
 * @Dare 20190403
 * @Describe:解压文件工具类,有bug，仅支持特定的压缩包
 * */
public class ZipUtil {


//    public static void unZip(File zipFile, File path) {
//        try {
//            if(!path.exists()) {
//                path.mkdirs();
//            }
//            FileInputStream fis = new FileInputStream(zipFile);
//            ZipInputStream zis = new ZipInputStream(fis);
//            ZipEntry zipEntry = null;
//            while((zipEntry = zis.getNextEntry()) != null) {//获取条目
//                String fileName = zipEntry.getName();//获取文件名
//                File file = new File(path.getAbsolutePath() + "/./" + fileName);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                int len = -1;
//                byte[] buf = new byte[1024];
//                while((len = zis.read(buf)) != -1) {
//                    baos.write(buf, 0, len);
//                }
//                baos.close();
//                byte[] fileContent = baos.toByteArray();//获取条目内容（即文件内容）
//                FileOutputStream fos = new FileOutputStream(file);
//                fos.write(fileContent);
//                fos.close();
//            }
//
//            zis.close();
//            fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static File unzip(String zipPath,String newPath){
        try{
            ZipFile zipFile = new ZipFile(zipPath,"GBK");
            //获取压缩文中的所以项
            for(Enumeration<ZipEntry> enumeration = zipFile.getEntries(); enumeration.hasMoreElements();){
                ZipEntry zipEntry = enumeration.nextElement();//获取元素
                //排除空文件夹
                if(!zipEntry.getName().endsWith(File.separator))
                {
                    System.out.println("正在解压文件:"+zipEntry.getName());//打印输出信息
                    //创建解压目录
                    File f = new File(newPath+zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf("/")));
                    //判断是否存在解压目录
                    if(!f.exists())
                    {
                        f.mkdirs();//创建解压目录
                    }
                    OutputStream os = new FileOutputStream(newPath+zipEntry.getName());//创建解压后的文件
                    BufferedOutputStream bos = new BufferedOutputStream(os);//带缓的写出流
                    InputStream is = zipFile.getInputStream(zipEntry);//读取元素
                    BufferedInputStream bis = new BufferedInputStream(is);//读取流的缓存流
                    CheckedInputStream cos = new CheckedInputStream(bis, new CRC32());//检查读取流，采用CRC32算法，保证文件的一致性
//                    byte [] b = new byte[1024];//字节数组，每次读取1024个字节
//                    //循环读取压缩文件的值
//                    while(cos.read(b)!=-1)
//                    {
//                        bos.write(b);//写入到新文件
//                    }
                    int readLen = 0;
                    byte[] buffer = new byte[1024 * 8];
                    while ((readLen = is.read(buffer, 0, 1024 * 8)) != -1) {
                        bos.write(buffer, 0, readLen);
                    }
                    cos.close();
                    bis.close();
                    is.close();
                    bos.close();
                    os.close();
                    if(zipEntry.getName().contains("pos")){
                        return new File(newPath + zipEntry.getName());
                    }
                }
                else
                {
                    //如果为空文件夹，则创建该文件夹
                    new File(newPath+zipEntry.getName()).mkdirs();
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void handleFile(File file){
        try(FileReader reader = new FileReader(file.getPath());
            BufferedReader br = new BufferedReader(reader)){
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                String[] str = line.split("\t");
                System.out.println(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String arg[])throws Exception{
        File zip = new File("E:\\pos_X820SN0000360_20181123_173604.zip");
        File path = new File("E:\\pos_X820SN0000360_20181123_173604");
//        ZipUtil.unZip(zip, path);
//        String zipPath = "E:\\pos_X820SN0000360_20181123_173604.zip";
//        String newPath = "E:\\pos_X820SN0000360_20181123_173604\\";
        String zipPath = "E:\\12.07.zip";
        String newPath = "E:\\12.07\\";
        File file = unzip(zipPath,newPath);
        System.out.println("文件名为:"+file.getPath());
        handleFile(file);
    }
}
