package com.kodlamaio.rentACar.business.requests.individualCustomers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
	@NotBlank
	@NotNull
	@Size(min = 3, max = 50, message = "Must be at least three characters.")
	private String firstName;

	@NotBlank
	@NotNull
	@Size(min = 3, max = 50, message = "Must be at least three characters.")
	private String lastName;
	@NotBlank
	@NotNull
	@Pattern(regexp = "[0-9]{11}", message = "Length must be 11")
	private String nationalIdentification;

	@NotNull
	private int birthYear;

	@NotBlank
	@NotNull
	@Pattern(regexp = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
			+ "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$", message = "Write according to the rules name@domain.com")
	private String email;

	@NotBlank
	@NotNull
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "Conditions do not meet")
	// en az bir büyük harf, bir küçük harf ve sayıdan oluşan parola için
	private String password;

	@NotNull
//	@Pattern(regexp = "/^(05)([0-9]{2})\\s?([0-9]{3})\\s?([0-9]{2})\\s?([0-9]{2})$/")
	private String phoneNumber;

	@NotNull
	private String customerNumber;

	private int minFindeksScore;

}
