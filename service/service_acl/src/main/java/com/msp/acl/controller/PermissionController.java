package com.msp.acl.controller;

import com.msp.acl.entity.Permission;
import com.msp.acl.service.PermissionService;
import com.msp.eduOrder.commonUtils.MResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public MResult indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return MResult.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public MResult remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return MResult.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public MResult doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return MResult.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public MResult toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return MResult.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public MResult save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return MResult.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public MResult updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return MResult.ok();
    }

}

