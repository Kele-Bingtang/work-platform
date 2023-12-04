package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDeptDto;
import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dept(部门表)】的数据库操作Service
 */
public interface SysDeptService extends IService<SysDept> {

    SysDeptVo queryById(Long id);

    List<SysDeptVo> queryListWithPage(SysDeptDto sysDeptDto, PageQuery pageQuery);

    Boolean insertOne(SysDeptDto sysDeptDto);

    Boolean updateOne(SysDeptDto sysDeptDto);

    Boolean removeOne(List<Long> ids);
}
