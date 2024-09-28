package cn.youngkbt.notice.system.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.notice.system.aspect.annotation.AppAuthorize;
import cn.youngkbt.notice.system.model.dto.NoticeInfoDTO;
import cn.youngkbt.notice.system.model.vo.NoticeInfoVO;
import cn.youngkbt.notice.system.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2024/8/18 23:44:28
 * @note
 */
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping
    @Operation(summary = "发送邮件", description = "发送邮件（不支持附件）")
    @AppAuthorize("#noticeInfoDTO.getAppId()")
    public Response<NoticeInfoVO> sendMail(NoticeInfoDTO noticeInfoDTO) {
        NoticeInfoVO noticeVO = mailService.sendMail(noticeInfoDTO);
        return HttpResult.ok(noticeVO);
    }

    @PostMapping
    @Operation(summary = "发送邮件", description = "发送邮件（支持附件）")
    @AppAuthorize("#noticeInfoDTO.getAppId()")
    public Response<NoticeInfoVO> sendMailWithFile(NoticeInfoDTO noticeInfoDTO) {
        NoticeInfoVO noticeVO = mailService.sendMail(noticeInfoDTO);
        return HttpResult.ok(noticeVO);
    }

}
