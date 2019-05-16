package com.selenium.test.disc;

/**
 * Created by harbor on 2019/5/16.
 */



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Vector;

public class SequenceInputStreamDemo {

    public static void main(String[] args) {
        SequenceInputStreamDemo io=new SequenceInputStreamDemo();
        io.sample();
//        io.advance();
    }

    public void sample() {
        InputStream input1;
        InputStream input2;
        SequenceInputStream sequenceInputStream = null;
        try {
            input1 = new FileInputStream("c:\\data\\file1.txt");
            input2 = new FileInputStream("c:\\data\\file2.txt");

            sequenceInputStream = new SequenceInputStream(input1, input2);

            int data = sequenceInputStream.read();
            while (data != -1) {
                System.out.println((char)data);
                data = sequenceInputStream.read();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: handle exception
        } finally {
            if (sequenceInputStream != null) {
                try {
                    sequenceInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public void advance() {
        SequenceInputStream sequenceInputStream=null;
        try {
            InputStream input1 = new FileInputStream("c:\\data\\file1.txt");
            InputStream input2 = new FileInputStream("c:\\data\\file2.txt");

            Vector<InputStream> streams = new Vector<InputStream>();
            streams.add(input1);
            streams.add(input2);

            sequenceInputStream = new SequenceInputStream(streams.elements());

            int data= sequenceInputStream.read();

            while(data != -1){
                System.out.println((char)data);
                data = sequenceInputStream.read();
            }
            sequenceInputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if (sequenceInputStream != null) {
                try {
                    sequenceInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

}
