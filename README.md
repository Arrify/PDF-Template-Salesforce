# Salesforce-PDF-Generator


# PDF Generation in Salesforce

This is a code base to generate PDFs in Salesforce using Visualforce Page, Apex, and Trigger.
This code is based on the tutorial [Generate PDF In Salesforce](https://arrify.com/generate-pdf-in-salesforce/).

## Visualforce Page

The `PDFGeneratorVFPage` Visualforce Page is used to display the PDF content. This page contains the following:

- A standard controller for the `Account` object.
- A custom `renderAs` attribute to specify that the page should be rendered as a PDF.
- An `<apex:outputText>` tag to display the PDF content.

## Apex Class

The `PDFGeneratorController` Apex Class is used to generate the PDF content. This class contains the following:

- A method named `generatePDF` that returns a `PageReference` object.
- The `generatePDF` method retrieves the current `Account` record and uses it to populate a `Map<String, Object>` object.
- The `generatePDF` method creates a new `PageReference` object and sets the page name to `PDFGeneratorVFPage`.
- The `generatePDF` method sets the `renderAs` attribute of the `PageReference` object to `"pdf"`.
- The `generatePDF` method sets the `contentDisposition` attribute of the `PageReference` object to `"attachment; filename=Account.pdf"`.
- The `generatePDF` method returns the `PageReference` object.

## Trigger

The `PDFGeneratorTrigger` Trigger is used to generate the PDF when a new `Account` record is created. This trigger contains the following:

- A `before insert` trigger handler that calls the `PDFGeneratorController.generatePDF` method.
- The `before insert` trigger handler sets the PDF content to the `Description` field of the new `Account` record.



## Limitations

This code has the following limitations:

- The PDF content is limited to the `Description` field of the `Account` record.
- The PDF is generated only when a new `Account` record is created.

## License

This code is released under the MIT License.
