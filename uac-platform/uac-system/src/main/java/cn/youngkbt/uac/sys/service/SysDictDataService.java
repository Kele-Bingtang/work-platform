package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDTO;
import cn.youngkbt.uac.sys.model.po.SysDictData;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dict_data(字典数据表)】的数据库操作Service
 */
public interface SysDictDataService extends IService<SysDictData> {

    SysDictDataVO listById(Long id);

    List<SysDictDataVO> queryListWithPage(SysDictDataDTO sysDictDataDto, PageQuery pageQuery);

    boolean insertOne(SysDictDataDTO sysDictDataDto);

    boolean updateOne(SysDictDataDTO sysDictDataDto);

    boolean updateDictCode(String oldDictCode, String newDictCodeS);

    boolean removeBatch(List<Long> ids);
}
