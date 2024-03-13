package cn.youngkbt.uac.sys.utils;

import cn.youngkbt.uac.sys.model.vo.extra.DeptTree;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Young Kbt
 * @date 2024/2/15 20:46
 * @note 部门树结构构建工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeptTreeUtil {

    public static List<DeptTree> build(List<DeptTree> nodes) {
        Map<String, DeptTree> nodeMap = new HashMap<>();
        for (DeptTree tree : nodes) {
            nodeMap.put(tree.getDeptId(), tree);
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

