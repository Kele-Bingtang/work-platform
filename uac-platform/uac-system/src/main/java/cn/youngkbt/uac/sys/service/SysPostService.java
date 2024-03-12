package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysPostDTO;
import cn.youngkbt.uac.sys.model.po.SysPost;
import cn.youngkbt.uac.sys.model.vo.SysPostVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_post(岗位表)】的数据库操作Service
 */
public interface SysPostService extends IService<SysPost> {

    SysPostVO listById(Long id);

    List<SysPostVO> queryListWithPage(SysPostDTO sysPostDto, PageQuery pageQuery);
    
    boolean checkPostNameUnique(SysPostDTO sysPostDto);
    
    boolean checkPostCodeUnique(SysPostDTO sysPostDto);

    boolean checkPostExitUser(SysPostDTO sysPostDto);

    boolean insertOne(SysPostDTO sysPostDto);

    boolean updateOne(SysPostDTO sysPostDto);

    boolean removeBatch(List<Long> ids);

}
