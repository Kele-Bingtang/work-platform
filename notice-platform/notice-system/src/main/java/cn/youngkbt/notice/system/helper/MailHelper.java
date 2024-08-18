package cn.youngkbt.notice.system.helper;

import cn.youngkbt.core.exception.ServerException;
import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.notice.system.model.dto.NoticeInfoDTO;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.StringUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2024/8/18 21:56:42
 * @note
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Slf4j
public class MailHelper {
    /**
     * 多邮箱账户缓存
     */
    private static Map<String, JavaMailSender> javaMailSenderMap = new LinkedHashMap<>();
    private static final JavaMailSender defaultJavaMailSender;
    private static final String from;

    static {
        from = SpringHelper.getEnvProperty("spring.mail.username");
        defaultJavaMailSender = SpringHelper.getBean(JavaMailSender.class);
    }


    public static void send(NoticeInfoDTO noticeInfoDTO) {
        send(noticeInfoDTO.getTo(), noticeInfoDTO.getCc(), noticeInfoDTO.getBcc(), noticeInfoDTO.getSubject(), noticeInfoDTO.getContent(),
                noticeInfoDTO.getPriority(), multipartFileToFile(noticeInfoDTO.getFileList()));
    }

    public static void send(String to, String subject, String content) {
        send(to, "", subject, content);
    }

    public static void send(String to, String cc, String subject, String content) {
        send(to, cc, subject, content, new File(""));
    }

    public static void send(String to, String subject, String content, File file) {
        send(to, "", subject, content, Collections.singletonList(file));
    }

    public static void send(String to, String cc, String subject, String content, File file) {
        send(to, cc, subject, content, Collections.singletonList(file));
    }

    public static void send(String to, String subject, String content, List<File> fileList) {
        send(to, "", subject, content, fileList);
    }

    public static void send(String to, String cc, String subject, String content, List<File> fileList) {
        send(to, cc, "", subject, content, 3, fileList);
    }

    public static void send(String to, String cc, String bcc, String subject, String content, int priority, List<File> fileList) {
        MimeMessage mimeMessage = defaultJavaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            if (to.contains(",")) {
                helper.setTo(to.replace(" ", "").split(","));
            } else {
                helper.setTo(to);
            }
            helper.setFrom(from);
            if (StringUtil.hasText(cc)) {
                if (cc.contains(",")) {
                    helper.setTo(cc.replace(" ", "").split(","));
                } else {
                    helper.setTo(cc);
                }
            }
            if (StringUtil.hasText(bcc)) {
                helper.setBcc(bcc);
            }
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setPriority(priority);

            for (File file : fileList) {
                if (StringUtil.hasText(file.getPath())) {
                    helper.addAttachment(file.getName(), file);
                }
            }

            log.info("发送邮件：to={}, cc={}, bcc={}, subject={}, content={}, priority={}, fileList={}", to, cc, bcc, subject, content, priority, fileList);
            defaultJavaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("发送邮件失败：{}", e.getMessage());
            throw new ServerException(e.getMessage());
        }
    }

    public static void sendMultipartFile(String to, String subject, String content, List<MultipartFile> fileList) {
        send(to, "", subject, content, multipartFileToFile(fileList));
    }

    public static File multipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String suffixFileName = FilenameUtils.getExtension(fileName);
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(StringUtil.hasEmpty(fileName) ? "" : fileName, suffixFileName);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            log.error("MultipartFile 转换 File 失败：{}", e.getMessage());
            throw new ServerException(e.getMessage());
        }
    }

    public static List<File> multipartFileToFile(List<MultipartFile> multiFileList) {
        if (ListUtil.isEmpty(multiFileList)) {
            return ListUtil.newArrayList();
        }
        return multiFileList.stream().map(MailHelper::multipartFileToFile).toList();
    }
}
