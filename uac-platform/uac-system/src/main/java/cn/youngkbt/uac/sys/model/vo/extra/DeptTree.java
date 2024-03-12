package cn.youngkbt.uac.sys.model.vo.extra;

import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Kbt
 * @date 2024/2/15 22:16
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = SysDept.class, convertGenerate = false)
public class DeptTree extends SysDeptVO {
    private List<SysDeptVO> children = new ArrayList<>();
}