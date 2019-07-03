package com.killer.ksport.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killer.ksport.common.core.db.dao.ksport.RoleDao;
import com.killer.ksport.common.core.db.view.ksport.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-6-24 下午3:16
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RoleDao roleDao;

    @RequestMapping("/insert")
    public String insert(){
        Role role = new Role();
        role.setName("Admin");
        role.setRemark("管理员");
        roleDao.insert(role);
        //mybatisplus会自动把当前插入对象在数据库中的id写回到该实体中
        System.out.println(role.getId());
 //       System.out.println();
        return "123";
    }

    @RequestMapping("/update")
    public String update(){
        Role role = new Role();
        role.setId(27l);
        role.setRemark("超级管理员");
        roleDao.updateById(role);//根据id进行更新，没有传值的属性就不会更新
        return "123";
    }

    @RequestMapping("/selectById")
    public Role selectById(){
        return roleDao.selectById(20l);
    }

    @RequestMapping("/select")
    public List<Role> select(){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("id","16");//写表中的列名
        columnMap.put("remark","管理员1");
        //该方法若为空,则会返回一个空list
        List<Role> roles= roleDao.selectByMap(columnMap);
        System.out.println(roles);
        return roles;
    }

    @RequestMapping("/selectByIds")
    public List<Role> selectByIds(){
        List<Integer> idList = new ArrayList<>();
//        idList.add(111);
//        idList.add(222);
        idList.add(333);
        //该方法若为空,则会返回一个空list
        //idList为null或为空集合时会报错
        List<Role> roles= roleDao.selectBatchIds(idList);
        System.out.println(roles);
        return roles;
    }

    /**
     * 这个分页其实并不是物理分页，而是内存分页。也就是说，查询的时候并没有limit语句。等配置了分页插件后才可以实现真正的分页。
     * @return
     */
    @RequestMapping("/selectPage")
    public IPage<Role> selectPage(){
        return roleDao.selectPage(new Page<>(1, 4), null);
    }


    @RequestMapping("/deleteById")
    public String deleteById(){
        roleDao.deleteById(25);
        return "123";
    }

    @RequestMapping("/delete")
    public String delete(){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("id",20);
        columnMap.put("remark","超级管理员");
        roleDao.deleteByMap(columnMap);
        return "123";
    }

    @RequestMapping("/deleteByIds")
    public String deleteByIds(){
        List<Integer> idList = new ArrayList<>();
        idList.add(11);
        idList.add(12);
        idList.add(13);
        roleDao.deleteBatchIds(idList);
        return "123";
    }

    @RequestMapping("/wrapperSelect")
    public IPage<Role> wrapperSelect(){
        return roleDao.selectPage(new Page<Role>(1, 3), new QueryWrapper<Role>()
                .eq("remark", "管理员").orderByDesc("id"));
    }

    @RequestMapping("/wrapperUpdate")
    public String wrapperUpdate(){
        Role role = new Role();
        role.setName("Admin");

        roleDao.update(role,
                new UpdateWrapper<Role>()
                        .eq("remark","管理员")
        );
        return "123";
    }

    @RequestMapping("/wrapperDelete")
    public String wrapperDelete(){
        roleDao.delete(new UpdateWrapper<Role>()
                        .isNull("name")
        );
        return "123";
    }

    @RequestMapping("/arInsert")
    public String arInsert(){
        Role role = new Role();
        role.setName("SuperAdmin");
        role.setRemark("超级管理员");
        role.insert();
        //mybatisplus会自动把当前插入对象在数据库中的id写回到该实体中
        System.out.println(role.getId());
        //       System.out.println();
        return "123";
    }

    @RequestMapping("/arUpdate")
    public String arUpdate(){
        Role role = new Role();
        role.setId(10l);
        role.setName("张飞");
        role.updateById();
        return "123";
    }

    @RequestMapping("/arSelect")
    public String arSelect(){
        Role role = new Role();
        //1、根据id查询(没查出会返回空)
//        role= roleDao.selectById(10);
//        System.out.println(role);
        //或者这样用
//        role.setId(10l);
//        role = role.selectById();
//        System.out.println(role);

        //2、查询所有(注意:这里role的属性值并不会作为筛选条件)
//        role.setName("张飞");//这里并不会筛选出名字为张飞的
//        List<Role> roles = role.selectAll();
//        System.out.println(roles);

        //3、根据条件查询
//        List<Role> roles = role.selectList(new QueryWrapper<Role>().like("name","张"));
//        System.out.println(roles);
        //4、查询符合条件的总数
        int result = role.selectCount(new QueryWrapper<Role>().eq("name","张飞"));
        System.out.println(result);
        return "123";
    }

    @RequestMapping("/arDelete")
    public String arDelete(){
        Role role = new Role();
        //删除数据库中不存在的数据也是返回true
        //1、根据id删除数据
        //boolean result = role.deleteById(14);
        //或者这样写
        //role.setId(15l);
        //boolean result = role.deleteById();

        //2、根据条件删除
        boolean result = role.delete(new UpdateWrapper<Role>().like("name","SuperAdmin"));
        System.out.println(result);
        return "123";
    }

    /**
     * 这个分页方法和BaseMapper提供的分页一样都是内存分页，并非物理分页，
     * 因为sql语句中没用limit，和BaseMapper的selectPage方法一样，
     * 配置了分页插件后就可以实现真正的物理分页
     */
    @RequestMapping("/arSelectPage")
    public IPage<Role> arSelectPage(){
        Role role = new Role();
        IPage<Role> page =
                role.selectPage(new Page<>(1,8),
                        new QueryWrapper<Role>().like("name","Admin"));
        System.out.println(page.getRecords());
//        List<R> users = page.getRecords();
//        System.out.println(users);
        return page;
    }

    @RequestMapping("/deleteAll")
    public String deleteAll(){
        roleDao.delete(null);
        return "123";
    }
}
