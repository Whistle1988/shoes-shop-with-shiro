package cn.shoes.system.controller;

import cn.shoes.common.annotation.Log;
import cn.shoes.common.controller.BaseController;
import cn.shoes.common.dto.QueryPage;
import cn.shoes.common.dto.ResponseCode;
import cn.shoes.common.enums.StatusEnums;
import cn.shoes.common.exception.GlobalException;
import cn.shoes.system.entity.Role;
import cn.shoes.system.entity.RoleWithMenu;
import cn.shoes.system.service.RoleService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shoes
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/system/role")
@Api(value = "RoleController", tags = {"角色管理模块接口"})
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, Role role) {
        return ResponseCode.success(super.selectByPageNumSize(queryPage, () -> roleService.queryList(role)));
    }

    @GetMapping("/findById")
    public ResponseCode findById(Long id) {
        return ResponseCode.success(roleService.findById(id));
    }

    @Log("添加角色")
    @PostMapping("/add")
    @RequiresPermissions("role:add")
    public ResponseCode add(@RequestBody RoleWithMenu role) {
        try {
            roleService.add(role);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/checkName")
    public ResponseCode checkName(String name, String id) {
        if (name.isEmpty()) {
            return new ResponseCode(StatusEnums.PARAM_ERROR);
        }
        if (!roleService.checkName(name, id)) {
            return new ResponseCode(StatusEnums.PARAM_REPEAT);
        }
        return ResponseCode.success();
    }

    @Log("更新角色")
    @PostMapping("update")
    @RequiresPermissions("role:update")
    public ResponseCode update(@RequestBody RoleWithMenu role) {
        try {
            roleService.update(role);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Log("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("role:delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            roleService.delete(ids);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
