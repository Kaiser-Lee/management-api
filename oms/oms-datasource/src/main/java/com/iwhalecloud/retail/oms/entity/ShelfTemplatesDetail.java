package com.iwhalecloud.retail.oms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ShelfTemplatesDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("SHELF_TEMPLATES_DETAIL")
@ApiModel(value = "对应模型SHELF_TEMPLATES_DETAIL, 对应实体ShelfTemplatesDetail类")
public class ShelfTemplatesDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "SHELF_TEMPLATES_DETAIL";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "id")
  	private java.lang.Long id;
  	
  	/**
  	 * gmtCreate
  	 */
	@ApiModelProperty(value = "gmtCreate")
  	private java.util.Date gmtCreate;
  	
  	/**
  	 * gmtModified
  	 */
	@ApiModelProperty(value = "gmtModified")
  	private java.util.Date gmtModified;
  	
  	/**
  	 * creator
  	 */
	@ApiModelProperty(value = "creator")
  	private java.lang.String creator;
  	
  	/**
  	 * modifier
  	 */
	@ApiModelProperty(value = "modifier")
  	private java.lang.String modifier;
  	
  	/**
  	 * isDeleted
  	 */
	@ApiModelProperty(value = "isDeleted")
  	private java.lang.Integer isDeleted;
  	
  	/**
  	 * defCategoryId
  	 */
	@ApiModelProperty(value = "defCategoryId")
  	private java.lang.String defCategoryId;
  	
  	/**
  	 * shelfTemplatesNumber
  	 */
	@ApiModelProperty(value = "shelfTemplatesNumber")
  	private java.lang.String shelfTemplatesNumber;
  	
  	/**
  	 * operatingPositionId
  	 */
	@ApiModelProperty(value = "operatingPositionId")
  	private java.lang.String operatingPositionId;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** id. */
		id("id","ID"),
		
		/** gmtCreate. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** gmtModified. */
		gmtModified("gmtModified","GMT_MODIFIED"),
		
		/** creator. */
		creator("creator","CREATOR"),
		
		/** modifier. */
		modifier("modifier","MODIFIER"),
		
		/** isDeleted. */
		isDeleted("isDeleted","IS_DELETED"),
		
		/** defCategoryId. */
		defCategoryId("defCategoryId","DEF_CATEGORY_ID"),
		
		/** shelfTemplatesNumber. */
		shelfTemplatesNumber("shelfTemplatesNumber","SHELF_TEMPLATES_NUMBER"),
		
		/** operatingPositionId. */
		operatingPositionId("operatingPositionId","OPERATING_POSITION_ID");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

}
