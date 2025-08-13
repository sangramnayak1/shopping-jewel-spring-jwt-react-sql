// kept minimal because sandbox-payment.html implements most logic.
// you can add helper functions here to support different gateways.

// Sandbox payment: build order from cart and call /api/orders/place
async function getCartItems(){
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/cart`, {headers:{'Authorization':'Bearer '+token}});
  if (!res.ok) return [];
  return await res.json();
}

async function placeOrderFromCart(forceFail=false){
  const token = localStorage.getItem('token');
  const cart = await getCartItems();
  if (!cart || cart.length===0){ alert('Cart empty'); return; }
  const orderItems = [];
  for (const c of cart){
    const pRes = await fetch(`${API_BASE}/products/${c.productId}`);
    const p = await pRes.json();
    orderItems.push({ product: p, quantity: c.quantity, unitPrice: p.price });
  }
  let total = orderItems.reduce((s,i)=> s + i.unitPrice * i.quantity, 0);
  if (forceFail) total = Math.round((total*100))/100 + 0.01; // make cents odd to simulate fail if backend uses that rule
  const order = { items: orderItems, totalAmount: total };
  const res = await fetch(`${API_BASE}/orders/place`, {method:'POST', headers:{'Content-Type':'application/json','Authorization':'Bearer '+token}, body: JSON.stringify(order)});
  if (res.ok) { const o = await res.json(); alert('Order status: ' + o.status); location='order-history.html'; }
  else { alert('Order failed'); }
}
