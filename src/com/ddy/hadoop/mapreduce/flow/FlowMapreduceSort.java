package com.ddy.hadoop.mapreduce.flow;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class FlowMapreduceSort {
	public static class FlowSortMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable> {

		private FlowBean flowBean = new FlowBean();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = StringUtils.split(line, "\t");

			String phoneNbr = fields[0];
			long up_flow = Long.parseLong(fields[1]);
			long d_flow = Long.parseLong(fields[2]);

			flowBean.set(phoneNbr, up_flow, d_flow);

			context.write(flowBean, NullWritable.get());

		}
	}

	private static class FlowSortReducer extends Reducer<FlowBean, NullWritable, Text, FlowBean> {

		@Override
		protected void reduce(FlowBean bean, Iterable<NullWritable> arg1, Context context)
				throws IOException, InterruptedException {
			context.write(new Text(bean.getPhoneNbr()), bean);

		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "flowSortJob");

		job.setJarByClass(FlowMapreduceSort.class);

		job.setMapperClass(FlowSortMapper.class);
		job.setReducerClass(FlowSortReducer.class);

		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(NullWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(job, new Path("hdfs://hadoop:8020/flowCount/output"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop:8020/flowCount/output2"));

		job.waitForCompletion(true);
	}

}
