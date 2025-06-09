document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  const registerForm = document.getElementById("registerForm");
  const loginTab = document.getElementById("login-tab");
  const registerTab = document.getElementById("register-tab");
  const loginPage = document.getElementById("login");
  const registerPage = document.getElementById("register");

  const slideLogin = document.getElementById("slide-login");
  const slideRegister = document.getElementById("slide-register");
  const forgotPasswordLink = document.getElementById("forgotPasswordLink");

  // === Slide Toggle ===
  function showSlide(type) {
    if (type === "login") {

      slideLogin.classList.remove("active");
      slideRegister.classList.add("active");

      loginTab.classList.add("active");
      registerTab.classList.remove("active");

      loginPage.classList.add("show", "active");
      registerPage.classList.remove("show", "active");

      // Reset register form to step 1
      document.getElementById("registerStep1").style.display = "block";
      document.getElementById("registerStep2").style.display = "none";
    } else {
      slideLogin.classList.add("active");
      slideRegister.classList.remove("active");

      loginTab.classList.remove("active");
      registerTab.classList.add("active");

      loginPage.classList.remove("show", "active");
      registerPage.classList.add("show", "active");

      document.getElementById("registerStep1").style.display = "block";
      document.getElementById("registerStep2").style.display = "none";
    }
  }

  // === Login Submit ===
  /*loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = loginForm.username.value.trim();
    const password = loginForm.password.value.trim();
    const rememberMe = loginForm.rememberMe.checked;

    try {
      const res = await fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password, rememberMe }),
      });
      const result = await res.json();
      alert(result.success ? "Login successful!" : "Login failed: " + result.message);
    } catch (err) {
      console.error(err);
      alert("Login error");
    }
  });*/

  // === Register Submit ===
  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const fullName = registerForm.name.value.trim();
    const email = registerForm.email.value.trim();
    const phone = registerForm.phone.value.trim();
    const username = document.getElementById("registerUsername").value.trim();
    const password = document.getElementById("registerPassword").value.trim();
    const confirmPassword = document.getElementById("registerConfirmPassword").value.trim();

    if (password !== confirmPassword) {
      alert("Passwords do not match.");
      return;
    }

    const payload = {
      customer: { fullName, email, phone },
      user: { username, password },
    };

    try {
      const res = await fetch("/api/register-customer", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      const result = await res.json();
      alert(result.success ? "Registered successfully! Please login." : "Error: " + result.message);

      if (result.success) {
        const tab = new bootstrap.Tab(loginTab);
        tab.show();
        showSlide("login");
      }
    } catch (err) {
      console.error(""+err);
      alert("Registration error");
    }
  });

  // === Forgot Password ===
  forgotPasswordLink.addEventListener("click", (e) => {
    e.preventDefault();
    const email = prompt("Enter your email:");
    if (!email) return;

    fetch("/forgot-password", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email }),
    })
      .then((res) => res.json())
      .then((data) => {
        alert(data.success ? "Check console for reset link." : "Error: " + data.message);
        if (data.success) console.log("Reset link:", data.data);
      })
      .catch((err) => {
        console.error(err);
        alert("Request failed.");
      });
  });

  // === Step Switching ===
  window.nextStep = function () {
    document.getElementById("registerStep1").style.display = "none";
    document.getElementById("registerStep2").style.display = "block";
  };

  window.previousStep = function () {
    document.getElementById("registerStep2").style.display = "none";
    document.getElementById("registerStep1").style.display = "block";
  };

  // === Tabs Event Binding ===
  loginTab.addEventListener("click", () => showSlide("login"));
  registerTab.addEventListener("click", () => showSlide("register"));

  // === Init (optional based on current tab state) ===
  if (registerTab.classList.contains("active")) {
    showSlide("register");
  } else {
    showSlide("login");
  }
});
