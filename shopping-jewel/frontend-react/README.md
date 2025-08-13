# Shopping Jewel — React Skeleton

## Quick start
```bash
# 1) go into this folder
cd shopping-jewel-frontend

# 2) install deps
npm install

# 3) start dev server (http://localhost:3000)
npm run dev
```

### Configure backend URL
Create a `.env` file in this folder if your API base is not `http://localhost:8080/api`:

```
VITE_API_BASE=http://localhost:8080/api
```

### What’s included
- React Router routes for all pages
- JWT auth stored in localStorage
- axios instance that adds `Authorization: Bearer <token>` automatically
- Protected routes (login required)
- Placeholders for: Admin, Cart, Feedback, Orders, Profile, Payment Sandbox, Wallet, Wishlist, Home, Products
