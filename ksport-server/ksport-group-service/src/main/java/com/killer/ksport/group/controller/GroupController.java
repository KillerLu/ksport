package com.killer.ksport.group.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import com.killer.ksport.common.core.db.view.ksport.Group;
import com.killer.ksport.common.core.db.view.ksport.GroupUser;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.group.service.IGroupService;
import com.killer.ksport.group.vo.GroupUserVo;
import com.killer.ksport.group.vo.GroupVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-8-16 下午4:14
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
public class GroupController extends BaseController{

    @Autowired
    private IGroupService groupService;

    @ApiOperation(value = "新增群组", httpMethod = "POST", notes = "新增群组")
    @RequestMapping("/addGroup")
    public Object addGroup(@Valid GroupVo groupVo, BindingResult result){
        ResponseBuilder builder = new ResponseBuilder();
        Map<String, String> fieldErrorMap = getErrors(result);
        if (fieldErrorMap.size() > 0)
        {
            builder.error().message(getErrorsString(result)).add("fields", fieldErrorMap);
            return builder.build();
        }
        groupService.saveGroup(groupVo);
        return builder.success().build();
    }

    @ApiOperation(value = "修改群组", httpMethod = "POST", notes = "修改群组")
    @RequestMapping("/modifyGroup")
    public Object modifyGroup(GroupVo groupVo){
        ResponseBuilder builder = new ResponseBuilder();
        groupService.modifyGroup(groupVo);
        return builder.success().build();
    }

    @ApiOperation(value = "删除群组", httpMethod = "POST", notes = "删除群组")
    @RequestMapping("/deleteGroup")
    public Object deleteGroup(Long id){
        ResponseBuilder builder = new ResponseBuilder();
        groupService.deleteGroup(id);
        return builder.success().build();
    }


    @ApiOperation(value = "新增群组用户关系", httpMethod = "POST", notes = "新增群组用户关系")
    @RequestMapping("/addGroupUser")
    public Object addGroupUser(@Valid GroupUserVo groupUserVo, BindingResult result){
        ResponseBuilder builder = new ResponseBuilder();
        Map<String, String> fieldErrorMap = getErrors(result);
        if (fieldErrorMap.size() > 0)
        {
            builder.error().message(getErrorsString(result)).add("fields", fieldErrorMap);
            return builder.build();
        }
        groupService.addGroupUser(groupUserVo);
        return builder.success().build();
    }

    @ApiOperation(value = "修改群组用户关系", httpMethod = "POST", notes = "修改群组用户关系")
    @RequestMapping("/modifyGroupUser")
    public Object modifyGroupUser(GroupUserVo groupUserVo){
        ResponseBuilder builder = new ResponseBuilder();
        groupService.modifyGroupUser(groupUserVo);
        return builder.success().build();
    }

    @ApiOperation(value = "移除群组用户关系", httpMethod = "POST", notes = "移除群组用户关系")
    @RequestMapping("/removeGroupUser")
    public Object removeGroupUser(Long id){
        ResponseBuilder builder = new ResponseBuilder();
        groupService.removeGroupUser(id);
        return builder.success().build();
    }

    @ApiOperation(value = "查询某群组(如学校或年级)以及其下所有的群组", httpMethod = "GET", notes = "查询某群组(如学校或年级)以及其下所有的群组",response = GroupVo.class)
    @RequestMapping("/getGroupStructure")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "群组id", required = true, dataType = "long", paramType = "query")
    })
    public Object getGroupStructure(Long id){
        ResponseBuilder builder = new ResponseBuilder();
        //查询学校
        GroupVo groupVo = groupService.getGroupAndChildGroups(id);
        return builder.success().data(groupVo).build();
    }


    @ApiOperation(value = "查询某群组下所有的用户", httpMethod = "GET", notes = "查询某群组下所有的用户",response = GroupVo.class)
    @RequestMapping("/listGroupUserByGroupId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组id", required = true, dataType = "long", paramType = "query")
    })
    public Object listGroupUserByGroupId(Long groupId){
        ResponseBuilder builder = new ResponseBuilder();
        List<GroupUserVo> groupUserVos = groupService.listGroupUserDetailByGroupId(groupId);
        return builder.success().data(groupUserVos).build();
    }


    @ApiOperation(value = "根据用户id删除群组用户关系", httpMethod = "POST", notes = "根据用户id删除群组用户关系")
    @RequestMapping(value = "/deleteGroupUserByUserId",method = RequestMethod.POST)
    public Object deleteGroupUserByUserId(Long userId){
        ResponseBuilder builder = new ResponseBuilder();
        try {
            groupService.deleteGroupUserByUserId(userId);
            return builder.success().build();
        } catch (Exception e) {
            return builder.error().message("操作失败").build();
        }
    }


    @ApiOperation(value = "根据用户id查看群组用户时候存在", httpMethod = "GET", notes = "根据用户id查看群组用户时候存在",response = Boolean.class)
    @RequestMapping("/checkGroupUserByUserId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int64", paramType = "query")
    })
    public Object checkUser(@RequestParam(value = "userId") Long userId){
        ResponseBuilder builder = new ResponseBuilder();
        try {
            List<GroupUser> groupUsers = groupService.listGroupUserByUserId(userId);
            //若该用户存在则返回成功
            return builder.success().data(CollectionUtils.isNotEmpty(groupUsers) ? true : false).build();
        } catch (Exception e) {
            return builder.error().build();
        }
    }

}

