package xin.wanyun.server.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.wanyun.server.entity.UserRole;
import xin.wanyun.server.mapper.UserRoleMapper;

interface UserRoleIServiceInterface extends IService<UserRole> { }

@Service
public class UserRoleIServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleIServiceInterface { }