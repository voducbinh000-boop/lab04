package thjava.thbuoi2.models;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Tên danh mục không được để trống")
	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(Long id2) {
		// TODO Auto-generated method stub
		
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String name2) {
		// TODO Auto-generated method stub
		
	}

	public String getName1() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName2() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName3() {
		// TODO Auto-generated method stub
		return null;
	}
}
