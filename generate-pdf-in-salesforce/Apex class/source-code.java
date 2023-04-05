// Create a new page reference
 PageReference MyFirstPDF = new PageReference('/apex/MyFirstPDF');

// Set the account id as a parameter
 MyFirstPDF.getParameters().put('accountId', someAccountId);


// Create a new PDF document and add the contents of the page reference to it
 ContentVersion cont = new ContentVersion();
 cont.Title = 'MyFirstPDF';
 cont.PathOnClient = 'MyFirstPDF.pdf';
 cont.VersionData = MyFirstPDF.getContentAsPDF();
 cont.Origin = 'H';

 insert cont; 

 Messaging.SingleEmailMessage mail = new Messaging.SingleEmailMessage();
 mail.setToAddresses(new String[] {'your@email.com'});
 mail.setSubject('Generate PDF from Apex Salesforce');
 mail.setHtmlBody('Generate PDF from Apex Salesforce');
 mail.setFileAttachments(new Messaging.EmailFileAttachment[] {attach});
 
 //Send the email
 Messaging.sendEmail(new Messaging.SingleEmailMessage[] {mail});