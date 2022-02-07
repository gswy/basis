package xin.wanyun.server.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.wanyun.server.entity.Permission;
import xin.wanyun.server.mapper.PermissionMapper;

interface PermissionIServiceInterface extends IService<Permission> { }

@Service
public class PermissionIServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionIServiceInterface { }