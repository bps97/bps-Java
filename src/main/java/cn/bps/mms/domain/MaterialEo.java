package cn.bps.mms.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


@Data
public class MaterialEo {

    /**
     * 全称
     */
    @ExcelProperty("全称")
    private String name;

    /**
     * 分类
     */
    @ExcelProperty("分类")
    private String category;

    /**
     * 仓库
     */
    @ExcelProperty("仓库")
    private String repository;

    /**
     * 专业线
     */
    @ExcelProperty("专业线")
    private String specialLine;

    /**
     * 数量
     */
    @ExcelProperty("数量")
    private Integer count;

    /**
     * 状态
     */
    @ExcelProperty("状态")
    private String status;


}
