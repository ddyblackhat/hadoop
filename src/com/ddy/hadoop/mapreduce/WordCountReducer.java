package com.ddy.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	@Override
	protected void reduce(Text key, Iterable<LongWritable> values, Context context)
			throws IOException, InterruptedException {

		//����һ���ۼӼ�����
		long count = 0;
		
		for(LongWritable value: values){
			
			count += value.get();
		}
		
		// ���<����:count> ��ֵ��
		context.write(key, new LongWritable(count));
		
	}

}
