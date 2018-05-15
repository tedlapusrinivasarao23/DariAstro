package com.dari.astro.utils;

import java.util.Comparator;

import com.dari.astro.bos.BirthChartDetails;

public class BirthChartComparator implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		BirthChartDetails s1=(BirthChartDetails)o1;  
		BirthChartDetails s2=(BirthChartDetails)o2;  
		String sf=s1.getFirst_Name();
		String sf1=s2.getFirst_Name();
		int res = 0;
		try
		{
		 res=sf.compareTo(sf1);
		}
		catch(Exception e)
		{
			
		}
		if(res!=0)
		{
			System.out.print(res);
			return res;
		}
		else
		{
			return -1;
		}
		
		
		
			} 

}
