package com.platform.mesh.mybatis.plus.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.List;

/**
 * @description 分页工具类
 * @author 蝉鸣
 */
public class MPageUtil {


    /**
     * 功能描述:
     * 〈设置分页信息〉
     * @param input input
     * @param clazz clazz
     * @return 正常返回:{@link MPage<T>}
     * @author 蝉鸣
     */
    public static <T>  MPage<T> pageEntityToMPage(PageDTO input, Class<T> clazz) {
        MPage<T> output = new MPage<>();
        output.setCurrent(input.getPageNum());
        if(NumberConst.NUM__1.equals(input.getPageNum())){
            //如果不分页,最大条数为2000
            output.setSize(NumberConst.NUM_2000);
        }else{
            output.setSize(input.getPageSize());
        }
        return output;
    }

    /**
     * 功能描述:
     * 〈设置分页信息〉
     * @param input input
     * @param clazz clazz
     * @return 正常返回:{@link MPage<T>}
     * @author 蝉鸣
     */
    public static <T, E>  MPage<T> convertToPage(MPage<E> input,Class<T> clazz) {
        MPage<T> output = new MPage<>();
        output.setCurrent(input.getCurrent());
        output.setSize(input.getSize());
        output.setTotal(input.getTotal());
        output.setRecords(BeanUtil.copyToList(input.getRecords(), clazz));
        return output;
    }

    /**
     * 功能描述:
     * 〈设置分页信息〉
     * @param input input
     * @return 正常返回:{@link MPage<T>}
     * @author 蝉鸣
     */
    public static <T, E>  MPage<T> convertToPage(MPage<E> input) {
        MPage<T> output = new MPage<>();
        output.setCurrent(input.getCurrent());
        output.setSize(input.getSize());
        output.setTotal(input.getTotal());
        return output;
    }

    /**
     * 功能描述:
     * 〈转换pageVO〉
     * @param input input
     * @param clazz clazz
     * @return 正常返回:{@link MPage<T>}
     * @author 蝉鸣
     */
    public static <T, E> PageVO<T> convertToVO(MPage<E> input, Class<T> clazz) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setCurrent(input.getCurrent());
        pageVO.setSize(input.getSize());
        pageVO.setTotal(input.getTotal());
        pageVO.setPages(PageUtil.totalPage(input.getTotal(), Integer.parseInt(StrUtil.toString(input.getSize()))));
        pageVO.setRecords(BeanUtil.copyToList(input.getRecords(), clazz));
        return pageVO;
    }

    /**
     * 功能描述:
     * 〈转换pageVO〉
     * @param input input
     * @param clazz clazz
     * @return 正常返回:{@link MPage<T>}
     * @author 蝉鸣
     */
    public static <T, E> PageVO<T> convertToVO(PageVO<E> input, Class<T> clazz) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setCurrent(input.getCurrent());
        pageVO.setSize(input.getSize());
        pageVO.setTotal(input.getTotal());
        pageVO.setPages(PageUtil.totalPage(input.getTotal(), Integer.parseInt(StrUtil.toString(input.getSize()))));
        pageVO.setRecords(BeanUtil.copyToList(input.getRecords(), clazz));
        return pageVO;
    }

}
