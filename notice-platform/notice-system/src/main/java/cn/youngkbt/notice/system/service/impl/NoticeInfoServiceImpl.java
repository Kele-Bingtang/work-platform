package cn.youngkbt.notice.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.notice.system.model.po.NoticeInfo;
import cn.youngkbt.notice.system.service.NoticeInfoService;
import cn.youngkbt.notice.system.mapper.NoticeInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-08-23 00:29:20
 * @note 针对表「t_notice_info（短信、邮件发送记录）」的数据库操作 Service 实现
 */
@Service
public class NoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo>
        implements NoticeInfoService {

}




