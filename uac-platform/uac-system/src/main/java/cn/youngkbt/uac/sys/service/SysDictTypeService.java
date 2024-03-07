package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictTypeDto;
import cn.youngkbt.uac.sys.model.po.SysDictType;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dict_type(字典类型表)】的数据库操作Service
 */
public interface SysDictTypeService extends IService<SysDictType> {

    SysDictTypeVo listById(Long id);

    List<SysDictTypeVo> queryListWithPage(SysDictTypeDto sysDictTypeDto, PageQuery pageQuery);

    boolean insertOne(SysDictTypeDto sysDictTypeDto);

    boolean updateOne(SysDictTypeDto sysDictTypeDto);

    boolean removeBatch(List<Long> ids);
}
