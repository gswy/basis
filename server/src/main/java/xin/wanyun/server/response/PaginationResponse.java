package xin.wanyun.server.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {

    /**
     * 数据体
     */
    private List data;

    /**
     * 总数
     */
    private long total;

    /**
     * 页大小
     */
    private long page_size;

    /**
     * 当前页码
     */
    private long current;

    /**
     * 最后一页页码
     */
    private long last_page;

    /**
     * Page 分页
     */
    public PaginationResponse(Page page) {
        this.data = page.getRecords();
        this.total = page.getTotal();
        this.page_size = page.getSize();
        this.current = page.getCurrent();
        this.last_page = page.getPages();
    }
}
