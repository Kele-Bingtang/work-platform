package cn.youngkbt.notice.system.service.impl;

import cn.youngkbt.notice.system.helper.MailHelper;
import cn.youngkbt.notice.system.model.dto.NoticeInfoDTO;
import cn.youngkbt.notice.system.model.vo.NoticeInfoVO;
import cn.youngkbt.notice.system.service.MailService;
import cn.youngkbt.utils.IdsUtil;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024/8/18 23:45:23
 * @note
 */
@Service
public class MailServiceImpl implements MailService {
    @Override
    public NoticeInfoVO sendMail(NoticeInfoDTO noticeInfoDTO) {
        MailHelper.send(noticeInfoDTO);
        
        String simpleUUID = IdsUtil.simpleUUID();
        return new NoticeInfoVO().setNoticeId(simpleUUID);
    }
}
