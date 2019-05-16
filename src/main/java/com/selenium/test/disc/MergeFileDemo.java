package com.selenium.test.disc;

import ch.qos.logback.core.util.FileUtil;
import org.apache.http.util.TextUtils;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by harbor on 2019/5/16.
 */
public class MergeFileDemo {


    public static boolean mergeFiles(String[] fpaths, String resultPath) {
        if (fpaths == null || fpaths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i ++) {
            files[i] = new File(fpaths[i]);
            if (TextUtils.isEmpty(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            int bufSize = 1024;
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFile));
            byte[] buffer = new byte[bufSize];

            for (int i = 0; i < fpaths.length; i ++) {
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(files[i]));
                int readcount;
                while ((readcount = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readcount);
                }
                inputStream.close();
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

//        for (int i = 0; i < fpaths.length; i ++) {
//            files[i].delete();
//        }

        return true;
    }

    public static boolean mergeFilesNIO(String[] fpaths, String resultPath) {
        if (fpaths == null || fpaths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i ++) {
            files[i] = new File(fpaths[i]);
            if (TextUtils.isEmpty(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < fpaths.length; i ++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

//        for (int i = 0; i < fpaths.length; i ++) {
//            files[i].delete();
//        }

        return true;
    }

    public static void generateTestFiles(String path){
        File f = new File(path);
        if (f.exists()==false){
            f.mkdirs();
        }
        for(int i=0;i<10000;i++){
            File file = new File(path+"/sample_"+i+"_"+System.currentTimeMillis()+".txt");
            PrintStream ps = null;
            try {
                ps = new PrintStream(new FileOutputStream(file));
                ps.println("http://www.jb51.net");
                ps.println("http://www.jb51.com");
                for(int j=0;j<100000;j++) {
                    ps.println("http://www." + System.currentTimeMillis() + ".com");
                }
                ps.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
       String path = "c:/data";
//        generateTestFiles(path);

        File dir  = new File(path);
        File[] files = dir.listFiles();
        String[] filePaths = new String[files.length];
        for(int i=0;i<files.length;i++){
            filePaths[i] = files[i].getAbsolutePath();
        }

        long start_time = System.currentTimeMillis();
        mergeFiles(filePaths,"c:/merge-dir-buffer/buffer.txt");
        System.out.println("buffer elapsed time is: " + (System.currentTimeMillis()-start_time)+"ms");
        start_time = System.currentTimeMillis();
        mergeFiles(filePaths,"c:/merge-dir-buffer/nio.txt");
        System.out.println("nio elapsed time is: " + (System.currentTimeMillis()-start_time)+"ms");

//        new SequenceInputStream(e)
    }

}
