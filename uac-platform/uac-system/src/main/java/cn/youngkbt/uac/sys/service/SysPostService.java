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

    SysPostVo listById(Long id);

    List<SysPostVo> queryListWithPage(SysPostDto sysPostDto, PageQuery pageQuery);
    
    boolean checkPostNameUnique(SysPostDto sysPostDto);
    
    boolean checkPostCodeUnique(SysPostDto sysPostDto);

    boolean checkPostExitUser(SysPostDto sysPostDto);

    boolean insertOne(SysPostDto sysPostDto);

    boolean updateOne(SysPostDto sysPostDto);

    boolean removeBatch(List<Long> ids);

}
