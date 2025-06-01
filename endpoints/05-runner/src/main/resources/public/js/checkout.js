const tg = window.Telegram.WebApp;
tg.expand();

// ⏪ Back Button
tg.BackButton.show();
tg.onEvent("backButtonClicked", () => {
  window.history.back();
});

// 🗓 Sana
const now = new Date();
document.getElementById("order-date").innerText = now.toLocaleString("uz-UZ");

// 🔢 Ma'lumotlar
const params = new URLSearchParams(window.location.search);
const cartData = params.get("data");
const cartItems = cartData ? JSON.parse(decodeURIComponent(cartData)) : {};

const itemsList = document.getElementById("items-list");
const totalItemsSpan = document.getElementById("total-items");
const totalPriceSpan = document.getElementById("total-price");

let totalItems = 0;
let totalPrice = 0;

Object.values(cartItems).forEach(({ product, count }) => {
  const itemDiv = document.createElement("div");
  itemDiv.className = "item";

  const name = document.createElement("div");
  name.className = "item-name";
  name.innerText = `📦 ${product.name}`;

  const detail = document.createElement("div");
  detail.className = "item-detail";
  detail.innerText = `Soni: ${count} dona | Narx: ${(product.price * count).toLocaleString()} so'm`;

  itemDiv.appendChild(name);
  itemDiv.appendChild(detail);
  itemsList.appendChild(itemDiv);

  totalItems += count;
  totalPrice += product.price * count;
});

// 🟩 Asosiy tugma
tg.MainButton.setText(`To‘lash: ${totalPrice.toLocaleString()} UZS`);
tg.MainButton.show();

tg.onEvent("mainButtonClicked", () => {
  tg.sendData(JSON.stringify({
    type: "checkout",
    items: cartItems,
    totalItems,
    totalPrice
  }));
  tg.close();
});
