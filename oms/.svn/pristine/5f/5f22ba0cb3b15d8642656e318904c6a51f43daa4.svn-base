package com.iwhalecloud.retail.oms.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.resquest.StaffBindingAdminUserReq;
import com.iwhalecloud.retail.oms.service.SystemService;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.PartnerStaffQryByPartnerIdResp;
import com.iwhalecloud.retail.partner.service.PartnerStaffService;
import com.ztesoft.net.eop.resource.model.AdminUser;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import params.adminuser.req.AdminAddReq;
import params.adminuser.resp.AdminAddResp;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/partnerStaff")
public class PartnerStaffController {

    @Reference
    private PartnerStaffService partnerStaffService;
    @Reference
    private SystemService systemService;


    @ApiOperation(value = "新增分销商店员", notes = "传入PartnerStaffAddReq对象，进行添加操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/add")
    public ResultVO<PartnerStaffDTO> addPartnerStaff(@RequestBody @ApiParam(value = "PartnerStaffAddReq", required = true) PartnerStaffAddReq req) {

        if (StringUtils.isEmpty(req.getPartnerShopId())) {
            return ResultVO.error("厅店ID不能为空！");
        }
        if (StringUtils.isEmpty(req.getPhoneNo())) {
            return ResultVO.error("员工员工手机号不能为空！");
        }
        if (StringUtils.isEmpty(req.getStaffName())) {
            return ResultVO.error("员工名称不能为空！");
        }
        PartnerStaffDTO partnerStaffDTO = partnerStaffService.addPartnerStaff(req);
        if (partnerStaffDTO == null)
            return ResultVO.error("添加员工信息失败");
        return ResultVO.success(partnerStaffDTO);
    }


    @ApiOperation(value = "店员绑定系统工号", notes = "传入PartnerStaffAddReq对象，进行添加操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/bindingAdminUser")
    public ResultVO bindingAdminUser(@RequestBody @ApiParam(value = "PartnerStaffAddReq", required = true) StaffBindingAdminUserReq req) {

        if (StringUtils.isEmpty(req.getStaffId())) {
            return ResultVO.error("员工ID不能为空！");
        }
        AdminAddReq adminAddReq = new AdminAddReq();
        AdminUser adminUser = new AdminUser();
        adminUser.setState(1); //
        adminUser.setParuserid(req.getParentUserId());
        adminUser.setPassword(req.getPassWord());
        adminUser.setUsername(req.getUserName());
        adminUser.setRealname(req.getRealName());
        adminUser.setFounder(OmsConst.CURR_FOUNDER6); // 分销商类型 founder 先写死3
        adminUser.setRelcode(req.getStaffId());
        adminAddReq.setAdminUser(adminUser);
        adminAddReq.setUserSessionId(req.getUserSessionId());
        try {
            AdminAddResp resp = systemService.addAdmin(adminAddReq);
            // 失败统一返回
            if (!resp.getError_code().equals(OmsCommonConsts.RESULE_CODE_SUCCESS)) {
                return ResultVO.error("员工绑定系统工号失败!");
            }
            return ResultVO.success(resp.getError_msg(), resp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
    }


    @ApiOperation(value = "编辑分销商店员", notes = "传入PartnerStaffUpdateReq对象，进行编辑操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/edit")
    public ResultVO<Integer> editPartnerStaff(@RequestBody @ApiParam(value = "PartnerStaffUpdateReq", required = true) PartnerStaffUpdateReq req) {

        //TODO 要限定哪些字段不能改
        if (StringUtils.isEmpty(req.getStaffId())) {
            return ResultVO.error("员工ID不能为空！");
        }
        int result = partnerStaffService.editPartnerStaff(req);
        if (result == 0)
            return ResultVO.error("编辑员工信息失败");
        return ResultVO.success(result);
    }

    @ApiOperation(value = "批量删除分销商店员", notes = "传入PartnerStaffDeleteReq对象的staffIds，进行批量删除操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/delete")
    public ResultVO<Integer> deletePartnerStaffs(@RequestBody @ApiParam(value = "PartnerStaffDeleteReq", required = true) PartnerStaffDeleteReq req) {

        if (req.getStaffIds() == null
                || req.getStaffIds().size() == 0) {
            return ResultVO.error("员工ID不能为空！");
        }
        try {
            int result = partnerStaffService.deletePartnerStaff(req);
            if (result == 0)
                return ResultVO.error("删除员工信息失败");
            return ResultVO.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
    }

    @ApiOperation(value = "查询店员列表（带出店名的）", notes = "可以根据厅店ID、员工姓名、店名条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/qryList")
    public ResultVO<Page<PartnerStaffDTO>> qryPartnerStaffList(
            @RequestBody @ApiParam(value = "PartnerStaffPageReq", required = true) PartnerStaffPageReq req) {
        try {
            Page<PartnerStaffDTO> page = partnerStaffService.getPartnerStaffList(req);
            return ResultVO.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());

        }
    }

    @ApiOperation(value = "查询分销商的店员列表）", notes = "根据分销商ID查询分销商的店员列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/qryListByPartnerId")
    public ResultVO<List<PartnerStaffQryByPartnerIdResp>> qryPartnerStaffListByPartnerId(
            @RequestBody @ApiParam(value = "PartnerStaffPageReq", required = true) PartnerStaffQryByPartnerIdReq req) {
        if (StringUtils.isEmpty(req.getPartnerId())) {
            return ResultVO.error("分销商ID不能为空！");
        }
        List<PartnerStaffQryByPartnerIdResp> list = partnerStaffService.getStaffListByPartnerId(req.getPartnerId());
        return ResultVO.success(list);
    }

}
