package com.management.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * 文件上传工具
 * @author lufangpu
 * @date 2018-11-23
 */
public class FileUtils {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static boolean deleteFile(String fileName){
        File file = new File(fileName);
        if(file.exists() && file.isFile()){
            if(file.delete()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static String renameToUUID(String fileName){
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
