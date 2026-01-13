

export const fadeIn = (element, duration) => {
    (function increment(value = 0) {
        element.style.opacity = String(value);
        if (element.style.opacity !== '1') {
            setTimeout(() => {
                increment(value + 0.1);
            }, duration / 10);
        }
    })();
};

export const fadeOut = (element, duration) => {
    (function decrement() {
        (element.style.opacity -= 0.1) < 0 ? element.style.display = 'none' : setTimeout(() => {
            decrement();
        }, duration / 10);
    })();
};
