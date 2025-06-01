const tg = window.Telegram.WebApp;
tg.expand();

const params = new URLSearchParams(window.location.search);
const cartData = params.get("data");

const cartItems = cartData ? JSON.parse(decodeURIComponent(cartData)) : {};

const cartDiv = document.getElementById("cart-items");
const totalItemsSpan = document.getElementById("total-items");
const totalPriceSpan = document.getElementById("total-price");

let totalItems = 0;
let totalPrice = 0;

Object.values(cartItems).forEach(({ product, count }) => {
  const itemDiv = document.createElement("div");
  itemDiv.className = "item";

  const name = document.createElement("div");
  name.innerText = `📦 ${product.name}`;

  const quantity = document.createElement("div");
  quantity.innerText = `Count: ${count}`;

  const price = document.createElement("div");
  const itemTotal = product.price * count;
  price.innerText = `Price: ${itemTotal.toLocaleString()} UZS`;

  itemDiv.appendChild(name);
  itemDiv.appendChild(quantity);
  itemDiv.appendChild(price);
  cartDiv.appendChild(itemDiv);

  totalItems += count;
  totalPrice += itemTotal;
});

totalItemsSpan.innerText = `Selected: ${totalItems}`;
totalPriceSpan.innerText = `Total: ${totalPrice.toLocaleString()} UZS`;

document.getElementById("confirm-btn").addEventListener("click", () => {
  tg.sendData(JSON.stringify({
    type: "checkout",
    items: cartItems,
    totalItems,
    totalPrice
  }));
  tg.close();
});
