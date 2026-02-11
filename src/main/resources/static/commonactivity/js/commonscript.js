
    var successMsg = /*[[${successMessage}]]*/ null;
    var errorMsg = /*[[${errorMessage}]]*/ null;

    if(successMsg) {
        Swal.fire("Success", successMsg, "success");
    }
    if(errorMsg) {
        Swal.fire("Error", errorMsg, "error");
    }
    /*]]>*/

    // Simple Client side validation
    function validatePasswordMatch() {
        var newPass = document.getElementById("newPass").value;
        var confirmPass = document.getElementById("confirmPass").value;
        
        if (newPass !== confirmPass) {
            Swal.fire("Validation Error", "New Password and Confirm Password do not match!", "warning");
            return false;
        }
        return true;
    }

    // Sidebar Logic
    document.querySelectorAll('.menu-title').forEach(title => {
        title.addEventListener('click', function() {
            const parent = this.parentElement;
            parent.classList.toggle('open');
            document.querySelectorAll('.menu-item').forEach(item => {
                if (item !== parent) {
                    item.classList.remove('open');
                }
            });
        });
    });
