async function loadCart(){
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/cart`, {headers:{'Authorization':'Bearer '+token}});
  if (!res.ok){ document.getElementById('cartItems').innerHTML='Failed to load cart'; return; }
  const items = await res.json();
  const html = [];
  for (const c of items){
    const pRes = await fetch(`${API_BASE}/products/${c.productId}`);
    const p = await pRes.json();
    html.push(`<div class="border p-2 mb-2">
      <strong>${p.name}</strong> — $${p.price} x ${c.quantity}
      <button class="btn btn-sm btn-danger float-end" onclick='removeFromCart(${p.id})'>Remove</button>
    </div>`);
  }
  document.getElementById('cartItems').innerHTML = html.join('');
}

async function addToCart(productId, qty=1){
  const token = localStorage.getItem('token');
  if (!token){ alert('Login required'); location='login.html'; return; }
  const body = { productId, quantity: qty };
  const res = await fetch(`${API_BASE}/cart/add`, {method:'POST', headers:{'Content-Type':'application/json','Authorization':'Bearer '+token}, body: JSON.stringify(body)});
  if (res.ok) alert('Added to cart'); else alert('Add failed');
}

async function removeFromCart(productId){
  const token = localStorage.getItem('token');
  const body = { productId };
  const res = await fetch(`${API_BASE}/cart/remove`, {method:'POST', headers:{'Content-Type':'application/json','Authorization':'Bearer '+token}, body: JSON.stringify(body)});
  if (res.ok) { alert('Removed'); loadCart(); } else alert('Remove failed');
}
