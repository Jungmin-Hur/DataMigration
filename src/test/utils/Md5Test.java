package test.utils;

import java.math.BigInteger;

import org.junit.Test;

import main.common.utils.Md5;

public class Md5Test {
	@Test
	public void Md5Test() {
		System.out.println(Md5.getMd5("test"));
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
