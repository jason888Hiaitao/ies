package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.PendingExamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EtExamPaperMapper {
    void insertExamPaper(EtExamPaper examPaper);

    int updateExamPaper(EtExamPaper examPaper);

    int deleteExamPaper(@Param("id") Long id);

    @Select("SELECT * FROM et_exam_paper WHERE id = #{id}")
    EtExamPaper getExamPaperById(@Param("id") Long id);

    List<EtExamPaper> listExamPapersByCreator(@Param("createdBy") Long createdBy);

    List<EtExamPaper> listExamPapersForUser(@Param("userId") String userId);

    List<EtExamPaper> listExamPapersForGroup(@Param("groupId") String groupId);

    List<EtExamPaper> listAllExamPapers();

    int updateExamPaperStatus(@Param("id") Long id, @Param("status") String status);

    // 获取用户已完成的试卷ID列表（提交时间不为空）
    List<Long> getCompletedExamPaperIds(@Param("userId") Long userId);

    // 获取用户可见的试卷列表（is_visible=1，paper_status=1，且部门或群组匹配）
    List<EtExamPaper> getVisibleExamPapersForUser(@Param("userId") Long userId);

    // 获取用户待参加的考试列表
    List<PendingExamDTO> getPendingExamsForUser(@Param("userId") Long userId);

    // 根据用户ID和部门查询试卷
    List<EtExamPaper> getExamPapersByUserAndDepartment( @Param("department") String department);

    // 根据用户ID和validsource查询试卷
    List<EtExamPaper> getExamPapersByUserAndValidSource(@Param("validSource") String validSource);

    // 根据field_id列表查询有效的试卷
    List<EtExamPaper> getExamPapersByFieldIds(@Param("fieldIds") List<Long> fieldIds);
}