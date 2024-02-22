package cn.youngkbt.uac.sys.utils;

import cn.youngkbt.uac.sys.model.vo.extra.MenuTree;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2024-08-22 22:08:35
 * @note 
*/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuTreeUtil {

    public static List<MenuTree> build(List<MenuTree> nodes) {
        Map<String, MenuTree> nodeMap = new HashMap<>();
        for (MenuTree tree : nodes) {
            nodeMap.put(tree.getMenuId(), tree);
        }

        for (MenuTree tree : nodes) {
            if (!"0".equals(tree.getParentId())) { // 非根节点
                MenuTree parentNode = nodeMap.get(tree.getParentId());
                if (parentNode != null) {
                    parentNode.getChildren().add(tree);
                }
            }
        }

        List<MenuTree> roots = new ArrayList<>();
        for (MenuTree tree : nodes) {
            if ("0".equals(tree.getParentId())) {
                roots.add(tree);
            }
        }

        return roots;
    }

}

