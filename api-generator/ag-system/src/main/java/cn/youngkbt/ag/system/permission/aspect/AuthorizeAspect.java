package cn.youngkbt.ag.system.permission.aspect;

import cn.youngkbt.ag.core.constant.CacheNameConstant;
import cn.youngkbt.ag.core.enums.ProjectMemberRole;
import cn.youngkbt.ag.core.enums.TeamMemberRole;
import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.core.helper.Assert;
import cn.youngkbt.ag.system.permission.annotation.ProjectAuthorize;
import cn.youngkbt.ag.system.permission.annotation.TeamAuthorize;
import cn.youngkbt.ag.system.service.ProjectMemberService;
import cn.youngkbt.ag.system.service.TeamMemberService;
import cn.youngkbt.cache.helper.CacheHelper;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.helper.SpElParserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 01:54:46
 * @note 团队权限、项目权限切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizeAspect {

    private final TeamMemberService teamMemberService;
    private final ProjectMemberService projectMemberService;

    @Before("@annotation(projectAuthorize)")
    public void before(JoinPoint joinPoint, ProjectAuthorize projectAuthorize) {
        boolean checkRead = projectAuthorize.checkRead();
        boolean checkReadAndWrite = projectAuthorize.checkReadAndWrite();
        boolean checkAdmin = projectAuthorize.checkAdmin();

        if (!checkRead && !checkReadAndWrite && !checkAdmin) {
            return;
        }

        // 注解只能放在方法上，所以强转 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String projectId = SpElParserHelper.parse(signature.getMethod(), joinPoint.getArgs(), projectAuthorize.value(), String.class);

        Assert.hasText(projectId, "团队不存在");

        String cacheTime = projectAuthorize.cacheTime();
        String userId = AgHelper.getUserId();

        String cacheName = CacheNameConstant.PREFIX + userId + cacheTime;

        // 项目管理员权限判断（项目可读、可写权限、项目关联的服务、报表、类配置项的可读、可写权限）
        if (checkAdmin && isProjectAdmin(userId, projectId, cacheName, cacheTime)) {
            return;
        }

        // 项目成员权限判断（项目关联的服务、报表、类配置项的可读、可写权限）
        if (checkReadAndWrite && isProjectMember(userId, projectId, cacheName, cacheTime)) {
            return;
        }

        // 项目可读权限判断（项目列表可读权限、项目关联的服务、报表、类配置项的可读权限）
        if (checkRead) {
            isProjectReader(userId, projectId, cacheName, cacheTime);
        }
    }

    /**
     * 判断用户是否是项目管理员
     */
    private boolean isProjectAdmin(String userId, String projectId, String cacheName, String cacheTime) {
        String adminKey = projectId + ":admin";
        // 先从缓存读取，如果存在，则有权限
        if (Objects.nonNull(CacheHelper.get(cacheTime, adminKey))) {
            return true;
        }

        if (!projectMemberService.checkMemberRole(projectId, userId, Collections.singletonList(ProjectMemberRole.ADMIN.ordinal()))) {
            throw new ServiceException("用户不是项目管理员，无法操作项目");
        } else {
            CacheHelper.put(cacheName, adminKey, true);
            return true;
        }
    }

    /**
     * 判断用户是否是项目成员
     */
    private boolean isProjectMember(String userId, String projectId, String cacheName, String cacheTime) {
        String readAndWriteKey = projectId + ":readAndWrite";
        // 先从缓存读取，如果存在，则有权限
        if (Objects.nonNull(CacheHelper.get(cacheTime, readAndWriteKey))) {
            return true;
        }

        if (!projectMemberService.checkMemberRole(projectId, userId, List.of(ProjectMemberRole.ADMIN.ordinal(), ProjectMemberRole.MEMBER.ordinal()))) {
            throw new ServiceException("用户没有权限操作数据");
        } else {
            CacheHelper.put(cacheName, readAndWriteKey, true);
            return true;
        }
    }

    /**
     * 判断用户是否拥有项目读取权限
     */
    private boolean isProjectReader(String userId, String projectId, String cacheName, String cacheTime) {
        String readKey = projectId + ":read";
        // 先从缓存读取，如果存在，则有权限
        if (Objects.nonNull(CacheHelper.get(cacheTime, readKey))) {
            return true;
        }

        if (!projectMemberService.checkMemberExist(projectId, userId) &&
                projectMemberService.checkMemberRole(projectId, userId, Collections.singletonList(ProjectMemberRole.PREVENT.ordinal()))) {
            throw new ServiceException("用户没有权限查看项目");
        } else {
            CacheHelper.put(cacheName, readKey, true);
            return true;
        }
    }

    @Before("@annotation(teamAuthorize)")
    public void before(JoinPoint joinPoint, TeamAuthorize teamAuthorize) {
        boolean checkOwner = teamAuthorize.checkOwner();
        boolean checkOwnerAndAdmin = teamAuthorize.checkOwnerAndAdmin();

        if (!checkOwner && !checkOwnerAndAdmin) {
            return;
        }

        // 注解只能放在方法上，所以强转 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String teamId = SpElParserHelper.parse(signature.getMethod(), joinPoint.getArgs(), teamAuthorize.value(), String.class);

        Assert.hasText(teamId, "团队不存在");

        String cacheTime = teamAuthorize.cacheTime();
        String userId = AgHelper.getUserId();

        String cacheName = CacheNameConstant.PREFIX + userId + cacheTime;

        // 团队所有者权限判断（团队信息编辑、项目解散，成员与团队权限配置、成员与项目权限配置等操作权限）
        if (checkOwner) {
            String readKey = teamId + ":owner";
            // 先从缓存读取，如果存在，则有权限
            if (Objects.nonNull(CacheHelper.get(cacheTime, readKey))) {
                return;
            }
            if (!teamMemberService.checkMemberRole(teamId, AgHelper.getUserId(), Collections.singletonList(TeamMemberRole.OWNER.ordinal()))) {
                throw new ServiceException("用户不是团队所有者，无法操作团队");
            } else {
                CacheHelper.put(cacheName, readKey, true);
                return;
            }
        }

        // 团队所有者 & 管理员权限判断（项目新增权限、成员与项目权限配置等操作权限）
        if (checkOwnerAndAdmin) {
            String readKey = teamId + ":ownerAndAdmin";
            // 先从缓存读取，如果存在，则有权限
            if (Objects.nonNull(CacheHelper.get(cacheTime, readKey))) {
                return;
            }
            if (!teamMemberService.checkMemberRole(teamId, AgHelper.getUserId(), List.of(TeamMemberRole.OWNER.ordinal(), TeamMemberRole.ADMIN.ordinal()))) {
                throw new ServiceException("用户没有操作权限");
            } else {
                CacheHelper.put(cacheName, readKey, true);
            }
        }

    }
}
