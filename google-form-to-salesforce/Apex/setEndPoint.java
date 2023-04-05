@RestResource(UrlMapping='/v1/form-response')
global without sharing class GoogleFormEndPointDemo {
    @HttpPost
    global static void getFormResponse() {

        RestRequest req = RestContext.request;
        Blob body = req.requestBody;
        String requestString = body.toString();
        RestContext.response.responseBody = Blob.valueOf(requestString);
        RestContext.response.statusCode = 200;

        System.debug('Form Response:'+requestString);
        return;
    }
}	