// Global variables
let currentUser = null;
let selectedRoom = '311';
let selectedSlot = null;
let currentWeekOffset = 0;
let bookings = [];
let cancelledBookings = [];
let users = [
    { id: 'admin', name: 'Admin User', officerName: 'System Administrator', internalNo: '1000', password: 'admin123' }
];

// DOM Elements
const loginPage = document.getElementById('loginPage');
const dashboardPage = document.getElementById('dashboardPage');
const loginForm = document.getElementById('loginForm');
const logoutBtn = document.getElementById('logoutBtn');
const loggedInUser = document.getElementById('loggedInUser');
const navBtns = document.querySelectorAll('.nav-btn');
const contentSections = document.querySelectorAll('.content-section');

// Login functionality
loginForm.addEventListener('submit', function(e) {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
	
	// Using the native 'fetch' API
	const apiUrl = 'http://localhost:8080/api/bookings';

	fetch(apiUrl, {
	    method: 'GET',
	    headers: {
	        // Include an Authorization header if required by your security setup
	        'Authorization': 'Bearer <your_jwt_token_here>' 
	    }
	})
	.then(response => {
	    // Check if the response status is OK (200)
	    if (!response.ok) {
	        throw new Error(`HTTP error! status: ${response.status}`);
	    }
	    return response.json(); // Parse the JSON body
	})
	.then(data => {
	    console.log('Bookings Data:', data);
	})
	.catch(error => {
	    console.error('Error fetching bookings:', error);
	});

    // Simple authentication (in production, this should be server-side)
    const user = users.find(u => u.id === username && u.password === password);

    if (user) {
        currentUser = user;
        loggedInUser.textContent = user.name;

        // Switch to dashboard
        loginPage.classList.remove('active');
        dashboardPage.classList.add('active');

        // Show booking page by default
        showPage('booking');
        initializeBookingPage();
    } else {
        alert('Invalid username or password!');
    }
});

// Logout functionality
logoutBtn.addEventListener('click', function() {
    currentUser = null;
    dashboardPage.classList.remove('active');
    loginPage.classList.add('active');
    loginForm.reset();
});

// Navigation between pages
navBtns.forEach(btn => {
    btn.addEventListener('click', function() {
        const page = this.getAttribute('data-page');

        // Update active nav button
        navBtns.forEach(b => b.classList.remove('active'));
        this.classList.add('active');

        // Show selected page
        showPage(page);
    });
});

// Back buttons
document.querySelectorAll('.back-btn').forEach(btn => {
    btn.addEventListener('click', function() {
        const backTo = this.getAttribute('data-back');
        showPage(backTo);
    });
});

function showPage(pageId) {
    // Hide all sections
    contentSections.forEach(section => {
        section.classList.remove('active');
    });

    // Show selected section
    document.getElementById(`${pageId}Page`).classList.add('active');

    // Update navigation button
    navBtns.forEach(btn => {
        btn.classList.remove('active');
        if (btn.getAttribute('data-page') === pageId) {
            btn.classList.add('active');
        }
    });

    // Initialize page if needed
    if (pageId === 'booking') {
        initializeBookingPage();
    } else if (pageId === 'reports') {
        initializeReportsPage();
    } else if (pageId === 'cancelReports') {
        initializeCancelReports();
    } else if (pageId === 'changePassword') {
        initializeChangePassword();
    }
}

// Initialize Booking Page
function initializeBookingPage() {
    // Room selection
    const roomBtns = document.querySelectorAll('.room-btn');
    roomBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            roomBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            selectedRoom = this.getAttribute('data-room');
            generateCalendar();
        });
    });

    // Week navigation
    document.getElementById('prevWeekBtn').addEventListener('click', () => {
        currentWeekOffset--;
        updateWeekDisplay();
        generateCalendar();
    });

    document.getElementById('nextWeekBtn').addEventListener('click', () => {
        currentWeekOffset++;
        updateWeekDisplay();
        generateCalendar();
    });

    // Initialize calendar
    updateWeekDisplay();
    generateCalendar();

    // Booking form
    document.getElementById('bookingForm').addEventListener('submit', function(e) {
        e.preventDefault();
        bookSelectedSlot();
    });

    // Cancel selection
    document.getElementById('cancelSelectionBtn').addEventListener('click', function() {
        selectedSlot = null;
        updateSelectedSlotInfo();
        resetTimeSlotSelection();
    });
}

function updateWeekDisplay() {
    const now = new Date();
    const startOfWeek = new Date(now);
    startOfWeek.setDate(now.getDate() - now.getDay() + 1 + (currentWeekOffset * 7));

    const endOfWeek = new Date(startOfWeek);
    endOfWeek.setDate(startOfWeek.getDate() + 5);

    const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
    const startStr = startOfWeek.toLocaleDateString('en-GB', options);
    const endStr = endOfWeek.toLocaleDateString('en-GB', options);

    document.getElementById('currentWeekDisplay').textContent =
        `Week: ${startStr} - ${endStr}`;
}

function generateCalendar() {
    const calendarSlots = document.getElementById('calendarSlots');
    calendarSlots.innerHTML = '';

    const now = new Date();
    const startOfWeek = new Date(now);
    startOfWeek.setDate(now.getDate() - now.getDay() + 1 + (currentWeekOffset * 7));

    // Generate time slots (9:00 to 17:00, 30-minute intervals)
    const timeSlots = [];
    for (let hour = 9; hour < 17; hour++) {
        for (let minute = 0; minute < 60; minute += 30) {
            timeSlots.push(`${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`);
        }
    }

    // Create calendar grid (6 days x time slots)
    for (let day = 0; day < 6; day++) {
        const currentDate = new Date(startOfWeek);
        currentDate.setDate(startOfWeek.getDate() + day);

        for (let timeSlot of timeSlots) {
            const slotElement = document.createElement('div');
            slotElement.className = 'time-slot available';

            const slotDate = currentDate.toISOString().split('T')[0];
            const slotId = `${slotDate}_${timeSlot}_${selectedRoom}`;

            // Check if slot is booked
            const isBooked = bookings.some(b =>
                b.date === slotDate &&
                b.time === timeSlot &&
                b.room === selectedRoom
            );

            if (isBooked) {
                slotElement.classList.remove('available');
                slotElement.classList.add('booked');
                slotElement.title = 'Already booked';
            }

            slotElement.textContent = timeSlot;
            slotElement.setAttribute('data-slot-id', slotId);
            slotElement.setAttribute('data-date', slotDate);
            slotElement.setAttribute('data-time', timeSlot);

            if (!isBooked) {
                slotElement.addEventListener('click', function() {
                    if (selectedSlot === slotId) {
                        // Show cancel modal
                        showCancelModal(slotDate, timeSlot);
                    } else {
                        // Select slot for booking
                        selectTimeSlot(this);
                    }
                });
            }

            calendarSlots.appendChild(slotElement);
        }
    }
}

function selectTimeSlot(element) {
    // Reset previous selection
    resetTimeSlotSelection();

    // Set new selection
    element.classList.add('selected');
    selectedSlot = element.getAttribute('data-slot-id');

    // Update booking form
    updateSelectedSlotInfo();
}

function resetTimeSlotSelection() {
    document.querySelectorAll('.time-slot').forEach(slot => {
        slot.classList.remove('selected');
    });
}

function updateSelectedSlotInfo() {
    const infoElement = document.getElementById('selectedSlotInfo');

    if (selectedSlot) {
        const [date, time, room] = selectedSlot.split('_');
        const dateObj = new Date(date);
        const formattedDate = dateObj.toLocaleDateString('en-GB', {
            weekday: 'long',
            day: '2-digit',
            month: 'short',
            year: 'numeric'
        });

        infoElement.innerHTML = `
            <i class="fas fa-calendar-check"></i> Selected Slot:
            <strong>${formattedDate}</strong> at <strong>${time}</strong>
            in <strong>VC Room ${room}</strong>
        `;
    } else {
        infoElement.textContent = 'No slot selected';
    }
}

function bookSelectedSlot() {
    if (!selectedSlot) {
        alert('Please select a time slot first!');
        return;
    }

    const guest = document.getElementById('guest').value;
    const subject = document.getElementById('subject').value;

    if (!guest || !subject) {
        alert('Please fill in all booking details!');
        return;
    }

    const [date, time, room] = selectedSlot.split('_');

    // Create booking object
    const booking = {
        id: Date.now(),
        date: date,
        time: time,
        room: room,
        guest: guest,
        subject: subject,
        bookedBy: currentUser.id,
        officerName: currentUser.officerName,
        bookedAt: new Date().toISOString()
    };

    // Add to bookings array
    bookings.push(booking);

    // Reset form
    document.getElementById('bookingForm').reset();
    selectedSlot = null;
    updateSelectedSlotInfo();
    resetTimeSlotSelection();
    generateCalendar();

    alert('Booking successful!');
}

// Modal functionality
const modal = document.getElementById('cancelModal');
const modalCloseBtns = document.querySelectorAll('.modal-close, .modal-close-btn');

function showCancelModal(date, time) {
    const booking = bookings.find(b =>
        b.date === date &&
        b.time === time &&
        b.room === selectedRoom
    );

    if (booking) {
        const dateObj = new Date(date);
        const formattedDate = dateObj.toLocaleDateString('en-GB', {
            weekday: 'long',
            day: '2-digit',
            month: 'short',
            year: 'numeric'
        });

        document.getElementById('modalBookingDetails').innerHTML = `
            <p><strong>Date:</strong> ${formattedDate}</p>
            <p><strong>Time:</strong> ${time}</p>
            <p><strong>Room:</strong> VC Room ${selectedRoom}</p>
            <p><strong>Guest:</strong> ${booking.guest}</p>
            <p><strong>Subject:</strong> ${booking.subject}</p>
        `;

        // Set cancel date to today
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('cancelDate').value = today;

        modal.classList.add('active');

        // Handle cancel booking form
        document.getElementById('cancelBookingForm').onsubmit = function(e) {
            e.preventDefault();
            cancelBooking(booking.id);
        };
    }
}

// Close modal
modalCloseBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        modal.classList.remove('active');
    });
});

// Close modal when clicking outside
modal.addEventListener('click', function(e) {
    if (e.target === modal) {
        modal.classList.remove('active');
    }
});

function cancelBooking(bookingId) {
    const reason = document.getElementById('cancelReason').value;
    const cancelDate = document.getElementById('cancelDate').value;

    if (!reason) {
        alert('Please provide a cancellation reason!');
        return;
    }

    // Find booking
    const bookingIndex = bookings.findIndex(b => b.id === bookingId);
    if (bookingIndex === -1) return;

    const booking = bookings[bookingIndex];

    // Add to cancelled bookings
    cancelledBookings.push({
        ...booking,
        cancelledBy: currentUser.id,
        cancelReason: reason,
        cancelDate: cancelDate,
        cancelledAt: new Date().toISOString()
    });

    // Remove from active bookings
    bookings.splice(bookingIndex, 1);

    // Close modal
    modal.classList.remove('active');

    // Refresh calendar
    generateCalendar();
    selectedSlot = null;
    updateSelectedSlotInfo();

    alert('Booking cancelled successfully!');
}

// Initialize Reports Page
function initializeReportsPage() {
    const today = new Date().toISOString().split('T')[0];
    const oneWeekAgo = new Date();
    oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
    const oneWeekAgoStr = oneWeekAgo.toISOString().split('T')[0];

    document.getElementById('startDate').value = oneWeekAgoStr;
    document.getElementById('endDate').value = today;

    updateReportDates();
    generateReportPreview();

    document.getElementById('reportForm').addEventListener('submit', function(e) {
        e.preventDefault();
        generateReportPreview();
    });

    document.getElementById('downloadPdfBtn').addEventListener('click', downloadPdfReport);
}

function updateReportDates() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    const startObj = new Date(startDate);
    const endObj = new Date(endDate);

    document.getElementById('reportStartDate').textContent =
        startObj.toLocaleDateString('en-GB');
    document.getElementById('reportEndDate').textContent =
        endObj.toLocaleDateString('en-GB');
}

function generateReportPreview() {
    updateReportDates();

    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    // Filter bookings within date range
    const filteredBookings = bookings.filter(booking => {
        return booking.date >= startDate && booking.date <= endDate;
    });

    const tableBody = document.getElementById('reportTableBody');
    tableBody.innerHTML = '';

    if (filteredBookings.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align: center; padding: 40px;">
                    <i class="fas fa-info-circle"></i> No bookings found for the selected date range
                </td>
            </tr>
        `;
        return;
    }

    filteredBookings.forEach(booking => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${new Date(booking.date).toLocaleDateString('en-GB')}</td>
            <td>${booking.time}</td>
            <td>${booking.bookedBy}</td>
            <td>${booking.officerName}</td>
            <td>VC Room ${booking.room}</td>
            <td>${booking.guest}</td>
            <td>${booking.subject}</td>
        `;
        tableBody.appendChild(row);
    });
}

function downloadPdfReport() {
    // In a real application, this would generate an actual PDF
    // For this demo, we'll create a downloadable text file
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    const startObj = new Date(startDate);
    const endObj = new Date(endDate);

    const reportTitle = `VC Slot Booking Report (${startObj.toLocaleDateString('en-GB')} to ${endObj.toLocaleDateString('en-GB')})`;

    let reportContent = `${reportTitle}\n`;
    reportContent += '='.repeat(60) + '\n\n';

    // Filter bookings
    const filteredBookings = bookings.filter(booking => {
        return booking.date >= startDate && booking.date <= endDate;
    });

    if (filteredBookings.length === 0) {
        reportContent += 'No bookings found for the selected date range.\n';
    } else {
        reportContent += 'Date,Slot Time,User ID,Officer Name,Internal Room,VC Guest,Subject\n';
        filteredBookings.forEach(booking => {
            reportContent += `${new Date(booking.date).toLocaleDateString('en-GB')},${booking.time},${booking.bookedBy},${booking.officerName},VC Room ${booking.room},${booking.guest},${booking.subject}\n`;
        });
    }

    // Create and download file
    const blob = new Blob([reportContent], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `VC_Booking_Report_${startDate}_to_${endDate}.txt`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);

    alert('Report downloaded as text file (PDF generation would require server-side processing)');
}

// Initialize Cancel Reports
function initializeCancelReports() {
    const tableBody = document.getElementById('cancelReportTableBody');
    tableBody.innerHTML = '';

    if (cancelledBookings.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="8" style="text-align: center; padding: 40px;">
                    <i class="fas fa-info-circle"></i> No cancelled bookings found
                </td>
            </tr>
        `;
        return;
    }

    cancelledBookings.forEach(booking => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${new Date(booking.date).toLocaleDateString('en-GB')}</td>
            <td>${booking.time}</td>
            <td>${booking.bookedBy}</td>
            <td>${booking.guest}</td>
            <td>${booking.subject}</td>
            <td>VC Room ${booking.room}</td>
            <td>${booking.cancelledBy}</td>
            <td>${booking.cancelReason}</td>
        `;
        tableBody.appendChild(row);
    });
}

// Initialize Change Password
function initializeChangePassword() {
    if (currentUser) {
        document.getElementById('changePasswordUserId').textContent = currentUser.id;
        document.getElementById('changePasswordName').textContent = currentUser.name;
    }

    document.getElementById('changePasswordForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const newPassword = document.getElementById('newPasswordField').value;
        const retypePassword = document.getElementById('retypePassword').value;

        if (newPassword !== retypePassword) {
            alert('Passwords do not match!');
            return;
        }

        if (newPassword.length < 6) {
            alert('Password must be at least 6 characters long!');
            return;
        }

        // Update password
        currentUser.password = newPassword;
        const userIndex = users.findIndex(u => u.id === currentUser.id);
        if (userIndex !== -1) {
            users[userIndex].password = newPassword;
        }

        // Reset form
        this.reset();

        alert('Password changed successfully!');
        showPage('booking');
    });
}

// Add User functionality
document.getElementById('addUserForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const userId = document.getElementById('newUserId').value;
    const userName = document.getElementById('newUserName').value;
    const officerName = document.getElementById('newOfficerName').value;
    const internalNo = document.getElementById('newInternalNo').value;
    const password = document.getElementById('newPassword').value;

    // Check if user already exists
    if (users.some(u => u.id === userId)) {
        alert('User ID already exists!');
        return;
    }

    // Add new user
    users.push({
        id: userId,
        name: userName,
        officerName: officerName,
        internalNo: internalNo,
        password: password
    });

    // Reset form
    this.reset();

    alert('User added successfully!');
});

// Add some sample data for demo
function initializeSampleData() {
    // Sample bookings
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    bookings.push({
        id: 1,
        date: today.toISOString().split('T')[0],
        time: '10:00',
        room: '311',
        guest: 'John Smith',
        subject: 'Project Review Meeting',
        bookedBy: 'admin',
        officerName: 'System Administrator',
        bookedAt: new Date().toISOString()
    });

    bookings.push({
        id: 2,
        date: tomorrow.toISOString().split('T')[0],
        time: '14:30',
        room: '312',
        guest: 'Tech Solutions Inc.',
        subject: 'Vendor Presentation',
        bookedBy: 'admin',
        officerName: 'System Administrator',
        bookedAt: new Date().toISOString()
    });
}

// Initialize when page loads
window.addEventListener('DOMContentLoaded', function() {
    initializeSampleData();

    // Add keyboard shortcuts
    document.addEventListener('keydown', function(e) {
        // Escape key closes modal
        if (e.key === 'Escape' && modal.classList.contains('active')) {
            modal.classList.remove('active');
        }

        // Ctrl + Alt + R generates report
        if (e.ctrlKey && e.altKey && e.key === 'r') {
            if (document.getElementById('reportsPage').classList.contains('active')) {
                generateReportPreview();
            }
        }
    });

    // Add animation to login page
    setTimeout(() => {
        document.querySelector('.login-form').style.opacity = '1';
        document.querySelector('.login-form').style.transform = 'translateY(0)';
    }, 300);
});
