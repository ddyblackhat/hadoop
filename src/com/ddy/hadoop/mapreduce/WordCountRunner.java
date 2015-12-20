package com.ddy.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * ��������һ����ҵjob(ʹ���ĸ�mapper,�ĸ�reducer,�����ļ�����,���������ġ�����)
 * @author dudy
 */
//
public class WordCountRunner {

	
	public static void main(String[] args) throws IOException, Exception, InterruptedException {
		Configuration conf = new Configuration();
		Job wcjob = Job.getInstance(conf);
		
		// ����job��ʹ�õ�jar��
		conf.set("mapreduce.job.jar", "wcount.jar");
		
		// ����wcjob�е���Դ���ڵ�jar��
		wcjob.setJarByClass(WordCountRunner.class);
		
		// wcjobҪʹ�õ��ĸ�mapper��
		wcjob.setMapperClass(WordCountMapper.class);
		wcjob.setReducerClass(WordCountReduce.class);
		
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(LongWritable.class);
		
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(LongWritable.class);
		
		// ָ��Ҫ�����ԭʼ��������ŵ�·��
		FileInputFormat.setInputPaths(wcjob, "hdfs://hadoop:8020/wc/srcdata");
		
		FileOutputFormat.setOutputPath(wcjob, new Path("hdfs://hadoop:8020/wc/output"));
	
		boolean res = wcjob.waitForCompletion(true);
		
		System.exit(res?0:1);
		
	}
}
