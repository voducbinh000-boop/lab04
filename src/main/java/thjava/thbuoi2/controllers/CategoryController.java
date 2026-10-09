package thjava.thbuoi2.controllers;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import thjava.thbuoi2.models.Category;
import thjava.thbuoi2.services.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	// Không dùng = new CategoryService()
	private final CategoryService categoryService;

	// Constructor để Spring tự động tiêm CategoryService vào
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 1. Hiển thị danh sách danh mục
	@GetMapping
	public String listCategories(Model model) {
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "categories/categories-list";
	}

	// 2. Hiển thị Form thêm mới
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("category", new Category());
		return "categories/add-category";
	}

	// 3. Xử lý lưu Form thêm mới
	@PostMapping("/add")
	public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
			RedirectAttributes ra) {
		if (result.hasErrors()) {
			return "categories/add-category";
		}
		categoryService.addCategory(category);
		ra.addFlashAttribute("success", "Đã thêm danh mục \"" + category.getName() + "\".");
		return "redirect:/categories";
	}

	// 4. Hiển thị Form cập nhật (sửa)
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
		return categoryService.getCategoryById(id).map(category -> {
			model.addAttribute("category", category);
			return "categories/update-category";
		}).orElseGet(() -> {
			ra.addFlashAttribute("error", "Danh mục ID " + id + " không tồn tại.");
			return "redirect:/categories";
		});
	}

	// 5. Xử lý lưu Form cập nhật
	@PostMapping("/update/{id}")
	public String updateCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") Category category,
			BindingResult result, RedirectAttributes ra) {
		if (result.hasErrors()) {
			category.setId(id);
			return "categories/update-category";
		}
		try {
			category.setId(id);
			categoryService.updateCategory(category);
			ra.addFlashAttribute("success", "Đã cập nhật danh mục.");
		} catch (IllegalStateException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/categories";
	}

	// 6. Xóa danh mục
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes ra) {
		try {
			categoryService.deleteCategoryById(id);
			ra.addFlashAttribute("success", "Đã xóa danh mục.");
		} catch (IllegalStateException e) {
			ra.addFlashAttribute("error", e.getMessage());
		} catch (DataAccessException e) {
			ra.addFlashAttribute("error", "Không thể xóa danh mục này vì đang được sử dụng.");
		}
		return "redirect:/categories";
	}
}