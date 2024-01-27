package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtNgonNguDaoTaoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtNgonNguDaoTaoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNguDaoTao;
import com.tdtu.webproject.mybatis.condition.TrainingLanguageCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class TrainingLanguageRepositoryImp implements TrainingLanguageRepository {
    private final TdtNgonNguDaoTaoMapper ngonNguDaoTaoMapper;

    @Override
    public Long countAllNgonNguDaoTao(TrainingLanguageCondition condition) {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingProcessIds())) {
            criteria.andQuaTrinhDaoTaoIdIn(condition.getTrainingProcessIds());
        }
        criteria.andIsActiveEqualTo(true);
        return ngonNguDaoTaoMapper.countByExample(example);
    }

    @Override
    public List<TdtNgonNguDaoTao> findNgonNguDaoTao(TrainingLanguageCondition condition) {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingProcessIds())) {
            criteria.andQuaTrinhDaoTaoIdIn(condition.getTrainingProcessIds());
        }
        criteria.andIsActiveEqualTo(true);
        return ngonNguDaoTaoMapper.selectByExample(example);
    }

    @Override
    public Long countAllNgonNguDaoTao() {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return ngonNguDaoTaoMapper.countByExample(example);
    }

    @Override
    public List<TdtNgonNguDaoTao> getAllNgonNguDaoTao() {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return ngonNguDaoTaoMapper.selectByExample(example);
    }

    @Override
    public int create(TdtNgonNguDaoTao record) {
        return ngonNguDaoTaoMapper.insertSelective(record);
    }

    @Override
    public int deleteByTrainingId(BigDecimal trainingId) {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(trainingId).ifPresent(criteria::andQuaTrinhDaoTaoIdEqualTo);
        TdtNgonNguDaoTao record = TdtNgonNguDaoTao.builder()
                .isActive(false)
                .build();
        return ngonNguDaoTaoMapper.updateByExampleSelective(record, example);
    }
}
