package com.xiao.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiao.core.generic.GenericDao;
import com.xiao.core.generic.GenericServiceImpl;
import com.xiao.web.dao.PermissionMapper;
import com.xiao.web.model.Permission;
import com.xiao.web.service.PermissionService;

/**
 * 权限Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午12:05:03
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public GenericDao<Permission, Long> getDao() {
        return permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(Long roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }
}
