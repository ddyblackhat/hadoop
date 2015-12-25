package com.ddy.hadoop.mapreduce.flow;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

// hadoop 实现自己的序列化机制跟jdk有区别：  比jdk更简洁
public class FlowMapreduce {

	public static class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
		
		private FlowBean flowBean = new FlowBean();
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// 拿一行数据
			String line = value.toString();
			// 切分字段
			String[] fields =StringUtils.split(line, "\t");
			// 拿到我们需要的若干个字段
			String phoneNbr = fields[1];
			long up_flow = Long.parseLong(fields[fields.length -3]);
			long d_flow = Long.parseLong(fields[fields.length -2]);
			// 将数据封装到一个flowBean中
			
			flowBean.set(phoneNbr, up_flow, d_flow);
			
			// 以手机号为key, 将流量数据输出去
			context.write(new Text(phoneNbr), flowBean);
			
		}
	}
	
	public static class FlowCountReducer extends Reducer<Text, FlowBean, Text, Text>{
		private FlowBean flowBean = new FlowBean();
		@Override
		protected void reduce(Text key, Iterable<FlowBean> values,Context context)
				throws IOException, InterruptedException {
			long up_flow_sum = 0;
			long d_flow_sum = 0;
			
			for (FlowBean bean : values) {
				up_flow_sum += bean.getUp_flow();
				d_flow_sum  += bean.getD_flow();
			}
		
			flowBean.set(key.toString(), up_flow_sum, d_flow_sum);
			context.write(key, new Text(flowBean.toString()));
		}
	}

	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"flowjob");
		
		job.setJarByClass(FlowMapreduce.class);
		
		job.setMapperClass(FlowCountMapper.class);
		job.setReducerClass(FlowCountReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://hadoop:8020/flowCount/srcdata"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop:8020/flowCount/output"));
		
		job.waitForCompletion(true);
	}
}
