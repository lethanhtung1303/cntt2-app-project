package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtQuaTrinhDaoTaoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtQuaTrinhDaoTaoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.model.condition.TrainingProcessCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class TrainingProcessRepositoryImp implements TrainingProcessRepository {
    private final TdtQuaTrinhDaoTaoMapper trainingProcessMapper;

    @Override
    public Long countTrainingProcess(TrainingProcessCondition condition) {
        TdtQuaTrinhDaoTaoExample example = new TdtQuaTrinhDaoTaoExample();
        TdtQuaTrinhDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingProcessMapper.countByExample(example);
    }

    @Override
    public List<TdtQuaTrinhDaoTao> findTrainingProcess(TrainingProcessCondition condition) {
        TdtQuaTrinhDaoTaoExample example = new TdtQuaTrinhDaoTaoExample();
        TdtQuaTrinhDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingProcessMapper.selectByExample(example);
    }

    @Override
    public List<TdtQuaTrinhDaoTao> create(TdtQuaTrinhDaoTao record) {
        int insert = trainingProcessMapper.insertSelective(record);
        if (insert > 0) {
            TdtQuaTrinhDaoTaoExample example = new TdtQuaTrinhDaoTaoExample();
            TdtQuaTrinhDaoTaoExample.Criteria criteria = example.createCriteria();
            criteria.andGiangVienIdEqualTo(record.getGiangVienId());
            criteria.andTrinhDoIdEqualTo(record.getTrinhDoId());
            if (StringUtil.isNotNullOrEmptyString(record.getTruong())) {
                criteria.andTruongEqualTo(record.getTruong());
            }
            if (StringUtil.isNotNullOrEmptyString(record.getNganh())) {
                criteria.andNganhEqualTo(record.getNganh());
            }
            criteria.andNamTotNghiepEqualTo(record.getNamTotNghiep());
            if (StringUtil.isNotNullOrEmptyString(record.getDeTaiTotNghiep())) {
                criteria.andDeTaiTotNghiepEqualTo(record.getDeTaiTotNghiep());
            }
            if (StringUtil.isNotNullOrEmptyString(record.getNguoiHuongDan())) {
                criteria.andNguoiHuongDanEqualTo(record.getNguoiHuongDan());
            }
            criteria.andLoaiTotNghiepIdEqualTo(record.getLoaiTotNghiepId());
            if (StringUtil.isNotNullOrEmptyString(record.getCreatedBy())) {
                criteria.andCreatedByEqualTo(record.getCreatedBy());
            }
            return trainingProcessMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    @Override
    public int update(TdtQuaTrinhDaoTao record, BigDecimal processId) {
        TdtQuaTrinhDaoTaoExample example = new TdtQuaTrinhDaoTaoExample();
        TdtQuaTrinhDaoTaoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(processId).ifPresent(criteria::andIdEqualTo);
        return trainingProcessMapper.updateByExampleSelective(record, example);
    }
}
