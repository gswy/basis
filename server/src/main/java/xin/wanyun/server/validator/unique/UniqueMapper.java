package xin.wanyun.server.validator.unique;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 内部查询数据库Mapper接口
 */
@Mapper
public interface UniqueMapper extends BaseMapper<Object> {

    @Select("SELECT ${field} FROM ${table} WHERE ${field} = #{value} LIMIT 1")
    Object uniqueRecord(@Param("table") String table,
                        @Param("field") String field,
                        @Param("value") String value);
}
