package cn.youngkbt.ag.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Kele-Bingtang
 * @date 2024/7/28 15:39:19
 * @note
 */
@Getter
@AllArgsConstructor
public enum DropdownConfigType {
    LOCAL("local"),
    SERVICE("service"),
    SQL("sql"),
    ;
    private final String type;
}
