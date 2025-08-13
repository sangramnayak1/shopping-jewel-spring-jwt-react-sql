import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { api } from '../api'

export default function LoginPage(){
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const navigate = useNavigate()

  async function onSubmit(e){
    e.preventDefault()
    setError('')
    try {
      const res = await api.post('/auth/login', { username, password })
      if (res?.data?.token){
        localStorage.setItem('token', res.data.token)
        localStorage.setItem('username', username)
        navigate('/')
      } else {
        setError('Invalid response from server')
      }
    } catch (err){
      setError('Invalid credentials')
    }
  }

  return (
    <section>
      <h2>Login</h2>
      <form onSubmit={onSubmit} style={{display:'grid', gap:12, maxWidth:360}}>
        <input placeholder="Username" value={username} onChange={e=>setUsername(e.target.value)} required />
        <input type="password" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)} required />
        <button type="submit">Login</button>
      </form>
      {error && <p style={{color:'red'}}>{error}</p>}
      <p>No account? <Link to="/register">Register</Link></p>
    </section>
  )
}
