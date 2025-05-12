package org.khr.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.khr.securitydemo.mapper.QuestionMapper;
import org.khr.securitydemo.model.dto.QuestionQueryRequest;
import org.khr.securitydemo.model.entity.Question;
import org.khr.securitydemo.model.vo.QuestionVO;
import org.khr.securitydemo.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 3031208
 * @description 针对表【question(题目)】的数据库操作Service实现
 * @createDate 2025-05-12 15:29:16
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Override
    public Page<Question> listQuestionByPage(QuestionQueryRequest questionQueryRequest) {
        return null;
    }

    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request) {
        return null;
    }
}




