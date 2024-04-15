package cn.youngkbt.uac.sys.model.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/4/15 下午9:07
 * @note
 */
@Data
public class TreeBO<T> {
    private String parentId;
    private List<T> children = new ArrayList<>();
}
