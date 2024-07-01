package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.enums.ProjectMemberRole;
import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.system.mapper.ServiceColMapper;
import cn.youngkbt.ag.system.model.dto.ServiceColDTO;
import cn.youngkbt.ag.system.model.po.ServiceCol;
import cn.youngkbt.ag.system.model.vo.ServiceColVO;
import cn.youngkbt.ag.system.service.ProjectMemberService;
import cn.youngkbt.ag.system.service.ServiceColService;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_service_col（服务列配置项表）」的数据库操作 Service 实现
 */
@Service
@RequiredArgsConstructor
public class ServiceColServiceImpl extends ServiceImpl<ServiceColMapper, ServiceCol> implements ServiceColService {

    private final ProjectMemberService projectMemberService;

    @Override
    public TablePage<ServiceColVO> listPage(ServiceColDTO serviceColDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<ServiceCol> wrapper = buildQueryWrapper(serviceColDTO);

        Page<ServiceCol> serviceColPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);
        return TablePage.build(serviceColPage, ServiceColVO.class);
    }

    private LambdaQueryWrapper<ServiceCol> buildQueryWrapper(ServiceColDTO serviceColDTO) {
        return Wrappers.<ServiceCol>lambdaQuery()
                .eq(StringUtil.hasText(serviceColDTO.getJsonCol()), ServiceCol::getJsonCol, serviceColDTO.getJsonCol())
                .eq(StringUtil.hasText(serviceColDTO.getServiceId()), ServiceCol::getServiceId, serviceColDTO.getServiceId())
                .eq(Objects.nonNull(serviceColDTO.getIsWhereKey()), ServiceCol::getIsWhereKey, serviceColDTO.getIsWhereKey())
                .orderByAsc(ServiceCol::getDisplaySeq);
    }

    @Override
    public boolean addServiceCol(ServiceColDTO serviceColDTO) {
        ServiceCol serviceCol = MapstructUtil.convert(serviceColDTO, ServiceCol.class);
        return baseMapper.insert(serviceCol) > 0;
    }

    @Override
    public boolean editServiceCol(ServiceColDTO serviceColDTO) {
        ServiceCol serviceCol = MapstructUtil.convert(serviceColDTO, ServiceCol.class);
        return baseMapper.updateById(serviceCol) > 0;
    }

    @Override
    public boolean removeServiceColById(String id) {
        ServiceCol serviceCol = baseMapper.selectById(id);
        checkServiceColAllowed(serviceCol.getProjectId(), AgHelper.getUserId());

        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeAllServiceColByServiceId(String serviceId) {
        return baseMapper.delete(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getServiceId, serviceId)) > 0;
    }

    @Override
    public boolean removeAllServiceColByServiceIdByCategoryId(String categoryId) {
        return baseMapper.delete(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getCategoryId, categoryId)) > 0;
    }

    @Override
    public boolean removeAllServiceColByServiceIdByProjectIdId(String projectId) {
        return baseMapper.delete(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getProjectId, projectId)) > 0;
    }

    @Override
    public boolean removeAllServiceColByServiceIdByTeamId(String teamId) {
        return baseMapper.delete(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getTeamId, teamId)) > 0;
    }

    @Override
    public boolean checkServiceColUnique(ServiceColDTO serviceColDTO) {
        return baseMapper.exists(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getTableCol, serviceColDTO.getTableCol())
                .or()
                .eq(ServiceCol::getJsonCol, serviceColDTO.getJsonCol()));
    }

    @Override
    public void checkServiceColAllowed(String projectId, String userId) {
        if (!projectMemberService.checkMemberRole(projectId, userId, List.of(ProjectMemberRole.ADMIN.ordinal(), ProjectMemberRole.MEMBER.ordinal()))) {
            throw new ServiceException("用户没有列配置项操作权限");
        }
    }

    @Override
    public boolean listColumnThenInsert(String serviceId, String selectSql) {
        return false;
    }

    @Override
    public Integer regenCol(String serviceId, String selectSql) {
        return 0;
    }

    @Override
    public Integer removeInvalidCol(String serviceId, String selectSql) {
        return 0;
    }

}




