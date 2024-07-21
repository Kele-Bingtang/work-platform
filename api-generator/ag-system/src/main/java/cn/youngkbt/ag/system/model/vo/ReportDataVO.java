package cn.youngkbt.ag.system.model.vo;

import cn.youngkbt.ag.system.model.vo.component.ProFormSchemaVO;
import cn.youngkbt.ag.system.model.vo.component.ProTableColumnsVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/7/21 17:58:26
 * @note
 */
@Data
@Accessors(chain = true)
public class ReportDataVO {
    private List<ProTableColumnsVO> proTableColumnsList;
    private List<ProFormSchemaVO> proFormSchemaList;
    private ReportVO report;
}
