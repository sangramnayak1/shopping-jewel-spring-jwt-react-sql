import axios from 'axios'

// Detect environment
const isProd = import.meta.env.MODE === 'production'

// In Docker (production), just call relative '/api' to let Nginx proxy it
const API_BASE = isProd
  ? '/api'
  : (import.meta.env.VITE_API_BASE || 'http://localhost:8080/api')

export const api = axios.create({
  baseURL: API_BASE,
})

// Attach JWT token automatically if present
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})
