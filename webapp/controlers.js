function sendConf() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendConf").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.responseText = false;
  xhr.open("POST", "http://localhost:8080/load/conffile");
  xhr.send(formData);
}

function sendActd() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendActd").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.responseText = false;
  xhr.open("POST", "http://localhost:8080/load/activiitydiagram");
  xhr.send(formData);
}

function sendLtl() {
  var formData = new FormData();
  formData.append("file", document.getElementById("sendLtl").files[0]);
  var xhr = new XMLHttpRequest();
  xhr.responseText = false;
  xhr.open("POST", "http://localhost:8080/load/ltlmodels");
  xhr.onreadystatechange = function(){
    if (get.readyState == 4){
      getLtl();
    }
  }
  xhr.send(formData);
}

function getLtl() {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/load/ltlpattern");
  xhr.onreadystatechange = function() {
    if (get.readyState == 4) {
      document.getElementById('outputltl').value = xhr.responseText;
  }
  xhr.send(null);
}

function getSpec() {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/load/ltlspec");
  xhr.onreadystatechange = function() {
    if (get.readyState == 4) {
      document.getElementById('outputspec').value = xhr.responseText;
  }
  xhr.send(null);
}
