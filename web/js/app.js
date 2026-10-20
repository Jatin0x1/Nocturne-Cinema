document.addEventListener('DOMContentLoaded', function () {
  const toggle = document.querySelector('.menu-toggle');
  const nav = document.querySelector('.main-nav');
  if (toggle && nav) toggle.addEventListener('click', function () {
    const open = nav.classList.toggle('open');
    toggle.setAttribute('aria-expanded', String(open));
    toggle.textContent = open ? '×' : '☰';
  });

  const dateInput = document.getElementById('showDate');
  if (dateInput) {
    const today = new Date();
    const localToday = new Date(today.getTime() - today.getTimezoneOffset() * 60000).toISOString().slice(0, 10);
    dateInput.min = localToday;
    if (!dateInput.value) dateInput.value = localToday;
  }

  const form = document.getElementById('bookingForm');
  if (form) {
    const price = Number(form.dataset.price || document.querySelector('.price-line')?.textContent.replace(/[^0-9]/g, '') || 0);
    const total = document.getElementById('totalPrice');
    const count = document.getElementById('seatCount');
    const timeInput = document.getElementById('showTime');
    function refreshAvailability() {
      if (!dateInput || !timeInput || !dateInput.value || !timeInput.value) return;
      const url = new URL(form.dataset.detailsUrl, window.location.href);
      url.searchParams.set('date', dateInput.value);
      url.searchParams.set('time', timeInput.value);
      window.location.assign(url.toString());
    }
    if (dateInput) dateInput.addEventListener('change', refreshAvailability);
    if (timeInput) timeInput.addEventListener('change', refreshAvailability);
    function updateTotal() {
      const selected = form.querySelectorAll('input[name="seats"]:checked').length;
      total.textContent = '₹' + (price * selected);
      count.textContent = selected ? selected + (selected === 1 ? ' seat selected' : ' seats selected') : 'Select seats to continue';
    }
    form.addEventListener('change', updateTotal);
    form.addEventListener('submit', function (event) {
      if (!form.reportValidity()) return;
      if (!form.querySelector('input[name="seats"]:checked')) {
        event.preventDefault();
        window.alert('Please select at least one seat to continue.');
        return;
      }
      const selected = form.querySelectorAll('input[name="seats"]:checked').length;
      if (!window.confirm('Confirm ' + selected + (selected === 1 ? ' ticket' : ' tickets') + ' for ' + total.textContent + '?')) event.preventDefault();
    });
    updateTotal();
  }

  document.querySelectorAll('.poster-wrap img').forEach(function (image) {
    image.addEventListener('error', function () {
      image.style.display = 'none';
      image.parentElement.classList.add('poster-fallback');
    }, { once: true });
  });
});
