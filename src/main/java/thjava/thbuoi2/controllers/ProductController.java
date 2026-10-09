package thjava.thbuoi2.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import thjava.thbuoi2.models.Product;
import thjava.thbuoi2.services.CategoryService;
import thjava.thbuoi2.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	// Không gán "= new ...", để Spring tự tiêm vào
	private final ProductService productService;
	private final CategoryService categoryService;

	// Constructor để Spring tự động Dependency Injection
	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping
	public String showProductList(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products/products-list";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryService.getAllCategories()); // Đổ dữ liệu danh mục ra dropdown select
		return "products/add-product";
	}

	@PostMapping("/add")
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model,
			RedirectAttributes ra) {
		if (result.hasErrors()) {
			model.addAttribute("categories", categoryService.getAllCategories()); // Nạp lại danh mục nếu form lỗi
			return "products/add-product";
		}
		try {
			productService.addProduct(product);
			ra.addFlashAttribute("success", "Đã thêm sản phẩm \"" + product.getName() + "\".");
		} catch (IllegalStateException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/products";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
		return productService.getProductById(id).map(product -> {
			model.addAttribute("product", product);
			model.addAttribute("categories", categoryService.getAllCategories()); // Nạp danh mục để người dùng thay đổi
			return "products/update-product";
		}).orElseGet(() -> {
			ra.addFlashAttribute("error", "Sản phẩm ID " + id + " không tồn tại.");
			return "redirect:/products";
		});
	}

	@PostMapping("/update/{id}")
	public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("product") Product product,
			BindingResult result, Model model, RedirectAttributes ra) {
		product.setId(id);
		if (result.hasErrors()) {
			model.addAttribute("categories", categoryService.getAllCategories());
			return "products/update-product";
		}
		try {
			productService.updateProduct(product);
			ra.addFlashAttribute("success", "Đã cập nhật sản phẩm.");
		} catch (IllegalStateException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/products";
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes ra) {
		try {
			productService.deleteProductById(id);
			ra.addFlashAttribute("success", "Đã xóa sản phẩm.");
		} catch (IllegalStateException e) {
			ra.addFlashAttribute("error", e.getMessage());
		} catch (DataAccessException e) {
			ra.addFlashAttribute("error", "Không thể xóa sản phẩm này vì đang được sử dụng.");
		}
		return "redirect:/products";
	}
}