import { useEffect, useState } from 'react'
import { api } from '../api'

export default function ProductPage(){
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/products')
        setProducts(res.data || [])
      } catch (e) {
        setError(e.message || 'Failed to load products')
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [])

  if (loading) return <p>Loading...</p>
  if (error) return <p style={{color:'red'}}>Error: {error}</p>

  return (
    <section>
      <h2>Products</h2>
      <div style={{display:'grid', gridTemplateColumns:'repeat(auto-fill, minmax(220px, 1fr))', gap:'1rem'}}>
        {products.map(p => (
          <article key={p.id} style={{border:'1px solid #eee', borderRadius:8, padding:12}}>
            <img src={p.imageUrl} alt={p.name} style={{width:'100%', height:160, objectFit:'cover'}} />
            <h3>{p.name}</h3>
            <p>{p.description}</p>
            <strong>{/* placeholder to avoid stray formatting */}</strong>
            <div>Price: {p.price}</div>
            <button>Add to Cart</button>
          </article>
        ))}
      </div>
    </section>
  )
}
