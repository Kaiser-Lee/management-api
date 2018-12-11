package com.iwhalecloud.retail.oms.web.controller.fdfs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.dto.resquest.FileManagerDTO;
import com.iwhalecloud.retail.oms.service.FileManagerService;
import com.iwhalecloud.retail.oms.web.controller.fdfs.req.FileBase64UploadReq;
import com.iwhalecloud.retail.oms.web.utils.ResponseComUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/fileManager")
@Slf4j
public class FileManagerController {

    @Resource
    private FileManagerService fileManagerService;

    @RequestMapping(value = "/uploadImageString", method = RequestMethod.POST)
    public ResultVO uploadImageString(@RequestBody List<FileBase64UploadReq> files) {
        ResultVO resultVO = new ResultVO();

        List<FileManagerDTO> fileDtos = null;
        try {
            fileDtos = new ArrayList<FileManagerDTO>();
            for (FileBase64UploadReq req : files) {

                FileManagerDTO dto = new FileManagerDTO();
                dto.setFileName(req.getFileName());
                InputStream is = new ByteArrayInputStream(Base64.decodeBase64(req.getImage()));
                dto.setImage(is);
                dto.setFileSize(req.getFileSize());
                fileDtos.add(dto);
            }
            CommonResultResp resp = fileManagerService.uploadImage(fileDtos);
            ResponseComUtil.RespToResultVO(resp, resultVO);
        } catch (Exception e) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
            log.error("图片上传失败",e);
        } finally {
            if (fileDtos != null) {
                for (FileManagerDTO dto : fileDtos) {
                    IOUtils.closeQuietly(dto.getImage());
                }
            }
        }
        return resultVO;
    }

    @RequestMapping(value = "/uploadImageByFile",headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO uploadImageByFile(@RequestParam("file") MultipartFile file) {
        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
            FileManagerDTO fileManagerDTO = new FileManagerDTO();
            fileManagerDTO.setImage(is);
            fileManagerDTO.setFileSize(file.getSize());
            fileManagerDTO.setFileName(file.getOriginalFilename());

            CommonResultResp resp = fileManagerService.uploadImage(fileManagerDTO);
            ResponseComUtil.RespToResultVO(resp, resultVO);
        } catch (Exception e) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
            log.error("图片流上传失败",e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return resultVO;
    }

    @RequestMapping(value = "/deleteImg", method = RequestMethod.POST)
    public ResultVO deleteImg(@RequestBody FileManagerDTO fileManagerDTO) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = fileManagerService.deleteImg(fileManagerDTO);
        ResponseComUtil.RespToResultVO(resp, resultVO);
        return resultVO;
    }


}
