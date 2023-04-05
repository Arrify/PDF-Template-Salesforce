//This method take a JSON string as input and processes it to create a new Contact_Gooogle_form__c record.
public static void pushData(String jsonBody){

	//Remove any question marks symbol from the JSON string.
	String responseBody = jsonBody.replaceAll('\\?', '');
	System.debug(responseBody);

	responseBody = responseBody.replaceAll('"Phone number":', '"Phone_number":');
	responseBody = responseBody.replaceAll('"I would like to receive updates about your products and services":', '"receive_updates":');
	responseBody = responseBody.replaceAll('"How did you hear about us":', '"about_us":');

	//Deserialize the JSON string into a GoogleFormResponse object
	GoogleFormResponse result = (GoogleFormResponse) System.JSON.deserialize(responseBody, GoogleFormResponse.class);

	// Create a new Contact_form_Google__c record and populate its fields with values from the deserialize JSON data.
	Contact_Google_Form__c  contactForm = new Contact_Google_Form__c();
	contactForm.Name__c = result.response.data.Name;
	contactForm.Email__c = result.response.data.Email;
	contactForm.Phone_Number__c = result.response.data.Phone_number;
	contactForm.Subject__c = result.response.data.Subject;
	contactForm.Message__c = result.response.data.Message;
	contactForm.How_did_you_hear_about_us__c = result.response.data.about_us;
	contactForm.I_would_like_to_receive_updates__c = result.response.data.receive_updates;
	
	//insert the new Contact_Google_Form__c record into database.
	insert contactForm;
}
