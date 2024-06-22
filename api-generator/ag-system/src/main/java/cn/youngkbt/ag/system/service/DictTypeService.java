package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.DictTypeDTO;
import cn.youngkbt.ag.system.model.po.DictType;
import cn.youngkbt.ag.system.model.vo.DictTypeVO;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t__dict_type(字典类型表)】的数据库操作Service
 */
public interface DictTypeService extends IService<DictType> {

    /**
     * 通过条件查询字典类型列表
     *
     * @param dictTypeDTO 查询条件
     * @return 字典类型列表
     */
    List<DictTypeVO> listAll(DictTypeDTO dictTypeDTO);

    /**
     * 通过条件查询字典类型列表
     *
     * @param dictTypeDTO 查询条件
     * @param pageQuery      分页参数
     * @return 字典类型列表
     */
    TablePage<DictTypeVO> listPage(DictTypeDTO dictTypeDTO, PageQuery pageQuery);

    /**
     * 新增字典类型
     *
     * @param dictTypeDTO 新增字典类型信息
     * @return 是否新增成功
     */
    boolean insertOne(DictTypeDTO dictTypeDTO);

    /**
     * 修改字典类型
     *
     * @param dictTypeDTO 修改字典类型信息
     * @return 是否修改成功
     */
    boolean updateOne(DictTypeDTO dictTypeDTO);

    /**
     * 批量删除字典类型
     *
     * @param ids 主键列表
     * @return 是否删除成功
     */
    boolean removeBatch(List<Long> ids);

    /**
     * 校验字典类型编码是否唯一
     *
     * @param dictTypeDTO 字典类型信息
     * @return 是否唯一
     */
    boolean checkDictCodeUnique(DictTypeDTO dictTypeDTO);
}
