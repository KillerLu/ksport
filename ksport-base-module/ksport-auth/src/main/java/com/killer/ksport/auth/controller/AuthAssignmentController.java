package com.killer.ksport.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killer.ksport.auth.service.IAuthAssignmentService;
import com.killer.ksport.auth.vo.AuthAssignmentVo;
import com.killer.ksport.common.core.constant.PageConstant;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.auth.AuthAssignment;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-8-8 上午9:49
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/assign")
public class AuthAssignmentController extends BaseController{

    @Autowired
    private IAuthAssignmentService authAssignmentService;

    @ApiOperation(value = "新增/修改资源权限分配", httpMethod = "POST", notes = "新增/修改资源权限分配")
    @RequestMapping("/addAssignment")
    public Object addAssignment(AuthAssignmentVo authAssignmentVo){
        AuthAssignment authAssignment = CloneUtil.clone(authAssignmentVo, AuthAssignment.class);
        if (authAssignmentVo.getId() == null) {
            authAssignmentService.save(authAssignment);
        }else{
            authAssignmentService.updateById(authAssignment);
        }
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "删除资源权限分配", httpMethod = "POST", notes = "删除资源权限分配")
    @RequestMapping("/deleteAssignment")
    public Object deleteAssignment(Long id) {
        authAssignmentService.removeById(id);
        return new ResponseBuilder().success().build();
    }


    @ApiOperation(value = "查询资源权限分配", httpMethod = "GET", notes = "查询资源权限分配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "查询第几页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageLength", value = "每页查询多少条", required = false, dataType = "int", paramType = "query")})
    @RequestMapping("/listAuthAssignment")
    public Object listAuthAssignment(@RequestParam(value = "page", defaultValue = PageConstant.PAGE_NUMBER+"") Integer page,
                           @RequestParam(value = "pageLength", defaultValue = PageConstant.PAGE_LENGTH + "") Integer pageLength) {
        IPage data=authAssignmentService.page(new Page<AuthAssignment>(page, pageLength), new QueryWrapper<AuthAssignment>());
        if (CollectionUtils.isNotEmpty(data.getRecords())) {
            List<AuthAssignmentVo> vos = CloneUtil.batchClone(data.getRecords(), AuthAssignmentVo.class);
            data.setRecords(vos);
        }
        return new ResponseBuilder().success().data(data).build();
    }


}
