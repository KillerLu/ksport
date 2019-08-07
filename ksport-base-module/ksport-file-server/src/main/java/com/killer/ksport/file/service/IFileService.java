package com.killer.ksport.file.service;

import com.killer.ksport.common.core.db.view.ksport.File;
import com.killer.ksport.common.core.service.IBaseService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：Killer
 * @date ：Created in 19-7-19 下午2:21
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IFileService extends IBaseService<File> {

    /**
     * 上传文件
     * @param uploadFile
     * @return
     */
    File uploadFile(MultipartFile uploadFile);
}
