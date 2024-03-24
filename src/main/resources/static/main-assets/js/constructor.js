var numberOfExercises = 0;

var exerciseData = {
    exercisesJsonData : []
};

function addExercise(){

    event.preventDefault();

    let exerciseName = document.getElementById('inputExerciseName').value;
    let exerciseSets = document.getElementById('inputSets').value;
    let exerciseReps = document.getElementById('inputReps').value;
    let exerciseTime = document.getElementById('inputTime').value;
    let exerciseDate = document.getElementById('inputDate').value;
    let exerciseVideoLink = document.getElementById('inputVideoLink').value;

    let exercise = {
        "id": numberOfExercises,
        "name": exerciseName,
        "sets": exerciseSets,
        "reps": exerciseReps,
        "time": exerciseTime,
        "date": exerciseDate,
        "videoLink": exerciseVideoLink
    }

    exerciseData.exercisesJsonData.push(exercise);

    var tbodyRef = document.getElementById('exercisesTable').getElementsByTagName('tbody')[0];
    var newRow = tbodyRef.insertRow();
    numberOfExercises += 1;

    var newIdCell = newRow.insertCell();
    newIdCell.className = 'border-bottom-0';
    newIdCell.innerHTML = "<div class=\"d-flex align-items-center gap-2\">\n" +
        "<span class=\"badge bg-primary rounded-3 fw-semibold\">" + numberOfExercises + "</span>\n" +
        "</div>"

    var newNameCell = newRow.insertCell();
    newNameCell.className = 'border-bottom-0';
    newNameCell.innerHTML = "<h6 class=\"fw-semibold mb-1\">" + exerciseName + "</h6>";

    var newSetsCell = newRow.insertCell();
    newSetsCell.className = 'border-bottom-0';
    newSetsCell.innerHTML = "<div class=\"d-flex align-items-center gap-2\">\n" +
        "<span class=\"badge bg-secondary rounded-3 fw-semibold\">" + exerciseSets + "</span>\n" +
        "</div>"

    var newRepsCell = newRow.insertCell();
    newRepsCell.className = 'border-bottom-0';
    newRepsCell.innerHTML = "<div class=\"d-flex align-items-center gap-2\">\n" +
        "<span class=\"badge bg-danger rounded-3 fw-semibold\">" + exerciseReps + "</span>\n" +
        "</div>"

    var newTimeCell = newRow.insertCell();
    newTimeCell.className = 'border-bottom-0';
    newTimeCell.innerHTML = "<div class=\"d-flex align-items-center gap-2\">\n" +
        "<span class=\"badge bg-success rounded-3 fw-semibold\">" + exerciseTime + "</span>\n" +
        "</div>"

    var newDateCell = newRow.insertCell();
    newDateCell.className = 'border-bottom-0';
    newDateCell.innerHTML = "<div class=\"d-flex align-items-center gap-2\">\n" +
        "<span class=\"badge bg-primary rounded-3 fw-semibold\">" + exerciseDate + "</span>\n" +
        "</div>"

    var newDeleteCell = newRow.insertCell();
    newDeleteCell.className = 'border-bottom-0';
    newDeleteCell.innerHTML ="<a href='javascript:removeItem(" + numberOfExercises.toString() + ")'><i class=\"ti ti-minus text-danger\"></a>";

}

function removeItem(exerciseNumber){
    var tbodyRef = document.getElementById('exercisesTable').getElementsByTagName('tbody')[0];
    var rows = tbodyRef.getElementsByTagName("tr");
    for (let i = 0; i < numberOfExercises; i++) {
        var rowToRemove = rows[i].getElementsByTagName("td")[0];
        var actualId = rowToRemove.getElementsByTagName("div")[0].getElementsByTagName("span")[0].textContent;
        // alert(actualId);
        if(actualId == exerciseNumber){
            // alert('Remove!');
            tbodyRef.deleteRow(i);

            // const objectToRemove = exerciseData.exercisesJsonData.filter(e => e.id === exerciseNumber);
            exerciseData.exercisesJsonData.splice(i);

            break;
        }
    }
}
