package cn.youngkbt.notice.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.notice.system.model.po.NoticeMailConfig;
import cn.youngkbt.notice.system.service.NoticeMailConfigService;
import cn.youngkbt.notice.system.mapper.NoticeMailConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author Kele-Bingtang
 * @date 2024-08-23 00:29:20
 * @note 针对表「t_notice_mail_config（邮箱配置）」的数据库操作 Service 实现
*/
@Service
public class NoticeMailConfigServiceImpl extends ServiceImpl<NoticeMailConfigMapper, NoticeMailConfig>
    implements NoticeMailConfigService{

}




