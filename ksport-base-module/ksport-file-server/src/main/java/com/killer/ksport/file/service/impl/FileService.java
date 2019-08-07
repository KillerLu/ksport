package com.killer.ksport.file.service.impl;

import com.killer.ksport.common.core.db.dao.ksport.FileDao;
import com.killer.ksport.common.core.db.view.ksport.File;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.common.core.util.DateUtil;
import com.killer.ksport.file.service.IFileService;
import com.killer.ksport.file.util.FtpUtil;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author ：Killer
 * @date ：Created in 19-7-19 下午2:22
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class FileService extends BaseService<FileDao,File> implements IFileService{

    @Autowired
    private FtpUtil ftpUtil;

    @Override
    @Transactional
    public File uploadFile(MultipartFile uploadFile) {
        File file=new File();
        String fileName=uploadFile.getOriginalFilename();
        file.setName(fileName);
        if (fileName.endsWith(".jar")) {
            //如果是jar文件,是属于定时执行的jar文件,统一放在quartz目录
            file.setPath("quartz");
        }else{
            //其余按照日期划分,放到ksport目录下
            file.setPath("ksport/"+ ftpUtil.getDateDir(DateUtil.getNow()));
        }
        file.setUuid(UUID.randomUUID().toString());
        //将文件上传至ftp服务器
        try {
            ftpUtil.uploadToFtp(uploadFile.getInputStream(), fileName, file.getPath());
        } catch (Exception e) {
            throw new RuntimeException("文件保存失败");
        }
        //将该文件记录保存进数据库
        super.save(file);
        return file;
    }
}
