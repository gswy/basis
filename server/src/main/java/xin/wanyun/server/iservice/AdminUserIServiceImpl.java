package xin.wanyun.server.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.mapper.AdminUserMapper;

interface AdminUserIServiceInterface extends IService<AdminUser> { }

@Service
public class AdminUserIServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserIServiceInterface { }