package cn.youngkbt.notice.system.service;

import cn.youngkbt.notice.system.model.dto.NoticeInfoDTO;
import cn.youngkbt.notice.system.model.vo.NoticeInfoVO;

/**
 * @author Kele-Bingtang
 * @date 2024/8/18 23:45:00
 * @note
 */
public interface MailService {
    NoticeInfoVO sendMail(NoticeInfoDTO noticeInfoDTO);
}
