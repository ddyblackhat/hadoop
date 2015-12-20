package com.ddy.hadoop.mapreduce;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// 获取一行内容
		String words = value.toString();
		// 切分
		String[] split = StringUtils.split(words, " ");
		// 计数存储

		for (String string : split) {
			context.write(new Text(string), new LongWritable(1));
		}
	}
}
