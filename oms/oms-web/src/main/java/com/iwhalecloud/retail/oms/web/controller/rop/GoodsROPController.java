package com.iwhalecloud.retail.oms.web.controller.rop;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.dto.AccMbrDTO;
import com.iwhalecloud.retail.goods.dto.CommentsPageListDTO;
import com.iwhalecloud.retail.goods.dto.GoodsContractDTO;
import com.iwhalecloud.retail.goods.dto.GoodsRelDTO;
import com.iwhalecloud.retail.goods.dto.resp.GoodsRelContractResp;
import com.iwhalecloud.retail.goods.dto.resp.RecommendGoodsInfoQueryResp;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsContractService;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsRelService;
import com.iwhalecloud.retail.goods.service.dubbo.rop.GoodsCatsService;
import com.iwhalecloud.retail.goods.service.dubbo.rop.GoodsService;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.service.ContentBaseService;
import com.iwhalecloud.retail.oms.service.OperatingPositionBindService;
import com.iwhalecloud.retail.oms.web.utils.ResponseComUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.support.GoodsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import zte.params.comments.req.CommentsPageListReq;
import zte.params.goods.req.*;
import zte.params.goods.resp.*;
import zte.params.goodscats.req.ComplexGoodsGetReq;
import zte.params.goodscats.resp.ComplexGoodsGetResp;
import zte.params.goodstype.req.GoodsTypeListReq;
import zte.params.goodstype.resp.GoodsTypeListResp;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mzl
 * @date 2018/10/8
 */
@RestController
@RequestMapping("/api/rop/goods")
@Slf4j
public class GoodsROPController {

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

	@Reference
    private GoodsService goodsService;

    @Reference
    private GoodsRelService goodsRelService;

    @Reference
    private GoodsCatsService goodsCatsService;

    @Reference
    private OperatingPositionBindService operatingPositionBindService;

    @Reference
    private ContentBaseService contentBaseService;

    @Reference
    private GoodsContractService goodsContractService;

    @GetMapping(value="listGoods")
    public ResultVO listGoods(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam
        (value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "catId", required = false)
        String catId, @RequestParam(value = "searchKey", required = false) String searchKey, @RequestParam(value = "marketEnable",
        required = false) String marketEnable, @RequestParam(value = "adscriptionShopId", required = false) String adscriptionShopId) {
        log.info("GoodsController.listGoods pageNo={}, pageSize={}, catId={}, searchKey={}, marketEnable={}", pageNo, pageSize, catId, searchKey, marketEnable);
        GoodsPageListReq req = new GoodsPageListReq();
        if (!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)) {
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
        }
        if (!StringUtils.isEmpty(adscriptionShopId)) {
            List<String> goodsIdList = operatingPositionBindService.getGoodsIdsByAdscriptionShopId(adscriptionShopId);
            if (!CollectionUtils.isEmpty(goodsIdList)) {
                String ids = goodsIdList.get(0);
                if (goodsIdList.size() > 1) {
                    for (int i = 1; i < goodsIdList.size(); i++) {
                        ids += "," + goodsIdList.get(i);
                    }
                }
                req.setIds(ids);
            } else {
                GoodsPageListResp resp = new GoodsPageListResp();
                Page page = new Page();
                page.setResult(Lists.newArrayList());
                resp.setGoodsPage(page);
                return ResultVO.success(ResponseComUtil.page(pageNo,resp.getGoodsPage()));
            }
        }
        req.setCat_id(catId);
        req.setSearch_key(searchKey);
        req.setMarket_enable(marketEnable);
        req.setAudit_state(OmsConst.AUDIT_STATE);
        GoodsPageListResp resp = goodsService.queryGoodsForPage(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            List<Goods> goodsList = (List<Goods>)resp.getGoodsPage().getData();
            if (!CollectionUtils.isEmpty(goodsList)) {
                for (Goods goods : goodsList) {
                    setFullImageFileUrl(goods, true);
                }
            }
            return ResultVO.success(ResponseComUtil.page(pageNo,resp.getGoodsPage()));
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="queryGoodsDetail")
    public ResultVO queryGoodsDetail(@RequestParam(value = "goodsId") String goodsId) {
        log.info("GoodsController.queryGoodsDetail goodsId={}", goodsId);
        GoodsIntroReq req = new GoodsIntroReq();
        req.setGoods_id(goodsId);
        GoodsIntroResp resp = goodsService.queryGoodsDetail(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            if (resp.getGoods() != null) {
                Goods goods = resp.getGoods();
                setFullImageFileUrl(goods, true);
            }
            return ResultVO.success(resp.getGoods());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    private void setFullImageFileUrl(Goods goods, boolean flag) {
        if (!StringUtils.isEmpty(goods.getPc_image_file())) {
            goods.setPc_image_file(fullImageUrl(goods.getPc_image_file(),dfsShowIp,flag));
        }
        if (!StringUtils.isEmpty(goods.getPc_image_default())) {
            goods.setPc_image_default(fullImageUrl(goods.getPc_image_default(),dfsShowIp,flag));
        }
        if (!StringUtils.isEmpty(goods.getMbl_image_file())) {
            goods.setMbl_image_file(fullImageUrl(goods.getMbl_image_file(),dfsShowIp,flag));
        }
        if (!StringUtils.isEmpty(goods.getMbl_image_default())) {
            goods.setMbl_image_default(fullImageUrl(goods.getMbl_image_default(),dfsShowIp,flag));
        }
        if (!StringUtils.isEmpty(goods.getImage_file())) {
            goods.setImage_file(fullImageUrl(goods.getImage_file(),dfsShowIp,flag));
        }
        if (!StringUtils.isEmpty(goods.getImage_default())) {
            goods.setImage_default(fullImageUrl(goods.getImage_default(),dfsShowIp,flag));
        }
    }

    @GetMapping(value="listGoodsComments")
    public ResultVO listGoodsComments(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam
            (value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "goodsId") String goodsId,
            @RequestParam(value = "commentType") String commentType) {
        log.info("GoodsController.listGoodsComments pageNo={}, pageSize={}, goodsId={}, commentType={}", pageNo, pageSize, goodsId, commentType);
        CommentsPageListReq req = new CommentsPageListReq();
        if (!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)) {
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
        }
        req.setGoods_id(goodsId);
        req.setComment_type(commentType);
        CommentsPageListDTO resp = goodsService.listGoodsComments(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp);
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="listGoodsSpec")
    public ResultVO listGoodsSpec(@RequestParam(value = "goodsId") String goodsId) {
        log.info("GoodsController.listGoodsSpec goodsId={}", goodsId);
        GoodsSpecListReq req = new GoodsSpecListReq();
        req.setGoods_id(goodsId);
        GoodsSpecListResp resp = goodsService.listGoodsSpec(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getSpecs());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="goodsTypeList")
    public ResultVO goodsTypeList() {
        GoodsTypeListReq req = new GoodsTypeListReq();
        GoodsTypeListResp resp = goodsService.getTypeList(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getTypeList());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="addGoods")
    public ResultVO addGoods(@RequestBody GoodsAddReq req, HttpServletRequest request) {
        log.info("GoodsController.addGoods req={}", req);
        ResultVO resultVO = getUserId(request);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        String id = (String)resultVO.getResultData();
        req.setUser_id(id);
        if (StringUtils.isEmpty(req.getSpecValues()) || req.getSpecValues().length == 0) {
            return ResultVO.errorEnum(ResultCodeEnum.SPEC_VALUES_CAN_NOT_BE_NULL);
        }
        if (!StringUtils.isEmpty(req.getPc_image_file())) {
            req.setPc_image_file(fullImageUrl(req.getPc_image_file(), dfsShowIp, false));
        }
        if (!StringUtils.isEmpty(req.getMbl_image_file())) {
            req.setMbl_image_file(fullImageUrl(req.getMbl_image_file(), dfsShowIp, false));
        }
        setImageDefault(req);
        GoodsAddResp resp = goodsService.addGoods(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            List<GoodsReq> goodsReqList = req.getRecommendList();
            if (!CollectionUtils.isEmpty(goodsReqList)) {
                List<GoodsRelDTO> goodsRelList = Lists.newArrayList();
                for (GoodsReq goodsReq : goodsReqList) {
                    GoodsRelDTO goodsRel = new GoodsRelDTO();
                    goodsRel.setAGoodsId(goodsReq.getGoods_id());
                    goodsRel.setZGoodsId(resp.getGoods_id());
                    goodsRelList.add(goodsRel);
                }
                goodsRelService.addGoodsRel(goodsRelList);
            }
            return ResultVO.success(resp.getGoods_id());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    private ResultVO getUserId(HttpServletRequest request) {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果http请求头没有token则尝试从session中获取
        if (StringUtil.isEmpty(token)) {
            token = (String)request.getSession().getAttribute("TOKEN");
        }
        if (StringUtil.isEmpty(token)) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        // 执行认证
        if (token == null || token.equals("")) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        String id;
        try {
            Map<String, Claim> claims = JWT.decode(token).getClaims();
            id = claims.get("id").asString();
        } catch (Exception ex) {
            log.error("GoodsROPController.getUserId Exception ex={}",ex);
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        if (StringUtils.isEmpty(id)) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        return ResultVO.success(id);
    }

    @PostMapping(value="editGoods")
    public ResultVO editGoods(@RequestBody GoodsEditReq req) {
        log.info("GoodsController.editGoods req={}", req);
        if (!StringUtils.isEmpty(req.getPc_image_file())) {
            req.setPc_image_file(fullImageUrl(req.getPc_image_file(), dfsShowIp, false));
        }
        if (!StringUtils.isEmpty(req.getMbl_image_file())) {
            req.setMbl_image_file(fullImageUrl(req.getMbl_image_file(), dfsShowIp, false));
        }
        GoodsEditResp resp = goodsService.editGoods(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            List<GoodsReq> goodsReqList = req.getRecommendList();
            String goodsId = req.getGoods_id();
            if (!CollectionUtils.isEmpty(goodsReqList)) {
                List<GoodsRelDTO> goodsRelList = Lists.newArrayList();
                for (GoodsReq goodsReq : goodsReqList) {
                    GoodsRelDTO goodsRel = new GoodsRelDTO();
                    goodsRel.setAGoodsId(goodsReq.getGoods_id());
                    goodsRel.setZGoodsId(goodsId);
                    goodsRelList.add(goodsRel);
                }
                goodsRelService.editRecommendGoodsByGoodsId(goodsRelList);
            } else {
                goodsRelService.delRecommendGoodsByGoodsId(goodsId);
            }
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="deleteGoods")
    public ResultVO deleteGoods(@RequestBody GoodsDeleteReq req) {
        log.info("GoodsController.deleteGoods req={}", req);
        GoodsDeleteResp resp = goodsService.deleteGoods(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="queryGoodsByIdsAndName")
    public ResultVO queryGoodsByIdsAndName(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam
            (value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "ids", required = false) String ids, @RequestParam(value = "marketEnable", required = false) String marketEnable) {
        log.info("GoodsController.queryGoodsByIdsAndName pageNo={},pageSize={},name={},ids={},marketEnable={}", pageNo, pageSize, name, ids, marketEnable);
        GoodsPageByIdsReq req = new GoodsPageByIdsReq();
        if (!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)) {
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
        }
        req.setName(name);
        req.setIds(ids);
        if (StringUtils.isEmpty(marketEnable)) {
            req.setMarketEnable(OmsConst.MARKET_ENABLE);
        } else {
            req.setMarketEnable(marketEnable);
        }
        GoodsPageListResp resp = goodsService.queryGoodsByIdsAndName(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(ResponseComUtil.page(req.getPageNo(),resp.getGoodsPage()));
        } else if (ResultCodeEnum.ROP_INVOKE_EXCEPTION.getCode().equals(resp.getError_code())) {
            return ResultVO.error(resp.getError_code(), resp.getError_msg());
        }
        else {
            return ResultVO.successMessage("查询数据为空");
        }
    }

    @PostMapping(value="updateMarketEnable")
    public ResultVO updateMarketEnable(@RequestBody UpdateMarketEnableReq req, HttpServletRequest request) {
        log.info("GoodsController.updateMarketEnable req={}", req);
        UpdateMarketEnableResp resp = goodsService.updateMarketEnable(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            ResultVO resultVO = getUserId(request);
            if (!resultVO.isSuccess()) {
                return resultVO;
            }
            String userId = (String)resultVO.getResultData();
            try {
                boolean flag = false;
                if (OmsConst.MARKET_ENABLE_ZERO.equals(req.getMarket_enable())) {
                    flag = contentBaseService.addContentPic(req.getGoods_id(), OmsConst.ActionTypeEnum.DELETE.getCode(), userId);
                } else if (OmsConst.MARKET_ENABLE.equals(req.getMarket_enable())) {
                    flag =contentBaseService.addContentPic(req.getGoods_id(), OmsConst.ActionTypeEnum.ADD.getCode(), userId);
                }
                log.info("updateMarketEnable req={},flag={}", JSON.toJSONString(req),flag);
            } catch (Exception ex) {
                log.error("updateMarketEnable addContentPic req={}, exception ex={}", JSON.toJSONString(req), ex);
            }
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(), resp.getError_msg());
        }
    }

    @GetMapping("/getRecommendGoodsByGoodsId")
    public ResultVO getRecommendGoodsInfoByGoodsId(@RequestParam(value = "goodsId") String goodsId) {
        com.iwhalecloud.retail.goods.dto.ResultVO resultVO = goodsRelService.getRecommendGoodsByGoodsId(goodsId);
        List<RecommendGoodsInfoQueryResp> recommendGoodsInfoQueryResps = Lists.newArrayList();
        if (resultVO.isSuccess()) {
            List<String> recommendGoodsIds = (List<String>)resultVO.getResultData();
            if (CollectionUtils.isEmpty(recommendGoodsIds)) {
                return ResultVO.success(recommendGoodsInfoQueryResps);
            }
            for (String recommendGoodsId : recommendGoodsIds) {
                GoodsIntroReq req = new GoodsIntroReq();
                req.setGoods_id(recommendGoodsId);
                GoodsIntroResp resp = goodsService.queryGoodsDetail(req);
                if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
                    Goods goods = resp.getGoods();
                    setFullImageFileUrl(goods, true);
                    RecommendGoodsInfoQueryResp recommendGoodsInfoQueryResp = new RecommendGoodsInfoQueryResp();
                    recommendGoodsInfoQueryResp.setGoodsId(goods.getGoods_id());
                    recommendGoodsInfoQueryResp.setName(goods.getName());
                    recommendGoodsInfoQueryResp.setMktPrice(goods.getMktprice());
                    recommendGoodsInfoQueryResp.setPrice(goods.getPrice());
                    recommendGoodsInfoQueryResp.setPcImageFile(goods.getPc_image_file());
                    recommendGoodsInfoQueryResps.add(recommendGoodsInfoQueryResp);
                }
            }
        }
        return ResultVO.success(recommendGoodsInfoQueryResps);
    }

    @GetMapping("/getRecommendGoodsInfo")
    public ResultVO getRecommendGoodsInfo(@RequestParam(value = "goodsId", required = false) String goodsId,
        @RequestParam(value = "catId", required = false) String catId) {
        if (StringUtils.isEmpty(goodsId) && StringUtils.isEmpty(catId)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        List<RecommendGoodsInfoQueryResp> recommendGoodsInfoQueryResps = Lists.newArrayList();
        ResultVO resultVO;
        if (!StringUtils.isEmpty(goodsId)) {
            resultVO = getRecommendGoodsInfoByGoodsId(goodsId);
            if (resultVO.isSuccess() && resultVO.getResultData() != null) {
                List<RecommendGoodsInfoQueryResp> goodsList = (List<RecommendGoodsInfoQueryResp>)resultVO.getResultData();
                if(!CollectionUtils.isEmpty(goodsList)) {
                    recommendGoodsInfoQueryResps.addAll(goodsList);
                }
            }
        }
        if (!StringUtils.isEmpty(catId)) {
            ComplexGoodsGetReq complexGoodsGetReq = new ComplexGoodsGetReq();
            complexGoodsGetReq.setCat_id(catId);
            ComplexGoodsGetResp complexGoodsGetResp = goodsCatsService.getComplexGoods(complexGoodsGetReq);
            if (complexGoodsGetResp != null && !CollectionUtils.isEmpty(complexGoodsGetResp.getGoods())) {
                List<GoodsView> goodsViewList = complexGoodsGetResp.getGoods();
                for (GoodsView goodsView : goodsViewList) {
                    setFullImageFileUrl(goodsView, true);
                    RecommendGoodsInfoQueryResp item = new RecommendGoodsInfoQueryResp();
                    item.setGoodsId(goodsView.getGoods_id());
                    item.setName(goodsView.getName());
                    item.setPcImageFile(goodsView.getPc_image_file());
                    item.setMktPrice(goodsView.getMktprice());
                    item.setPrice(goodsView.getPrice());
                    recommendGoodsInfoQueryResps.add(item);
                }
            }
        }
        return ResultVO.success(recommendGoodsInfoQueryResps);
    }

    @PostMapping(value="addGoodsContract")
    public ResultVO addGoodsContract(@RequestBody GoodsAddReq req, HttpServletRequest request) {
        log.info("GoodsController.addGoodsContract req={}", req);
        ResultVO resultVO = getUserId(request);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        String id = (String)resultVO.getResultData();
        req.setUser_id(id);
        setImageDefault(req);
        req.setImage_file(req.getPc_image_file());
        GoodsAddResp resp = goodsService.addGoods(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        return ResultVO.success(resp);
    }

    private void setImageDefault(@RequestBody GoodsAddReq req) {
        if (!StringUtils.isEmpty(req.getPc_image_file())) {
            if (req.getPc_image_file().contains(",")) {
                String [] imageArr = req.getPc_image_file().split(",");
                req.setImage_default(imageArr[0]);
            } else {
                req.setImage_default(req.getPc_image_file());
            }
        }
    }

    @GetMapping(value="queryOfferRelContract")
    public ResultVO queryOfferRelContract(@RequestParam(value = "goodsId") String goodsId) {
        log.info("GoodsController.queryOfferRelContract goodsId={}", goodsId);
        GoodsOfferRelContractReq req = new GoodsOfferRelContractReq();
        req.setGoods_id(goodsId);
        GoodsListResp resp = goodsService.queryOfferRelContract(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            if (StringUtils.isEmpty(resp.getGoodsPage())) {
                return ResultVO.success(Lists.newArrayList());
            }
            List<Goods> goodsList = (List<Goods>)resp.getGoodsPage().getData();
            if (CollectionUtils.isEmpty(goodsList)) {
                return ResultVO.success(Lists.newArrayList());
            }
            List<GoodsRelContractResp> relContractRespList = Lists.newArrayList();
            setRelContract(relContractRespList, goodsList);
            return ResultVO.success(relContractRespList);
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="queryTerminalRelPlan")
    public ResultVO queryTerminalRelPlan(@RequestParam(value = "goodsId") String goodsId) {
        log.info("GoodsController.queryTerminalRelPlan goodsId={}", goodsId);
                GoodsTerminalReq req = new GoodsTerminalReq();
        req.setGoods_id(goodsId);
                GoodsListResp resp = goodsService.queryTerminalRelPlan(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            if (resp.getGoodsPage() == null) {
                return ResultVO.success(Lists.newArrayList());
            }
            List<Goods> goodsList = (List<Goods>)resp.getGoodsPage().getData();
            if (CollectionUtils.isEmpty(goodsList)) {
                return ResultVO.success(Lists.newArrayList());
            }
            List<GoodsRelContractResp> relContractRespList = Lists.newArrayList();
            setRelContract(relContractRespList, goodsList);
            return ResultVO.success(relContractRespList);
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    private void setRelContract(List<GoodsRelContractResp> relContractRespList, List<Goods> goodsList) {
        List<String> goodsIdList = goodsList.stream()
                .map(Goods::getGoods_id)
                .collect(Collectors.toList());
        com.iwhalecloud.retail.goods.dto.ResultVO result = goodsContractService.listGoodsContractByGoodsIds(goodsIdList);
        List<GoodsContractDTO> goodsContractDTOList = Lists.newArrayList();
        if (result.isSuccess()) {
            goodsContractDTOList = (List<GoodsContractDTO>)result.getResultData();
        }
        for (Goods goods : goodsList) {
            setFullImageFileUrl(goods, true);
            GoodsRelContractResp goodsRelContractResp = new GoodsRelContractResp();
            BeanUtils.copyProperties(goods,goodsRelContractResp);
            for (GoodsContractDTO goodsContract : goodsContractDTOList) {
                if (goods.getGoods_id().equals(goodsContract.getGoodsId())) {
                    BeanUtils.copyProperties(goodsContract,goodsRelContractResp);
                }
            }
            relContractRespList.add(goodsRelContractResp);
        }
    }

    @GetMapping(value="getAccMbr")
    public ResultVO getAccMbr(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam
        (value = "pageSize", required = false) Integer pageSize,@RequestParam(value = "orgId") String orgId,
        @RequestParam(value = "lastNum") String lastNum) {
        List<AccMbrDTO> accMbrDTOList = Lists.newArrayList();
        AccMbrDTO accMbrDTO = new AccMbrDTO();
        accMbrDTO.setAccNbr("15918756329");
        accMbrDTO.setDeposit(100.0);
        accMbrDTOList.add(accMbrDTO);
        AccMbrDTO accMbrDTO2 = new AccMbrDTO();
        accMbrDTO2.setAccNbr("18918756389");
        accMbrDTO2.setDeposit(150.0);
        accMbrDTOList.add(accMbrDTO);
        return ResultVO.success(accMbrDTOList);
    }

    /**
     * 查询合约关联套餐
     * 反向查询
     * @param goodsId
     * @return
     */
    @GetMapping(value="queryContractRelOffer")
    public ResultVO queryContractRelOffer(@RequestParam(value = "goodsId") String goodsId) {
        log.info("GoodsController.queryOfferRelContract goodsId={}", goodsId);
        GoodsContractRelOfferReq req = new GoodsContractRelOfferReq();
        req.setGoods_id(goodsId);
        GoodsListResp resp = goodsService.queryContractRelOffer(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            if (StringUtils.isEmpty(resp.getGoodsPage())) {
                return ResultVO.success(Lists.newArrayList());
            }
            List<Goods> goodsList = (List<Goods>)resp.getGoodsPage().getData();
            if (CollectionUtils.isEmpty(goodsList)) {
                return ResultVO.success(Lists.newArrayList());
            }
            for (Goods goods : goodsList) {
                setFullImageFileUrl(goods, true);
            }
            return ResultVO.success(goodsList);
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    private static String fullImageUrl(String imagePath, String showUrl, boolean flag) {
        String aftPath = "";
        if (flag) {
            String[] pathArr = imagePath.split(",");
            for (String befPath : pathArr) {
                if (StringUtils.isEmpty(aftPath)) {
                    if (!befPath.startsWith("http")) {
                        aftPath += showUrl + befPath;
                    } else {
                        aftPath += befPath;
                    }
                } else {
                    if (!befPath.startsWith("http")) {
                        aftPath += "," + showUrl + befPath;
                    } else {
                        aftPath += "," + befPath;
                    }
                }
            }
        } else {
            if (!StringUtils.isEmpty(imagePath)) {
                aftPath = imagePath.replaceAll(showUrl, "");
            }
        }
        return aftPath;
    }
}
