package org.khr.securitydemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.khr.securitydemo.model.dto.QuestionBankQueryRequest;
import org.khr.securitydemo.model.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;
import org.khr.securitydemo.model.vo.QuestionBankVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 3031208
* @description 针对表【question_bank(题库)】的数据库操作Service
* @createDate 2025-05-12 15:29:16
*/
public interface QuestionBankService extends IService<QuestionBank> {

    Wrapper<QuestionBank> getQueryWrapper(QuestionBankQueryRequest questionBankQueryRequest);

    Page<QuestionBankVO> getQuestionBankVOPage(Page<QuestionBank> questionBankPage, HttpServletRequest request);

}
