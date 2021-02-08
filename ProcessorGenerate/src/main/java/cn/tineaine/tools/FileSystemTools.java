package cn.tineaine.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileSystemTools {

    // 创建文件夹
    public boolean mkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {//如果文件夹不存在
            return file.mkdirs();//创建文件夹
        } else {
            return true;
        }
    }

    // 复制文件到指定目录
    public boolean cp(String source, String target) {

        File sourceFile = new File(source);
        File targetFile = new File(target);

        if (!sourceFile.exists()) {
            return false;
        }

        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath());
        } catch (IOException e) {
            return false;
        }

        return true;
    }


    // 替换指定文件中所有内容
    public boolean replace(String path, String before, String later) {
        try {
            File file = new File(path);
            long length = file.length();
            byte[] context = new byte[(int) length];
            FileInputStream in = null;
            PrintWriter out = null;
            in = new FileInputStream(path);
            in.read(context);
            String str = new String(context, StandardCharsets.UTF_8);//字节转换成字符
            str = str.replace(before, later);
            out = new PrintWriter(path, "utf-8");//写入文件时的charset
            out.write(str);
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
