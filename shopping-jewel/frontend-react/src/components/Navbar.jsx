import { Link, NavLink, useNavigate } from 'react-router-dom'

export default function Navbar(){
  const navigate = useNavigate()
  const username = localStorage.getItem('username')

  function logout(){
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    navigate('/login')
  }

  const linkCls = ({isActive}) => (isActive ? 'active' : '')

  return (
    <header className="nav">
      <h1><Link to="/">Shopping Jewel</Link></h1>
      <nav>
        <NavLink to="/" className={linkCls}>Home</NavLink>
        <NavLink to="/products" className={linkCls}>Products</NavLink>
        <NavLink to="/cart" className={linkCls}>Cart</NavLink>
        <NavLink to="/orders" className={linkCls}>Orders</NavLink>
        <NavLink to="/wishlist" className={linkCls}>Wishlist</NavLink>
        <NavLink to="/wallet" className={linkCls}>Wallet</NavLink>
        <NavLink to="/feedback" className={linkCls}>Feedback</NavLink>
        <NavLink to="/admin" className={linkCls}>Admin</NavLink>
        {username ? (
          <>
            <NavLink to="/profile" className={linkCls}>{username}</NavLink>
            <button onClick={logout}>Logout</button>
          </>
        ) : (
          <>
            <NavLink to="/login" className={linkCls}>Login</NavLink>
            <NavLink to="/register" className={linkCls}>Register</NavLink>
          </>
        )}
      </nav>
      <style>{`
        .nav { display:flex; align-items:center; gap:1rem; padding:1rem; border-bottom:1px solid #eee; }
        .nav nav { display:flex; gap:.75rem; align-items:center; flex-wrap:wrap; }
        a { text-decoration:none; }
        .active { font-weight:700; text-decoration:underline; }
        button { padding:.4rem .7rem; }
      `}</style>
    </header>
  )
}
