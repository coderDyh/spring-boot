package com.cathaynav.dms.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Dengyh
 * @Dare 20190403
 * @Describe:解压文件工具类
 * */
public class ZipUtils {
    /**
     * 解压方法
     * @param zipPath 压缩包所在路径
     * @param newPath 解压后要放入的路径
     * */
    public static void unZip(String zipPath,String newPath){
        try{
            ZipFile zFile = new ZipFile(zipPath);
            zFile.setFileNameCharset("GBK");
            File destDir = new File(newPath);
            zFile.extractAll(newPath);
            List<FileHeader > headerList = zFile.getFileHeaders();
            List<File> extractedFileList= new ArrayList<File>();
            for(FileHeader fileHeader : headerList) {

                if (!fileHeader.isDirectory()) {
                     extractedFileList.add(new File(destDir,fileHeader.getFileName()));

                    }

            }
            File [] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            for(File f:extractedFileList){

                System.out.println(f.getAbsolutePath()+"....");

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 解压方法,获取文件名带有指定名称的文件
     * @param zipPath 压缩包所在路径
     * @param newPath 解压后要放入的路径
     * @param str 包含指定名称
     * */
    public static File unZipOfUav(String zipPath,String newPath,String str){
        try{
            ZipFile zFile = new ZipFile(zipPath);
            zFile.setFileNameCharset("GBK");
            File destDir = new File(newPath);
            zFile.extractAll(newPath);
            List<FileHeader > headerList = zFile.getFileHeaders();
            List<File> extractedFileList= new ArrayList<File>();
            for(FileHeader fileHeader : headerList) {

                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir,fileHeader.getFileName()));

                }

            }
            File [] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            for(File f:extractedFileList){
                if(f.getName().contains(str)){
                    return f;
                }
                System.out.println(f.getAbsolutePath()+"....");

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 读文件
     * @param file 文件名
     * */
    public static void readFile(File file){
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
        String zipPath = "E:\\pos_X820SN0000360_20181123_173604.zip";
        String newPath = "E:\\pos_X820SN0000360_20181123_173604\\";
//        String zipPath = "E:\\12.07.zip";
//        String newPath = "E:\\12.07\\";
        File f = unZipOfUav(zipPath,newPath,"pos");
//        System.out.println("文件名为:"+file.getPath());
        readFile(f);
    }
}
