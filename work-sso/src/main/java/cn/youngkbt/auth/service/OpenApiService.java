package cn.youngkbt.auth.service;

import cn.youngkbt.auth.dto.OpenApiClientDTO;
import cn.youngkbt.auth.entity.SysAuthClient;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2023/9/24 2:46
 * @note
 */
public interface OpenApiService extends IService<SysAuthClient> {
    boolean addApp(OpenApiClientDTO openApiClientDTO);

    SysAuthClient getApp(String appId, String appSecret, String authorizedGrantType);
}
