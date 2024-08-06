package cn.youngkbt.file.system.helper;

import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.file.system.properties.FileProperties;
import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.utils.JacksonUtil;
import cn.youngkbt.utils.StringUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/8/5 21:46:23
 * @note
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Slf4j
public class FileHelper {

    private static final String SEPARATOR = "/";
    private static final FileProperties fileProperties;

    static {
        fileProperties = SpringHelper.getBean(FileProperties.class);
    }

    /**
     * 文件保存
     */
    public static File saveFile(MultipartFile multipartFile, String appId, String appModule, String fileKey) {
        // 创建文件保存路径 
        String saveFilePath = mkFileDirs(appId, appModule);
        if (null == saveFilePath) {
            log.error("创建文件路径失败，appId：{}，appModule：{}，fileKey：{}", appId, appModule, fileKey);
            throw new ServiceException("创建文件路径失败,appId：" + appId);
        }
        // 获得文件后缀并生成存储文件名
        String originalFileName = multipartFile.getOriginalFilename();
        String suffixFileName = FilenameUtils.getExtension(originalFileName);
        String saveFileName = fileKey + "." + suffixFileName;
        // 保存文件
        File saveFile = new File(saveFilePath, saveFileName);
        try {
            multipartFile.transferTo(saveFile);
        } catch (IOException e) {
            throw new ServiceException("图片报错失败，报错信息：" + e.getMessage());
        }
        return saveFile;
    }

    /**
     * 删除多个文件
     **/
    public static void removeFiles(List<File> fileList) {
        if (Objects.nonNull(fileList)) {
            fileList.forEach(FileHelper::removeFile);
        }
    }

    /**
     * 删除文件
     **/
    public static boolean removeFile(File file) {
        if (Objects.nonNull(file) && file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 创建文件夹路径
     **/
    public static String mkFileDirs(String appId, String appModule) {
        LocalDate now = LocalDate.now();
        // 设置文件上传保存文件路径：指定文件路径 + 当前日期 
        String savePath = fileProperties.getFileStorePath() + SEPARATOR +
                appId + SEPARATOR +
                now.getYear() + SEPARATOR +
                now.getMonth() + SEPARATOR +
                now.getDayOfMonth();

        if (StringUtil.hasText(appModule)) {
            savePath = savePath + SEPARATOR + appModule;
        }
        File folder = new File(savePath);
        if (!folder.exists()) {
            boolean mkSuccess = folder.mkdirs();
            if (mkSuccess) {
                return savePath;
            } else {
                return null;
            }
        }
        return savePath;
    }

    /**
     * fileKeys 转换成 List 集合
     **/
    public static List<String> fileKeysToList(String fileKeys) {
        if (Objects.isNull(fileKeys)) {
            return null;
        }

        String[] fileKeyArray = fileKeys.split(",");
        return Arrays.stream(fileKeyArray).distinct().filter(StringUtil::hasText).toList();
    }

    /**
     * 获得文件，并将文件流写入 Response
     **/
    public static void getFileForResponse(String filePath, String fileName, HttpServletResponse response, boolean isBrowse) {
        FileInputStream fileInputStream;
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                log.error("getFileForResponse -> 文件不存在, filePath：{} , fileName：{}", filePath, fileName);
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                // 将错误信息转换成 JSON
                writer.println(JacksonUtil.toJsonStr(HttpResult.response(null, 400, "fail", "文件不存在")));
                return;
            }
            fileInputStream = new FileInputStream(file);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }

        getFileForResponse(fileInputStream, filePath, fileName, response, isBrowse);
    }

    /**
     * 获得文件，并将文件流写入 Response
     **/
    public static void getFileForResponse(InputStream inputStream, String filePath, String fileName, HttpServletResponse response, boolean isBrowse) {
        OutputStream outputStream = null;
        try {
            response.reset();
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            // 设置允许跨域的 key
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            if (isBrowse) {
                // 如果是浏览器访问
                URL url = new URL("file:///" + filePath);
                response.setContentType(url.openConnection().getContentType());
                response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            } else {
                // 非浏览器访问，直接下载
                response.setContentType("application/x-download;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            }
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("getFileForResponse -> filePath：{},fileName：{}，Exception：{}", filePath, fileName, e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
