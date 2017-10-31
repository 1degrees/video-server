package com.xiao.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiao.core.generic.GenericDao;
import com.xiao.core.generic.GenericServiceImpl;
import com.xiao.web.dao.RoleMapper;
import com.xiao.web.model.Role;
import com.xiao.web.service.RoleService;

/**
 * 角色Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午4:16:33
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public GenericDao<Role, Long> getDao() {
        return roleMapper;
    }

    @Override
    public List<Role> selectRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

}
