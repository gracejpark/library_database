function verify() {

	var password = document.forms['form']['password'].value;
	
	var username = document.forms['form']['userName'].value;
	
	if (password == null || password == "" || 
		username == null || username == "") {
		
		document.getElementById("error").innerHTML 
			= "⚠️ User name and password are required";
		return false;
	}else{
			return true;
	}
}

function verifyBook(){
	
	var title = document.forms['form']['title'].value;
	
	var author = document.forms['form']['author'].value;
	
	if (title == null || title == "" || 
		author == null || author == "") {
		
		document.getElementById("error").innerHTML 
			= "Author and title are required.";
		return false;
	}
	return true;
}


function verifyReview(){
	
	var textArea = document.forms['form']['textArea'].value;
	
	if (textArea == null || textArea == "") {
		
		document.getElementById("error").innerHTML 
			= "Review is required.";
		return false;
	}
	return true;
}

const reviewBtn = document.querySelector('.btn_02');
const reviews = document.querySelector('.reviews');

reviewBtn.addEventListener('click', () => {
		reviews.classList.toggle('active');
});
