function onOpen(e) {
  FormApp.getUi()
    .createAddonMenu()
    .addItem('Configure', 'showSidebar')
    .addToUi();
}

function onInstall(e) {
  onOpen(e);
}

function showSidebar() {
  var sidebarPage = HtmlService.createHtmlOutputFromFile('sidebar')
    .setTitle('Your add-on configuration');
  FormApp.getUi().showSidebar(sidebarPage);
}

function saveSettings(settings) {
  PropertiesService.getDocumentProperties().setProperties(settings);
  adjustFormSubmitTrigger();
}

function fetchSettings() {
  return PropertiesService.getDocumentProperties().getProperties();
}

function sendResponse(e) {
  var data = {
    "form": {
      "id": e.source.getId(),
      "title": e.source.getTitle() ? e.source.getTitle() : "Untitled Form",
      "is_private": e.source.requiresLogin(),
      "is_published": e.source.isAcceptingResponses(),
    },
    "response": {
      "id": e.response.getId(),
      "email": e.response.getRespondentEmail(),
      "timestamp": e.response.getTimestamp(),
      "data": e.response.getItemResponses().map(function(y) {
        return {
          h: y.getItem().getTitle(),
          k: y.getResponse()
        }
      }, this).reduce(function(r, y) {
        r[y.h] = y.k;
        return r
      }, {}),
    }
  };

  var options = {
    method: "post",
    payload: JSON.stringify(data),
    contentType: "application/json; charset=utf-8",
  };

  var settings = PropertiesService.getDocumentProperties();
  UrlFetchApp.fetch(settings.getProperty('url'), options);
};

function adjustFormSubmitTrigger() {
  var form = FormApp.getActiveForm();
  var triggers = ScriptApp.getUserTriggers(form);
  var settings = PropertiesService.getDocumentProperties();
  var url = settings.getProperty('url')
  var triggerNeeded = url && url.length > 0;
  
  // Create a new trigger if required; delete existing trigger
  //   if it is not needed.
  var existingTrigger = null;
  for (var i = 0; i < triggers.length; i++) {
    if (triggers[i].getEventType() == ScriptApp.EventType.ON_FORM_SUBMIT) {
      existingTrigger = triggers[i];
      break;
    }
  }
  if (triggerNeeded && !existingTrigger) {
    var trigger = ScriptApp.newTrigger('sendResponse')
      .forForm(form)
      .onFormSubmit()
      .create();
  } else if (!triggerNeeded && existingTrigger) {
    ScriptApp.deleteTrigger(existingTrigger);
  }
}


