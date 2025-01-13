package kiwi.shop.order.adapter.in.web;

import kiwi.shop.order.adapter.in.web.request.PaymentCheckOutRequest;
import kiwi.shop.order.application.port.in.PaymentCheckOutCommand;
import kiwi.shop.order.application.port.in.RequestOrderCheckOutUseCase;
import kiwi.shop.order.domain.PaymentCheckOutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentCheckoutController {

	private final RequestOrderCheckOutUseCase requestOrderCheckOutUseCase;

	@GetMapping
	public String checkoutPage(PaymentCheckOutRequest request, Model model) {

		PaymentCheckOutCommand paymentCheckOutCommand = PaymentCheckOutCommand.of(request.getProductNoList(), request.getMemberNo());
		PaymentCheckOutResponse paymentCheckOut = requestOrderCheckOutUseCase.paymentCheckOut(paymentCheckOutCommand);

		model.addAttribute("orderId", paymentCheckOut.getOrderId());
		model.addAttribute("amount", paymentCheckOut.getAmount());
		model.addAttribute("orderName", paymentCheckOut.getOrderName());

		return "checkout";
	}
}