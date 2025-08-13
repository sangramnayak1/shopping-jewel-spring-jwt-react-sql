async function loadWishlist(){
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/wishlist`, {headers:{'Authorization':'Bearer '+token}});
  if (!res.ok){ document.getElementById('wishlistItems').innerHTML='Failed'; return; }
  const list = await res.json();
  const html = [];
  for (const w of list){
    const p = await (await fetch(`${API_BASE}/products/${w.productId}`)).json();
    html.push(`<div class="border p-2 mb-2"><strong>${p.name}</strong>
      <button class="btn btn-sm btn-primary float-end" onclick="addToCart(${p.id},1)">Add to cart</button>
      <button class="btn btn-sm btn-danger float-end me-2" onclick="removeFromWishlist(${p.id})">Remove</button>
    </div>`);
  }
  document.getElementById('wishlistItems').innerHTML = html.join('');
}

async function addToWishlist(productId){
  const token = localStorage.getItem('token');
  if (!token){ alert('Login required'); location='login.html'; return; }
  const res = await fetch(`${API_BASE}/wishlist/add`, {method:'POST', headers:{'Content-Type':'application/json','Authorization':'Bearer '+token}, body: JSON.stringify({productId})});
  if (res.ok) alert('Added to wishlist'); else alert('Failed');
}

async function removeFromWishlist(productId){
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/wishlist/remove`, {method:'POST', headers:{'Content-Type':'application/json','Authorization':'Bearer '+token}, body: JSON.stringify({productId})});
  if (res.ok) { alert('Removed'); loadWishlist(); } else alert('Remove failed');
}
