package cn.edu.aiit.gradution.controller.router;

import cn.edu.aiit.gradution.pojo.entity.TopicType;
import cn.edu.aiit.gradution.pojo.vo.CategoryVo;
import cn.edu.aiit.gradution.service.TopicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

	@Autowired
	private TopicTypeService topicTypeService;

	@GetMapping("/admin/user/studentList")
	public String getStudentList(){
		return "admin/user/studentList";
	}

	@GetMapping("/admin/cagetory/list")
	public String getCagetoryList(Model model){
		List<CategoryVo> categoryList = topicTypeService.getCategoryList();
		model.addAttribute("categoryList", categoryList);
		return "admin/category/list";
	}
}
