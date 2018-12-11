package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date: 2018/10/30 14:31
 * @Description:
 */

@Data
public class CloudShelfDetailDTO implements Serializable {
	/**
	 * 表名常量
	 */
	public static final String TNAME = "cloud_shelf_detail";
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id; //id

	@ApiModelProperty(value = "创建时间")
	private Date gmtCreate; //创建时间

	@ApiModelProperty(value = "修改时间")
	private Date gmtModified; //修改时间

	@ApiModelProperty(value = "创建人")
	private String creator; //创建人

	@ApiModelProperty(value = "修改人")
	private String modifier; //修改人

	@ApiModelProperty(value = "是否删除")
	private int isDeleted; //是否删除：0未删、1删除

	@ApiModelProperty(value = "云货架编号")
	private String cloudShelfNumber; //云货架编号

	@ApiModelProperty(value = "货架类目id")
	private String shelfCategoryId; //货架类目id:推荐、套餐、手机、智能家居、配件

	@ApiModelProperty(value = "运营位ID")
	private String operatingPositionId; //运营位ID

	@ApiModelProperty(value = "运营位名称")
	private String operatingPositionName; //运营位名称

	@ApiModelProperty(value = "运营位归属")
	private String operatingPositionAdscription; //运营位归属:省、市

	@ApiModelProperty(value = "运营位类型")
	private String operatingPositionType; //运营位类型

	@ApiModelProperty(value = "运营位属性")
	private String operatingPositionProperty; //运营位属性

	@ApiModelProperty(value = "运营位过期时间")
	private Date operatingPositionExpiryTime; //运营位过期时间

	@ApiModelProperty(value = "运营位播放方式: 0单图，N时间间隔大小，单位是秒")
	private Integer operatingPositionPlayMode; //运营位播放方式: 0单图，N时间间隔大小，单位是秒

	@ApiModelProperty(value = "运营位排序")
	private Integer operatingPositionSequence; //运营位排序

	@ApiModelProperty(value = "是否推荐")
	private int isRecommended; //是否推荐：0否、1是

	@ApiModelProperty(value = "默认运营位ID")
	private String defaultOperatingPositionId;//默认运营位ID

	private OperatingPositionBindDTO operatingPositionBindDTO;

	public static String getTNAME() {
		return TNAME;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCloudShelfNumber() {
		return cloudShelfNumber;
	}

	public void setCloudShelfNumber(String cloudShelfNumber) {
		this.cloudShelfNumber = cloudShelfNumber;
	}

	public String getShelfCategoryId() {
		return shelfCategoryId;
	}

	public void setShelfCategoryId(String shelfCategoryId) {
		this.shelfCategoryId = shelfCategoryId;
	}

	public String getOperatingPositionId() {
		return operatingPositionId;
	}

	public void setOperatingPositionId(String operatingPositionId) {
		this.operatingPositionId = operatingPositionId;
	}

	public String getOperatingPositionName() {
		return operatingPositionName;
	}

	public void setOperatingPositionName(String operatingPositionName) {
		this.operatingPositionName = operatingPositionName;
	}

	public String getOperatingPositionAdscription() {
		return operatingPositionAdscription;
	}

	public void setOperatingPositionAdscription(String operatingPositionAdscription) {
		this.operatingPositionAdscription = operatingPositionAdscription;
	}

	public String getOperatingPositionType() {
		return operatingPositionType;
	}

	public void setOperatingPositionType(String operatingPositionType) {
		this.operatingPositionType = operatingPositionType;
	}

	public String getOperatingPositionProperty() {
		return operatingPositionProperty;
	}

	public void setOperatingPositionProperty(String operatingPositionProperty) {
		this.operatingPositionProperty = operatingPositionProperty;
	}

	public Date getOperatingPositionExpiryTime() {
		return operatingPositionExpiryTime;
	}

	public void setOperatingPositionExpiryTime(Date operatingPositionExpiryTime) {
		this.operatingPositionExpiryTime = operatingPositionExpiryTime;
	}

	public Integer getOperatingPositionPlayMode() {
		return operatingPositionPlayMode;
	}

	public void setOperatingPositionPlayMode(Integer operatingPositionPlayMode) {
		this.operatingPositionPlayMode = operatingPositionPlayMode;
	}

	public int getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(int isRecommended) {
		this.isRecommended = isRecommended;
	}

	public String getDefaultOperatingPositionId() {
		return defaultOperatingPositionId;
	}

	public void setDefaultOperatingPositionId(String defaultOperatingPositionId) {
		this.defaultOperatingPositionId = defaultOperatingPositionId;
	}

	public OperatingPositionBindDTO getOperatingPositionBindDTO() {
		return operatingPositionBindDTO;
	}

	public void setOperatingPositionBindDTO(OperatingPositionBindDTO operatingPositionBindDTO) {
		this.operatingPositionBindDTO = operatingPositionBindDTO;
	}
}

