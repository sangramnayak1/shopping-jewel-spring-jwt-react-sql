import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { api } from '../api'

export default function RegisterPage(){
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const navigate = useNavigate()

  async function onSubmit(e){
    e.preventDefault()
    setError('')
    try {
      const res = await api.post('/auth/register', { username, email, password })
      if (res?.data?.token){
        localStorage.setItem('token', res.data.token)
        localStorage.setItem('username', username)
        navigate('/')
      } else {
        navigate('/login')
      }
    } catch (err){
      setError('Registration failed')
    }
  }

  return (
    <section>
      <h2>Register</h2>
      <form onSubmit={onSubmit} style={{display:'grid', gap:12, maxWidth:360}}>
        <input placeholder="Username" value={username} onChange={e=>setUsername(e.target.value)} required />
        <input type="email" placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} required />
        <input type="password" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)} required />
        <button type="submit">Create Account</button>
      </form>
      {error && <p style={{color:'red'}}>{error}</p>}
      <p>Have an account? <Link to="/login">Login</Link></p>
    </section>
  )
}
