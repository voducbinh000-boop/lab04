package thjava.thbuoi2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotNull;
import thjava.thbuoi2.models.Category;
import thjava.thbuoi2.repository.CategoryRepository;
import thjava.thbuoi2.repository.ProductRepository;

@Service
@Transactional
public class CategoryService {

    // Bỏ "= null" để Spring tiêm bean vào
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // Constructor để Spring tự động inject dependency
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    // 1. Lấy toàn bộ danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 2. Tìm danh mục theo ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // 3. Thêm mới danh mục
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    // 4. Cập nhật danh mục
    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Danh mục ID " + category.getId() + " không tồn tại."));
        
        // Dùng getName() thay vì getName1()
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    // 5. Xóa danh mục theo ID
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Danh mục ID " + id + " không tồn tại.");
        }
        if (productRepository.existsByCategoryId(id)) {
            throw new IllegalStateException("Danh mục ID " + id + " đang có sản phẩm, không thể xóa.");
        }
        categoryRepository.deleteById(id);
    }
}