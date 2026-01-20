// Main landing page functionality
class LandingPageApp {
    constructor() {
        this.currentImage = 0;
        this.modalCurrentImage = 0;
        this.isModalOpen = false;
        this.countersAnimated = false;
        this.autoRotationInterval = null;
        this.images = [
            {
                src: "/img/LandingPage/GaleriadeImagenes/484916015_1206753598119236_1185312524214688440_n.jpg",
                alt: "484916015_1206753598119236_1185312524214688440_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/484916415_1206753818119214_842021100620764364_n.jpg",
                alt: "484916415_1206753818119214_842021100620764364_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/484920229_1206753601452569_6260370428698180435_n.jpg",
                alt: "484920229_1206753601452569_6260370428698180435_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/484955101_1206754014785861_8794386061035026811_n.jpg",
                alt: "484955101_1206754014785861_8794386061035026811_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485139376_1209466007847995_6284948666201741301_n.jpg",
                alt: "485139376_1209466007847995_6284948666201741301_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485153780_1206753538119242_3375312249742782591_n.jpg",
                alt: "485153780_1206753538119242_3375312249742782591_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485306675_1206754008119195_2223497109425569889_n.jpg",
                alt: "485306675_1206754008119195_2223497109425569889_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485346235_1209466134514649_4249398363974895262_n.jpg",
                alt: "485346235_1209466134514649_4249398363974895262_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485650603_1206753994785863_1143973541971425290_n.jpg",
                alt: "485650603_1206753994785863_1143973541971425290_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485663331_1209466004514662_2928164811376539531_n.jpg",
                alt: "485663331_1209466004514662_2928164811376539531_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485896979_1209465991181330_3730705632868846505_n.jpg",
                alt: "485896979_1209465991181330_3730705632868846505_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/485898700_1209466117847984_4527187935162610682_n.jpg",
                alt: "485898700_1209466117847984_4527187935162610682_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/486017518_1209466151181314_8926460815616979166_n.jpg",
                alt: "486017518_1209466151181314_8926460815616979166_n"
            },
            {
                src: "/img/LandingPage/GaleriadeImagenes/486037775_1209466101181319_8525579210303765509_n.jpg",
                alt: "486037775_1209466101181319_8525579210303765509_n"
            }
        ];
        this.init();
    }
    
    init() {
        this.setupSmoothScrolling();
        this.setupAnimations();
        this.setupGallery();
        this.setupCounters();
        this.setupProgressBars();
        this.setupInteractions();
    }
    
    setupSmoothScrolling() {
        // Smooth scrolling for anchor links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', (e) => {
                e.preventDefault();
                const target = document.querySelector(anchor.getAttribute('href'));
                if (target) {
                    const headerHeight = document.querySelector('header').offsetHeight;
                    const targetPosition = target.offsetTop - headerHeight - 20;
                    
                    window.scrollTo({
                        top: targetPosition,
                        behavior: 'smooth'
                    });
                }
            });
        });
    }
    
    setupAnimations() {
        // Intersection Observer for scroll animations
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -100px 0px'
        };
        
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('animate-in');
                    
                    // Trigger counters animation when benefits section is visible
                    if (entry.target.classList.contains('why-choose-us-section') && !this.countersAnimated) {
                        this.animateCounters();
                        this.animateProgressBars();
                        this.countersAnimated = true;
                    }
                }
            });
        }, observerOptions);
        
        // Observe sections for animation
        const sections = document.querySelectorAll('section, .mission-card, .vision-card, .service-card, .benefit-card');
        sections.forEach(section => {
            observer.observe(section);
        });
    }
    
    setupGallery() {
        // Initialize main image
        const mainImage = document.querySelector('.main-gallery-image');
        const mainImageContainer = document.getElementById('galleryMainImage');
        const thumbnailsContainer = document.getElementById('galleryThumbnails');
        
        if (mainImage && this.images.length > 0) {
            // Set initial main image
            mainImage.src = this.images[this.currentImage].src;
            mainImage.alt = this.images[this.currentImage].alt;
            
            // Add click handler to main image
            mainImageContainer.addEventListener('click', () => {
                this.openModal(this.currentImage);
            });
            
            // Generate thumbnails based on number of images
            this.renderGalleryThumbnails();
            
            // Start auto rotation (change image every 10 seconds)
            this.startAutoRotation();
        }
        
        // Setup modal controls
        this.setupModalControls();
    }
    

    
    setupCounters() {
        // Setup counter elements with data attributes
        const counters = document.querySelectorAll('[data-counter]');
        counters.forEach(counter => {
            counter.textContent = '0';
        });
    }
    
    animateCounters() {
        const counters = document.querySelectorAll('[data-counter]');
        
        counters.forEach(counter => {
            const target = parseInt(counter.getAttribute('data-counter'));
            const duration = 2000; // 2 seconds
            const increment = target / (duration / 16); // 60fps
            let current = 0;
            
            const updateCounter = () => {
                current += increment;
                if (current < target) {
                    counter.textContent = Math.floor(current);
                    requestAnimationFrame(updateCounter);
                } else {
                    counter.textContent = target;
                }
            };
            
            updateCounter();
        });
    }
    
    setupProgressBars() {
        // Initialize progress bars
        const progressBars = document.querySelectorAll('.budget-progress');
        progressBars.forEach(bar => {
            bar.style.width = '0%';
        });
    }
    
    animateProgressBars() {
        const progressBars = document.querySelectorAll('.budget-progress');
        
        progressBars.forEach(bar => {
            const targetWidth = bar.getAttribute('data-width') || '85';
            
            setTimeout(() => {
                bar.style.width = targetWidth + '%';
            }, 500);
        });
    }
    
    setupInteractions() {
        // Add hover effects to cards
        const cards = document.querySelectorAll('.service-card, .mission-card, .vision-card');
        cards.forEach(card => {
            card.addEventListener('mouseenter', () => {
                card.style.transform = 'translateY(-10px)';
            });
            
            card.addEventListener('mouseleave', () => {
                card.style.transform = 'translateY(0)';
            });
        });
        
        // Add ripple effect to buttons
        const buttons = document.querySelectorAll('.btn, .contact-button, .contact-button2');
        buttons.forEach(button => {
            button.addEventListener('click', (e) => {
                this.createRipple(e, button);
            });
        });
        
        // Setup contact button functionality
        const contactButtons = document.querySelectorAll('.contact-button2');
        contactButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                e.preventDefault();
                const message = 'Hola, me interesa conocer más sobre sus servicios de construcción.';
                const whatsappUrl = this.generateWhatsAppURL('+573001234567', message);
                window.open(whatsappUrl, '_blank');
            });
        });
    }
    
    createRipple(event, element) {
        const ripple = document.createElement('span');
        const rect = element.getBoundingClientRect();
        const size = Math.max(rect.width, rect.height);
        const x = event.clientX - rect.left - size / 2;
        const y = event.clientY - rect.top - size / 2;
        
        ripple.style.cssText = `
            position: absolute;
            width: ${size}px;
            height: ${size}px;
            left: ${x}px;
            top: ${y}px;
            background: rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            transform: scale(0);
            animation: ripple 0.6s linear;
            pointer-events: none;
            z-index: 1;
        `;
        
        const originalPosition = element.style.position;
        const originalOverflow = element.style.overflow;
        
        element.style.position = 'relative';
        element.style.overflow = 'hidden';
        element.appendChild(ripple);
        
        setTimeout(() => {
            ripple.remove();
            element.style.position = originalPosition;
            element.style.overflow = originalOverflow;
        }, 600);
    }
    
    generateWhatsAppURL(phone, message) {
        const cleanPhone = phone.replace(/\D/g, '');
        const encodedMessage = encodeURIComponent(message);
        return `https://wa.me/${cleanPhone}?text=${encodedMessage}`;
    }
    
    // Gallery functions
    renderGalleryThumbnails() {
        const thumbnailsContainer = document.getElementById('galleryThumbnails');
        if (!thumbnailsContainer) return;
        
        // Clear existing thumbnails
        thumbnailsContainer.innerHTML = '';
        
        // Create thumbnails for each image
        this.images.forEach((image, index) => {
            const thumbnailDiv = document.createElement('div');
            thumbnailDiv.className = `gallery-thumbnail ${index === this.currentImage ? 'active' : ''}`;
            
            const thumbnailImg = document.createElement('img');
            thumbnailImg.src = image.src;
            thumbnailImg.alt = image.alt;
            thumbnailImg.className = 'img-fluid';
            
            thumbnailDiv.appendChild(thumbnailImg);
            
            // Add click handler to thumbnail
            thumbnailDiv.addEventListener('click', () => {
                this.currentImage = index;
                this.updateMainGalleryImage();
                this.updateThumbnailActiveState();
            });
            
            thumbnailsContainer.appendChild(thumbnailDiv);
        });
    }
    
    updateMainGalleryImage() {
        const mainImage = document.querySelector('.main-gallery-image');
        if (mainImage && this.images[this.currentImage]) {
            mainImage.src = this.images[this.currentImage].src;
            mainImage.alt = this.images[this.currentImage].alt;
        }
    }
    
    updateThumbnailActiveState() {
        const thumbnails = document.querySelectorAll('.gallery-thumbnail');
        thumbnails.forEach((thumbnail, index) => {
            if (index === this.currentImage) {
                thumbnail.classList.add('active');
            } else {
                thumbnail.classList.remove('active');
            }
        });
    }
    
    startAutoRotation() {
        // Clear any existing interval
        if (this.autoRotationInterval) {
            clearInterval(this.autoRotationInterval);
        }
        
        // Start auto rotation (10 seconds like React component)
        this.autoRotationInterval = setInterval(() => {
            if (!this.isModalOpen) {
                this.currentImage = (this.currentImage + 1) % this.images.length;
                this.updateMainGalleryImage();
                this.updateThumbnailActiveState();
            }
        }, 10000);
    }
    
    stopAutoRotation() {
        if (this.autoRotationInterval) {
            clearInterval(this.autoRotationInterval);
            this.autoRotationInterval = null;
        }
    }
    
    setupModalControls() {
        // Close modal when clicking the close button
        const closeBtn = document.getElementById('galleryModalClose');
        if (closeBtn) {
            closeBtn.addEventListener('click', () => this.closeModal());
        }
        
        // Close modal when clicking outside the image
        const modal = document.getElementById('galleryModal');
        if (modal) {
            modal.addEventListener('click', (e) => {
                if (e.target === modal || e.target.classList.contains('modal-overlay')) {
                    this.closeModal();
                }
            });
        }
        
        // Previous/Next buttons
        const prevBtn = document.getElementById('galleryModalPrev');
        const nextBtn = document.getElementById('galleryModalNext');
        
        if (prevBtn) {
            prevBtn.addEventListener('click', () => this.prevModalImage());
        }
        
        if (nextBtn) {
            nextBtn.addEventListener('click', () => this.nextModalImage());
        }
        
        // Keyboard navigation
        document.addEventListener('keydown', (e) => {
            if (this.isModalOpen) {
                switch(e.key) {
                    case 'Escape':
                        this.closeModal();
                        break;
                    case 'ArrowLeft':
                        this.prevModalImage();
                        break;
                    case 'ArrowRight':
                        this.nextModalImage();
                        break;
                }
            }
        });
    }
    
    openModal(index) {
        this.modalCurrentImage = index;
        this.isModalOpen = true;
        
        const modal = document.getElementById('galleryModal');
        const modalImage = document.getElementById('galleryModalImage');
        
        if (modal && modalImage && this.images[index]) {
            modalImage.src = this.images[index].src;
            modalImage.alt = this.images[index].alt;
            modal.style.display = 'block';
            document.body.style.overflow = 'hidden';
            
            // Stop auto rotation when modal is open (like React component)
            this.stopAutoRotation();
        }
    }
    
    closeModal() {
        this.isModalOpen = false;
        
        const modal = document.getElementById('galleryModal');
        if (modal) {
            modal.style.display = 'none';
            document.body.style.overflow = '';
            
            // Restart auto rotation when modal is closed
            this.startAutoRotation();
        }
    }
    
    nextModalImage() {
        this.modalCurrentImage = (this.modalCurrentImage + 1) % this.images.length;
        this.updateModalImage();
    }
    
    prevModalImage() {
        this.modalCurrentImage = (this.modalCurrentImage - 1 + this.images.length) % this.images.length;
        this.updateModalImage();
    }
    
    updateModalImage() {
        const modalImage = document.getElementById('galleryModalImage');
        if (modalImage && this.images[this.modalCurrentImage]) {
            modalImage.src = this.images[this.modalCurrentImage].src;
            modalImage.alt = this.images[this.modalCurrentImage].alt;
        }
    }
}

// CSS animations for landing page
const style = document.createElement('style');
style.textContent = `
    @keyframes ripple {
        to {
            transform: scale(4);
            opacity: 0;
        }
    }
    
    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(50px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    @keyframes slideInLeft {
        from {
            opacity: 0;
            transform: translateX(-50px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }
    
    @keyframes slideInRight {
        from {
            opacity: 0;
            transform: translateX(50px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }
    
    @keyframes scaleIn {
        from {
            opacity: 0;
            transform: scale(0.8);
        }
        to {
            opacity: 1;
            transform: scale(1);
        }
    }
    
    .animate-in {
        animation: fadeInUp 0.8s ease-out forwards;
    }
    
    .mission-card.animate-in {
        animation: slideInLeft 0.8s ease-out forwards;
    }
    
    .vision-card.animate-in {
        animation: slideInRight 0.8s ease-out forwards;
    }
    
    .service-card.animate-in {
        animation: scaleIn 0.6s ease-out forwards;
    }
    
    .benefit-card.animate-in {
        animation: fadeInUp 0.6s ease-out forwards;
    }
    
    section {
        opacity: 0;
        transform: translateY(30px);
    }
    
    .mission-card, .vision-card {
        opacity: 0;
        transform: translateX(-30px);
    }
    
    .vision-card {
        transform: translateX(30px);
    }
    
    .service-card, .benefit-card {
        opacity: 0;
        transform: scale(0.9);
    }
`;
document.head.appendChild(style);

// Initialize app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.landingPageApp = new LandingPageApp();
});

// Utility functions
window.utils = {
    // Format phone number for WhatsApp
    formatPhoneForWhatsApp: (phone) => {
        return phone.replace(/\D/g, '');
    },
    
    // Generate WhatsApp URL
    generateWhatsAppURL: (phone, message) => {
        const cleanPhone = window.utils.formatPhoneForWhatsApp(phone);
        const encodedMessage = encodeURIComponent(message);
        return `https://wa.me/${cleanPhone}?text=${encodedMessage}`;
    },
    
    // Generate email URL
    generateEmailURL: (subject, body) => {
        const encodedSubject = encodeURIComponent(subject);
        const encodedBody = encodeURIComponent(body);
        return `mailto:?subject=${encodedSubject}&body=${encodedBody}`;
    },
    
    // Show toast notification
    showToast: (message, type = 'info') => {
        const toast = document.createElement('div');
        toast.className = `toast align-items-center text-white bg-${type} border-0`;
        toast.setAttribute('role', 'alert');
        toast.innerHTML = `
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        `;
        
        // Add to toast container or create one
        let toastContainer = document.querySelector('.toast-container');
        if (!toastContainer) {
            toastContainer = document.createElement('div');
            toastContainer.className = 'toast-container position-fixed top-0 end-0 p-3';
            document.body.appendChild(toastContainer);
        }
        
        toastContainer.appendChild(toast);
        
        // Initialize and show toast
        if (typeof bootstrap !== 'undefined' && bootstrap.Toast) {
            const bsToast = new bootstrap.Toast(toast);
            bsToast.show();
        }
        
        // Remove from DOM after hiding
        toast.addEventListener('hidden.bs.toast', () => {
            toast.remove();
        });
    },
    
    // Validate email
    isValidEmail: (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    },
    
    // Validate phone
    isValidPhone: (phone) => {
        const phoneRegex = /^\+?[\d\s\-\(\)]{8,}$/;
        return phoneRegex.test(phone);
    },
    
    // Debounce function
    debounce: (func, wait) => {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },
    
    // Smooth scroll to element
    scrollToElement: (elementId, offset = 100) => {
        const element = document.getElementById(elementId);
        if (element) {
            const headerHeight = document.querySelector('header')?.offsetHeight || 0;
            const targetPosition = element.offsetTop - headerHeight - offset;
            
            window.scrollTo({
                top: targetPosition,
                behavior: 'smooth'
            });
        }
    }
};