package com.projectcustomer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectcustomer.entity.Customer;
import com.projectcustomer.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> customers = customerService.findAll();

		model.addAttribute("Customers", customers);

		return "list-Customers";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// get the Customer from the service

		Customer customer = new Customer();
		// set Customer as a model attribute to pre-populate the form
		theModel.addAttribute("Customer", customer);

		// send over to our form
		return "Customer-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

		// get the Customer from the service
		Customer theCustomer = customerService.findById(theId);

		// set Customer as a model attribute to pre-populate the form
		theModel.addAttribute("Customer", theCustomer);

		// send over to our form
		return "Customer-form";
	}

	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {

		System.out.println(id);
		Customer theCustomer;
		if (id != 0) {
			theCustomer = customerService.findById(id);
			theCustomer.setFirstName(firstName);
			theCustomer.setLastName(lastName);
			theCustomer.setEmail(email);
		} else
			theCustomer = new Customer(firstName, lastName, email);
		// save the Customer
		customerService.save(theCustomer);

		// use a redirect to prevent duplicate submissions
		return "redirect:/customers/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {

		// delete the Customer
		customerService.deleteById(theId);

		// redirect to /Customers/list
		return "redirect:/customers/list";

	}

}
