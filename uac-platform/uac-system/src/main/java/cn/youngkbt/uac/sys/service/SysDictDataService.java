package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDto;
import cn.youngkbt.uac.sys.model.po.SysDictData;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dict_data(字典数据表)】的数据库操作Service
 */
public interface SysDictDataService extends IService<SysDictData> {

    SysDictDataVo queryById(Long id);

    List<SysDictDataVo> queryListWithPage(SysDictDataDto sysDictDataDto, PageQuery pageQuery);

    Boolean insertOne(SysDictDataDto sysDictDataDto);

    Boolean updateOne(SysDictDataDto sysDictDataDto);

    Boolean updateDictCode(String oldDictCode, String newDictCodeS);

    Boolean removeOne(List<Long> ids);
}
