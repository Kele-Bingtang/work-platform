package cn.youngkbt.uac.sys.utils;

import cn.youngkbt.uac.sys.model.bo.TreeBO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Kele-Bingtang
 * @date 2024/4/15 下午9:05
 * @note
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeBuildUtil extends cn.hutool.core.lang.tree.TreeUtil {

    public static <T extends TreeBO<T>> List<T> build(List<T> nodes, Function<T, String> function) {
        Map<String, T> nodeMap = new HashMap<>();
        for (T tree : nodes) {
            nodeMap.put(function.apply(tree), tree);
        }

        for (T tree : nodes) {
            if (!"0".equals(tree.getParentId())) { // 非根节点
                T parentNode = nodeMap.get(tree.getParentId());
                if (parentNode != null) {
                    parentNode.getChildren().add(tree);
                }
            }
        }

        List<T> roots = new ArrayList<>();
        for (T tree : nodes) {
            if ("0".equals(tree.getParentId())) {
                roots.add(tree);
            }
        }

        return roots;
    }
}
