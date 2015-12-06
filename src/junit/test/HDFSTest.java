package junit.test;

import org.junit.Test;

import com.ddy.hadoop.hdfs.HDFSUtils;

public class HDFSTest {

	@Test
	public void mkdirTest() {

		boolean b = HDFSUtils.mkdir("/ddy");
		System.out.println(b);
	}
	
	@Test
	public void listStatiesTest(){
		HDFSUtils.listStaties("/");
	}

}
