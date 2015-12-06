package com.ddy.hadoop.hdfs;
//  由于限制，没有注释 ，基本根据 单词意思也可以推测出来
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HDFSUtils {

        private static FileSystem hdfs;

        public HDFSUtils() {

                String uri = "hdfs://192.168.226.128:8020";
                Configuration conf = new Configuration();
                try {
                        hdfs = FileSystem.get(URI.create(uri), conf);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }


        public static boolean mkdir(String path) {
                boolean b = false;
                try {
                        b = hdfs.mkdirs(new Path(path));
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return b;
        }


        public static boolean delete(String path) {
                boolean b = false;
                Path f = new Path(path);
                try {
                        if (hdfs.exists(f)) {
                                b = hdfs.delete(f, true);
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }

                return b;
        }

        
        public static  void copyFromLocalFile(String src, String dst) {
                try {
                        hdfs.copyFromLocalFile(new Path(src), new Path(dst));
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }


        public static void copyToLocalFile(String src, String dst) {
                try {
                
                        hdfs.copyToLocalFile(false, new Path(src),new Path( dst), true);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public static void listStaties(String path){
                try {
                        FileStatus[] status = hdfs.listStatus(new Path(path));
                        for (FileStatus fileStatus : status) {
                                System.out.println(fileStatus.toString());
                        }                
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        

        public static void rename(String src, String dst){
                try {
                        hdfs.rename(new Path(src), new Path(dst));
                } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        

}
