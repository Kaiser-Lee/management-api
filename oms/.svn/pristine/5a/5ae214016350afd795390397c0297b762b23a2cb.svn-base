package com.iwhalecloud.retail.oms.web.fdfs;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.FileSubfixConstants;
import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.dto.response.FileManagerRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.FileManagerDTO;
import com.iwhalecloud.retail.oms.service.FileManagerService;
import com.iwhalecloud.retail.oms.web.fdfs.multithread.SynchroThread;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

@Service
@Slf4j
public class FileManagerServiceImpl implements FileManagerService {

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    @Autowired
    public FastFileStorageClient fastFileStorageClient;

    public CommonResultResp uploadImage(FileManagerDTO file) {
        CommonResultResp resultVO = new CommonResultResp();

        try {
            String fileName = file.getFileName();
            String subfix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!StringUtils.hasText(subfix)) {
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("图片格式不对");
                return resultVO;
            }
            List<FileManagerRespDTO> respList = new ArrayList<>();
            // 若为视频文件，抓取视频第一帧作为视频简介
            if (FileSubfixConstants.checkContainSubfix(subfix)){
                FileManagerDTO thumbNailDto = fetchVedioFrame(file.getImage());
                String thumbNailFileName = thumbNailDto.getFileName();
                String thumbNailSubfix = thumbNailFileName.substring(thumbNailFileName.lastIndexOf(".") + 1);
                respList.add(uploadFile(thumbNailDto, thumbNailSubfix));
            }
            //保存上传文件，并加入返回列表
            respList.add(uploadFile(file,subfix));
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(respList);
            resultVO.setResultMsg("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("gs_10010_上传图片异常，e{}", JSON.toJSONString(e));
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

    // 上传文件私有方法
    private FileManagerRespDTO uploadFile(FileManagerDTO file,String subfix){
        long c1 = System.currentTimeMillis();
        StorePath storePath = fastFileStorageClient.uploadFile(file.getImage(), file.getFileSize(), subfix, null);
        log.info("上传fastdfs耗时：" + (System.currentTimeMillis()-c1));
        FileManagerRespDTO resp = new FileManagerRespDTO();
        BeanUtils.copyProperties(storePath, resp);
        resp.setFileUrl(dfsShowIp + storePath.getFullPath());
        log.info("gs_10010_uploadImage,storePath{}", JSON.toJSONString(storePath));
        return resp;
    }

    // 截取视频图片作为简图
    private FileManagerDTO fetchVedioFrame(InputStream fileIs) throws Exception{
        long start = System.currentTimeMillis();
        FFmpegFrameGrabber videoGrabber = new FFmpegFrameGrabber(fileIs);
        videoGrabber.start();
        // 视频总帧数
        int frameNumTotal = videoGrabber.getLengthInFrames();
        org.bytedeco.javacv.Frame frameOfImg = null;
        int frameCount = 0;
        while (frameCount < frameNumTotal){
            frameOfImg = videoGrabber.grabFrame();
            if (null != frameOfImg && null != frameOfImg.image){
                break;
            }
            frameCount++;
        }

        // 转化成IO输出
        /*List<byte[]> byteArrList = new ArrayList<>();
        Buffer[] imgBuffer = frameOfImg.image;
        for (Buffer buf : imgBuffer){
            ByteBuffer bbuf = (ByteBuffer)buf;
            byteArrList.add(bbuf.array());
            // InputStream is = new ByteArrayInputStream(byteArr);
        }
        byte[] byteArrTotal = byteMergerAll(byteArrList);
        InputStream imgIs = new ByteArrayInputStream(byteArrTotal);*/

        int owidth = frameOfImg.imageWidth;
        int oheight = frameOfImg.imageHeight;
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        Java2DFrameConverter converter =new Java2DFrameConverter();
        BufferedImage fecthedImage =converter.getBufferedImage(frameOfImg);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(fecthedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);

        String fileName = "videoCover" + System.currentTimeMillis() + ".jpg";
        String tmpDir = System.getProperty("java.io.tmpdir");
        //File imgFile = new File(tmpDir + File.separator + fileName);
        File imgFile = new File(tmpDir + fileName);
        ImageIO.write(bi,"jpg", imgFile);
        videoGrabber.stop();
        // 装箱返回
        FileManagerDTO dto = new FileManagerDTO();
        dto.setImage(new FileInputStream(imgFile));
        dto.setFileSize(imgFile.length());
        dto.setFileName(fileName);
        long end = System.currentTimeMillis();
        log.info("截取视频某帧作为封面，共耗时：" + (end - start));
        return dto;
    }

    /**
     * 合并多个byte数组方法
     * @param byteArrList
     * @return
     */
    private static byte[] byteMergerAll(List<byte[]> byteArrList) {
        int byteArrLength = 0;
        for (int i = 0; i < byteArrList.size(); i++) {
            byteArrLength += byteArrList.get(i).length;
        }
        byte[] byteArrTotal = new byte[byteArrLength];
        int countLength = 0;
        for (int i = 0; i < byteArrList.size(); i++) {
            byte[] b = byteArrList.get(i);
            System.arraycopy(b, 0, byteArrTotal, countLength, b.length);
            countLength += b.length;
        }
        return byteArrTotal;
    }

    @Override
    public CommonResultResp uploadImage(List<FileManagerDTO> files) {

        List<FileManagerRespDTO> resps = new ArrayList<FileManagerRespDTO>();
        for (FileManagerDTO dto : files) {
            String suffix = dto.getFileName().substring(dto.getFileName().lastIndexOf(".") + 1);
            FileManagerRespDTO uploadResp = this.uploadFile(dto,suffix);
            resps.add(uploadResp);
        }
        CommonResultResp resultVO = new CommonResultResp();
        resultVO.setResultMsg("上传成功");
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(resps);
        return resultVO;
    }


    public CommonResultResp deleteImg(FileManagerDTO fileName) {
        CommonResultResp resultVO = new CommonResultResp();
        if (fileName == null || StringUtils.isEmpty(fileName.getDeleteImgName())) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("图片名不能为空");
            return resultVO;
        }
        try {
            fastFileStorageClient.deleteFile(fileName.getDeleteImgName());
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }



}
