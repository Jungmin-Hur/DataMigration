package test.utils;

import java.math.BigInteger;
import org.junit.Test;
import main.common.utils.Md5;

public class Md5Test {
	@Test
	public void getMd5Test() {
		System.out.println(Md5.getMd5("test"));
	}	

	@Test
	public void getMd5PerformanceTest() {
		System.out.println("Md5 Performance Test Start");
		long start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++) {
			String a = String.valueOf(i) + "abcdefghigklmnopqrstuvwxyz";
		}
		long end = System.currentTimeMillis();
		System.out.println("string test : " + (end-start));
		
		start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++) {
			String a = Md5.getMd5(String.valueOf(i)+"abcdefghigklmnopqrstuvwxyz");
		}
		end = System.currentTimeMillis();
		System.out.println("md5 test : " + (end-start));
		System.out.println("Md5 Performance Test Finish!!");
	}
	
	@Test
	public void Test() {
		BigInteger result = new BigInteger("1");
		BigInteger three = new BigInteger("3");
		for(int i=0;i<78;i++) {
			result = result.multiply(three);
		}
		System.out.println(result);
		System.out.println(result.mod(new BigInteger("7879")));
	}
	
}
