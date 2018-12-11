package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.response.MemberLoginDTO;
import com.iwhalecloud.retail.oms.dto.resquest.RandomLogAddReq;
import com.iwhalecloud.retail.oms.dto.resquest.RandomLogQueryReq;
import com.iwhalecloud.retail.oms.entity.MemberBinding;
import com.iwhalecloud.retail.oms.entity.TRandomLog;
import com.iwhalecloud.retail.oms.mapper.MemberBindingMapper;
import com.iwhalecloud.retail.oms.mapper.RandomLogMapper;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.MemberService;
import com.iwhalecloud.retail.oms.service.TokenService;
import com.ztesoft.net.app.base.core.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import params.member.req.MemberByIdReq;
import params.member.resp.MemberByIdResp;
import zte.params.member.req.MemberIsExistsReq;
import zte.params.member.req.MemberLoginReq;
import zte.params.member.req.MemberRegisterReq;
import zte.params.member.resp.MemberIsExistsResp;
import zte.params.member.resp.MemberLoginResp;
import zte.params.member.resp.MemberRegisterResp;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Value("${effInterval}")
    private String effInterval;

	@Resource
    private ZteRopClient client;
    @Resource
    private TokenService tokenService;
    @Resource
    private MemberBindingMapper memberBindingMapper;
    @Resource
    private RandomLogMapper randomLogMapper;

    @Override
    public MemberLoginResp login(String userName, String pwd) {
        MemberLoginReq req = new MemberLoginReq();
        req.setUserName(userName);
        req.setPwd(pwd);

        MemberLoginResp resp = client.execute(req, MemberLoginResp.class);

        log.info("testRop resp{}", JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public MemberLoginResp login(MemberLoginReq req) {
        log.info("MemberServiceImpl.login req={}", req);
        MemberLoginResp resp;
        try {
            resp = client.execute(req, MemberLoginResp.class);
        } catch(Exception ex) {
            log.error("MemberServiceImpl.login Rop Invoke Exception, ex={}", ex);
            resp = new MemberLoginResp();
            resp.setError_code(ResultCodeEnum.ROP_INVOKE_EXCEPTION.getCode());
            resp.setError_msg(ResultCodeEnum.ROP_INVOKE_EXCEPTION.getDesc());
        }
        return resp;
    }

    @Override
    public MemberRegisterResp register(Member member) {

        MemberRegisterReq req = new MemberRegisterReq();
        req.setMember(member);
        log.info("MemberServiceImpl.register req={}", req);
        MemberRegisterResp resp;
        try {
            resp = client.execute(req, MemberRegisterResp.class);
        } catch(Exception ex) {
            log.error("MemberServiceImpl.register Rop Invoke Exception, ex={}", ex);
            throw ex;
        }

        return resp;
    }

    @Override
    public MemberIsExistsResp isExists(MemberIsExistsReq req) {
        log.info("MemberServiceImpl.isExists req={}", req);
        MemberIsExistsResp resp;
        try {
            resp = client.execute(req, MemberIsExistsResp.class);
        } catch(Exception ex) {
            log.error("MemberServiceImpl.isExists Rop Invoke Exception, ex={}", ex);
            resp = new MemberIsExistsResp();
            resp.setError_code(ResultCodeEnum.ROP_INVOKE_EXCEPTION.getCode());
            resp.setError_msg(ResultCodeEnum.ROP_INVOKE_EXCEPTION.getDesc());
        }
        return resp;
    }

    @Override
    public MemberBinding getMemberBinding(String targetId) {
        log.info("MemberServiceImpl.getMemberBinding targetId={}", targetId);

        MemberBinding entity = new MemberBinding();
        entity.setTargetId(targetId);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("target_id", targetId);
        List<MemberBinding> list;
        try {
            list = memberBindingMapper.selectByMap(param);

//            list = memberBindingMapper.selectList(new Wrapper<MemberBinding>() {
//                @Override
//                public MemberBinding getEntity() {
//                    return entity;
//                }
//
//                @Override
//                public String getSqlSegment() {
//                    return null;
//                }
//            });

        } catch(Exception ex) {
            log.error("MemberServiceImpl.getMemberBinding e Exception, ex={}", ex);
            throw ex;
        }
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int addMemberBinding(MemberBinding memberBinding) {
        log.info("MemberServiceImpl.addMemberBinding memberBinding={}", memberBinding);
        int result = 0;
        try {
            result = memberBindingMapper.insert(memberBinding);
        } catch(Exception ex) {
            log.error("MemberServiceImpl.addMemberBinding e Exception, ex={}", ex);
            throw ex;
        }
        return result;

    }

    @Override
    public int deleteMemberBinding(String targetId) {
        log.info("MemberServiceImpl.deleteMemberBinding targetId={}", targetId);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("target_id", targetId);
        int result = 0;
        try {
            result = memberBindingMapper.deleteByMap(param);
        } catch(Exception ex) {
            log.error("MemberServiceImpl.deleteMemberBinding e Exception, ex={}", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public int updateMemberBinding(MemberBinding memberBinding) {
        return memberBindingMapper.updateById(memberBinding);
    }

    @Override
    public ResultVO<MemberLoginDTO> login(MemberLoginReq req,String sessionId) {
        log.info("MemberServiceImpl.login req={}", req);
        ResultVO<MemberLoginDTO> resultVO = new ResultVO<MemberLoginDTO>();
        try {
        	MemberLoginResp resp = client.execute(req, MemberLoginResp.class);
            
        	// 失败统一返回
    		if(!ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())){
    			log.error("MemberServiceImpl.login MemberLoginResp Exception, ex={}", resp.getError_msg());
    			return ResultVO.error("登录失败!");
    		}
    		resultVO.setResultCode(resp.getError_code());
    		resultVO.setResultMsg(resp.getError_msg());
    		
    		MemberLoginDTO memberLoginDto = new MemberLoginDTO();
    		memberLoginDto.setMember(resp.getMember());
    		memberLoginDto.setToken(tokenService.getMemberToken(resp.getMember().getMember_id(), sessionId));
    		resultVO.setResultData(memberLoginDto);

    		log.info("memberid = {}", resp.getMember().getMember_id());
        } catch(Exception ex) {
            log.error("MemberServiceImpl.login Rop Invoke Exception, ex={}", ex);
            return ResultVO.error("登录失败!");
        }
        return resultVO;
    }


    @Override
    public ResultVO insertRandomLog(String sessionId, RandomLogAddReq req) {
        log.info("MemberServiceImpl.insertRandomLog req={}", req);
        if (StringUtils.isEmpty(req.getBusiType())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        else if (StringUtils.isEmpty(req.getReceviNo())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        else if (StringUtils.isEmpty(req.getSendType())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        // TODO 从短信平台获取
        req.setRandomCode("888888");
        TRandomLog randomLog = new TRandomLog();
        BeanUtils.copyProperties(req, randomLog);
        randomLog.setBusiId(sessionId);
        randomLog.setValidStatus(OmsConst.ValidStatusEnum.NOT_VALID.getCode());
        Date now = new Date();
        randomLog.setCreateDate(now);
        Date effDate = new Date(now .getTime() + Long.parseLong(effInterval));
        randomLog.setEffDate(effDate);
        randomLog.setSendTime(now);
        randomLog.setSendStatus(OmsConst.SendStatusEnum.HAVE_SEND.getCode());
        try {
            randomLogMapper.insertSelective(randomLog);
        } catch (Exception ex) {
            log.error("MemberServiceImpl.insertRandomLog insert Exception, ex={}", ex);
            return ResultVO.errorEnum(ResultCodeEnum.INSERT_DB_EXCEPTION);
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<String> selectLogIdByRandomCode(RandomLogQueryReq req) {
        log.info("MemberServiceImpl.RandomLogQueryReq req={}", req);
        try {
            TRandomLog randomLog = new TRandomLog();
            BeanUtils.copyProperties(req, randomLog);
            randomLog.setEffDate(new Date());
            String logId = randomLogMapper.selectLogIdByRandomCode(randomLog);
            if (!StringUtils.isEmpty(logId)) {
                return ResultVO.success(logId);
            }
        } catch (Exception ex) {
            log.error("MemberServiceImpl.selectCountByRandomCode Exception, ex={}", ex);
            return ResultVO.errorEnum(ResultCodeEnum.INSERT_DB_EXCEPTION);
        }
        return ResultVO.error();
    }

    @Override
    public ResultVO updateValidStatus(String logId) {
        log.info("MemberServiceImpl.updateValidStatus logId={}", logId);
        try {
            TRandomLog randomLog = new TRandomLog();
            randomLog.setLogId(Long.parseLong(logId));
            randomLog.setValidStatus(OmsConst.ValidStatusEnum.HAVE_VALID.getCode());
            randomLog.setValidTime(new Date());
            int result = randomLogMapper.updateByPrimaryKey(randomLog);
            if (result > 0) {
                return ResultVO.success();
            }
        } catch (Exception ex) {
            log.error("MemberServiceImpl.updateValidStatus Exception, ex={}", ex);
            return ResultVO.errorEnum(ResultCodeEnum.UPDATE_DB_EXCEPTION);
        }
        return ResultVO.error();
    }

    /**
	 * 根据会员ID获取会员信息
	 * @param memberId 会员ID
	 * @return
	 */
    @Override
	public Member getMember(String memberId) {
		
		log.info("MemberServiceImpl.getMember memberId={}", memberId);
		MemberByIdReq req = new MemberByIdReq();
		req.setMember_id(memberId);
		MemberByIdResp resp = client.execute(req, MemberByIdResp.class);
		if (!ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
			log.info("MemberServiceImpl.getMember 获取会员信息失败", resp.getError_msg());
			return null;
		}
		
		return resp.getMember();
		
	}
}
