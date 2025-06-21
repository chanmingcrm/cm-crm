package com.platform.mesh.upms.biz.captcha.listener;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.constant.CommonConstant;
import cloud.tianai.captcha.resource.ResourceStore;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.impl.provider.ClassPathResourceProvider;
import cloud.tianai.captcha.resource.impl.provider.FileResourceProvider;
import cloud.tianai.captcha.resource.impl.provider.URLResourceProvider;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.captcha.tianai.constant.CaptchaConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.captcha.event.CaptchaEvent;
import com.platform.mesh.upms.biz.modules.doc.file.enums.FileFlagEnum;
import com.platform.mesh.upms.biz.modules.doc.file.service.IDocFileService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 异步监听系统登录日志事件
 * @author 蝉鸣
 */
@Component
public class CaptchaListener {

	private final ResourceStore resourceStore;

	private final IDocFileService docFileService;

	public CaptchaListener(ResourceStore resourceStore,IDocFileService docFileService) {
		this.resourceStore = resourceStore;
		this.docFileService = docFileService;
	}

	/**
	 * 功能描述:
	 * 〈响应日志事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Async
	@Order
	@EventListener(CaptchaEvent.class)
	public void addAuthorization(CaptchaEvent event) {
		// 2. 添加自定义背景图片, resource 的参数1为资源类型(默认支持 classpath/file/url ), resource 的参数2为资源路径, resource 的参数3为标签
		// 添加本地环境默认图片,避免资源为空
		String defaultKey = CaptchaTypeConstant.SLIDER.concat(SymbolConst.COLON).concat(StrUtil.toString(NumberConst.NUM_0));
		resourceStore.addResource(defaultKey, new Resource(ClassPathResourceProvider.NAME, CaptchaConst.DEFAULT_CLASS_PATH, CommonConstant.DEFAULT_TAG));
		// 动态加载数据库资源
		List<DocFileVO> docFileVOS = docFileService.getByFileFlagNoAuth(FileFlagEnum.CAPTCHA.getValue());
		if (CollUtil.isNotEmpty(docFileVOS)) {
			for (int i = 0; i < docFileVOS.size(); i++) {
				DocFileVO docFileVO = docFileVOS.get(i);
				String key = CaptchaTypeConstant.SLIDER.concat(SymbolConst.COLON).concat(StrUtil.toString(i+NumberConst.NUM_1));
				if(docFileVO.getFileSource().equals(CaptchaConst.LOCAL)){
					resourceStore.addResource(key, new Resource(FileResourceProvider.NAME, docFileVO.getFileBucket().concat(docFileVO.getFileAddr()), CommonConstant.DEFAULT_TAG));
				} else if (docFileVO.getFileSource().equals(CaptchaConst.AWS)) {
                    String fileEndpoint = docFileVO.getFileEndpoint();
                    int lastIndexOf = fileEndpoint.lastIndexOf(SymbolConst.HTTP_CONCAT);
                    String prefix = fileEndpoint.substring(NumberConst.NUM_0, lastIndexOf + NumberConst.NUM_3);
                    String region = fileEndpoint.substring(lastIndexOf+NumberConst.NUM_3);
                    String fileAddr = docFileVO.getFileAddr().replaceAll(SymbolConst.PATTERN_FORWARD_SLASH, StrUtil.EMPTY);
                    resourceStore.addResource(key, new Resource(URLResourceProvider.NAME, prefix.concat(docFileVO.getFileBucket()).concat(SymbolConst.PERIOD)
                            .concat(region).concat(fileAddr), CommonConstant.DEFAULT_TAG));
				}
			}
		}
	}

}