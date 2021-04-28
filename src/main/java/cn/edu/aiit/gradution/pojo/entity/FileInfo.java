package cn.edu.aiit.gradution.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="File对象", description="")
@TableName("file")
public class FileInfo implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文件Id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "fileId", type = IdType.INPUT)
    private Long fileId;

    @ApiModelProperty(value = "'文件名'")
    @TableField("fileName")
    private String fileName;

    @ApiModelProperty(value = "'admin表中的id'")
    @TableField("publisherId")
    private Integer publisherId;

    @ApiModelProperty(value = "'文件的上传时间'")
    @TableField("uploadTime")
    private Date uploadTime;

    @ApiModelProperty(value = "'文件浏览量 默认0'")
    @TableField("viewNum")
    private Integer viewNum;

    @ApiModelProperty(value = "文件访问路径")
    @TableField("fileUrl")
    private String fileUrl;

    @ApiModelProperty(value = "'文件下载量 默认0'")
    @TableField("downloadNum")
    private Integer downloadNum;

    @ApiModelProperty(value = "'0 已删除，1 可用 默认 1'")
    @TableField(value = "status", fill = FieldFill.INSERT)
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmtCreate", fill = FieldFill.INSERT)
    private Date gmtCreate;


    @TableField(value = "gmtModified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
