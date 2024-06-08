package cn.youngkbt.encrypt.config;

import cn.youngkbt.encrypt.core.EncryptorManager;
import cn.youngkbt.encrypt.interceptor.MybatisDecryptInterceptor;
import cn.youngkbt.encrypt.interceptor.MybatisEncryptInterceptor;
import cn.youngkbt.encrypt.properties.EncryptorProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 23:40:17
 * @note
 */
@AutoConfiguration(after = MybatisPlusAutoConfiguration.class)
@EnableConfigurationProperties(EncryptorProperties.class)
@ConditionalOnProperty(value = "mybatis-encryptor.enable", havingValue = "true")
@Slf4j
@RequiredArgsConstructor
public class EncryptorAutoConfiguration {
    
    private final EncryptorProperties properties;

    @Bean
    public EncryptorManager encryptorManager() {
        return new EncryptorManager(properties.getClassPackage());
    }

    @Bean
    public MybatisEncryptInterceptor mybatisEncryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisEncryptInterceptor(encryptorManager, properties);
    }

    @Bean
    public MybatisDecryptInterceptor mybatisDecryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisDecryptInterceptor(encryptorManager, properties);
    }
}
