package com.iwhalecloud.retail.oms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 云货架终端设备连接日志
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("CLOUD_DEVICE_LOG")
@ApiModel(value = "对应模型CLOUD_DEVICE_LOG, 对应实体CloudDeviceLog类")
public class CloudDeviceLog implements Serializable {
    /**表名常量*/
    public static final String TNAME = "CLOUD_DEVICE_LOG";
  	private static final long serialVersionUID = 1L;


  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;

  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;

  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;

  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String creator;

  	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private java.lang.String modifier;

  	/**
  	 * 是否删除：0未删、1删除
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除")
  	private java.lang.Integer isDeleted;

  	/**
  	 * 设备编号
  	 */
	@ApiModelProperty(value = "设备编号")
  	private java.lang.String cloudDeviceNumber;

  	/**
  	 * 连接时间
  	 */
	@ApiModelProperty(value = "连接时间")
  	private java.util.Date onlineTime;

  	/**
  	 * 离线时间
  	 */
	@ApiModelProperty(value = "离线时间")
  	private java.util.Date offlineTime;

  	/**
  	 * 工作时长：单位秒
  	 */
	@ApiModelProperty(value = "工作时长：单位秒")
  	private java.lang.Long workTime;

    /**
     * 所属城市
     */
    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity;

    /**
     * 所属城区
     */
    @ApiModelProperty(value = "所属城区")
    private String adscriptionCityArea;

    /**
     * 所属厅店ID
     */
    @ApiModelProperty(value = "所属厅店ID")
    private String adscriptionShopId;
  	//属性 end


  	public static enum FieldNames{
        /** ID */
        id,
        /** 创建时间 */
        gmtCreate,
        /** 修改时间 */
        gmtModified,
        /** 创建人 */
        creator,
        /** 修改人 */
        modifier,
        /** 是否删除：0未删、1删除 */
        isDeleted,
        /** 设备编号 */
        cloudDeviceNumber,
        /** 连接时间 */
        onlineTime,
        /** 离线时间 */
        offlineTime,
        /** 工作时长：单位秒 */
        workTime,
        /**所属城市*/
        adscriptionCity,
        /**所属城区*/
        adscriptionCityArea,
        /**所属厅店ID*/
        adscriptionShopId
    }

	

}
