// Admin helpers: load orders, subscribers, feedback; refund an order
async function loadOrders(){
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/admin/orders`, {headers:{'Authorization':'Bearer '+token}});
  if (!res.ok){ document.getElementById('adminArea').innerText='Failed to load orders'; return; }
  const list = await res.json();
  document.getElementById('adminArea').innerHTML = list.map(o=>`
    <div class="border p-2 mb-2">
      <strong>Order ${o.id}</strong> - ${o.status} - $${o.totalAmount}
      <button class="btn btn-sm btn-danger float-end" onclick="refundOrder(${o.id})">Refund</button>
    </div>`).join('');
}

async function refundOrder(id){
  if (!confirm('Refund this order?')) return;
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/admin/orders/${id}/refund`, {method:'POST', headers:{'Authorization':'Bearer '+token}});
  if (res.ok) { alert('Refund processed'); loadOrders(); } else { const j = await res.json(); alert(j.error || 'Failed'); }
}

async function loadSubscribers(){
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/admin/subscribers`, {headers:{'Authorization':'Bearer '+token}});
  if (!res.ok){ document.getElementById('adminArea').innerText='Failed to load'; return; }
  const list = await res.json();
  document.getElementById('adminArea').innerHTML = '<h5>Subscribers</h5>' + list.map(s=>`<div class="border p-2 mb-1">${s.email} — ${s.subscribedAt}</div>`).join('');
}

async function loadFeedback(){
  const token = localStorage.getItem('token');
  const res = await fetch(`${API_BASE}/feedback`, {headers:{'Authorization':'Bearer '+token}});
  if (!res.ok){ document.getElementById('adminArea').innerText='Failed to load feedback'; return; }
  const list = await res.json();
  document.getElementById('adminArea').innerHTML = '<h5>Feedback</h5>' + list.map(f=>`<div class="border p-2 mb-1">${f.user ? f.user.username : 'Anon'} — ${f.rating || ''}<br>${f.message}</div>`).join('');
}
