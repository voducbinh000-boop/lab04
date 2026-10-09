package thjava.thbuoi2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// Truy cập http://localhost:9999/ sẽ chuyển sang trang sản phẩm
	@GetMapping("/")
	public String home() {
		return "redirect:/products";
	}
}
