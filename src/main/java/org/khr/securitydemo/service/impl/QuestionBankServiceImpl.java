package org.khr.securitydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.khr.securitydemo.mapper.QuestionBankMapper;
import org.khr.securitydemo.model.dto.QuestionBankQueryRequest;
import org.khr.securitydemo.model.entity.QuestionBank;
import org.khr.securitydemo.model.vo.QuestionBankVO;
import org.khr.securitydemo.service.QuestionBankService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 3031208
 * @description 针对表【question_bank(题库)】的数据库操作Service实现
 * @createDate 2025-05-12 15:29:16
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
        implements QuestionBankService {

    @Override
    public Wrapper<QuestionBank> getQueryWrapper(QuestionBankQueryRequest questionBankQueryRequest) {
        Long id = questionBankQueryRequest.getId();
        Long notId = questionBankQueryRequest.getNotId();
        String searchText = questionBankQueryRequest.getSearchText();
        String title = questionBankQueryRequest.getTitle();
        String description = questionBankQueryRequest.getDescription();
        String picture = questionBankQueryRequest.getPicture();
        Long userId = questionBankQueryRequest.getUserId();
        boolean needQueryQuestionList = questionBankQueryRequest.isNeedQueryQuestionList();
        long current = questionBankQueryRequest.getCurrent();
        long pageSize = questionBankQueryRequest.getPageSize();
        String sortField = questionBankQueryRequest.getSortField();
        String sortOrder = questionBankQueryRequest.getSortOrder();

        return null;
    }

    @Override
    public Page<QuestionBankVO> getQuestionBankVOPage(Page<QuestionBank> questionBankPage, HttpServletRequest request) {
        return null;
    }
}




