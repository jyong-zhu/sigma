package com.zone.process.application.service.query;

import com.zone.commons.entity.Page;
import com.zone.mybatis.util.PlusPageConverter;
import com.zone.process.application.service.query.assembler.CategoryDetailDTOAssembler;
import com.zone.process.application.service.query.dto.CategoryDetailDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;
import com.zone.process.infrastructure.db.query.ProcessCategoryQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:45 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessCategoryQueryService {

    @Autowired
    private ProcessCategoryQuery categoryQuery;

    /**
     * 分页查询流程分类
     */
    public Page<CategoryDetailDTO> page(String name, Integer pageNo, Integer pageSize) {

        Page<ProcessCategoryDO> page = PlusPageConverter.convert(categoryQuery.page(name, pageNo, pageSize));

        return page.convert(tmp -> CategoryDetailDTOAssembler.getCategoryDetailDTO(tmp));
    }
}
