package com.platform.mesh.utils.http;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.utils.spring.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.lionsoul.ip2region.xdb.LongByteArray;
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description IP工具类
 * @author 蝉鸣
 */
public class IpUtil {

	private static final Logger log = LoggerFactory.getLogger(IpUtil.class);

	private static final String UNKNOWN = "unknown";

	// IP数据库文件路径
	private static final String DB_FILE_PATH = "ip/ip2region_v4.xdb";
	// 使用 AtomicReference 保证原子性
	//查询器
	private static final AtomicReference<Searcher> SEARCHER_REF = new AtomicReference<>();
	//向量索引
	private static final AtomicReference<byte[]> VECTOR_INDEX_REF = new AtomicReference<>();

	/**
	 * 初始化（只执行一次）
	 */
	static {
		initSearcher();
	}
	/**
	 * 获取客户端IP
	 * @return IP
	 */
	public static String getIpAddr(String ip) {
		return getIpAddrByLocal(ip);
	}

	/**
	 * 获取访问代理
	 * @return IP
	 */
	public static String getIpAgent() {
		HttpServletRequest request = ServletUtil.getRequestInst();
		return request != null ? request.getHeader(HttpHeaders.USER_AGENT) : UNKNOWN;
	}

	/**
	 * 获得服务器IP
	 * @return String
	 */
	public static String getHostIp() {
		return getHostIp(ServletUtil.getRequestInst());
	}

	/**
	 * 获取客户端IP地址
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static String getHostIp(HttpServletRequest request) {

		if (request == null) {
			return UNKNOWN;
		}

		String ip = null;
		String ipAddresses = null;

		// 依次尝试各种代理头
		String[] headers = {
				// X-Forwarded-For：Squid 服务代理
				HttpConst.X_FORWARDED_FOR,
				// Proxy-Client-IP：apache 服务代理
				HttpConst.PROXY_CLIENT_IP,
				// WL-Proxy-Client-IP：weblogic 服务代理
				HttpConst.WL_PROXY_CLIENT_IP,
				// HTTP_CLIENT_IP：有些代理服务器
				HttpConst.HTTP_CLIENT_IP,
				// X-Real-IP：nginx服务代理
				HttpConst.X_REAL_IP
		};
		for (String header : headers) {
			ipAddresses = request.getHeader(header);
			if (CharSequenceUtil.isNotBlank(ipAddresses) && !UNKNOWN.equalsIgnoreCase(ipAddresses)) {
				break;
			}
		}
		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (CharSequenceUtil.isNotBlank(ipAddresses)) {
			String[] ips = ipAddresses.split(SymbolConst.COMMA);
			for (String candidate : ips) {
				candidate = candidate.trim();
				if (CharSequenceUtil.isNotBlank(candidate) && !UNKNOWN.equalsIgnoreCase(candidate)) {
					ip = candidate;
					break;
				}
			}
		}
		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ObjectUtil.isEmpty(ip) || Objects.requireNonNull(ip).isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		return HttpConst.DEFAULT_MAC.equals(ip) ? HttpConst.DEFAULT_IP : ip;
	}

	/**
	 * 初始化
	 * @return String
	 */
	private static void initSearcher() {
		// 从resources目录加载数据库文件
		try(InputStream is = IpUtil.class.getClassLoader().getResourceAsStream("ip/ip2region.xdb")){
			// 检查输入流是否为空
			if (is == null) {
				throw new BaseException("IP数据库文件不存在");
			}
			LongByteArray longByteArray = Searcher.loadContentFromInputStream(is);
			// 创建Searcher实例
			// 原子性创建 Searcher
			SEARCHER_REF.updateAndGet(existing -> {
				if (existing != null) return existing;
				try {
					return Searcher.newWithBuffer(Version.IPv4, longByteArray);
				} catch (IOException e) {
					throw new BaseException(e);
				}
			});
		}catch (IOException e){
			throw new BaseException("IP数据库初始化失败: " + e.getMessage(), e);
		}
	}

	/**
	 * 使用离线ip2region解析ip所属地址
	 * @return String
	 */
	public static String getIpAddrByLocal(String ip) {
		try {
			return SEARCHER_REF.get().search(ip);
		} catch (Exception e) {
			log.debug("IP地址解析失败, ip={}", ip, e);
			return StrUtil.EMPTY;
		}
	}

}
