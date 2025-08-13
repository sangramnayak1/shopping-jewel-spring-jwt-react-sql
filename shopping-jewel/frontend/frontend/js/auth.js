//const API_BASE = 'http://backend:8080/api';

// Detect if running inside Docker container by checking hostname
const isDocker = window.location.hostname !== 'localhost' && window.location.hostname !== '127.0.0.1';

// If in Docker, use container service name
const API_BASE = isDocker
  ? 'http://shopping-backend:8080/api' // Docker network URL
  : 'http://localhost:8080/api';       // Local dev URL

async function doLogin(username, password) {
  try {
    const res = await fetch(`${API_BASE}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });

    if (!res.ok) return false;

    const j = await res.json();
    if (j.token) {
      localStorage.setItem('token', j.token);
      localStorage.setItem('username', username);
      return true;
    }
    return false;
  } catch (e) {
    console.error('Login failed:', e);
    return false;
  }
}

function getAuthHeaders() {
  const token = localStorage.getItem('token');
  return token ? { 'Authorization': `Bearer ${token}` } : {};
}

async function doRegister(username, email, password){
  try {
    const res = await fetch(`${API_BASE}/auth/register`, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({username,email,password})});
    if (!res.ok) { const t = await res.text(); throw new Error(t); }
    const j = await res.json();
    if (j.token){ localStorage.setItem('token', j.token); localStorage.setItem('username', j.username || username); return true; }
    return false;
  } catch (e){ console.error(e); return false; }
}

// OTP: request and verify helpers
async function requestOtp(email){
  try {
    const res = await fetch(`${API_BASE}/auth/request-otp`, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({email})});
    const j = await res.json(); return j;
  } catch (e){ console.error(e); return null; }
}

async function requestVerifyOtp(email, code){
  try {
    const res = await fetch(`${API_BASE}/auth/verify-otp`, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({email, code})});
    return res.ok;
  } catch (e){ console.error(e); return false; }
}

function isLoggedIn(){ return !!localStorage.getItem('token'); }

function logout(){ localStorage.removeItem('token'); localStorage.removeItem('username'); }
