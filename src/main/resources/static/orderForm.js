const selectEstimate = document.getElementById("estimatesId");

const divAverageDailyRate = document.getElementById("averageDailyRate");
const divNumberOfDays = document.getElementById("numberOfDays");
const divTotalHT = document.getElementById("totalHT");
const divTva = document.getElementById("tva");
const divTotalTTC = document.getElementById("totalTTC");


selectEstimate.addEventListener("change", function(){
	if (selectEstimate.value > 0) {
		let optionSelected = selectEstimate.options[selectEstimate.selectedIndex],
			averageDailyRate = parseFloat(optionSelected.dataset.averagedailyrate),
			numberOfDays= parseFloat(optionSelected.dataset.numberofdays),
			totalHT = averageDailyRate*numberOfDays,
			tva = parseFloat(optionSelected.dataset.tva),
			totalTTC = totalHT*(100+tva)/100;
		console.log(averageDailyRate, numberOfDays, totalHT, tva, totalTTC);
			
		divAverageDailyRate.innerText = averageDailyRate+' €';
		divNumberOfDays.innerText = numberOfDays;
		divTotalHT.innerText = totalHT+' €';
		divTva.innerText = tva+' %';
		divTotalTTC.innerText = totalTTC+' €';
	}
	else {
		divAverageDailyRate.innerText = '';
		divNumberOfDays.innerText = '';
		divTotalHT.innerText = '';
		divTva.innerText = '';
		divTotalTTC.innerText = '';
	}
})