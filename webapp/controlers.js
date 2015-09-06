function sendConf() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendConf").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/load/conffile");
  xhr.onreadystatechange = function(){showAlert(xhr);}
  xhr.send(formData);

}

function sendActd() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendActd").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/load/activitydiagram");
  xhr.onreadystatechange = function(){showAlert(xhr);}
  xhr.send(formData);
}

function sendLtl() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendLtl").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.responseText = false;
  xhr.open("POST", "http://localhost:8080/load/ltlmodels");
  xhr.onreadystatechange = function(){getLtl(xhr);}
  xhr.send(formData);
}

function getLtl(xhr){
  if (xhr.readyState == 4  && xhr.status == 200){
     var xhrLtl = new XMLHttpRequest();
     xhrLtl.open("POST", "http://localhost:8080/data/ltlpattern");
     xhrLtl.onreadystatechange = function(){
      if (xhr.readyState == 4  && xhr.status == 200){
      var json = xhrLtl.responseText,
      obj = JSON.parse(json);
      if (obj.errorMessage == null){
        document.getElementById("outputltl").value = obj.payload;}
      else{
        alert(obj.errorMessage);
        document.getElementById("outputltl").value = "";}
      }}
     xhrLtl.send(null);}
}

function getSpec() {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/data/ltlspec");
  // xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.onreadystatechange = function(){
    if (xhr.readyState == 4  && xhr.status == 200){
      var json = xhr.responseText,
      obj = JSON.parse(json);
      if (obj.errorMessage == null){
        document.getElementById("outputspec").value = obj.payload;}
      else{
        alert(obj.errorMessage);
        document.getElementById("outputspec").value = "";} //czyszczenie textarea?
      }}
  xhr.send(null);
  // sendConf();
  // sendActd();
}

function showAlert(xhr){
 if (xhr.readyState == 4  && xhr.status == 200){
      var json = xhr.responseText,
      obj = JSON.parse(json);
      if (obj.errorMessage !== null){
       alert(obj.errorMessage);}
      }}
