package com.iwhalecloud.retail.oms.web.controller.rop;

import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.service.dubbo.rop.SpecService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zte.params.spec.req.SpecAddReq;
import zte.params.spec.req.SpecDeleteReq;
import zte.params.spec.req.SpecGetReq;
import zte.params.spec.req.SpecUpdateReq;
import zte.params.spec.req.SpecValueAddReq;
import zte.params.spec.req.SpecValueGetReq;
import zte.params.spec.req.SpecValueListReq;
import zte.params.spec.resp.SpecGetResp;
import zte.params.spec.resp.SpecListResp;
import zte.params.spec.resp.SpecOperatorResp;
import zte.params.spec.resp.SpecValueGetResp;
import zte.params.spec.resp.SpecValueListResp;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author mzl
 * @date 2018/10/25
 */
@RestController
@RequestMapping("/api/rop/spec")
@Slf4j
public class SpecROPController {

	@Reference
    private SpecService specService;

    @GetMapping(value="listSpec")
    public ResultVO listSpec() {
        SpecListResp resp = specService.listSpec();
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getSpecs());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="addSpec")
    public ResultVO addSpec(@RequestBody SpecAddReq req) {
        log.info("SpecController.addSpec req={}", req);
        SpecOperatorResp resp = specService.addSpec(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="updateSpec")
    public ResultVO updateSpec(@RequestBody SpecUpdateReq req) {
        log.info("SpecController.updateSpec req={}", req);
        SpecOperatorResp resp = specService.updateSpec(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="deleteSpec")
    public ResultVO deleteSpec(@RequestBody SpecDeleteReq req) {
        log.info("SpecController.deleteSpec req={}", req);
        SpecOperatorResp resp = specService.deleteSpec(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="getSpec")
    public ResultVO getSpec(@RequestParam(value = "specId") String specId) {
        log.info("SpecController.getSpec specId={}", specId);
        SpecGetReq req = new SpecGetReq();
        req.setSpec_id(specId);
        SpecGetResp resp = specService.getSpec(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getMap());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="addSpecValue")
    public ResultVO addSpecValue(@RequestBody SpecValueAddReq req) {
        log.info("SpecController.addSpec req={}", req);
        SpecOperatorResp resp = specService.addSpecValue(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="listSpecValue")
    public ResultVO listSpecValue(@RequestParam(value = "specId") String specId) {
        log.info("SpecController.listSpecValue specId={}", specId);
        SpecValueListReq req = new SpecValueListReq();
        req.setSpec_id(specId);
        SpecValueListResp resp = specService.listSpecValue(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getSpecValueList());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="getSpecValue")
    public ResultVO getSpecValue(@RequestParam(value = "valueId") String valueId) {
        log.info("SpecController.getSpecValue valueId={}", valueId);
        SpecValueGetReq req = new SpecValueGetReq();
        req.setValue_id(valueId);
        SpecValueGetResp resp = specService.getSpecValue(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getMap());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }
}
