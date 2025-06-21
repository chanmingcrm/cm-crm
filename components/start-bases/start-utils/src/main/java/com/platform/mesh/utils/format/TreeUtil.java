package com.platform.mesh.utils.format;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.application.domain.vo.TreeVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description 工具类
 * @author 蝉鸣
 */
public class TreeUtil {


	/**
	 * 功能描述:
	 * 〈封装树形VO〉
	 * @param allList allList
	 * @param parentId parentId
	 * @return 正常返回:{@link List<T>}
	 * @author 蝉鸣
	 */
	public static <T extends TreeVO<T>> List<T> packageTree(Long parentId,List<T> allList) {
		if(CollUtil.isEmpty(allList)){
			return CollUtil.newArrayList();
		}
		//封装树形结构
		return allList.stream()
				.filter(item -> item.getParentId().equals(parentId))
				.peek(item -> item.setChildren(getChildren(item.getId(), allList)))
				.collect(Collectors.toList());
	}

	/**
	 * 功能描述:
	 * 〈递归查询子节点〉
	 * @param parentId parentId
	 * @param allList allList
	 * @return 正常返回:{@link List<T>}
	 * @author 蝉鸣
	 */
	private static <T extends TreeVO<T>> List<T> getChildren(Long parentId, List<T> allList) {
		return allList.stream().filter(item -> Objects.equals(item.getParentId(), parentId)).peek(
				(item) -> item.setChildren(getChildren(item.getId(), allList))
		).collect(Collectors.toList());
	}

}
