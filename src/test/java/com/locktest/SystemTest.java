package com.locktest;

import com.lock.MonitorInfoBean;
import com.lock.MonitorService;

public class SystemTest {
	public static void main(String[] args) throws Exception {
		MonitorService ms = new MonitorService();
		MonitorInfoBean mb = ms.getMonitorInfoBean();
		for (int i = 0; i < 10000; i++) {
			int x=0;
			x++;
		}		
		System.out.println(mb.getUsedMemory());
	}

}
