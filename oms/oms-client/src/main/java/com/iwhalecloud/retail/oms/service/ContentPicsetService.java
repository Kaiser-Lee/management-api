package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentPicsetDTO;
import com.iwhalecloud.retail.oms.entity.ContentPicset;
import java.util.List;

public interface ContentPicsetService{

    /**
     * 查询推广内容信息
     * @param contentId
     * @return
     */
    public List<ContentPicsetDTO> queryContentPicsetList(Long contentId);

}