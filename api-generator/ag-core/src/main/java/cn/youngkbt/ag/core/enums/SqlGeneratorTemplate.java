package cn.youngkbt.ag.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Kele-Bingtang
 * @date 2024/7/21 00:07:29
 * @note
 */
@Getter
@AllArgsConstructor
public enum SqlGeneratorTemplate {
    SELECT,
    INSERT,
    UPDATE,
    DELETE,
    DDL,
    ;
    
}
