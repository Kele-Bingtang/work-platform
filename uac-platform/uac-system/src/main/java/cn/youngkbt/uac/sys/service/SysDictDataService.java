package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
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

    /**
     * 根据主键查询字典数据信息
     *
     * @param id 主键
     * @return 字典数据信息
     */
    SysDictDataVO listById(Long id);

    /**
     * 通过条件查询字典数据列表
     *
     * @param sysDictDataDTO 查询条件
     * @return 字典数据列表
     */
    List<SysDictDataVO> queryList(SysDictDataDTO sysDictDataDTO);
    
    /**
     * 通过条件查询字典数据列表
     *
     * @param sysDictDataDTO 查询条件
     * @param pageQuery      分页参数
     * @return 字典数据列表
     */
    TablePage<SysDictDataVO> listPage(SysDictDataDTO sysDictDataDTO, PageQuery pageQuery);

    /**
     * 新增字典数据
     *
     * @param sysDictDataDTO 字典数据
     * @return 是否成功
     */
    boolean insertOne(SysDictDataDTO sysDictDataDTO);

    /**
     * 修改字典数据
     *
     * @param sysDictDataDTO 字典数据
     * @return 是否成功
     */
    boolean updateOne(SysDictDataDTO sysDictDataDTO);

    /**
     * 更新字典编码
     *
     * @param oldDictCode  旧字典编码
     * @param newDictCodeS 新字典编码
     * @return 是否成功
     */
    boolean updateDictCode(String oldDictCode, String newDictCodeS);

    /**
     * 批量删除字典数据
     *
     * @param ids 主键列表
     * @return 是否成功
     */
    boolean removeBatch(List<Long> ids);

}
