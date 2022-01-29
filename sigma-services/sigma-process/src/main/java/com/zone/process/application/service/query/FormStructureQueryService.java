package com.zone.process.application.service.query;

import com.google.common.base.Preconditions;
import com.zone.commons.entity.Page;
import com.zone.mybatis.util.PlusPageConverter;
import com.zone.process.application.service.query.assembler.FormStructureDTOAssembler;
import com.zone.process.application.service.query.dto.FormStructureDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.query.FormStructureQuery;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:46 上午
 * @Description:
 */
@Slf4j
@Service
public class FormStructureQueryService {

  @Autowired
  private FormStructureQuery formStructureQuery;

  /**
   * 查看表单详情
   */
  public FormStructureDTO detail(String formKey) {
    FormStructureDO formDO = formStructureQuery.queryByKey(formKey);
    Preconditions.checkNotNull(formDO, "表单不存在");

    return FormStructureDTOAssembler.getFormStructureDTO(formDO);
  }

  /**
   * 分页查询表单
   */
  public Page<FormStructureDTO> page(String name, Integer pageNo, Integer pageSize) {

    Page<FormStructureDO> page = PlusPageConverter.convert(formStructureQuery.page(name, pageNo, pageSize));

    return page.convert(FormStructureDTOAssembler::getFormStructureDTO);
  }

  /**
   * 查询表单列表
   */
  public List<FormStructureDTO> list(String name) {

    List<FormStructureDO> list = formStructureQuery.list(name);

    return FormStructureDTOAssembler.getFormStructureDTOList(list);
  }
}
