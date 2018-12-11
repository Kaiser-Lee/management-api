package com.iwhalecloud.retail.oms.rop;

import com.iwhalecloud.retail.oms.properties.RopProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.DefaultZteRopClient;

@Service
public class ZteRopClient {

	@Autowired
	private RopProperties ropProperties;
	
	// 调用电商平台
	@SuppressWarnings("rawtypes")
	public <T extends ZteResponse> T execute(ZteRequest request,
			Class<T> respclass) throws ApiException {

		DefaultZteRopClient client = new DefaultZteRopClient(ropProperties.getPlatUrl(),"wssmall_mm",AppKeyEnum.APP_KEY_WSSMALL_MM.getAppSec(),"1.0");
		return client.execute(request, respclass);
	}
	
}
