package com.platform.mesh.upms;

import com.platform.mesh.resource.annotation.EnableResourceServerConfig;
import com.platform.mesh.upms.biz.captcha.event.CaptchaEvent;
import com.platform.mesh.upms.biz.modules.doc.file.enums.FileFlagEnum;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Bean;

/**
 * @description 启动程序
 * @author 蝉鸣
 */
@EnableResourceServerConfig
@SpringBootApplication
public class UpmsApplication {

	private static final Logger log = LoggerFactory.getLogger(UpmsApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(UpmsApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));//将启动期间的日志缓存起来，启动后再统一打印，目的：提升项目启动速度
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
	 * @author 蝉鸣
	 */
	public static class CustomRunner implements CommandLineRunner {
		//工程启动后执行步骤
		@Override
		public void run(String... args) {

			//案例：打印启动成功图例
			this.printInfoAfterStart();
			//初始化验证码
			SpringContextHolderUtil.publishEvent(new CaptchaEvent(FileFlagEnum.CAPTCHA.getDesc()));
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
