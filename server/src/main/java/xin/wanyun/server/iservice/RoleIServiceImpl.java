package xin.wanyun.server.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.wanyun.server.entity.Role;
import xin.wanyun.server.mapper.RoleMapper;

interface RoleIServiceInterface extends IService<Role> { }

@Service
public class RoleIServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleIServiceInterface { }