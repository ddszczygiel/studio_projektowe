function sendConf() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendConf").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/load/conffile");
  xhr.onreadystatechange = function(){
    // showAlert(xhr);
    if (xhr.readyState == 4  && xhr.status == 200){
      var json = xhr.responseText,
      obj = JSON.parse(json);
      if (obj.errorMessage !== null){
        var comment = "&emsp;Status: Wystąpił błąd podczas wczytywania pliku konfiguracyjnego.&emsp;Komunikat: " + obj.errorMessage;
        document.getElementById("footer").innerHTML = comment;
        alert(obj.errorMessage);}
      else{
        var comment = "&emsp;Status: Pomyślnie wczytano plik konfiguracyjny";
        document.getElementById("footer").innerHTML = comment;
       }
      }
  }
  xhr.send(formData);
}

function sendActd() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendActd").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/load/activitydiagram");
  xhr.onreadystatechange = function(){
    if (xhr.readyState == 4  && xhr.status == 200){
      var json = xhr.responseText,
      obj = JSON.parse(json);
      if (obj.errorMessage !== null){
        var comment = "&emsp;Status: Wystąpił błąd podczas wczytywania pliku zawierającego model diagramu.&emsp;Komunikat: " + obj.errorMessage;
        document.getElementById("footer").innerHTML = comment;
        alert(obj.errorMessage);}
      else{
        var comment = "&emsp;Status: Pomyślnie wczytano plik zawierający model diagramu";
        document.getElementById("footer").innerHTML = comment;
       }
      }
  }
  xhr.send(formData);
}

function sendLtl() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendLtl").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.responseText = false;
  xhr.open("POST", "http://localhost:8080/load/ltlmodels");
  xhr.onreadystatechange = function(){
    if (xhr.readyState == 4  && xhr.status == 200){
      var json = xhr.responseText,
      obj = JSON.parse(json);
      if (obj.errorMessage !== null){
        var comment = "&emsp;Status: Wystąpił błąd podczas wczytywania pliku z formułami LTL.&emsp;Komunikat: " + obj.errorMessage;
        document.getElementById("footer").innerHTML = comment;
        alert(obj.errorMessage);}
      else{
        var comment = "&emsp;Status: Pomyślnie wczytano plik ze zdefiniowanymi formułami LTL";
        document.getElementById("footer").innerHTML = comment;
       }
      }
  }
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
        var comment = "&emsp;Status: Pomyślnie wygenerowano specyfikację!";
        document.getElementById("footer").innerHTML = comment;
        document.getElementById("outputltl").value = obj.payload;} //obj.payload
      else{
        alert(obj.errorMessage);
        var comment = "&emsp;Status: Wystąpił błąd podczas generowania wzorca LTL.&emsp;Komunikat: " + obj.errorMessage;
        document.getElementById("footer").innerHTML = comment;
        document.getElementById("outputltl").value = "";}
      }}
     xhrLtl.send(null);}
}

function getSpec() {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/data/ltlspec");
  xhr.onreadystatechange = function(){
    if (xhr.readyState == 4  && xhr.status == 200){
      var json = xhr.responseText,
      obj = JSON.parse(json);
      if (obj.errorMessage == null){
        getLtl(xhr);
        var spec = obj.payload;
        var out = "";
        spec.forEach(function(value) {
        out += value + "\n";})
        document.getElementById("outputspec").value = out;}
      else{
        alert(obj.errorMessage);
        var comment = "&emsp;Status: Wystąpił błąd podczas generowania specyfikacji.&emsp;Komunikat: " + obj.errorMessage;
        document.getElementById("footer").innerHTML = comment;
        document.getElementById("outputspec").value = "";} //czyszczenie textarea?
      }}
  xhr.send(null);
}
