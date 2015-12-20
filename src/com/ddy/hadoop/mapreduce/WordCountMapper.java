package com.ddy.hadoop.mapreduce;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// ��ȡһ������
		String words = value.toString();
		// �з�
		String[] split = StringUtils.split(words, " ");
		// �����洢

		for (String string : split) {
			context.write(new Text(string), new LongWritable(1));
		}
	}
}
