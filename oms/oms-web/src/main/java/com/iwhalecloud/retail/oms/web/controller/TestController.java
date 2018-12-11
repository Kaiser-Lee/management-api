package com.iwhalecloud.retail.oms.web.controller;

import com.iwhalecloud.retail.oms.entity.MemberBinding;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zte.params.member.resp.MemberLoginResp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.entity.UserCopyTest;
import com.iwhalecloud.retail.oms.service.MemberService;
import com.iwhalecloud.retail.oms.service.TestService;
//import com.iwhalecloud.retail.order.entity.Order;
//import com.iwhalecloud.retail.order.service.OrderService;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController extends BaseController {

	@Reference
	private TestService testService;

	@Reference
	private MemberService memberService;
	
//	@Reference
//	private OrderService orderService;

	@RequestMapping("")
    public ResultVO<MemberLoginResp> test() {
		MemberLoginResp resp = testService.testRop();
		return super.successResultVO(resp);
	}


	@RequestMapping("/s")
	public List<UserCopyTest> tests() {

		String user=testService.testH2Mapper();
		log.info(user);
		List<UserCopyTest> userCopyTest= JSON.parseArray(user,UserCopyTest.class);

		return userCopyTest;
	}

	@RequestMapping("/getMemberBinding")
	public MemberBinding getMemberBinding() {

		MemberBinding memberBinding = memberService.getMemberBinding("123");
//		memberService.addMemberBinding(new MemberBinding());
		log.info("getMemberBinding");
		return null;
	}

//	@RequestMapping("/upload")
//	public String testUpoload(){
//		return  testService.upload();
//	}
	
	
//	 @ApiOperation(value = "查询订单", notes = "根据orderId，查询order对象")
//	 @ApiImplicitParams({
//         @ApiImplicitParam(name = "订单ID", value = "orderId", paramType = "path", required = true, dataType = "String")
//	 })
//	 @ApiResponses({
//	         @ApiResponse(code=400,message="请求参数没填好"),
//	         @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//	 })
//	@RequestMapping(value = "/get/{orderId}", method = RequestMethod.GET)
//	public Order getOrder(@PathVariable String orderId) {
//		return orderService.getOrder(orderId);
//	}
}
