$(document).ready(function () {
    // Slick Slider for hero section
    $('.hero-slider').slick({
        autoplay: true,
        autoplaySpeed: 4000,
        dots: true,
        arrows: false,
        fade: true
    });

    // Slick Slider for products
    $('.product-slider').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 3000,
        responsive: [
            { breakpoint: 992, settings: { slidesToShow: 2 } },
            { breakpoint: 576, settings: { slidesToShow: 1 } }
        ]
    });

    // Slick Slider for testimonials
    $('.testimonial-slider').slick({
        slidesToShow: 1,
        autoplay: true,
        dots: true,
        arrows: false
    });

    // Nice Select init
    $('select').niceSelect();

    // Cart remove item
    $('.cart-item-remove').on('click', function () {
        $(this).closest('.cart-preview-item').fadeOut(300, function () {
            $(this).remove();
        });
    });

    // Wishlist toggle
    $('.btn-wishlist').on('click', function () {
        $(this).find('i').toggleClass('far fas');
    });

    // Quick view modal (example placeholder)
    $('.btn-quickview').on('click', function () {
        alert('Quick view functionality will go here!');
    });
});
