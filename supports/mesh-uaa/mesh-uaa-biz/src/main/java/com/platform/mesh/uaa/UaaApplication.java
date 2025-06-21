package com.platform.mesh.uaa;

import com.platform.mesh.authorization.annotation.EnableAuthorizationServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Bean;

/**
 * @description 认证中心启动器
 * @author 蝉鸣
 */
@EnableAuthorizationServerConfig
@SpringBootApplication
public class UaaApplication {

	private static final Logger log = LoggerFactory.getLogger(UaaApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(UaaApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));
		application.run(args);
	}

	/**
	 * 功能描述:
	 * 〈自定义启动器〉
	 * @return 正常返回:{@link CustomRunner}
	 * @author 蝉鸣
	 */
	@Bean
	public CustomRunner customRunner() {
		return new CustomRunner();
	}

	/**
	 * 功能描述:
	 * 〈工程启动后执行〉
	 */
	public static class CustomRunner implements CommandLineRunner {
		//工程启动后执行步骤
		@Override
		public void run(String... args) {

			//案例：打印启动成功图例
			this.printInfoAfterStart();
			//其他：例如执行数据脚本,初始任务等等
			//...
		}
		public void printInfoAfterStart(){
			log.info("""
                    \s
                     ~~~~~~~~ ﾞ(ლˊڡ´)ლ 来起开车火小  ﾞﾉ(◠‿◠♥)    .        .   .  . .- .- ----. \s
                         __                                         '      ' ' - - -----ˎ'. )    \s
                      ___||_=========____  ___--------------------__          -:--;      \\_/     \s
                       |..|_i_|..|_i_|..|   | |_!_||_!_||_!_||_!_| |  ___ooo  .]__''_^_n__U__    \s
                       |                |   | |___||___||___||___| | [ˎˎˎˎˎˎ\\ ).. _).. _).. _)ɔ  \s
                     i_!________________!ˎi_|______________________|ˎ[______⅃ˎ|_'___________/_ˎ  \s
                         (o)(o)-(o)(o)          (o)^(o)--(o)^(o)      (o)(o)   (@)(@)==(@)(@)_;\\ \s
                    ""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~""\"~"\"""");
		}

	}

}
