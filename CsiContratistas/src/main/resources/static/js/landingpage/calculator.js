document.addEventListener('DOMContentLoaded', function() {
    // Exchange rate (default value, will be updated from API)
    let exchangeRate = 3.80;
    
    // Fetch current exchange rate from API
    async function fetchExchangeRate() {
        try {
            // Using Exchange Rates Data API (free tier available)
            const response = await fetch('https://api.exchangerate-api.com/v4/latest/USD');
            if (!response.ok) throw new Error('No se pudo obtener el tipo de cambio');
            
            const data = await response.json();
            exchangeRate = data.rates.PEN || 3.80; // Fallback to 3.80 if rate not found
            
            // Update the UI with the current exchange rate
            const exchangeRateElement = document.getElementById('exchange-rate');
            if (exchangeRateElement) {
                exchangeRateElement.textContent = exchangeRate.toFixed(2);
                // Show when the rate was last updated
                const now = new Date();
                const timeString = now.toLocaleTimeString('es-PE', { hour: '2-digit', minute: '2-digit' });
                const dateString = now.toLocaleDateString('es-PE');
                const lastUpdated = document.createElement('div');
                lastUpdated.className = 'last-updated';
                lastUpdated.textContent = `Actualizado: ${dateString} ${timeString}`;
                exchangeRateElement.parentNode.appendChild(lastUpdated);
            }
        } catch (error) {
            console.error('Error al obtener el tipo de cambio:', error);
            // Use default rate if API fails
            exchangeRate = 3.80;
        }
    }
    
    // Call the function to get the exchange rate when the page loads
    fetchExchangeRate();
    
    // Price per square meter based on construction quality (in S/.)
    const PRICE_RANGES = {
        economica: { min: 1200, max: 1800 },
        media: { min: 1800, max: 2500 },
        alta: { min: 2500, max: 3500 },
        premium: { min: 3500, max: 5000 }
    };

    // Location modifiers (multipliers) for Lambayeque districts
    const LOCATION_MODIFIERS = {
        // Chiclayo (reference)
        chiclayo: 1.0,
        // Main urban districts
        jose_leonardo: 0.95,
        la_victoria: 0.98,
        pimentel: 1.05,
        // Nearby districts
        pomalca: 0.92,
        pucala: 0.9,
        ferrenafe: 0.95,
        // More distant districts
        motupe: 0.88,
        olmos: 0.87,
        jayanca: 0.85,
        tucume: 0.86,
        // Smaller districts
        mochumi: 0.84,
        monsefu: 0.92,
        etenguene: 0.9,
        reque: 0.91,
        lagunas: 0.83,
        chongoyape: 0.89,
        illimo: 0.87,
        pueblo_nuevo: 0.88,
        tuman: 0.9,
        chochope: 0.85,
        juanito: 0.84,
        mochumi_viejo: 0.83,
        naylamp: 1.02,
        // Default for other locations
        otro: 1.1
    };

    // Construction type modifiers
    const CONSTRUCTION_MODIFIERS = {
        casa: 1.0,
        departamento: 1.1,
        oficina: 1.2,
        local: 1.3,
        otro: 1.0
    };

    // DOM Elements
    const form = document.querySelector('.calculator-form');
    const calculateBtn = document.getElementById('calculate-btn');
    const resultContainer = document.getElementById('result-container');
    const contactBtn = document.getElementById('contact-btn');

    // Event Listeners
    if (calculateBtn) {
        calculateBtn.addEventListener('click', calculateEstimate);
    }

    if (contactBtn) {
        contactBtn.addEventListener('click', scrollToContact);
    }

    // Calculate estimate
    function calculateEstimate(e) {
        e.preventDefault();
        
        // Get form values
        const location = document.getElementById('location').value;
        const constructionType = document.getElementById('construction-type').value;
        const area = parseFloat(document.getElementById('area').value) || 0;
        const quality = document.getElementById('quality').value;
        const includesMaterials = document.getElementById('includes-materials').checked;

        // Validate required fields
        if (!location || !constructionType || area <= 0) {
            alert('Por favor complete todos los campos requeridos');
            return;
        }

        // Calculate base price per m² based on quality
        const priceRange = PRICE_RANGES[quality] || PRICE_RANGES.media;
        const basePrice = (priceRange.min + priceRange.max) / 2; // Average price
        
        // Apply modifiers
        let finalPrice = basePrice * 
                        (LOCATION_MODIFIERS[location] || 1) * 
                        (CONSTRUCTION_MODIFIERS[constructionType] || 1);

        // Adjust for materials (if not included, reduce price by 40%)
        if (!includesMaterials) {
            finalPrice *= 0.6; // 40% reduction for labor only
        }

        // Calculate total
        const totalEstimate = finalPrice * area;

        // Update UI with both PEN and USD
        const formatCurrency = (amount, currency = 'PEN') => {
            if (currency === 'USD') {
                const usdAmount = amount / exchangeRate;
                return usdAmount.toLocaleString('en-US', { 
                    style: 'currency', 
                    currency: 'USD',
                    minimumFractionDigits: 2, 
                    maximumFractionDigits: 2 
                });
            } else {
                return amount.toLocaleString('es-PE', { 
                    style: 'currency', 
                    currency: 'PEN',
                    minimumFractionDigits: 2, 
                    maximumFractionDigits: 2 
                });
            }
        };
        
        // Update PEN values
        document.getElementById('cost-per-meter-pen').textContent = 
            formatCurrency(finalPrice, 'PEN').replace('PEN', 'S/.');
        document.getElementById('total-estimate-pen').textContent = 
            formatCurrency(totalEstimate, 'PEN').replace('PEN', 'S/.');
            
        // Update USD values
        document.getElementById('cost-per-meter-usd').textContent = 
            `(${formatCurrency(finalPrice, 'USD')})`;
        document.getElementById('total-estimate-usd').textContent = 
            `(${formatCurrency(totalEstimate, 'USD')})`;
        
        // Show results with animation
        resultContainer.style.display = 'block';
        resultContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }

    // Scroll to contact section
    function scrollToContact() {
        const contactSection = document.getElementById('contacto');
        if (contactSection) {
            contactSection.scrollIntoView({ behavior: 'smooth' });
        }
    }

    // Add input validation for area field
    const areaInput = document.getElementById('area');
    if (areaInput) {
        areaInput.addEventListener('input', function(e) {
            // Remove any non-numeric characters
            this.value = this.value.replace(/[^0-9.]/g, '');
            
            // Ensure only one decimal point
            if ((this.value.match(/\./g) || []).length > 1) {
                this.value = this.value.slice(0, -1);
            }
        });
    }
});
