// Header functionality
class HeaderManager {
    constructor() {
        this.header = document.getElementById('header');
        this.menuToggle = document.getElementById('menuToggle');
        this.mobileMenu = document.getElementById('mobileMenu');
        this.menuOpen = false;
        this.isScrolled = false;
        this.isVisible = true;
        this.lastScrollY = 0;
        this.transparentBg = false;
        
        this.init();
    }
    
    init() {
        this.setupEventListeners();
        this.handleInitialState();
    }
    
    setupEventListeners() {
        // Menu toggle
        if (this.menuToggle) {
            this.menuToggle.addEventListener('click', () => this.toggleMenu());
        }
        
        // Scroll handling
        window.addEventListener('scroll', () => this.handleScroll(), { passive: true });
        
        // Close mobile menu when clicking on links
        const mobileLinks = this.mobileMenu?.querySelectorAll('a');
        if (mobileLinks) {
            mobileLinks.forEach(link => {
                link.addEventListener('click', () => this.closeMobileMenu());
            });
        }
        
        // Close mobile menu when clicking outside
        document.addEventListener('click', (e) => {
            if (this.menuOpen && !this.header.contains(e.target)) {
                this.closeMobileMenu();
            }
        });
    }
    
    handleInitialState() {
        // Check if we're on a page that should have transparent header initially
        const currentPage = window.location.pathname;
        this.transparentBg = currentPage === '/' || currentPage === '/index.html' || currentPage.includes('galeria');
        
        // Set initial state
        if (!this.transparentBg) {
            this.header.classList.add('scrolled');
            this.isScrolled = true;
        }
    }
    
    toggleMenu() {
        this.menuOpen = !this.menuOpen;
        
        // Toggle hamburger animation
        const spans = this.menuToggle.querySelectorAll('span');
        spans.forEach(span => {
            span.classList.toggle('active', this.menuOpen);
        });
        
        // Toggle mobile menu
        this.mobileMenu.classList.toggle('active', this.menuOpen);
    }
    
    closeMobileMenu() {
        if (this.menuOpen) {
            this.menuOpen = false;
            
            // Remove hamburger animation
            const spans = this.menuToggle.querySelectorAll('span');
            spans.forEach(span => {
                span.classList.remove('active');
            });
            
            // Hide mobile menu
            this.mobileMenu.classList.remove('active');
        }
    }
    
    handleScroll() {
        const currentScrollY = window.scrollY;
        
        // Determine if we're at the top
        if (currentScrollY < 50 && this.transparentBg) {
            // Only make header transparent if it has the transparentBg flag
            this.setScrolled(false);
            this.setVisible(true);
        } else {
            this.setScrolled(true);
            
            // Determine scroll direction
            if (currentScrollY > this.lastScrollY && currentScrollY > 100) {
                // Scrolling down - hide header
                this.setVisible(false);
            } else {
                // Scrolling up - show header
                this.setVisible(true);
            }
        }
        
        this.lastScrollY = currentScrollY;
    }
    
    setScrolled(scrolled) {
        if (this.isScrolled !== scrolled) {
            this.isScrolled = scrolled;
            this.header.classList.toggle('scrolled', scrolled);
        }
    }
    
    setVisible(visible) {
        if (this.isVisible !== visible) {
            this.isVisible = visible;
            this.header.classList.toggle('visible', visible);
            this.header.classList.toggle('hidden', !visible);
        }
    }
    
    // Method to set transparent background (for specific pages)
    setTransparentBackground(transparent) {
        this.transparentBg = transparent;
        if (!transparent) {
            this.setScrolled(true);
        }
    }
}

// Initialize header when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.headerManager = new HeaderManager();
});

// Smooth scrolling for anchor links
document.addEventListener('DOMContentLoaded', () => {
    const anchorLinks = document.querySelectorAll('a[href^="#"]');
    
    anchorLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            const href = link.getAttribute('href');
            if (href === '#') return;
            
            const target = document.querySelector(href);
            if (target) {
                e.preventDefault();
                
                // Close mobile menu if open
                if (window.headerManager) {
                    window.headerManager.closeMobileMenu();
                }
                
                // Smooth scroll to target
                const headerHeight = document.getElementById('header').offsetHeight;
                const targetPosition = target.offsetTop - headerHeight - 20;
                
                window.scrollTo({
                    top: targetPosition,
                    behavior: 'smooth'
                });
            }
        });
    });
});
