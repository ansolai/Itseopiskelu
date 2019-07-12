var task = [];
document.addEventListener("DOMContentLoaded", performGetRequest1);

// function init(){
//     performGetRequest1();
// }

function suorita(task){
    var todolista = document.getElementById("tehtavatTaulukkoon");
    // todolista.innerHTML = "<tr>" + "<th>" + "ID" + "<th>" + "TASK" + "</tr>";
    for ( var i = 0; i< task.length; i++){
        var yksiTehtava = task[i];
        console.dir(yksiTehtava);
        todolista.innerHTML += "<tr>" + "<td>" + yksiTehtava.id  + "<td>"+  yksiTehtava.task + "<td>"+ "</tr>";
    }
    console.log("JSON listan läpikäynti")
}

//hae kaikki tehtävät
function performGetRequest1() {
     var kaikkiTehtavat = document.getElementById('tehtavatTaulukkoon');
    // kaikkiTehtavat.innerHTML = '';

    axios.get('/api/tehtavat/kaikki')
        .then(function (response) {
//            kaikkiTehtavat.innerHTML = generateSuccessHTMLOutput(response);
            task = response.data;
             suorita(task);
        })
        .catch(function (error) {
            console.log(error);
            kaikkiTehtavat.innerHTML += "<tr>" +  "<td>" +error.message + "<td>" + error.response.status + ' ' + error.response.statusText + "<td>" + + "<td>" + "<td>" + "</tr>";
            kaikkiTehtavat.innerHTML = generateErrorHTMLOutput(error);
        });
}

//poista tietyn Id:n perusteella
function performDeleteRequest2() {
    var todoId = document.getElementById('todoId').value;

    axios.delete('/api/tehtavat/'+ todoId,  {
        id: todoId
    })
        .then(function (response) {
            console.log("tehtava postettu: " + response);
            todoId.innerHTML = generateSuccessHTMLOutput(response);
            performGetRequest1();
        })
        .catch(function (error) {
            var poistettu = document.getElementById('virheilmoitukset');
            console.log("virhe poiston yhteydessä " + error);
            poistettu.innerHTML = generateErrorHTMLOutput(error);
        });
}

function performPostRequest(e) {
    var todoTitle = document.getElementById('todoTitle').value;
    axios.post('/api/tehtavat', {
        task: todoTitle
    })
        .then(function (response) {
            todoTitle.innerHTML = generateSuccessHTMLOutput(response);
            console.log("tehtävä luotu: " +todoTitle);
        })
        .catch(function (error) {
            todoTitle.innerHTML = generateErrorHTMLOutput(error);
        });
    e.preventDefault();
}

function generateSuccessHTMLOutput(response) {
    console.log("testi generateSuccessHTMLOutput: " + JSON.stringify(response.data, null, '\t'));
}

function generateErrorHTMLOutput(error) {
    var virheviesti = document.getElementById('virheilmoitukset');
    // virheviesti.innerHTML = "<tr>" + "<th>" +"Message"+  "<th>" +"Status"+ "<th>" +"Headers"+ "<th>" +"Data"+ "</th>";
    virheviesti.innerHTML += "<tr>" +  "<td>" +error.message + "<td>" + error.response.status + ' ' +
        error.response.statusText + "<td>" + JSON.stringify(error.response.headers, null, '\t') +
        "<td>" +JSON.stringify(error.response.data, null, '\t') + "<td>" + "</tr>";
}