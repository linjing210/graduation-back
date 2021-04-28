package cn.edu.aiit.gradution.mapper;

import cn.edu.aiit.gradution.pojo.entity.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Repository
public interface FileMapper extends BaseMapper<FileInfo> {
	int addFileRelation(@Param("fileIds") List<Long> fileIds,
	                 @Param("relationId") Integer relationId,
	                 @Param("typeId") Integer typeId);

	@Select("select * from file f, file_ref fr where f.fileId = fr.fileId and fr.typeId = #{typeId} and fr.relationId = #{relationId}")
	List<FileInfo> getFileListByRef(@Param("relationId") Integer relationId, @Param("typeId") Integer typeId);
}
