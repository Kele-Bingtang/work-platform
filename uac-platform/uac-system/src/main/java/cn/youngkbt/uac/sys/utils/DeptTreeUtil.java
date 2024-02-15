package cn.youngkbt.uac.sys.utils;

import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVo;
import cn.youngkbt.uac.sys.model.vo.extra.DeptTree;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Young Kbt
 * @date 2024/2/15 20:46
 * @description
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeptTreeUtil {

    public static List<DeptTree> build(List<DeptTree> nodes) {
        Map<String, DeptTree> nodeMap = new HashMap<>();
        for (DeptTree node : nodes) {
            nodeMap.put(node.getDeptId(), node);
        }

        for (DeptTree tree : nodes) {
            if (!"0".equals(tree.getParentId())) { // 非根节点
                DeptTree parentNode = nodeMap.get(tree.getParentId());
                if (parentNode != null) {
                    parentNode.getChildren().add(tree);
                }
            }
        }

        List<DeptTree> roots = new ArrayList<>();
        for (DeptTree tree : nodes) {
            if ("0".equals(tree.getParentId())) {
                roots.add(tree);
            }
        }

        return roots;
    }

}

