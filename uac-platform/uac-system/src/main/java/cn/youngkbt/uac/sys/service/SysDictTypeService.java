package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictTypeDTO;
import cn.youngkbt.uac.sys.model.po.SysDictType;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dict_type(字典类型表)】的数据库操作Service
 */
public interface SysDictTypeService extends IService<SysDictType> {

    SysDictTypeVO listById(Long id);

    List<SysDictTypeVO> queryListWithPage(SysDictTypeDTO sysDictTypeDto, PageQuery pageQuery);

    boolean insertOne(SysDictTypeDTO sysDictTypeDto);

    boolean updateOne(SysDictTypeDTO sysDictTypeDto);

    boolean removeBatch(List<Long> ids);
}
