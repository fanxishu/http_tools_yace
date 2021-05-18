//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.newland.research.serviceKeeper;

import com.newland.research.serviceKeeper.common.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServiceKeeperApplication {
	public ServiceKeeperApplication() {
	}

	public static void main(String[] args) {
		System.out.println(args[0]);
		System.out.println(args[1]);
		String usrHome = System.getProperty("user.home");
		System.out.println(usrHome);
		Constant.nums = args;
		SpringApplication.run(ServiceKeeperApplication.class, args);
	}
}
