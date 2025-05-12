package org.khr.securitydemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.khr.securitydemo.model.dto.QuestionQueryRequest;
import org.khr.securitydemo.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import org.khr.securitydemo.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 3031208
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2025-05-12 15:29:16
*/
public interface QuestionService extends IService<Question> {

    Page<Question> listQuestionByPage(QuestionQueryRequest questionQueryRequest);

    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
}
