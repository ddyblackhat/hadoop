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
 * 用来描述一个作业job(使用哪个mapper,哪个reducer,输入文件在哪,输出结果放哪。。。)
 * @author dudy
 */
//
public class WordCountRunner {

	
	public static void main(String[] args) throws IOException, Exception, InterruptedException {
		Configuration conf = new Configuration();
		Job wcjob = Job.getInstance(conf);
		
		// 设置job所使用的jar包
		conf.set("mapreduce.job.jar", "wcount.jar");
		
		// 设置wcjob中的资源所在的jar包
		wcjob.setJarByClass(WordCountRunner.class);
		
		// wcjob要使用的哪个mapper类
		wcjob.setMapperClass(WordCountMapper.class);
		wcjob.setReducerClass(WordCountReduce.class);
		
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(LongWritable.class);
		
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(LongWritable.class);
		
		// 指定要处理的原始数据所存放的路径
		FileInputFormat.setInputPaths(wcjob, "hdfs://hadoop:8020/wc/srcdata");
		
		FileOutputFormat.setOutputPath(wcjob, new Path("hdfs://hadoop:8020/wc/output"));
	
		boolean res = wcjob.waitForCompletion(true);
		
		System.exit(res?0:1);
		
	}
}
