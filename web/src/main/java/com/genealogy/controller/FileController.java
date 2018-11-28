package com.genealogy.controller;

import com.genealogy.common.config.FileConfig;
import com.management.utils.FileUtils;
import com.management.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @desc 文件上传接口
 * @author lufangpu
 * @date 2018-11-28
 * @since 1.0.0
 *
 */
@Controller
@RequestMapping("/api/file")
@Api(description = "文件上传接口")
public class FileController {

    @Autowired
    private FileConfig fileConfig;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "图片上传")
    public R uploadImg(@RequestParam(value = "file", required = false) MultipartFile file){
        if(file == null){
            return R.error("上传内容为空！");
        }
        String fileName = file.getOriginalFilename();
        StringBuffer name = new StringBuffer();
        StringBuffer uploadPath = new StringBuffer(fileConfig.getUploadPath());
        StringBuffer showPath = new StringBuffer(fileConfig.getShowPath());
        fileName = fileName.substring(fileName.lastIndexOf("."));
        if(!".jpg|.png|.jpeg".contains(fileName.toLowerCase())){
            return R.error("请上传.jpg|.png|.jpeg格式文件");
        }
        name.append("img_");
        Long time = System.currentTimeMillis();
        name.append(time);
        name.append(fileName);
        if(uploadPath.charAt(uploadPath.length()-1) != '/'){
            uploadPath.append("/");
        }
        uploadPath.append("/genealogy/Img/");
        if (showPath.charAt(showPath.length()-1) != '/'){
            showPath.append("/");
        }
        showPath.append("/genealogy/Img/");
        showPath.append(name);
        try{
            FileUtils.uploadFile(file.getBytes(), uploadPath.toString(), name.toString());
            return R.ok("文件上传成功！").put("showPath",showPath);
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.ok();
    }
}
