/**
 * 
 */



const toggleBtn = document.querySelector('.navbar_toggleBtn');
const menu = document.querySelector('.navbar_menu');
const icons = document.querySelector('.navbar_icons');

toggleBtn.addEventListener('click', () => {
	menu.classList.toggle('active');
	icons.classList.toggle('active');
});

var slides = document.querySelector('.slides'),
	slide = document.querySelectorAll('.slides li'),
	currentIndex = 0,
	slideCount = slide.length,
	slideWidth = 2000,
	slideMargin = 0,
	prevBtn = document.querySelector('.prev'),
	nextBtn = document.querySelector('.next');
	
	slides.style.width = (slideWidth + slideMargin) * slideCount - slideMargin + 'px';
	
	
function moveSlide(num){
	slides.style.left = -num * 2000 + 'px';
	currentIndex = num;
} 

nextBtn.addEventListener('click', function(){
	if(currentIndex < slideCount - 1 ) {
	moveSlide(currentIndex + 1);	
	} else {
		moveSlide(0);
	}
});

prevBtn.addEventListener('click', function(){
	if(currentIndex > 0 ) {
	moveSlide(currentIndex - 1);	
	} else {
		moveSlide(slideCount - 1);
	}
});
	
	
