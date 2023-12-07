package com.example.pj2be.service.inquiryservice;

import com.example.pj2be.domain.answer.AnswerDTO;
import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.mapper.inquirymapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class InquiryService {

    private final InquiryMapper mapper;

    public List<InquiryDTO> list() {
        return mapper.selectAll();

    }

    public void add(InquiryDTO dto) {

        mapper.insert(dto);
        System.out.println(dto.getInquiry_member_id() + "유저가 " + "문의게시판에 글을 작성하였습니다.(" + dto.getId() + "번 게시물)" );
    }

    public boolean validate(InquiryDTO dto) {

        if (dto == null) {
            return false;
        }
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            return false;
        }
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            return false;
        }
        if (dto.getInquiry_member_id() == null || dto.getInquiry_member_id().isBlank()) {
            return false;
        }
        if (dto.getInquiry_category() == null || dto.getInquiry_category().isBlank()) {
            return false;
        }
            return true;

    }

    public InquiryDTO get(Integer id) {
        return mapper.selectByInquiryId(id);
    }

    public void delete(Integer id) {
        mapper.deleteByInquiryId(id);
    }

    public void update(InquiryDTO dto) {

        mapper.update(dto);
    }

//    답변추가
    public void answerAdd(AnswerDTO dto) {
        mapper.insertAnswer(dto);

        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setId(dto.getAnswer_board_id());
        inquiryDTO.setAnswer_status("답변완료");
        mapper.updateAnswerState(inquiryDTO);


    }
}
