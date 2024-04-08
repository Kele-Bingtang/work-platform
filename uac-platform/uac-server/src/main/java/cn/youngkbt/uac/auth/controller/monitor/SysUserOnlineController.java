package cn.youngkbt.uac.auth.controller.monitor;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.security.utils.UacHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/4/9 0:24
 * @note
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor/online")
public class SysUserOnlineController {

    @GetMapping("/listPage")
    @Operation(summary = "在线用户列表查询", description = "通过条件查询在线用户列表（支持分页）")
    public Response<TablePage<LoginUser>> listPage(PageQuery pageQuery) {
        List<LoginUser> allLoginUser = UacHelper.getAllLoginUser();
        Page<LoginUser> page = pageQuery.buildPage();
        
        int fromIndex = (int) ((page.getCurrent() - 1) * page.getSize());
        int toIndex = (int) Math.min(fromIndex + page.getCurrent(), allLoginUser.size());

        // 使用 subList 方法进行分页
        List<LoginUser> paginatedLoginUserList = allLoginUser.subList(fromIndex, toIndex);

        page.setRecords(paginatedLoginUserList);

        return HttpResult.ok(TablePage.build(page));
    }
} 
