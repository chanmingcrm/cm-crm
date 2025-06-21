package com.platform.mesh.utils.file;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "文件响应类型枚举",enumAsRef = true)
public enum FileContentTypeEnum  implements BaseEnum<FileContentTypeEnum, Integer> {
    DEFAULT(0,"default","application/octet-stream"),
    PNG(1,"png", "image/png"),
    JPEG(2,"jpeg", "image/jpeg"),
    JPG(3,"jpg", "image/jpeg"),
    GIF(4,"gif", "image/gif"),
    WBMP(5,"wbmp", "image/vnd.wap.wbmp"),
    TIFF(6,"tiff", "image/tiff"),
    JFIF(7,"jfif", "image/jpeg"),
    TIF(8,"tif", "image/tiff"),
    FAX(9,"fax", "image/fax"),
    JPE(10,"jpe", "image/jpeg"),
    NET(11,"net", "image/pnetvue"),
    RP(12,"rp", "image/vnd.rn-realpix"),
    ICO(13,"ico", "image/x-icon");
    ;

    private final Integer value;

    private final String desc;

    @Getter
    private final String type;

    FileContentTypeEnum(Integer value, String desc, String type) {
        this.value = value;
        this.desc = desc;
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

}
