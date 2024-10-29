package cn.youngkbt.integrate.system.model.bo;

import lombok.*;

import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2024-10-28 21:16:50
 * @note 
*/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFlowBO {
    private ConfigPoolBO sourceInfo;
    private Map<String, Object> sourceAuthInfo;
    private Map<String, Object> sourceDeliverInfo;
}