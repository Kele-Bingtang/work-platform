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

    /**
     * 根据主键查询岗位信息
     *
     * @param id 主键
     * @return 岗位信息
     */
    SysPostVO listById(Long id);

    /**
     * 通过条件查询岗位列表
     *
     * @param sysPostDto 岗位列表查询参数
     * @param pageQuery  分页参数
     * @return 岗位列表
     */
    List<SysPostVO> listWithPage(SysPostDTO sysPostDto, PageQuery pageQuery);

    /**
     * 校验岗位名称是否唯一
     *
     * @param sysPostDto 岗位列表查询参数
     * @return 岗位列表
     */
    boolean checkPostNameUnique(SysPostDTO sysPostDto);

    /**
     * 校验岗位编码是否唯一
     *
     * @param sysPostDto 岗位列表查询参数
     * @return 岗位列表
     */
    boolean checkPostCodeUnique(SysPostDTO sysPostDto);

    /**
     * 校验岗位存在用户
     *
     * @param sysPostDto 查询参数
     * @return 是否存在用户
     */
    boolean checkPostExitUser(SysPostDTO sysPostDto);

    /**
     * 新增岗位
     *
     * @param sysPostDto 岗位列表查询参数
     * @return 是否成功
     */
    boolean insertOne(SysPostDTO sysPostDto);

    /**
     * 更新岗位
     *
     * @param sysPostDto 岗位列表查询参数
     * @return 是否成功
     */
    boolean updateOne(SysPostDTO sysPostDto);

    /**
     * 批量删除岗位
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    boolean removeBatch(List<Long> ids);

}
