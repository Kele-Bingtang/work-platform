package cn.youngkbt.uac.demo.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.encrypt.annotation.ApiEncrypt;
import cn.youngkbt.uac.demo.model.DemoEncryptPO;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/6/9 01:30:35
 * @note
 */
@RestController
@RequestMapping("/demo/encrypt")
public class DemoEncryptController {

    @PostMapping("/test/save")
    public Response<String> testSaveEncrypt() {
        DemoEncryptPO demoEncryptPo = new DemoEncryptPO();

        demoEncryptPo.setTestKey("123456789");
        demoEncryptPo.setValue("测试 Encrypt");
        Db.save(demoEncryptPo);

        return HttpResult.ok("testEncrypt");
    }

    @GetMapping("/test/query/{id}")
    public Response<DemoEncryptPO> testQueryEncrypt(@PathVariable Long id) {
        DemoEncryptPO demoEncryptPo = Db.getById(id, DemoEncryptPO.class);

        return HttpResult.ok(demoEncryptPo);
    }

    @GetMapping("/test/api/{id}")
    @ApiEncrypt
    public Response<DemoEncryptPO> testApiEncrypt(@PathVariable Long id) {
        DemoEncryptPO demoEncryptPo = Db.getById(id, DemoEncryptPO.class);

        return HttpResult.ok(demoEncryptPo);
    }
}
