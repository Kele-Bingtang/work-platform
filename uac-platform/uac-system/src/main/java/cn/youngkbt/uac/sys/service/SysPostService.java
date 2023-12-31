package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysPostDto;
import cn.youngkbt.uac.sys.model.po.SysPost;
import cn.youngkbt.uac.sys.model.vo.SysPostVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_post(岗位表)】的数据库操作Service
 */
public interface SysPostService extends IService<SysPost> {

    SysPostVo queryById(Long id);

    List<SysPostVo> queryListWithPage(SysPostDto sysPostDto, PageQuery pageQuery);

    Boolean insertOne(SysPostDto sysPostDto);

    Boolean updateOne(SysPostDto sysPostDto);

    Boolean removeOne(List<Long> ids);
}
