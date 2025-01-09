package kiwi.shop.order.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentViewController {

	@GetMapping("success")
	public String successPage() {
		return "success";
	}

	@GetMapping("fail")
	public String failPage() {
		return "fail";
	}
}
