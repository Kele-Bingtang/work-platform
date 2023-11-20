package cn.youngkbt.mp.interceptor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * @author Kele-Bingtang
 * @date 2023/7/4 23:07
 * @note 分页拦截器：重构分页插件, 当 size 小于 0 时, 直接设置为 0, 防止错误查询全表
 */
public class WorkPaginationInnerInterceptor extends PaginationInnerInterceptor {

    @Override
    public void beforeQuery(org.apache.ibatis.executor.Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
        // size 小于 0 直接设置为 0 , 即不查询任何数据
        if (null != page && page.getSize() < 0) {
            page.setSize(0);
        }
        super.willDoQuery(executor, ms, page, rowBounds, resultHandler, boundSql);
    }
}