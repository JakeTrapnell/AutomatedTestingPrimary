package com.qa.SeleniumReadWrite;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Para {

	@Parameters
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {{"a","b",1, true},{"c","d",2, false}});
	}
	
	private String x;
	private String y;
	private int z;
	private Boolean a;
	
	public Para(String x, String y, int z, Boolean a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}
	
	@Test
	public void testOne() {
		System.out.println(x + " " +y + " " + z + " " + a);
	}
}
