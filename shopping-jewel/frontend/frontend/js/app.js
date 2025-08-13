const API_BASE = 'http://localhost:8080/api';

async function fetchProducts(q=''){
  try {
    const url = q ? `${API_BASE}/products?q=${encodeURIComponent(q)}` : `${API_BASE}/products`;
    const res = await fetch(url);
    const data = await res.json();
    renderProducts(data);
  } catch (e){ console.error(e); }
}

async function fetchProductsByTag(tag){
  const url = `${API_BASE}/products/tag/${encodeURIComponent(tag)}`;
  const res = await fetch(url);
  const data = await res.json();
  renderProducts(data);
}

function renderProducts(products){
  const container = document.getElementById('product-list');
  if (!container) return;
  container.innerHTML = '';
  products.forEach(p=>{
    const col = document.createElement('div'); col.className='col-md-4';
    col.innerHTML = `
      <div class="card h-100">
        <img src="${p.imageUrl}" class="card-img-top">
        <div class="card-body d-flex flex-column">
          <h5 class="card-title">${p.name}</h5>
          <p class="card-text small text-muted">${p.description}</p>
          <p class="fw-bold">$${p.price}</p>
          <div class="mt-auto">
            <a class="btn btn-sm btn-outline-primary" href="product.html?id=${p.id}">View</a>
            <button class="btn btn-sm btn-primary" onclick="addToCart(${p.id},1)">Add to cart</button>
            <button class="btn btn-sm btn-outline-secondary" onclick="addToWishlist(${p.id})">♡</button>
          </div>
        </div>
        <div class="card-footer">
          ${renderTags(p.tags)}
          <span class="float-end small">⭐ ${p.ratingAvg?.toFixed(1)||0}</span>
        </div>
      </div>`;
    container.appendChild(col);
  });
}

function renderTags(csv){
  if (!csv) return '';
  return csv.split(',').map(t=>`<span class="badge bg-info text-dark tag-badge">${t}</span>`).join(' ');
}
