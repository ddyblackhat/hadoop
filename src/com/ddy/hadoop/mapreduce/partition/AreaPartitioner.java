package com.ddy.hadoop.mapreduce.partition;

import java.util.HashMap;

import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 
 * @author dudy
 *
 */
public class AreaPartitioner<KEY, VALUE> extends Partitioner<KEY, VALUE>{

	private static HashMap<String, Integer> areaMap = new HashMap<>();
	
	
	static { // 模拟手机号的分区
		areaMap.put("136", 0);
		areaMap.put("137", 1);
		areaMap.put("138", 2);
		areaMap.put("139", 3);
	}
	
	
	@Override
	public int getPartition(KEY key, VALUE value, int numPartitions) {// key 手机号, numPartitions : 分区号码
		
		Integer provinceCode = areaMap.get(key.toString().substring(0, 3));
		
		return provinceCode == null ? 4:provinceCode;
	}
	
	

}
