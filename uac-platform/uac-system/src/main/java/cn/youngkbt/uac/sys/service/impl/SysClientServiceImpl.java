package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysClientMapper;
import cn.youngkbt.uac.sys.model.dto.SysClientDto;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.model.vo.SysClientVo;
import cn.youngkbt.uac.sys.service.SysClientService;
import cn.youngkbt.utils.IdsUtil;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-59-13 22:59:42
 * @note 针对表【t_sys_client(客户端授权表)】的数据库操作Service实现
 */
@Service
public class SysClientServiceImpl extends ServiceImpl<SysClientMapper, SysClient> implements SysClientService {

    @Override
    public SysClient checkClientIdThenGet(String clientId) {
        return baseMapper.selectOne(Wrappers.<SysClient>lambdaQuery().eq(SysClient::getClientId, clientId));
    }

    @Override
    public SysClientVo listById(Long id) {
        SysClient sysClient = baseMapper.selectById(id);
        Assert.nonNull(sysClient, "客户端不存在");
        SysClientVo result = MapstructUtil.convert(sysClient, SysClientVo.class);
        result.setGrantTypeList(List.of(result.getGrantTypes().split(",")));
        return result;
    }

    @Override
    public List<SysClientVo> queryListWithPage(SysClientDto sysClientDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysClient> wrapper = Wrappers.<SysClient>lambdaQuery()
                .like(StringUtils.hasText(sysClientDto.getClientId()), SysClient::getClientId, sysClientDto.getClientId())
                .eq(StringUtils.hasText(sysClientDto.getClientKey()), SysClient::getClientKey, sysClientDto.getClientKey())
                .eq(StringUtils.hasText(sysClientDto.getClientName()), SysClient::getClientName, sysClientDto.getClientName())
                .like(StringUtils.hasText(sysClientDto.getClientSecret()), SysClient::getClientSecret, sysClientDto.getClientSecret())
                .in(Objects.nonNull(sysClientDto.getGrantTypeList()), SysClient::getGrantTypes, sysClientDto.getGrantTypeList())
                .eq(Objects.nonNull(sysClientDto.getStatus()), SysClient::getStatus, sysClientDto.getStatus());

        List<SysClient> sysClientList;
        if (Objects.isNull(pageQuery)) {
            sysClientList = baseMapper.selectList(wrapper);
        } else {
            sysClientList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        List<SysClientVo> result = MapstructUtil.convert(sysClientList, SysClientVo.class);
        result.forEach(r -> r.setGrantTypeList(List.of(r.getGrantTypes().split(","))));
        return result;
    }

    @Override
    public List<SysClientVo> listTreeList() {
        // TODO 是否分页
        List<SysClient> sysClientList = baseMapper.selectList(Wrappers.<SysClient>lambdaQuery()
                .select(SysClient::getClientId, SysClient::getClientName));
        
        return MapstructUtil.convert(sysClientList, SysClientVo.class);
    }

    @Override
    public boolean insertOne(SysClientDto sysClientDto) {
        SysClient sysClient = MapstructUtil.convert(sysClientDto, SysClient.class);
        if (Objects.isNull(sysClient.getClientSecret())) {
            sysClient.setClientSecret(IdsUtil.simpleUUID());
        }
        String clientSecret = sysClient.getClientSecret();
        String clientKey = sysClient.getClientKey();
        String appId = SecureUtil.md5(clientSecret + clientKey);
        sysClient.setClientId(appId);
        sysClient.setGrantTypes(String.join(",", sysClientDto.getGrantTypeList()));
        return baseMapper.insert(sysClient) > 0;
    }

    @Override
    public boolean updateOne(SysClientDto sysClientDto) {
        SysClient sysClient = MapstructUtil.convert(sysClientDto, SysClient.class);
        sysClient.setGrantTypes(String.join(",", sysClientDto.getGrantTypeList()));
        return baseMapper.updateById(sysClient) > 0;
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        return baseMapper.update(null, Wrappers.<SysClient>lambdaUpdate()
                .eq(SysClient::getId, id)
                .set(SysClient::getStatus, status)) > 0;
    }

    @Override
    public boolean removeBatch(Collection<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}




