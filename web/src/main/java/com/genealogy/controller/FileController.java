package com.genealogy.controller;

import com.management.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/file")
@Api(description = "文件上传接口")
public class FileController {

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "图片上传")
    public R uploadImg(){
        return R.ok();
    }
}
