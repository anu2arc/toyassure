package com.increff.assure.controller;

import com.increff.assure.model.data.InfoData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

	@Value("${app.baseUrl}")
	private String baseUrl;

	@RequestMapping(value = "")
	public String index() {
		return "index.html";
	}

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/party")
	public ModelAndView brand() {
		return mav("party.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return mav("products.html");
	}

	@RequestMapping(value = "/ui/bin")
	public ModelAndView inventory() {
		return mav("bin.html");
	}

	@RequestMapping(value = "/ui/channel")
	public ModelAndView placeOrder() {
		return mav("channel.html");
	}

	@RequestMapping(value = "/ui/channelListing")
	public ModelAndView viewOrder() {
		return mav("channelListing.html");
	}

	@RequestMapping(value = "/ui/order")
	public ModelAndView reports() {
		return mav("order.html");
	}

	private ModelAndView mav(String page) {
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", new InfoData());
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

}
