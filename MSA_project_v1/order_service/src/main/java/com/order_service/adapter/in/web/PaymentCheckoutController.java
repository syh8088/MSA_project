package com.order_service.adapter.in.web;

import com.order_service.adapter.in.web.request.PaymentCheckOutRequest;
import com.order_service.application.port.in.PaymentCheckOutCommand;
import com.order_service.application.port.in.RequestOrderCheckOutUseCase;
import com.order_service.domain.PaymentCheckOutResponse;
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

		PaymentCheckOutCommand paymentCheckOutCommand = PaymentCheckOutCommand.of(request.getProductNoList());
		PaymentCheckOutResponse paymentCheckOut = requestOrderCheckOutUseCase.paymentCheckOut(paymentCheckOutCommand);

		model.addAttribute("orderId", paymentCheckOut.getOrderId());
		model.addAttribute("amount", paymentCheckOut.getAmount());
		model.addAttribute("orderName", paymentCheckOut.getOrderName());

		return "checkout";
	}
}