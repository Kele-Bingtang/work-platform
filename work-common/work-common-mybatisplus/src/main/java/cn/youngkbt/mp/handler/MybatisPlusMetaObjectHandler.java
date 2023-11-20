package cn.youngkbt.mp.handler;

import cn.hutool.core.util.StrUtil;
import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.SysUserBO;
import cn.youngkbt.mp.constant.MyBatisDefaultConstants;
import cn.youngkbt.security.utils.SecurityUtils;
import cn.youngkbt.utils.IdsUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/7/4 23:06
 * @note MybatisPlus 自动填充配置
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis plus start insert fill ....");

        LocalDateTime now = LocalDateTime.now();

        fillValIfNullByName("createTime", now, metaObject, false);
        fillValIfNullByName("updateTime", now, metaObject, false);
        fillValIfNullByName("createBy", getUser().getUserName(), metaObject, false);
        fillValIfNullByName("updateBy", getUser().getUserName(), metaObject, false);
        fillValIfNullByName("createById", getUser().getUserId(), metaObject, false);
        fillValIfNullByName("updateById", getUser().getUserId(), metaObject, false);
        fillValIfNullByName("isDeleted", MyBatisDefaultConstants.DEFAULT_DELETED, metaObject, false);
        fillValIfNullByName("status", MyBatisDefaultConstants.DEFAULT_STATUS, metaObject, false);

        fillValFieldTypes(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis plus start update fill ....");
        fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
        fillValIfNullByName("updateBy", getUser().getUsername(), metaObject, true);
        fillValIfNullByName("updateById", getUser().getUserId(), metaObject, true);
    }

    public static void fillValFieldTypes(MetaObject metaObject) {
        List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(metaObject.getOriginalObject().getClass(), FieldValueFill.class);
        if (!fieldList.isEmpty()) {
            for (Field field : fieldList) {
                FieldValueFill fieldValueFill = field.getAnnotation(FieldValueFill.class);
                if (fieldValueFill.value().equals(ValueStrategy.UUID)) {
                    fillValIfNullByName(field.getName(), IdsUtil.simpleUUID(), metaObject, false);
                } else if (fieldValueFill.value().equals(ValueStrategy.SNOWFLAKE)) {
                    fillValIfNullByName(field.getName(), IdWorker.getId(), metaObject, false);
                }
            }
        }
    }

    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
     *
     * @param fieldName  属性名
     * @param fieldVal   属性值
     * @param metaObject MetaObject
     * @param isCover    是否覆盖原有值,避免更新操作手动入参
     */
    private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
        // 1. 没有 set 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (StringUtils.hasText(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    /**
     * 获取 spring security 当前的用户名
     *
     * @return 当前用户名
     */
    private SysUserBO getUser() {
        return (SysUserBO) SecurityUtils.getAuthentication();
    }
}