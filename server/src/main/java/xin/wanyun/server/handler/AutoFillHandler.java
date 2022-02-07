package xin.wanyun.server.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AutoFillHandler implements MetaObjectHandler {

    /**
     * 插入数据表的填充策略
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("添加时间");
        this.strictInsertFill(metaObject, "CreatedAt", Date.class, new Date());
        this.strictInsertFill(metaObject, "UpdatedAt", Date.class, new Date());
    }

    /**
     * 更新数据表的填充策略
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("修改时间");
        this.strictUpdateFill(metaObject, "UpdatedAt", Date.class, new Date());
    }
}
