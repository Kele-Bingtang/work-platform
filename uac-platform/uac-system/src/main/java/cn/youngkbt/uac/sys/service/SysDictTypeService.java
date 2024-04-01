package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
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

    /**
     * 根据主键查询字典类型信息
     *
     * @param id 主键
     * @return 字典类型信息
     */
    SysDictTypeVO listById(Long id);

    /**
     * 通过条件查询字典类型列表
     *
     * @param sysDictTypeDTO 查询条件
     * @return 字典类型列表
     */
    List<SysDictTypeVO> queryList(SysDictTypeDTO sysDictTypeDTO);

    /**
     * 通过条件查询字典类型列表
     *
     * @param sysDictTypeDTO 查询条件
     * @param pageQuery      分页参数
     * @return 字典类型列表
     */
    TablePage<SysDictTypeVO> listPage(SysDictTypeDTO sysDictTypeDTO, PageQuery pageQuery);

    /**
     * 新增字典类型
     *
     * @param sysDictTypeDTO 新增字典类型信息
     * @return 是否新增成功
     */
    boolean insertOne(SysDictTypeDTO sysDictTypeDTO);

    /**
     * 修改字典类型
     *
     * @param sysDictTypeDTO 修改字典类型信息
     * @return 是否修改成功
     */
    boolean updateOne(SysDictTypeDTO sysDictTypeDTO);

    /**
     * 批量删除字典类型
     *
     * @param ids 主键列表
     * @return 是否删除成功
     */
    boolean removeBatch(List<Long> ids);

}
