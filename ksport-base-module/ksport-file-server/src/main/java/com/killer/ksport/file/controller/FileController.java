package com.killer.ksport.file.controller;

import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.ksport.File;
import com.killer.ksport.common.core.web.ResponseBuilder;
import com.killer.ksport.file.service.IFileService;
import com.killer.ksport.file.util.FtpUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ：Killer
 * @date ：Created in 19-7-19 上午9:28
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController{

    @Autowired
    private IFileService fileService;

    /**
     *  远程调用接口
     */
    @RequestMapping("/upload")
    @ApiOperation(value = "上传文件", httpMethod = "POST", notes = "上传文件")
    public Object getLoginUser(@RequestParam MultipartFile file) {
        File f=fileService.uploadFile(file);
        return new ResponseBuilder().success().data(f).build();
    }

//    /**
//     *  远程调用接口
//     */
//    @RequestMapping("/download")
//    public void download(String name, HttpServletResponse response) throws Exception {
//        InputStream inputStream=ftpUtil.downloadFile(name);
//        wrapDownloadHttpResponse(name, response);
//        StreamUtils.copy(StreamUtils.copyToByteArray(inputStream), response.getOutputStream());
//    }
}
