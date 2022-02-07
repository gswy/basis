package xin.wanyun.server.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.wanyun.server.entity.RolePermission;
import xin.wanyun.server.mapper.RolePermissionMapper;

interface RolePermissionIServiceInterface extends IService<RolePermission> { }

@Service
public class RolePermissionIServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionIServiceInterface { }